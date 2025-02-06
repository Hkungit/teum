import api from './api';
import { Product } from '../types/product';

export interface ProductsResponse {
  content: Product[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export interface ProductSearchParams {
  name?: string;
  categories?: string[];
  page?: number;
  size?: number;
}

export const productService = {
  getProducts: (params: ProductSearchParams) =>
    api.get<ProductsResponse>('/api/products', { params }),
    
  getProduct: (id: number) =>
    api.get<Product>(`/api/products/${id}`),
    
  searchProducts: (name: string, params?: Omit<ProductSearchParams, 'name'>) =>
    api.get<ProductsResponse>('/api/products/search', { 
      params: { name, ...params }
    }),
    
  getProductsByCategory: (categories: string[], params?: Omit<ProductSearchParams, 'categories'>) =>
    api.get<ProductsResponse>('/api/products/category', {
      params: { categories: categories.join(','), ...params }
    })
};
