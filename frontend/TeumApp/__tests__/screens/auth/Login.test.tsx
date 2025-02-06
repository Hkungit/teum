import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react-native';
import { NavigationContainer } from '@react-navigation/native';
import { ThemeProvider } from '@rneui/themed';
import { I18nextProvider } from 'react-i18next';
import Login from '../../../src/screens/auth/Login';
import { authService } from '../../../src/services/auth';
import i18n from '../../../src/i18n/config';
import { theme } from '../../../src/utils/theme';
import { jest, describe, it, expect, beforeEach } from '@jest/globals';
jest.mock('@react-native-async-storage/async-storage', () => ({
  setItem: jest.fn(),
  getItem: jest.fn(),
  removeItem: jest.fn()
}));

const mockNavigation = {
  navigate: jest.fn(),
  reset: jest.fn()
};

jest.mock('@react-navigation/native', () => {
  const actualNav = jest.requireActual('@react-navigation/native');
  return {
    ...actualNav,
    useNavigation: () => mockNavigation
  };
});

describe('Login Screen', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const renderLogin = (props = {}) => {
    return render(
      <NavigationContainer>
        <I18nextProvider i18n={i18n}>
          <ThemeProvider theme={theme}>
            <Login route={{}} {...props} />
          </ThemeProvider>
        </I18nextProvider>
      </NavigationContainer>
    );
  };

  it('should render login form', () => {
    const { getByPlaceholderText, getByText } = renderLogin();
    expect(getByPlaceholderText('Username')).toBeTruthy();
    expect(getByPlaceholderText('Password')).toBeTruthy();
    expect(getByText('Login')).toBeTruthy();
  });

  it('should show success message when registered', () => {
    const { getByText } = renderLogin({ route: { params: { registered: true } } });
    expect(getByText('Registration successful! Please login.')).toBeTruthy();
  });

  it('should handle successful login', async () => {
    const mockResponse = {
      data: {
        token: 'test-token',
        id: 1
      }
    };
    (authService.login as jest.Mock<any, any>).mockResolvedValueOnce(mockResponse);

    const { getByPlaceholderText, getByText } = renderLogin();
    fireEvent.changeText(getByPlaceholderText('Username'), 'testuser');
    fireEvent.changeText(getByPlaceholderText('Password'), 'password');
    fireEvent.press(getByText('Login'));

    await waitFor(() => {
      expect(authService.login).toHaveBeenCalledWith({
        username: 'testuser',
        password: 'password'
      });
      expect(mockNavigation.reset).toHaveBeenCalled();
    });
  });

  it('should handle login error', async () => {
    (authService.login as jest.Mock).mockRejectedValueOnce(new Error('Login failed'));

    const { getByPlaceholderText, getByText } = renderLogin();
    fireEvent.changeText(getByPlaceholderText('Username'), 'testuser');
    fireEvent.changeText(getByPlaceholderText('Password'), 'password');
    fireEvent.press(getByText('Login'));

    await waitFor(() => {
      expect(getByText('Login failed. Please check your credentials.')).toBeTruthy();
    });
  });
});
