import StaticData from '../../../lib/model/staticdata/staticdata'

interface IProps {
    data: StaticData;
}

const MultiContentBox: React.FC<IProps> = ({ data }) => {
    return (
        <div className="w-full shadow-md flex flex-col">
            <div className='my-8 mx-8'>
                <div className="ml-4 font-uni-corporate-bold font-bold text-lg">
                    {data.title}
                </div>
                <div className="my-2 sm:mx-4 mx-2 font-uni-corporate-regular text-base16-gray-900">
                    <div className="markdown-content-box" dangerouslySetInnerHTML={{ __html: data.content }} />
                </div>
            </div>
        </div>
    );
};

export default MultiContentBox;