type StorageReturn = {
    get: (key: string) => string | null;
    set: (key: string, value: string) => boolean;
    remove: (key: string) => void;
};

const useStorage = (): StorageReturn => {
    const isBrowser = typeof window !== 'undefined';

    const get = (key: string): string | null => {
        return isBrowser ? window.localStorage.getItem(key) : '';
    };

    const set = (key: string, value: string): boolean => {
        if (isBrowser) {
            window.localStorage.setItem(key, value);
            return true;
        }
        return false;
    };

    const remove = (key: string): void => {
        if (isBrowser) {
            window.localStorage.removeItem(key);
        }
    };

    return { get, set, remove };
};

export default useStorage;