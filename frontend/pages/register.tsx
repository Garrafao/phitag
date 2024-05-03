// React Modules
import { useEffect, useState } from "react";

// Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router from 'next/router';
import Link from "next/link";

// Toast
import { toast } from "react-toastify";

// Custom Controllers
import { useFetchAllLanguages } from "../lib/service/language/LanguageResource";
import { createUser } from "../lib/service/user/UserResource";


// Custom Models
import Language from "../lib/model/language/model/Language";
import CreateUserCommand from "../lib/model/user/command/CreateUserCommand";

// React Icons
import { FiLock, FiUser, FiMail, FiGlobe, FiSliders } from "react-icons/fi";

// Custom Components
import BasicCheckbox from "../components/generic/checkbox/basiccheckbox";

// Layout
import BasicLayout from "../components/generic/layout/basiclayout";
import CenteredLayout from "../components/generic/layout/centeredlayout";
import useAuthenticated from "../lib/hook/useAuthenticated";
import BasicDropdownSelect from "../components/generic/dropdown/basicdropdownselect";
import { useFetchAllUsecase } from "../lib/service/usecase/UsecaseResource";
import Usecase from "../lib/model/usecase/model/Usecase";

const Register: NextPage = () => {

    // Hooks & Fetching
    const authenticated = useAuthenticated();
    const language = useFetchAllLanguages();
    const usecase = useFetchAllUsecase();
    language.languages.sort((a, b) => a.getName().localeCompare(b.getName()));

    // Register State and Handlers
    const [registerState, setRegisterState] = useState({
        username: "",
        email: "",
        password: "",
        passwordConfirm: "",
        language: Array<Language>(),
        usecase: null as unknown as Usecase,
        terms: false,
        age: false
    });

    const handleLanguageSelect = (language: Language) => {
        setRegisterState({
            ...registerState,
            language: calculateNewLanguageArray(language, registerState.language)
        });
    }

    const handleSignUp = () => {
        const user: CreateUserCommand | null = verifySignUp(registerState);
        if (user == null) return;

        createUser(user).then(() => {
            toast.success("Successfully registered");
            Router.push("/login");
        }).catch(error => {
            if (error?.response?.status === 500) {
                toast.error("Error while registering: " + error.response.data.message + "!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        });

    }

    // Effects
    useEffect(() => {
        if (authenticated.isReady && authenticated.isAuthenticated) {
            toast.info("You are already logged in!");
            Router.push("/dashboard");
        }

        if (language.isError) {
            toast.warning("Please try again later.");
        }
    }, [authenticated, language.isError]);

    // Page
    return (
        <BasicLayout>

            <Head>
                <title>PhiTag: Register</title>
            </Head>

            <CenteredLayout>
                <div className="flex justify-around items-center">
                    <form className="shadow-md">
                        <div className="my-8 mx-8 font-dm-mono-bolf font-bold">
                            <h1 className="font-bold text-xl">
                                Sign Up to the PhiTag-System!
                            </h1>

                            <div className="flex items-center border-b-2 py-2 px-3 my-6">
                                <FiUser className="basic-svg" />

                                <input
                                    id="username"
                                    name="username"
                                    className="pl-3 flex flex-auto outline-none border-none "
                                    type={"text"}
                                    placeholder="Username"
                                    value={registerState.username}
                                    onChange={(e: any) => setRegisterState({
                                        ...registerState,
                                        username: e.target.value
                                    })} />
                            </div>

                            <div className="flex items-center border-b-2 py-2 px-3 my-6">
                                <FiMail className="basic-svg" />
                                <input
                                    id="email"
                                    name="email"
                                    className="pl-3 flex flex-auto outline-none border-none "
                                    type={"text"}
                                    placeholder="Email Address"
                                    value={registerState.email}
                                    onChange={(e: any) => setRegisterState({
                                        ...registerState,
                                        email: e.target.value
                                    })} />
                            </div>

                            <div className="flex items-center border-b-2 py-2 px-3 my-6">
                                <FiLock className="basic-svg" />
                                <input
                                    id="password"
                                    name="password"
                                    className="pl-3 flex flex-auto outline-none border-none "
                                    type={"password"}
                                    placeholder="Password"
                                    value={registerState.password}
                                    onChange={(e: any) => setRegisterState({
                                        ...registerState,
                                        password: e.target.value
                                    })} />
                            </div>

                            <div className="flex items-center border-b-2 py-2 px-3 my-6">
                                <FiLock className="basic-svg" />
                                <input
                                    id="passwordConfirmation"
                                    name="passwordConfirmation"
                                    className="pl-3 flex flex-auto outline-none border-none "
                                    type={"password"}
                                    placeholder="Confirm Password"
                                    value={registerState.passwordConfirm}
                                    onChange={(e: any) => setRegisterState({
                                        ...registerState,
                                        passwordConfirm: e.target.value
                                    })} />
                            </div>

                            <div className="flex w-full items-center border-b-2 py-2 px-3 my-6">
                                <BasicDropdownSelect
                                    icon={<FiGlobe className="basic-svg" />}
                                    items={language.languages}
                                    selected={registerState.language}
                                    onSelectFunction={handleLanguageSelect}
                                    message={`${registerState.language.length} Languages selected`} />
                            </div>

                            <div className="flex w-full items-center border-b-2 py-2 px-3 my-6">
                                <BasicDropdownSelect
                                    icon={<FiSliders className="basic-svg" />}
                                    items={usecase.usecases}
                                    selected={registerState.usecase ? [registerState.usecase] : []}
                                    onSelectFunction={(usecase: Usecase) => setRegisterState({
                                        ...registerState,
                                        usecase: usecase
                                    })}
                                    message={registerState.usecase ? `${registerState.usecase.getVisiblename()}` : "No use case selected"} />
                            </div>

                            <div className="flex w-full items-center px-3 my-6">
                                <BasicCheckbox
                                    selected={registerState.terms}
                                    description={privacypolicyLink()}
                                    onClick={() => setRegisterState({
                                        ...registerState,
                                        terms: !registerState.terms
                                    })} />
                            </div>

                            <div className="flex w-full items-center px-3 my-6">
                                <BasicCheckbox
                                    selected={registerState.age}
                                    description={"I am at least 18 years old"}
                                    onClick={() => setRegisterState({
                                        ...registerState,
                                        age: !registerState.age
                                    })} />
                            </div>
                            <button type="button" className="block w-full mt-8 py-2 font-dm-mono-bolf bg-uni-corporate-mittelblau text-white " onClick={() => handleSignUp()}>Register</button>

                        </div>
                    </form>
                </div>
            </CenteredLayout>

        </BasicLayout>

    );

};

export default Register;

function verifySignUp(registerState: {
    username: string;
    email: string;
    password: string;
    passwordConfirm: string;
    language: Language[];
    usecase: Usecase;
    terms: boolean;
    age: boolean;
}): null | CreateUserCommand {
    if (!registerState.username || !registerState.email || !registerState.password || !registerState.passwordConfirm) {
        toast.error("Please fill out all the fields");
        return null;
    }

    if (!registerState.age || !registerState.terms) {
        toast.error("Please accept the privacy policy and the legal age confirmation");
        return null;
    }

    if (registerState.language.length == 0) {
        toast.error("Please select at least one language");
        return null;
    }

    if (!registerState.usecase) {
        toast.error("Please select a usecase");
        return null;
    }

    if (registerState.password !== registerState.passwordConfirm) {
        toast.error("Passwords do not match");
        return null;
    }

    return new CreateUserCommand(
        registerState.username,
        registerState.email,
        registerState.password,
        registerState.usecase.getName(),
        registerState.language.map(l => l.getName()),
        registerState.terms,
        registerState.age
    );
}

function calculateNewLanguageArray(language: Language, selectedLanguages: Array<Language>) {
    const filtered = selectedLanguages.filter(l => l.getName() !== language.getName());

    if (filtered.length === selectedLanguages.length) {
        return [...selectedLanguages, language];
    }
    return filtered;
}

function privacypolicyLink() {
    return (
        <div>
            I accept the &thinsp;
            <Link href="/privacy-policy">
                <a className="underline">
                    Privacy Policy
                </a>
            </Link>
        </div>
    );
}
