import { jest } from '@jest/globals';

export type JestMockWithPromise<T = any> = jest.Mock<Promise<T>>;

export interface AuthServiceMock {
  login: JestMockWithPromise;
  register: JestMockWithPromise;
}
