import '../styles/globals.css'
import 'react-toastify/dist/ReactToastify.css';
import { toast, ToastContainer } from 'react-toastify';

import type { AppProps } from 'next/app'


function App({ Component, pageProps }: AppProps) {

    return (
        <>
            <Component {...pageProps} />

            {/* Register Toast */}
            <ToastContainer
                position="bottom-right"
                autoClose={5000}
                hideProgressBar={true}
                newestOnTop
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                closeButton={false}

                theme={"dark"}
                toastClassName={"prose font-dm-mono-medium font-black"}


            />
        </>
    )
}

export default App;