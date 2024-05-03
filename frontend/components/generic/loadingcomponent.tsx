import { FiLoader } from "react-icons/fi";


const LoadingComponent = () => {

    return (
        <div className="flex justify-center animate-spin my-32">
            <FiLoader className="w-8 h-8 text-base16-gray-400" />
        </div>
    );
}

export default LoadingComponent;