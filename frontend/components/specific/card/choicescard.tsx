import React from 'react';
import useStorage from '../../../lib/hook/useStorage';
import SentimentInstance from '../../../lib/model/instance/sentimentinstance/model/SentimentInstance';

const ChoicesCard: React.FC<{ isOpen: boolean; choiceInstance: SentimentInstance }> = ({ isOpen, choiceInstance }) => {
    const { get } = useStorage();

    if (!isOpen) {
        return null;
    }

    return (
        <div className="min-w-full px-6 py-4 whitespace-nowrap overflow-auto max-w-[700px]">
            <div className="shadow-md overflow-auto">
                {choiceInstance.getLabelSet().map((item, index) => (
                    <div className="m-4 border border-gray-200 rounded-md p-4 overflow-auto" key={index}>
                        {item}
                    </div>
                ))}
            </div>
        </div>
    );    
};

export default ChoicesCard;
