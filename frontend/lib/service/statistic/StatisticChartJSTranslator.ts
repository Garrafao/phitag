export function translateMapToChartJSData(title: string, map: Map<string, number>): any {
    const data: any = {
        labels: [],
        datasets: [{
            label: title,
            data: [],
            backgroundColor: [],
            hoverOffset: 16
        }]
    };

    map.forEach((value, key) => {
        data.labels.push(key);
        data.datasets[0].data.push(value);
        data.datasets[0].backgroundColor.push('#151515');
    });

    return data;
}