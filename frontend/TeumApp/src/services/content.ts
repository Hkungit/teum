import api from './api';

export interface Content {
  id: number;
  title: string;
  content: string;
  type: 'BLOG' | 'NEWS' | 'ANNOUNCEMENT' | 'PROMOTION';
  imageUrl?: string;
  published: boolean;
  publishDate?: string;
  authorId: number;
  createdAt: string;
  updatedAt: string;
}

export interface ContentResponse {
  content: Content[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export interface UserFeedback {
  feedbackType: string;
  comment?: string;
  rating?: number;
  pageUrl?: string;
  browserInfo?: string;
  deviceInfo?: string;
}

export const contentService = {
  getPublishedContent: (page = 0, size = 10) =>
    api.get<ContentResponse>('/api/content', { params: { page, size } }),

  getContentByType: (type: Content['type'], page = 0, size = 10) =>
    api.get<ContentResponse>(`/api/content/type/${type}`, { params: { page, size } }),

  getContent: (id: number) =>
    api.get<Content>(`/api/content/${id}`),

  submitFeedback: (feedback: UserFeedback) =>
    api.post('/api/feedback', feedback)
};
