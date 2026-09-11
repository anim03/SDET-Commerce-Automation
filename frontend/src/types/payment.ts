export interface PaymentRequest {
  orderId: number;
  paymentMethod: string;
}

export interface PaymentResponse {
  paymentId: number;
  orderId: number;
  amount: number;
  paymentMethod: string;
  status: string;
  transactionId: string;
  createdAt: string;
}
