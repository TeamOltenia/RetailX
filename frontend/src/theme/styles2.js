export const globalStyles2 = {
  colors: {
    gray: {
      700: "#1f2733",
    },
    brand: {
      50: "#fff7f0", // Beige
      100: "#fdd9c4",
      200: "#faab8e",
      300: "#f57a5c",
      400: "#f04b30",
      500: "#e01e0a", // Red
      600: "#b91608",
      700: "#800f06",
      800: "#5c0a04",
      900: "#390602",
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
