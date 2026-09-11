export interface OrderItemResponse {
  productId: number;
  productName: string;
  price: number;
  quantity: number;
  subtotal: number;
}

export interface OrderResponse {
  orderId: number;
  totalAmount: number;
  status: string;
  createdAt: string;
  items: OrderItemResponse[];
}
