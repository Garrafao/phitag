import React, { useEffect, useState } from "react";

interface ProgressCardProps {
  minValue: number;
  maxValue: number;
  currentValue: number;
}

const ProgressBar: React.FC<ProgressCardProps> = ({ currentValue, maxValue, minValue }) => {
  const [percentage, setPercentage] = useState(0);

  useEffect(() => {
    // Ensure the values are valid numbers
    const min = isNaN(minValue) ? 0 : minValue;
    const max = isNaN(maxValue) ? 100 : maxValue;
    const current = isNaN(currentValue) ? 0 : currentValue;

    // Calculate the new percentage
    const newPercentage = Math.round(((current - min) / (max - min)) * 100);

    // Update the percentage state
    setPercentage(newPercentage);
  }, [currentValue, maxValue, minValue]);

  return (
    <div className="relative overflow-auto">
      <div className="bg-white rounded">
        <progress className="w-full h-7" value={percentage} max="100" />
       {/*  <div className="absolute top-0 left-1/2 transform -translate-x-1/2 text-white p-1 font-dm-mono-medium">
          {percentage}% completed
        </div> */}
       <div className="absolute top-0 left-1/2 transform -translate-x-1/2 text-white p-1 font-dm-mono-medium">
          {currentValue} out of {maxValue} completed
        </div> 
      </div>
    </div>
  );
};

export default ProgressBar;
