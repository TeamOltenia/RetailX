export const globalStyles3 = {
  colors: {
    gray: {
      700: "#1f2733",
    },
    brand: {
      50: "#f2ffe5",
      100: "#ccffcc",
      200: "#99ff99",
      300: "#66ff66",
      400: "#33ff33",
      500: "#00ff00", // Green
      600: "#00cc00",
      700: "#009900",
      800: "#006600",
      900: "#003300",
    },
  },
  styles: {
    global: (props) => ({
      body: {
        fontFamily: "Plus Jakarta Display",
        bg: "gray.800", // Set the background color to a dark gray
      },
      "*::placeholder": {
        color: "gray.400",
      },
      html: {
        fontFamily: "Plus Jakarta Display",
      },
    }),
  },
};
