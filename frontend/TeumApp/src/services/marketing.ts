import api from './api';

export interface Campaign {
  id: number;
  name: string;
  description?: string;
  startDate: string;
  endDate: string;
  type: 'DISCOUNT' | 'COUPON' | 'FLASH_SALE' | 'BUNDLE';
  discountAmount?: number;
  discountPercentage?: number;
  couponCode?: string;
  usageLimit?: number;
  currentUsage: number;
  products: number[];
}

export interface CampaignsResponse {
  content: Campaign[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export const marketingService = {
  getActiveCampaigns: (page = 0, size = 10) =>
    api.get<CampaignsResponse>('/api/campaigns/active', { params: { page, size } }),

  getCampaignsByProduct: (productId: number) =>
    api.get<Campaign[]>(`/api/campaigns/product/${productId}`),

  applyCoupon: (code: string, orderId: number) =>
    api.post<{ discountAmount: number }>('/api/campaigns/coupon/apply', { code, orderId })
};
