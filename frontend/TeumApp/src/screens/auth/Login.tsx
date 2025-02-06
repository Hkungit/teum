import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import { Input, Button, Text } from 'react-native-elements';
import { useTranslation } from 'react-i18next';
import { useNavigation } from '@react-navigation/native';
import { NavigationProps } from '../../types/navigation';
import api from '../../services/api';
import AsyncStorage from '@react-native-async-storage/async-storage';

const Login = () => {
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
      const response = await api.post('/api/auth/login', { username, password });
      await AsyncStorage.setItem('token', response.data.token);
      navigation.navigate('Home');
    } catch (err) {
      setError(t('auth.loginError'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <Text h3 style={styles.title}>{t('auth.login')}</Text>
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
  }
});

export default Login;
