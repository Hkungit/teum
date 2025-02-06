import { createTheme } from '@rneui/themed';

export const theme = createTheme({
  mode: 'light',
  lightColors: {
    primary: '#1890ff',
    secondary: '#f5222d',
    success: '#52c41a',
    warning: '#faad14',
    error: '#ff4d4f',
    grey0: '#000000',
    grey5: '#d9d9d9',
    white: '#ffffff'
  },
  components: {
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
  }
});
