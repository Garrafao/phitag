// React Modules
import { useEffect, useState } from "react";

// Next Modules
import { NextPage } from "next";
import Router, { useRouter } from 'next/router';
import Head from "next/head";

// Toast
import { toast } from "react-toastify";

// Custom Controllers

// React Icons
import { FiLock, FiUser } from "react-icons/fi";

// Custom Components
import BasicLayout from "../../../../../../../components/generic/layout/basiclayout";
import CenteredLayout from "../../../../../../../components/generic/layout/centeredlayout";
import useStorage from "../../../../../../../lib/hook/useStorage";
import { login } from "../../../../../../../lib/service/auth/AuthenticationController";
import Link from "next/link";
import useAuthenticated from "../../../../../../../lib/hook/useAuthenticated";

const Login: NextPage = () => {

    //Hooks
    const authenticated = useAuthenticated();
    const storage = useStorage();

    // Login States
    const [loginState, setLoginState] = useState({
        username: "",
        password: "",
    });
    const router = useRouter();

    const { joblisting: joblistingname, ownername: ownername, projectname:projectname, phase } = router.query as { joblisting: string, ownername: string, projectname: string, phase:string };

    // Handlers
    const handleLogin = () => {
        login(loginState.username, loginState.password).then(res => {
            storage.set('JWT', res.authenticationToken);
            storage.set('USER', loginState.username);
            Router.push(`/phi/${ownername}/${projectname}/${phase}/annotate`);
        }).catch(error => {
            if (error?.response?.status === 500) {
                toast.error("Login failed, please check your credentials!");
            } else {
                toast.warning("The system is currently not available, please try again later!");
            }
        }).finally(() => {
            setLoginState({
                username: "",
                password: "",
            });
        })
    }

    // Effects
    useEffect(() => {
        if (authenticated.isReady && authenticated.isAuthenticated) {
            toast.info("You are already logged in!");
            Router.push(`/phi/${ownername}/${projectname}/${phase}/annotate`);

        }
    }, [authenticated]);

    // Page
    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: Login</title>
            </Head>

            <CenteredLayout>
                <div className="m-auto">
                    <div className="flex flex-col items-center">
                        <form className="shadow-md">
                            <div className="my-8 mx-8 font-uni-corporate-bold font-bold">
                                <h1 className="font-bold text-xl">
                                    Welcome Back to the PhiTag-System!
                                </h1>

                                <div className="flex items-center border-b-2 py-2 px-3 my-6">
                                    <FiUser className="basic-svg" />
                                    <input
                                        id="username"
                                        name="username"
                                        className="pl-3 flex flex-auto outline-none border-none"
                                        type={"text"}
                                        placeholder="Username"
                                        value={loginState.username}
                                        onChange={(e: any) => setLoginState({
                                            ...loginState,
                                            username: e.target.value
                                        })} />
                                </div>

                                <div className="flex items-center border-b-2 py-2 px-3 my-6">
                                    <FiLock className="basic-svg" />
                                    <input
                                        id="password"
                                        name="password"
                                        className="pl-3 flex flex-auto outline-none border-none"
                                        type={"password"}
                                        placeholder="Password"
                                        value={loginState.password}
                                        onChange={(e: any) => setLoginState({
                                            ...loginState,
                                            password: e.target.value
                                        })} />
                                </div>

                                <button type="button" className="font-uni-corporate-bold block w-full mt-8 py-2 bg-uni-corporate-mittelblau text-white" onClick={() => handleLogin()}>
                                    Sign In
                                </button>
                            </div>
                        </form>

                        <div className="mt-2">
                            Don&apos;t have an account yet? <Link href="/register"><span className="text-uni-corporate-mittelblau cursor-pointer font-bold">Register here!</span></Link>
                        </div>
                    </div>
                </div>
            </CenteredLayout>

        </BasicLayout>
    );

};

export default Login;