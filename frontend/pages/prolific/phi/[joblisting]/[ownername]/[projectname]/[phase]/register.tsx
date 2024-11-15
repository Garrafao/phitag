// React Modules
import { useEffect, useState } from "react";


// Toast
import { toast } from "react-toastify";


// Next Modules
import { NextPage } from "next";
import Head from "next/head";
import Router, { useRouter } from 'next/router';
import Link from "next/link";
// Custom Controllers
import { useFetchAllLanguages } from "../../../../../../../lib/service/language/LanguageResource";
import { createProlificUser, createUser } from "../../../../../../../lib/service/user/UserResource";

//Resources
import { joinJoblisting } from "../../../../../../../lib/service/joblisting/JoblistingResource";

// Custom Models
import Language from "../../../../../../../lib/model/language/model/Language";
import JoinJoblistingCommand from "../../../../../../../lib/model/joblisting/command/JoinJoblistingCommand";

// React Icons
import { FiLock, FiUser, FiMail, FiGlobe, FiSliders } from "react-icons/fi";

// Custom Components
import BasicCheckbox from "../../../../../../../components/generic/checkbox/basiccheckbox";
import useStorage from "../../../../../../../lib/hook/useStorage";

// Layout
import BasicLayout from "../../../../../../../components/generic/layout/basiclayout";
import CenteredLayout from "../../../../../../../components/generic/layout/centeredlayout";
import { useFetchAllUsecase } from "../../../../../../../lib/service/usecase/UsecaseResource";
import Usecase from "../../../../../../../lib/model/usecase/model/Usecase";
import BasicDropdownSelect from "../../../../../../../components/generic/dropdown/basicdropdownselect";
import { login } from "../../../../../../../lib/service/auth/AuthenticationController";
import CreateProlificUserCommand from "../../../../../../../lib/model/user/command/CreateProlificUserCommnad";

const Index = () => {
  const storage = useStorage();
  const language = useFetchAllLanguages();
  const usecase = useFetchAllUsecase();
  language.languages.sort((a, b) => a.getName().localeCompare(b.getName()));
  const router = useRouter();
  const { joblisting: joblistingname, ownername: ownername, projectname: projectname, phase: phase }
    = router.query as
    { joblisting: string, ownername: string, projectname: string, phase: string };



  // Register State and Handlers
  const [registerState, setRegisterState] = useState({
    username: "",
    email: "",
    password: "",
    passwordConfirm: "",
    language: Array<Language>(),
    usecase: null as unknown as Usecase,
    prolific_id: "",
    terms: false,
    age: false,
  });

  const jobListingCmd = new JoinJoblistingCommand(
    joblistingname,
    ownername,
    projectname
  )


  const handleLanguageSelect = (language: Language) => {
    setRegisterState({
      ...registerState,
      language: calculateNewLanguageArray(language, registerState.language)
    });
  }
  const handleSignUp = () => {
    const user: CreateProlificUserCommand | null = verifySignUp(registerState);
    if (user == null) return;
    createProlificUser(user).then(async () => {
      toast.success("Successfully registered");
      // triggerd login  
      const res = await login(registerState.username, registerState.password);
      // Store the authentication token and user in storage
      storage.set('JWT', res.authenticationToken);
      storage.set('USER', registerState.username);
      //Join project
      joinJoblisting(jobListingCmd, storage.get)
      // Redirect to the project page
      Router.push(`/phi/${ownername}/${projectname}/${phase}/annotate`);
    })
      .catch(error => {
        if (error?.response?.status === 500) {
          toast.error("Error while registering: " + error.response.data.message + "!");
        } else {
          toast.warning("The system is currently not available, please try again later!");
        }
      });

  }

  // Page
  return (
    <BasicLayout>

      <Head>
        <title>PhiTag: Register</title>
      </Head>
      <CenteredLayout>
        <div className="flex justify-around items-center">
          <form className="shadow-md">
            <div className="my-8 mx-8 font-uni-corporate-bold font-bold">
              <h1 className="font-bold text-xl">
                Sign Up to the PhiTag-System!
              </h1>
              <div className="flex items-center border-b-2 py-2 px-3 my-6">
                <FiUser className="basic-svg" />

                <input
                  id="prolific_id"
                  name="prolific_id"
                  className="pl-3 flex flex-auto outline-none border-none "
                  type={"text"}
                  placeholder="Please provide your prolific id here"
                  value={registerState.prolific_id}
                  onChange={(e: any) => setRegisterState({
                    ...registerState,
                    prolific_id: e.target.value
                  })} />
              </div>
              {/* <div className="flex items-center border-b-2 py-2 px-3 my-6">
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
              </div> */}

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
              <button type="button" className="block w-full mt-8 py-2 font-uni-corporate-bold bg-uni-corporate-mittelblau text-white " onClick={() => handleSignUp()}>Register</button>

            </div>
          </form>
        </div>
      </CenteredLayout>

    </BasicLayout>

  );
}
export default Index;

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

function verifySignUp(registerState: {
  username: string;
  email: string;
  password: string;
  passwordConfirm: string;
  language: Language[];
  usecase: Usecase;
  prolific_id: string;
  terms: boolean;
  age: boolean;
}): null | CreateProlificUserCommand {
  if (!registerState.prolific_id || !registerState.email || !registerState.password || !registerState.passwordConfirm) {
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

  return new CreateProlificUserCommand(
    registerState.prolific_id,
    registerState.email,
    registerState.password,
    registerState.usecase.getName(),
    registerState.language.map(l => l.getName()),
    registerState.prolific_id,
    registerState.terms,
    registerState.age
  );
}

function verifyJoblistingCommnad() {
}