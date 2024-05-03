import { useRouter } from "next/router";
import { toast } from "react-toastify";
import useStorage from "../../lib/hook/useStorage";
import { useFetchUser } from "../../lib/service/user/UserResource";
import Phase from "../../lib/model/phase/model/Phase";


const SubmitStudyComponent: React.FC<{ phase: Phase }> = ({ phase }) => {

    const router = useRouter();


    //const phase = useFetchPhase(username, projectname, phasename, router.isReady);
    const storage = useStorage();
    const checkUser = useFetchUser(storage.get("USER"))

    const handleSubmit = () => {

        if (checkUser?.user?.getProlificId()) {
            const code = phase.getCode();
            const url = "https://app.prolific.com/submissions/complete?cc=";
            if (!code) {
                toast.error("There is an error in submitting the study. Please contact the researcher")
            }
            if (code && code.includes(url)) {

                window.location.href = code;

            } else if (code) {
                window.location.href = `${url}+${code}`
            }
        } else {
            toast.info("Your study has been submitted sucesfully!")
            const path = `/dashboard`;
            router.push(path);
            return;
        }

    }


    const handleEdit = () => {
        const path = `/phi/${phase?.getId()?.getOwner()}/${phase?.getId()?.getProject()}/${phase?.getName()}/judgement`;
        router.push(path);
    }


    return (
        <div className="flex justify-center  ">
            <div className="mx-4">
                <div className="flex flex-col items-left mt-10">
                    <div className="flex items-center justify-center my-10 font-black text-xl font-dm-mono-medium">
                        Thank you for participating in study
                    </div>
                    <div className="flex items-center justify-center my-5 font-black text-xl font-dm-mono-medium">
                        Your submission has been recorded
                    </div>

                    <div className="flex flex-row divide-x-8">
                        <button type="button" className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 text-xl font-dm-mono-medium" onClick={handleEdit}>Edit Response</button>

                        <button
                            type="button"
                            className="block w-full mt-8 py-2 bg-base16-gray-900 text-base16-gray-100 text-xl font-dm-mono-medium"
                            onClick={handleSubmit}
                        >
                            Submit
                        </button>
                    </div>

                </div>
            </div>
        </div>
    );
}
export default SubmitStudyComponent;
