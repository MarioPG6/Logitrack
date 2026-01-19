/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts}"
  ],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        primary: "#2E7D32",
        "background-light": "#f8fafc",
        "background-dark": "#0f172a",
      },
      fontFamily: {
        sans: ["Inter", "sans-serif"],
      },
    },
  },
  plugins: [
    require("@tailwindcss/forms"),
  ],
};


