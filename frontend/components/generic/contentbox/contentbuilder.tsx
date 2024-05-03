import StaticData from '../../../lib/model/staticdata/staticdata'
import ContentImageBox from "./contentimagebox";
import MultiContentBox from "./multicontentbox";

interface IProps {
    data: StaticData | StaticData[];
}

const ContentBuilder: React.FC<IProps> = ({ data }) => {

    if (data instanceof Array) {
        return (
            <div className="mt-8 flex flex-col lg:flex-row lg:space-x-8 space-y-8 lg:space-y-0 lg:my-8">
                {
                    data.map((item, index) => {
                        return (
                            <div key={index} className="w-full lg:w-1/2">
                                <MultiContentBox data={item} />
                            </div>
                        );
                    })
                }
            </div>
        );
    }

    if (data.imagepath !== "") {
        return (
            <div className="mt-8 w-full lg:w-1/2">
                <ContentImageBox data={data} />
            </div>
        )
    }

    return (
        <div className="mt-8 w-full lg:w-1/2">
            <MultiContentBox data={data} />
        </div>
    );

}

export default ContentBuilder;
