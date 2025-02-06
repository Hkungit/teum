import api from './api';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  firstName?: string;
  lastName?: string;
}

export interface AuthResponse {
  token: string;
  id: number;
  username: string;
  email: string;
}

export const authService = {
  login: (data: LoginRequest) => 
    api.post<AuthResponse>('/api/auth/login', data),
  
  register: (data: RegisterRequest) =>
    api.post('/api/auth/register', data),
    
  logout: async () => {
    await api.post('/api/auth/logout');
    localStorage.removeItem('token');
  }
};
