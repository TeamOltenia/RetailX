import React, { useState } from "react";
import {
  ChakraProvider,
  Button,
  extendTheme,
  CSSReset,
} from "@chakra-ui/react";
import { ThemeProvider as EmotionThemeProvider } from "@emotion/react";
import { globalStyles } from "../../theme/styles";
import { globalStyles2 } from "../../theme/styles2";
import { globalStyles3 } from "../../theme/styles3";

const ThemeSwitcher = () => {
  const [currentTheme, setCurrentTheme] = useState(extendTheme(globalStyles));

  const handleThemeChange = (selectedTheme) => {
    setCurrentTheme(extendTheme(selectedTheme));
  };

  return (
    <ChakraProvider theme={currentTheme}>
      <EmotionThemeProvider theme={currentTheme}>
        <CSSReset />
        {/* Your app content goes here */}
        <Button
          size="sm"
          bg={"gray.300"}
          onClick={() => handleThemeChange(globalStyles)}
        >
          Theme 1
        </Button>
        <Button
          size="sm"
          bg={"gray.300"}
          onClick={() => handleThemeChange(globalStyles2)}
        >
          Theme 2
        </Button>
        <Button
          size="sm"
          bg={"gray.300"}
          onClick={() => handleThemeChange(globalStyles3)}
        >
          Theme 3
        </Button>
      </EmotionThemeProvider>
    </ChakraProvider>
  );
};

export default ThemeSwitcher;
