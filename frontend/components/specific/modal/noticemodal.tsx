// react
import { useState } from "react";



import { LiaToolsSolid } from "react-icons/lia";
// modal


const NoticeModal = () => {


  const [modalState, setModalState] = useState({
    active: true
  });

  const onCancel = () => {
    setModalState({
      ...modalState,
      active: false
    });
  }

  if (!modalState.active) {
    return null;
  }

  return (
    <div className="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">
      <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
      <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
          <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
            <div className="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
              <div className="sm:flex sm:items-start">
                <div className="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                  <LiaToolsSolid className="basic-svg" />
                </div>
                <div className="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                  <h3 className="text-base font-semibold leading-6 text-gray-900" id="modal-title">Maintenance Notice</h3>
                  <div className="mt-2">
                    <div className="text-sm text-gray-800">
                      <h2 className="py-3 text-xl">
                        Dear valued user,
                      </h2>
                      <p className="text-sm">
                      We are currently performing maintenance on our system to enhance your experience. While taking every precaution to
                        ensure a smooth process, unforeseen issues may arise.
                        To safeguard your data, we recommend backing up your information regularly. In case of any unexpected data loss or issues,
                        please don&apos;t hesitate to reach out to us at
                        <a href="https://phitag.ims.uni-stuttgart.de/about-us" className="font-bold text-1xl text-blue-900 underline"> here. </a>
                        Your understanding and cooperation during this maintenance period are greatly appreciated. Thank you for being a part of our community!
                      </p>
                      <h2 className="py-2 text-1xl">
                        Best regards,
                        <p className="font-bold">PhiTag</p>
                      </h2>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
              <button type="button" className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto" onClick={onCancel}>Close</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default NoticeModal;

