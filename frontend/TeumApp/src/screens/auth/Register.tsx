import React, { useState } from 'react';
import { View, StyleSheet, ScrollView } from 'react-native';
import { Input, Button, Text } from 'react-native-elements';
import { useTranslation } from 'react-i18next';
import { useNavigation } from '@react-navigation/native';
import { NavigationProps } from '../../types/navigation';
import api from '../../services/api';

const Register = () => {
  const { t } = useTranslation();
  const navigation = useNavigation<NavigationProps>();
  const [form, setForm] = useState({
    username: '',
    email: '',
    password: '',
    firstName: '',
    lastName: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleRegister = async () => {
    try {
      setLoading(true);
      setError('');
      await api.post('/api/auth/register', form);
      navigation.navigate('Login');
    } catch (err) {
      setError(t('auth.registerError'));
    } finally {
      setLoading(false);
    }
  };

  const updateForm = (key: string) => (value: string) => {
    setForm(prev => ({ ...prev, [key]: value }));
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text h3 style={styles.title}>{t('auth.register')}</Text>
      <Input
        placeholder={t('auth.username')}
        value={form.username}
        onChangeText={updateForm('username')}
        autoCapitalize="none"
      />
      <Input
        placeholder={t('auth.email')}
        value={form.email}
        onChangeText={updateForm('email')}
        keyboardType="email-address"
        autoCapitalize="none"
      />
      <Input
        placeholder={t('auth.password')}
        value={form.password}
        onChangeText={updateForm('password')}
        secureTextEntry
      />
      <Input
        placeholder={t('auth.firstName')}
        value={form.firstName}
        onChangeText={updateForm('firstName')}
      />
      <Input
        placeholder={t('auth.lastName')}
        value={form.lastName}
        onChangeText={updateForm('lastName')}
      />
      {error ? <Text style={styles.error}>{error}</Text> : null}
      <Button
        title={t('auth.register')}
        onPress={handleRegister}
        loading={loading}
        containerStyle={styles.buttonContainer}
      />
      <Button
        title={t('auth.backToLogin')}
        type="outline"
        onPress={() => navigation.goBack()}
        containerStyle={styles.buttonContainer}
      />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
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

export default Register;
