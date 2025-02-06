import { jest } from '@jest/globals';

export type JestMockWithPromise = jest.Mock<Promise<any>, any[]>;

export interface AuthServiceMock {
  login: JestMockWithPromise;
  register: JestMockWithPromise;
}

export type AuthResponse = {
  token: string;
  id: number;
  username: string;
  email: string;
};
