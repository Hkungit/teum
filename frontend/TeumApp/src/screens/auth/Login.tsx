import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import { Input, Button, Text } from 'react-native-elements';
import { useTranslation } from 'react-i18next';
import { useNavigation } from '@react-navigation/native';
import { NavigationProps } from '../../types/navigation';
import { authService } from '../../services/auth';
import AsyncStorage from '@react-native-async-storage/async-storage';

const Login = ({ route }: { route: any }) => {
  const registered = route.params?.registered;
  const { t } = useTranslation();
  const navigation = useNavigation<NavigationProps>();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleLogin = async () => {
    try {
      setLoading(true);
      setError('');
      const response = await authService.login({ username, password });
      await AsyncStorage.setItem('token', response.data.token);
      await AsyncStorage.setItem('userId', response.data.id.toString());
      navigation.reset({
        index: 0,
        routes: [{ name: 'Home' }],
      });
    } catch (err) {
      setError(t('auth.loginError'));
      console.error('Login error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <Text h3 style={styles.title}>{t('auth.login')}</Text>
      {registered && (
        <Text style={styles.success}>{t('auth.registerSuccess')}</Text>
      )}
      <Input
        placeholder={t('auth.username')}
        value={username}
        onChangeText={setUsername}
        autoCapitalize="none"
      />
      <Input
        placeholder={t('auth.password')}
        value={password}
        onChangeText={setPassword}
        secureTextEntry
      />
      {error ? <Text style={styles.error}>{error}</Text> : null}
      <Button
        title={t('auth.login')}
        onPress={handleLogin}
        loading={loading}
        containerStyle={styles.buttonContainer}
      />
      <Button
        title={t('auth.register')}
        type="outline"
        onPress={() => navigation.navigate('Register')}
        containerStyle={styles.buttonContainer}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    justifyContent: 'center',
    backgroundColor: '#fff'
  },
  title: {
    textAlign: 'center',
    marginBottom: 30
  },
  buttonContainer: {
    marginVertical: 10
  },
  error: {
    color: '#ff4d4f',
    textAlign: 'center',
    marginBottom: 10
  },
  success: {
    color: '#52c41a',
    textAlign: 'center',
    marginBottom: 10
  }
});

export default Login;
