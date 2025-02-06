import api from './api';
import { Product } from '../types/product';

export interface OrderItem {
  productId: number;
  quantity: number;
  price: number;
}

export interface Order {
  id: number;
  userId: number;
  orderItems: OrderItem[];
  totalAmount: number;
  status: 'PENDING' | 'PAID' | 'PROCESSING' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
  shippingAddress: string;
  trackingNumber?: string;
  createdAt: string;
  updatedAt: string;
}

export interface OrdersResponse {
  content: Order[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export const orderService = {
  createOrder: (items: OrderItem[], shippingAddress: string) =>
    api.post<Order>('/api/orders', { items, shippingAddress }),

  getUserOrders: (page = 0, size = 10) =>
    api.get<OrdersResponse>('/api/orders', { params: { page, size } }),

  getOrder: (id: number) =>
    api.get<Order>(`/api/orders/${id}`),

  cancelOrder: (id: number) =>
    api.put<Order>(`/api/orders/${id}/status`, { status: 'CANCELLED' })
};
