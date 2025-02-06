import { StackNavigationProp } from '@react-navigation/stack';

export type RootStackParamList = {
  Home: undefined;
  Login: { registered?: boolean };
  Register: undefined;
};

export type NavigationProps = StackNavigationProp<RootStackParamList>;
