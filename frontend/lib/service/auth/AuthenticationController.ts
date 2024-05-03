import Router from "next/router";

import axios from "axios";
import { toast } from "react-toastify";

import BACKENDROUTES from "../../BackendRoutes";


interface JWT {
    authenticationToken: string;
}

// API Callers

export function login(username: string, password: string): Promise<JWT> {
    return axios.post<JWT>(`${BACKENDROUTES.AUTHENTICATION}/login`, { username, password }).then(res => res.data);
}

export function validate(token: string) {
    return axios.post(`${BACKENDROUTES.AUTHENTICATION}/validate`, { authenticationToken: token }).then(res => res.data);
}

// Preflight Checkers

/** 
 * @deprecated This function is deprecated and will be removed in the future.
 * 
 * Check if the user is logged in.
 * If no token is found, redirect to the index page.
 * If the token is found, validate it.
 * If the token is invalid, redirect to sign in page.
 * If the user is logged in, do nothing.
 * 
 * Should be called on every secure page where sensitive data is called, as a preflight check of sorts.
 * 
 * @returns {void}
*/
export function checkUserLoggedInSecurePage(get: Function = () => { }, remove: Function = () => { }): void {
    const token = get("JWT");

    validate(token ?? '').catch(() => {
        toast.error('Your session has expired.');
        logoutAndRedirectToLogin(remove);
    });
}

/**
 * @deprecated This function is deprecated and will be removed in the future.
 *  
 * Check if the user is logged in.
 * If the user is logged in, redirect to home page.
 * If the user is not logged in, do nothing.
 * 
 * This serves as a preflight check of sorts for sign in/up pages, such that users do not have to re-enter their credentials.
 * 
 * @returns {void}
 */
export function checkUserLoggedInSignInUpPage(get: Function = () => { }, remove: Function = () => { }): void {
    const token = get('JWT');

    if (token) {
        validate(token).then(() => {
            Router.push('/dashboard');
        })
            .catch(() => {
                // TODO, clear the token from storage
                remove('JWT');
                remove('USERNAME');
            });
    }
}


/*
    * Log the user out.
    * Clear the token from storage.
    * Redirect to the index page.
    * */
export function logout(remove: Function = () => { }): void {
    remove('JWT');
    remove('USERNAME');
    toast.success('You have been logged out.');
    Router.push('/');
}


/*
    * Log the user out.
    * Clear the token from storage.
    * Redirect to login page.
    * */
export function logoutAndRedirectToLogin(remove: Function = () => { }): void {
    remove('JWT');
    remove('USERNAME');
    Router.push('/login');
}