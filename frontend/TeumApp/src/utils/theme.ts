import { Theme } from 'react-native-elements';

export const theme: Partial<Theme> = {
  colors: {
    primary: '#1890ff',
    secondary: '#f5222d',
    success: '#52c41a',
    warning: '#faad14',
    error: '#ff4d4f',
    grey0: '#000000',
    grey5: '#d9d9d9',
    white: '#ffffff'
  },
  Button: {
    raised: true,
    buttonStyle: {
      borderRadius: 8
    }
  },
  Input: {
    containerStyle: {
      paddingHorizontal: 0
    }
  }
};
