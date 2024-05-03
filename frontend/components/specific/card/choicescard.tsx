import React from 'react';
import useStorage from '../../../lib/hook/useStorage';
import SentimentInstance from '../../../lib/model/instance/sentimentinstance/model/SentimentInstance';

const ChoicesCard: React.FC<{ isOpen: boolean; choiceInstance: SentimentInstance }> = ({ isOpen, choiceInstance }) => {
    const { get } = useStorage();

    if (!isOpen) {
        return null;
    }

    return (
        <div className="min-w-full border-b-[1px] border-base16-gray-200 divide-base16-gray-200 overflow-auto">
            <div className="w-full shadow-md">
                {choiceInstance.getLabelSet().map((item, index) => (
                    <div className="m-4 border border-gray-200 rounded-md p-4" key={index}>
                        {item}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ChoicesCard;
