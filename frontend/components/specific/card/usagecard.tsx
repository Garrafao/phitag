

// services
import useStorage from "../../../lib/hook/useStorage";
import UseRankInstance from "../../../lib/model/instance/userankinstance/model/UseRankInstance";
import Usage from "../../../lib/model/phitagdata/usage/model/Usage";
import { RiNumber0, RiNumber1, RiNumber2, RiNumber3, RiNumber4, RiNumber5, RiNumber6, RiNumber7, RiNumber8, RiNumber9 } from "react-icons/ri";




// components

const UsageCard: React.FC<{ isOpen: boolean, userankinstance: UseRankInstance }> = ({ isOpen, userankinstance }) => {

  const { get } = useStorage();




  if (!isOpen) {
    return null;
  }

  return (
    <div className="min-w-full border-b-[1px]  border-base16-gray-200 divide-base16-gray-200 overflow-auto">
      {userankinstance.getFirstusage().getContext() && (
        <div className=" group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
            First Usage Data Id: {userankinstance.getFirstusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5 ">
                <div><RiNumber1 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getFirstusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getSecondusage().getContext() && (
        <div className="items-center group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
              Second Usage Data Id: {userankinstance.getSecondusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber2 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getSecondusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getThirdusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
             Third Usage Data Id: {userankinstance.getThirdusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber3 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getThirdusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getFourthusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
              Fourth Usage Data Id: {userankinstance.getFourthusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber4 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getFourthusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getFifthusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
             Fifth Usage Data Id: {userankinstance.getFifthusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber5 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getFifthusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getSixthusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
              Sixth Usage Data Id: {userankinstance.getSixthusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber6 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getSixthusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getSeventhusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
              Seventh Usage Data Id: {userankinstance.getSeventhusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber7 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getSeventhusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getEightusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
              Eight Usage Data Id: {userankinstance.getEightusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber8 className="basic-svg" /></div>
              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getEightusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getNinthusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
             Ninth Usage Data Id: {userankinstance.getNinthusage().getId().getDataid()}
            </div>
          </div>
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
                <div><RiNumber9 className="basic-svg" /></div>

              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getNinthusage())}
              </div>
            </div>
          </div>
        </div>
      )}
      {userankinstance.getTenthusage().getContext() && (
        <div className="group relative">
          <div className="tooltip-container scale-0 group-hover:scale-100 absolute top-[-23px]">
            <div className="whitespace-nowrap mx-8 my-2">
              Tenth Usage Data Id: {userankinstance.getTenthusage().getId().getDataid()}
            </div>
          </div>
          {/*   <div className="font-mono text-xl basic-svg">10</div> */}
          <div className="w-full shadow-md ">
            <div className="m-6 flex flex-row">
              <div className="my-5">
              <div className="flex items-center ">
                  <RiNumber1 className="basic-svg"  style={{ marginRight: '-0.5rem' }}  />
                  <RiNumber0 className="basic-svg" style={{ marginRight: '-0.5rem' }}/>
                </div>


              </div>
              <div className="border-r-2 mx-4" />
              <div className="my-4 font-dm-mono-light text-sm overflow-auto">
                {getFormatedUsage(userankinstance.getTenthusage())}
              </div>
            </div>
          </div>
        </div>
      )}


    </div>
  );
};
export default UsageCard;

function getFormatedUsage(usage: Usage) {
  return (
    <div className="">
      {usageContextBuilder(usage).map((sentence, index) => {
        return (
          <span
            key={index}
            className={sentence.highlight === "bold" ? "font-dm-mono-medium" : sentence.highlight === "color" ? "inline font-dm-sans-bold text-lg text-base16-green" : ""}>
            {sentence.sentence}
          </span>
        );
      })}
    </div>
  )
}

/**
* Constructs a context array with Pairs of the form {sentence: string, highlight: "none" | "bold" | "color"}
* 
* @param usage usage to construct the context from
*/
function usageContextBuilder(usage: Usage): { sentence: string; highlight: "none" | "bold" | "color" }[] {
  if (!usage || !usage.getContext()) {
    return []; // Return an empty array if 'usage' or 'context' is null or undefined
  }

  const context = usage.getContext();
  const contextArray: { sentence: string; highlight: "none" | "bold" | "color" }[] = [];

  // Add the first sentence
  contextArray.push({
    sentence: context.substring(0, usage.getIndexTargetSentenceStart() ?? 0),
    highlight: "none",
  });

  let lastTargetTokenEnd = 0;

  usage.getIndexTargetSentence().forEach((sentence, index) => {
    lastTargetTokenEnd = sentence.left;

    usage.getIndexTargetToken().forEach((token, index) => {
      if (token.left >= sentence.left && token.right <= sentence.right) {
        // Add the sentence till the target token
        contextArray.push({
          sentence: context.substring(lastTargetTokenEnd, token.left),
          highlight: "bold",
        });
        // Add the target token
        contextArray.push({
          sentence: context.substring(token.left, token.right),
          highlight: "color",
        });
        lastTargetTokenEnd = token.right;
      }
    });

    // Add the sentence after the target token
    contextArray.push({
      sentence: context.substring(lastTargetTokenEnd, sentence.right),
      highlight: "bold",
    });
  });

  // Add the last sentence
  contextArray.push({
    sentence: context.substring(usage.getIndexTargetSentenceEnd() ?? context.length),
    highlight: "none",
  });

  return contextArray;
}
