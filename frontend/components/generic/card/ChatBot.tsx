import { useState } from 'react';
import { sendMessageToBroker } from '../../../lib/service/computationalAnnotator/ComputationalAnnotatorResource';
import { toast } from 'react-toastify';

const ChatBot: React.FC = () => {
    const [messages, setMessages] = useState<{ text: string; type: 'user' | 'bot' }[]>([]);
    const [newMessage, setNewMessage] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(false);

    const sendMessage = async () => {
        if (newMessage.trim() === '') return;

        setLoading(true);

        try {
            // Add user's message to state
            setMessages(prevMessages => [...prevMessages, { text: newMessage, type: 'user' }]);
            
            setNewMessage('');
            console.log( "RESPIENS");
            const { response, isError } = await sendMessageToBroker(newMessage);
            console.log(response, "RESPIENS")

            if (isError) {
                toast.error("Error getting response.");
                setMessages(prevMessages => [...prevMessages, { text: "Error getting response.", type: 'bot' }]);
            } else {
                // Convert the Blob response to text
                const responseText = await response;
                // Add chatbot's response to state
                setMessages(prevMessages => [...prevMessages, { text: responseText, type: 'bot' }]);
            }
        } catch (error) {
            console.error('Error sending message:', error);
            toast.error("An unexpected error occurred.");
            setMessages(prevMessages => [...prevMessages, { text: "An unexpected error occurred.", type: 'bot' }]);
        } finally {
            setLoading(false);
        }
    };

    const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            event.preventDefault(); // Prevent the default form submission
            sendMessage();
        }
    };

    return (
        <div className="w-full flex flex-col justify-center items-center">
            <div className="text-gray-800 text-2xl font-uni-corporate-bold">
                Computational Annotator
            </div>

            <div className="flex flex-col items-center w-full h-full">
                <div className="w-1/2 bg-white rounded-lg shadow-md overflow-hidden">
                    <div className="flex flex-col h-96 px-4 py-6 space-y-4 overflow-y-auto">
                        {messages.map((msg, index) => (
                            <div
                                key={index}
                                className={`flex items-center justify-center py-2 px-4 rounded-lg ${msg.type === 'user' ? 'bg-blue-500 text-white self-end' : 'bg-gray-200'}`}
                            >
                                {msg.text}
                            </div>
                        ))}
                        {loading && <div className="text-blue-500">Sending...</div>}
                    </div>
                    <div className="flex items-center p-4 border-t border-gray-200">
                        <input
                            type="text"
                            value={newMessage}
                            onChange={(e) => setNewMessage(e.target.value)}
                            onKeyDown={handleKeyDown} // Add this line
                            className="flex-1 py-2 px-4 rounded-lg bg-gray-100 focus:outline-none focus:bg-white"
                            placeholder="Type a message..."
                            disabled={loading}
                        />
                        <button
                            onClick={sendMessage}
                            className="ml-2 py-2 px-4 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none"
                            disabled={loading}
                        >
                            Send
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ChatBot;
