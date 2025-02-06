import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react-native';
import { NavigationContainer } from '@react-navigation/native';
import { ThemeProvider } from '@rneui/themed';
import { I18nextProvider } from 'react-i18next';
import Register from '../../../src/screens/auth/Register';
import { authService } from '../../../src/services/auth';
import i18n from '../../../src/i18n/config';
import { theme } from '../../../src/utils/theme';
import { jest, describe, it, expect, beforeEach } from '@jest/globals';

const mockNavigation = {
  navigate: jest.fn(),
  goBack: jest.fn()
};

jest.mock('@react-navigation/native', () => {
  const actualNav = jest.requireActual('@react-navigation/native');
  return {
    ...actualNav,
    useNavigation: () => mockNavigation
  };
});

describe('Register Screen', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  const renderRegister = () => {
    return render(
      <NavigationContainer>
        <I18nextProvider i18n={i18n}>
          <ThemeProvider theme={theme}>
            <Register />
          </ThemeProvider>
        </I18nextProvider>
      </NavigationContainer>
    );
  };

  it('should render registration form', () => {
    const { getByPlaceholderText, getByText } = renderRegister();
    expect(getByPlaceholderText('Username')).toBeTruthy();
    expect(getByPlaceholderText('Email')).toBeTruthy();
    expect(getByPlaceholderText('Password')).toBeTruthy();
    expect(getByPlaceholderText('First Name')).toBeTruthy();
    expect(getByPlaceholderText('Last Name')).toBeTruthy();
    expect(getByText('Register')).toBeTruthy();
  });

  it('should handle successful registration', async () => {
    (authService.register as jest.Mock<any, any>).mockResolvedValueOnce({});

    const { getByPlaceholderText, getByText } = renderRegister();
    fireEvent.changeText(getByPlaceholderText('Username'), 'testuser');
    fireEvent.changeText(getByPlaceholderText('Email'), 'test@example.com');
    fireEvent.changeText(getByPlaceholderText('Password'), 'password');
    fireEvent.changeText(getByPlaceholderText('First Name'), 'Test');
    fireEvent.changeText(getByPlaceholderText('Last Name'), 'User');
    fireEvent.press(getByText('Register'));

    await waitFor(() => {
      expect(authService.register).toHaveBeenCalledWith({
        username: 'testuser',
        email: 'test@example.com',
        password: 'password',
        firstName: 'Test',
        lastName: 'User'
      });
      expect(mockNavigation.navigate).toHaveBeenCalledWith('Login', { registered: true });
    });
  });

  it('should handle registration error', async () => {
    (authService.register as jest.Mock).mockRejectedValueOnce(new Error('Registration failed'));

    const { getByPlaceholderText, getByText } = renderRegister();
    fireEvent.changeText(getByPlaceholderText('Username'), 'testuser');
    fireEvent.changeText(getByPlaceholderText('Email'), 'test@example.com');
    fireEvent.changeText(getByPlaceholderText('Password'), 'password');
    fireEvent.press(getByText('Register'));

    await waitFor(() => {
      expect(getByText('Registration failed. Please try again.')).toBeTruthy();
    });
  });
});
