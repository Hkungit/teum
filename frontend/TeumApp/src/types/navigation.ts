import { StackNavigationProp } from '@react-navigation/stack';

export type RootStackParamList = {
  Home: undefined;
  Login: undefined;
  Register: undefined;
};

export type NavigationProps = StackNavigationProp<RootStackParamList>;
