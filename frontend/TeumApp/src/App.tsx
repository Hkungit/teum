import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { ThemeProvider } from 'react-native-elements';
import { I18nextProvider } from 'react-i18next';
import i18n from './i18n/config';
import { theme } from './utils/theme';
import Home from './screens/Home';
import Login from './screens/auth/Login';
import Register from './screens/auth/Register';

import { RootStackParamList } from './types/navigation';

const Stack = createStackNavigator<RootStackParamList>();

const App = () => {
  return (
    <NavigationContainer>
      <I18nextProvider i18n={i18n}>
        <ThemeProvider theme={theme}>
          <Stack.Navigator initialRouteName="Login">
            <Stack.Screen 
              name="Login" 
              component={Login}
              options={{ headerShown: false }}
            />
            <Stack.Screen 
              name="Register" 
              component={Register}
              options={{ headerShown: false }}
            />
            <Stack.Screen 
              name="Home" 
              component={Home}
              options={{ title: '首页' }}
            />
          </Stack.Navigator>
        </ThemeProvider>
      </I18nextProvider>
    </NavigationContainer>
  );
};

export default App;
