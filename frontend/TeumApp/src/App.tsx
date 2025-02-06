import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { ThemeProvider } from 'react-native-elements';
import { I18nextProvider } from 'react-i18next';
import i18n from './i18n/config';
import { theme } from './utils/theme';

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <I18nextProvider i18n={i18n}>
        <ThemeProvider theme={theme}>
          <Stack.Navigator>
            {/* Screens will be added here */}
          </Stack.Navigator>
        </ThemeProvider>
      </I18nextProvider>
    </NavigationContainer>
  );
};

export default App;
