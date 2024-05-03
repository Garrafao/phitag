

const LoadingComponentCard: React.FC<{ text: string; icon: any, isOpen: boolean}> = ({ text, icon, isOpen}) => {



    if (!isOpen) {
        return null;
    }


    return (
      <div className="relative z-10 font-dm-mono-medium">
        <div className="fixed inset-0 bg-base16-gray-100 bg-opacity-100" />
  
        <div className="fixed z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-full">
            <div className="mx-4">
              <div className="flex flex-col items-center mt-4 ">
                {icon}
                <div className="py-2 px-2 text-2xl font-mono">
                  {text}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };
  
  export default LoadingComponentCard;
  