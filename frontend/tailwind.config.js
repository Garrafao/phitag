module.exports = {
    content: [
        "./pages/**/*.{js,ts,jsx,tsx}",
        "./components/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {

            fontFamily: {
                'dm-mono-light': ['DM Mono Light'],
                'dm-mono-light-italic': ['DM Mono Light Italic'],

                'dm-mono-regular': ['DM Mono Regular'],
                'dm-mono-italic': ['DM Mono Italic'],

                'dm-mono-medium': ['DM Mono Medium'],
                'dm-mono-medium-italic': ['DM Mono Medium Italic'],

                'uni-corporate-light': ['Univers LT Std 45 Light'],
                'uni-corporate-regular': ['Univers LT Std 55 Roman'],
                'uni-corporate-bold': ['Univers LT Std 65 Bold'],
            },

            colors: {
                'base16-gray': {
                    900: '#151515',
                    800: '#1F1F1F',
                    700: '#282828',
                    600: '#2d2d2d',
                    500: '#707070',
                    400: '#B0B0B0',
                    300: '#C0C0C0',
                    200: '#D0D0D0',
                    100: '#E0E0E0',
                },
                'base16-red': '#AC4142',
                'base16-yellow': '#F4BF75',
                'base16-green': '#90A959',
                'base16-blue': '#6A9FB5',
                'base16-cyan': '#75B5AA',
                'base16-rose': '#AA759F',
                'base16-orange': '#F4BF75',

                'uni-corporate-anthrazit': '#323232',
                'uni-corporate-mittelblau': '#004191',
                'uni-corporate-hellblau': '#00BEFF',

            },
            screens: {
                'xs': '320px',    // phones
                'sm': '640px',    // phones
                'md': '768px',    // tablets, small laptops
                'lg': '1000px',   // tablets, small laptops
                'xl': '1200px',   // laptops, desktops and upwards
                '2xl': '1500px',  // laptops, desktops and upwards
                '3xl': '2050px',  // high resolution desktops
            },

        },
    },

    plugins: [
        require('@tailwindcss/line-clamp'),

        require('@tailwindcss/typography'),
    ],
}