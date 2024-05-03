// React
import { useEffect, useState, useCallback } from "react";
import Router, { useRouter } from "next/router";
import { useFetchProject } from "../../lib/service/project/ProjectResource";

// Nextjs
import { NextPage } from "next";
import Head from "next/head";

// Toastify
import { toast } from "react-toastify";

// React Icons
import { FiEye, FiFeather, FiGlobe, FiLock, FiMail, FiSettings, FiSliders, FiUser } from "react-icons/fi";

// Services
import useAuthenticated from "../../lib/hook/useAuthenticated";
import useStorage from "../../lib/hook/useStorage";
import { useFetchPersonalData, updateUser as updateUserApi } from "../../lib/service/user/UserResource";
import { useFetchAllLanguages } from "../../lib/service/language/LanguageResource";
import { useFetchAllVisibility } from "../../lib/service/visibility/VisibilityResource";
import { logoutAndRedirectToLogin } from "../../lib/service/auth/AuthenticationController";

// Models
import UserData from "../../lib/model/user/model/UserData";
import Language from "../../lib/model/language/model/Language";
import Visibility from "../../lib/model/visibility/model/Visibility";
import UpdateUserCommand from "../../lib/model/user/command/UpdateUserCommand";

// Layout
import Layout from "../../components/generic/layout/layout";
import SingleContentLayout from "../../components/generic/layout/singlecontentlayout";

// Components
import DropdownSelect from "../../components/generic/dropdown/dropdownselect";
import PasswordConfirmationModal from "../../components/generic/modal/passwordconfirmationmodal";
import FullLoadingPage from "../../components/pages/fullloadingpage";
import { useFetchAllUsecase } from "../../lib/service/usecase/UsecaseResource";
import Usecase from "../../lib/model/usecase/model/Usecase";
import HelpButton from "../../components/generic/button/helpbutton";


const SettingIndex: NextPage = () => {

    // fetch data
    const authenticated = useAuthenticated();
    const storage = useStorage();

    const personalData = useFetchPersonalData();
    const languages = useFetchAllLanguages();
    const visibilities = useFetchAllVisibility();
    const usecases = useFetchAllUsecase();

    languages.languages.sort((a, b) => a.getName().localeCompare(b.getName()));
    visibilities.visibilities.sort((a, b) => a.getName().localeCompare(b.getName()));





    // update user state
    const [updateUser, setUpdateUser] = useState({
        username: "",
        email: "",

        password: "",
        passwordConfirm: "",

        languages: Array<Language>(),
        visibility: null as unknown as Visibility,
        usecase: null as unknown as Usecase,

        description: "",
    });

    // Password confirmation modal, state and callback function
    const [passwordModal, setPasswordModal] = useState({
        show: false,
        confirmPassword: "",
    });

    const handleConfirm = () => {

        if (personalData.user == null) {
            toast.warning("This should not happen. Please contact the developer");
            return;
        }

        const updateUserCommand: UpdateUserCommand | null = verifyUpdate(personalData.user, passwordModal.confirmPassword, updateUser);
        setPasswordModal({ show: false, confirmPassword: "" });

        if (updateUserCommand == null) {
            return;
        }

        updateUserApi(updateUserCommand, storage.get).then(() => {
            toast.success("User updated successfully. Please login again to see the changes.");
            // clear the state
            setUpdateUser({
                username: "",
                email: "",
                password: "",
                passwordConfirm: "",
                languages: Array<Language>(),
                visibility: null as unknown as Visibility,
                usecase: null as unknown as Usecase,
                description: "",
            });
        }).catch(error => {
            if (error?.response?.status === 500) {
                toast.error("Error while updating data: " + error.response.data.message + "!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        }).finally(() => {
            logoutAndRedirectToLogin(storage.remove);
        });
    }

    const handleCancel = () => {
        setPasswordModal({ show: false, confirmPassword: "" });
        toast.info("Canceled User edit");
    }

    // callback function to set the state of the update user language and visibility
    const setCorrectState = useCallback(() => {
        if (!personalData.isLoading && !personalData.isError && personalData.user != null) {
            if (updateUser.languages.length === 0) {
                setUpdateUser({
                    ...updateUser,
                    languages: personalData.user.getLanguages() || [],
                })
            }

            if (updateUser.visibility == null) {
                setUpdateUser({
                    ...updateUser,
                    visibility: personalData.user.getVisibility() || null,
                })
            }

            if (updateUser.usecase == null) {
                setUpdateUser({
                    ...updateUser,
                    usecase: personalData.user.getUsecase() || null,
                })
            }
        }
    }, [personalData, updateUser]);

    // effect
    useEffect(() => {
        if (authenticated.isReady && !authenticated.isAuthenticated) {
            toast.info("Session expired, please login again.");
            Router.push("/");
        }

        if (personalData.isError) {
            toast.error("Error while fetching personal data!");
            Router.push("/dashboard");
        }

        setCorrectState();

    }, [authenticated, personalData.isError, setCorrectState]);

    if (personalData.isLoading || languages.isLoading || visibilities.isLoading || usecases.isLoading) {
        return <FullLoadingPage headtitle="PhiTag: Settings" />;
    }

    return (
        <Layout>
            <Head>
                <title>PhiTag: Settings</title>
            </Head>

            <SingleContentLayout>
                <div className="flex flex-row justify-between items-center">
                    <h1 className="font-dm-mono-medium font-black text-3xl">
                        Personal Settings
                    </h1>

                    <HelpButton
                        title="Help: Settings"
                        tooltip="Help: Settings"
                        text="Here you can change your personal settings. You can change your username, email, password, language, visibility and usecase. You can also change your description. 
                    After saving your change, you will be logged out and have to login again."
                        reference=""
                        linkage={false}
                    />
                </div>


                {/* Account */}
                <div className="font-dm-mono-medium mt-6">
                    <h2 className="font-black text-2xl">
                        Account Managment
                    </h2>

                    <div className="flex flex-col xl:flex-row justify-between my-6 space-y-4 xl:space-y-0 xl:space-x-8">

                        <div className="xl:w-1/2 flex flex-col grow items-left xl:mr-4">
                            <div className="font-bold text-lg">
                                Username
                            </div>
                            <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                                <FiUser className="basic-svg" />
                                <input
                                    id="username"
                                    name="username"
                                    className="pl-3 flex flex-auto outline-none border-none placeholder:text-base16-gray-900"
                                    type={"text"}
                                    placeholder={personalData.user.getDisplayname()}
                                    value={updateUser.username}
                                    onChange={(e: any) => setUpdateUser({
                                        ...updateUser,
                                        username: e.target.value,
                                    })}
                                />
                            </div>
                        </div>


                        <div className="xl:w-1/2 flex flex-col grow items-left xl:ml-4">
                            <div className="font-bold text-lg">
                                Email
                            </div>
                            <div className="flex flex-auto items-center border-b-2 py-2 px-3">
                                <FiMail className="basic-svg" />
                                <input
                                    id="email"
                                    name="email"
                                    className="pl-3 flex flex-auto outline-none border-none placeholder:text-base16-gray-900"
                                    type={"text"}
                                    placeholder={personalData.user.getEmail()}
                                    value={updateUser.email}
                                    onChange={(e: any) => setUpdateUser({
                                        ...updateUser,
                                        email: e.target.value,
                                    })} />
                            </div>
                        </div>


                    </div>


                    <div className="flex flex-col xl:flex-row justify-between my-6 space-y-4 xl:space-y-0 xl:space-x-8">

                        <div className="xl:w-1/2 flex flex-col grow items-left xl:mr-4">
                            <div className="font-bold text-lg">
                                Visibility
                            </div>
                            <div className="flex flex-auto items-center border-b-2 py-2 px-3">
                                <DropdownSelect
                                    icon={<FiEye className="basic-svg" />}
                                    items={visibilities.visibilities}
                                    selected={updateUser.visibility ? [updateUser.visibility] : []}
                                    onSelectFunction={(item: Visibility) => setUpdateUser({
                                        ...updateUser,
                                        visibility: item,
                                    })}
                                    message={updateUser.visibility ? updateUser.visibility.getVisiblename() : ""} />

                            </div>
                        </div>

                        <div className="xl:w-1/2 flex flex-col grow items-left xl:ml-4">
                            <div className="font-bold text-lg">
                                Usecase
                            </div>
                            <div className="flex flex-auto items-center border-b-2 py-2 px-3">
                                <DropdownSelect
                                    icon={<FiSliders className="basic-svg" />}
                                    items={usecases.usecases}
                                    selected={updateUser.usecase ? [updateUser.usecase] : []}
                                    onSelectFunction={(item: Usecase) => setUpdateUser({
                                        ...updateUser,
                                        usecase: item,
                                    })}
                                    message={updateUser.usecase ? updateUser.usecase.getVisiblename() : ""} />
                            </div>
                        </div>
                    </div>


                    <div className="flex flex-col xl:flex-row justify-between my-6 space-y-4 xl:space-y-0 xl:space-x-8">

                        <div className="xl:w-1/2 flex flex-col grow items-left xl:mr-4">
                            <div className="font-bold text-lg">
                                New Password
                            </div>
                            <div className="flex flex-auto items-center border-b-2 py-2 px-3">
                                <FiLock className="basic-svg" />
                                <input
                                    id="password"
                                    name="password"
                                    className="pl-3 flex flex-auto outline-none border-none "
                                    type={"password"}
                                    placeholder="New Password"
                                    value={updateUser.password}
                                    onChange={(e: any) => setUpdateUser({
                                        ...updateUser,
                                        password: e.target.value,
                                    })} />
                            </div>
                        </div>
                        <div className="xl:w-1/2 flex flex-col grow items-left xl:ml-4">
                            <div className="flex flex-auto items-center border-b-2 py-2 px-3">
                                <FiLock className="basic-svg" />
                                <input
                                    id="passwordConfirmation"
                                    name="password"
                                    className="pl-3 flex flex-auto outline-none border-none "
                                    placeholder="Confirm Password"
                                    type={"password"}
                                    value={updateUser.passwordConfirm}
                                    onChange={(e: any) => setUpdateUser({
                                        ...updateUser,
                                        passwordConfirm: e.target.value,
                                    })} />
                            </div>
                        </div>
                    </div>
                </div>

                <div className="mt-8 font-dm-mono-medium">
                    <h2 className="font-black text-2xl">
                        About Me
                    </h2>

                    <div className="flex flex-col items-left my-6">
                        <div className="font-bold text-lg">
                            Languages
                        </div>
                        <div className="flex items-center border-b-2 py-2 px-3 mt-2">
                            <DropdownSelect
                                icon={<FiGlobe className="basic-svg" />}
                                items={languages.languages}
                                selected={updateUser.languages}
                                onSelectFunction={(item: Language) => setUpdateUser({
                                    ...updateUser,
                                    languages: calculateNewLanguageArray(item, updateUser.languages),
                                })}
                                message={renderLanguageSelect(updateUser.languages, (language: Language) => {
                                    calculateNewLanguageArray(language, updateUser.languages);
                                })} />
                                
                        </div>
                    </div>


                    <div className="flex flex-col items-left my-6">
                        <div className="font-bold text-lg">
                            Description
                        </div>
                        <div className="h-32 flex items-start border-l-2 py-2 px-3 mt-2">
                            <FiFeather className='basic-svg' />
                            <textarea
                                id="description"
                                name="description"
                                className="w-full h-full resize-none pl-3 flex flex-auto outline-none border-none"
                                placeholder={personalData.user.getDescription()}
                                value={updateUser.description}
                                onChange={(e: any) => setUpdateUser({
                                    ...updateUser,
                                    description: e.target.value,
                                })} />
                        </div>
                    </div>
                </div>

                <div className="mt-8  font-dm-mono-medium">
                    <button
                        type="button"
                        className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 "
                        onClick={() => {
                            setPasswordModal({
                                ...passwordModal,
                                show: true,
                            })
                        }}>Confirm Changes</button>
                </div>

                <PasswordConfirmationModal
                    isOpen={passwordModal.show}
                    onConfirm={handleConfirm}
                    onCancel={handleCancel}

                    password={passwordModal.confirmPassword}
                    setPassword={(password: string) => setPasswordModal({ show: passwordModal.show, confirmPassword: password })}
                />

            </SingleContentLayout>
        </Layout >
    );


}

export default SettingIndex;

function renderLanguageSelect(selected: Language[], removeItem: Function) {
    if (selected.length === 0) {
        return "No language selected";
    }

    return (
        <div className="flex flex-row ">
            {selected.sort((a, b) => a.getName().localeCompare(b.getName())).map((language: Language) => {
                return (
                    <div key={language.getName()} className="flex px-2 mr-2 border-2 rounded-full border-base16-gray-900 text-sm" onClick={() => removeItem(language)}>
                        {language.getVisiblename()}
                    </div>
                )
            })}
        </div>
    )
}

function calculateNewLanguageArray(language: Language, selectedLanguages: Array<Language>) {
    const filtered = selectedLanguages.filter(l => l.getName() !== language.getName());

    if (filtered.length === selectedLanguages.length) {
        return [...selectedLanguages, language];
    }
    return filtered;
}

function verifyUpdate(user: UserData, currentPassword: string, updateUser: {
    username: string,
    email: string,

    password: string,
    passwordConfirm: string,

    languages: Array<Language>,
    visibility: Visibility,
    usecase: Usecase,

    description: string,
}): UpdateUserCommand | null {
    if (updateUser.username === user.getUsername()) {
        // Cannot change username to the same value
        toast.warning("Cannot change username to the same value");
        return null;
    }

    if (updateUser.email === user.getEmail()) {
        // Cannpt change email to the same value
        toast.warning("Cannot change email to the same value");
        return null;
    }

    if (updateUser.languages.length === 0) {
        // Cannot change languages to none
        toast.warning("Cannot change languages to none");
        return null;
    }

    if (updateUser.password !== updateUser.passwordConfirm) {
        // Passwords do not match
        toast.warning("Passwords do not match");
        return null;
    }


    return new UpdateUserCommand(
        updateUser.username,
        updateUser.email,
        updateUser.password,
        updateUser.visibility.getName(),
        updateUser.usecase.getName(),
        updateUser.languages.map(l => l.getName()),
        updateUser.description,
        currentPassword);
}