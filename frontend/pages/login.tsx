// React Modules
import { useEffect, useState } from "react";

// Next Modules
import { NextPage } from "next";
import Router from 'next/router';
import Head from "next/head";

// Toast
import { toast } from "react-toastify";

// Custom Controllers
import useAuthenticated from "../lib/hook/useAuthenticated";

// React Icons
import { FiLock, FiUser } from "react-icons/fi";

// Custom Components
import BasicLayout from "../components/generic/layout/basiclayout";
import CenteredLayout from "../components/generic/layout/centeredlayout";
import useStorage from "../lib/hook/useStorage";
import { login } from "../lib/service/auth/AuthenticationController";

const Login: NextPage = () => {

    //Hooks
    const authenticated = useAuthenticated();
    const storage = useStorage();

    // Login States
    const [loginState, setLoginState] = useState({
        username: "",
        password: "",
    });

    // Handlers
    const handleLogin = () => {
        login(loginState.username, loginState.password).then(res => {
            storage.set('JWT', res.authenticationToken);
            storage.set('USER', loginState.username);

            // decode jwt, get payload
            var jwt = require('jsonwebtoken');
            const payload = jwt.decode(res.authenticationToken, {complete: true}).payload;
            storage.set('USECASE', payload?.usecase);

            Router.push(`/dashboard`);
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
            Router.push("/dashboard");
        }
    }, [authenticated]);

    // Page
    return (
        <BasicLayout>
            <Head>
                <title>PhiTag: Login</title>
            </Head>

            <CenteredLayout>
                <div className="flex justify-around items-center">
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

                            <button type="button" className="font-uni-corporate-bold block w-full mt-8 py-2 bg-base16-gray-900 text-white" onClick={() => handleLogin()}>
                                Sign In
                            </button>
                        </div>
                    </form>
                </div>
            </CenteredLayout>

        </BasicLayout>
    );

};

export default Login;