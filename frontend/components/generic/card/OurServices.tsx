import Link from 'next/link';
import { useState } from 'react';
import { toast } from 'react-toastify';

const OurServices: React.FC = () => {
    return (
        <div className="w-full min-h-screen flex flex-col justify-center items-center bg-blue-900 py-16 px-4">
            <div className="text-gray-100 text-5xl font-uni-corporate-bold mb-6 shadow-lg">
                Our Services
            </div>

            <div className="w-full max-w-5xl grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-10">
                <div className="bg-white bg-opacity-90 p-8 rounded-lg shadow-lg hover:shadow-2xl transition-all duration-300 hover:scale-105 transform">
                    <h3 className="text-2xl font-uni-corporate-semibold text-gray-900 mb-4">Data Annotation</h3>
                    <p className="text-gray-700 leading-snug m-0">
                        We provide high-quality text data annotation services, driven by skilled human annotators.<br /> Our platform combines expert human input with automation to deliver precise, scalable, and consistent solutions for tasks like semantic analysis, sentiment analysis, etc.
                    </p>
                </div>

                <div className="bg-white bg-opacity-90 p-8 rounded-lg shadow-lg hover:shadow-2xl transition-all duration-300 hover:scale-105 transform">
                    <h3 className="text-2xl font-uni-corporate-semibold text-gray-900 mb-4">Computational Annotator</h3>
                    <p className="text-gray-700 leading-snug m-0">
                        Our Computational Annotator feature utilizes GPT technology to provide a powerful, AI-driven solution for automating text annotation across diverse use cases.<br /> Check <Link href='guide/how-to-use-gpt-annotator'>
                            <span className='underline cursor-pointer'>computational annotator guide here.</span></Link>
                    </p>
                </div>

                <div className="bg-white bg-opacity-90 p-8 rounded-lg shadow-lg hover:shadow-2xl transition-all duration-300 hover:scale-105 transform">
                    <h3 className="text-2xl font-uni-corporate-semibold text-gray-900 mb-4">Prolific Integration</h3>
                    <p className="text-gray-700 leading-snug m-0">
                        PhiTag offers seamless integration with Prolific, enabling you to connect with a diverse pool of skilled annotators for your data annotation needs. Check <Link href='guide/prolific-integration'><span className='underline cursor-pointer'>prolific Integration guide here.</span></Link>
                    </p>
                </div>

                <div className="bg-white bg-opacity-90 p-8 rounded-lg shadow-lg hover:shadow-2xl transition-all duration-300 hover:scale-105 transform">
                    <h3 className="text-2xl font-uni-corporate-semibold text-gray-900 mb-4">AI as a Service</h3>
                    <p className="text-gray-700 leading-snug m-0">
                        We build intelligent AI models that help automate your business workflows, making complex tasks easier and faster. Our solutions are designed to seamlessly integrate with your existing processes, saving you time and effort while improving efficiency.
                    </p>
                </div>
            </div>
        </div>
    );
};

export default OurServices;
