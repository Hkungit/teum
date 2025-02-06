import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { ThemeProvider as ElementsThemeProvider, Theme } from 'react-native-elements';
import { I18nextProvider } from 'react-i18next';
import i18n from './i18n/config';
import { theme } from './utils/theme';
import Home from './screens/Home';

type RootStackParamList = {
  Home: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const App = () => {
  return (
    <NavigationContainer>
      <I18nextProvider i18n={i18n}>
        <ElementsThemeProvider theme={theme}>
          <Stack.Navigator initialRouteName="Home">
            <Stack.Screen 
              name="Home" 
              component={Home}
              options={{ title: '首页' }}
            />
          </Stack.Navigator>
        </ElementsThemeProvider>
      </I18nextProvider>
    </NavigationContainer>
  );
};

export default App;
