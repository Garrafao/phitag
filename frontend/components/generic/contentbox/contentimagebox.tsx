import Image from 'next/image'
import StaticData from '../../../lib/model/staticdata/staticdata'

interface IProps {
    data: StaticData;
}

const ContentImageBox: React.FC<IProps> = ({ data }) => {
    return (
        <div className='shadow-md my-6'>
            <div className={"flex justify-center " + (data.imagereversed ? 'flex-row-reverse' : 'flex-row')}>
                <div className="flex flex-col my-8 mx-8 ">
                    <div className={"font-dm-mono-medium font-black text-lg ml-4"}>
                        {data.title}
                    </div>
                    <div className={"my-2 font-dm-mono-regular text-base16-gray-900 ml-4"}>
                        <div className="" dangerouslySetInnerHTML={{ __html: data.content }} />
                    </div>
                </div>
                <div className={"flex items-center m-10 portrait:hidden " + (data.imagereversed ? 'justify-start' : 'justify-end')}>
                    <Image src={data.imagepath} layout="fixed" width={200} height={200} alt={"Image for " + data.title}/>
                </div>
            </div>
        </div>
    );
};

export default ContentImageBox;