// Hook for validating  authenticating a user
import { useEffect, useState } from "react";
import { validate } from "../service/auth/AuthenticationController";
import useStorage from "./useStorage";

const useAuthenticated = () => {
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [isReady, setReady] = useState(false);
    const storage = useStorage();

    useEffect(() => {
        const token = storage.get("JWT");
        if (token) {
            validate(token).
                then(response => {
                    setAuthenticated(true);
                    setReady(true);
                }).
                catch(() => {
                    storage.remove("JWT");
                    setAuthenticated(false);
                    setReady(true);
                });
        } else {
            setReady(true);
        }

    }, []);

    return { isAuthenticated, isReady };
}

export default useAuthenticated;