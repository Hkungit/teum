export interface Product {
  id: number;
  name: string;
  description?: string;
  price: number;
  stockQuantity: number;
  imageUrl?: string;
  categories: string[];
  tags: string[];
  createdAt: string;
  updatedAt: string;
}
