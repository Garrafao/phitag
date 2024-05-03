import React from "react";

import {
    Chart as ChartJS,
    DoughnutController,
    ArcElement,
    Tooltip,
    Filler,
    Title,
    Legend,
} from "chart.js"

const StatisticComponent = () => {


    // Register ChartJS
    ChartJS.register(
        // Dounghnut
        DoughnutController,
        ArcElement,

        // Others
        Filler,
        Title,
        Tooltip,
        Legend,

    );

    return (
        <div />
    );
}

export default StatisticComponent;