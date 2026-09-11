import apiClient from "./apiClient";

import type {
  PaymentRequest,
  PaymentResponse,
} from "../types/payment";

export const createPayment = async (
  request: PaymentRequest
): Promise<PaymentResponse> => {
  const response = await apiClient.post<PaymentResponse>(
    "/api/payments",
    request
  );

  return response.data;
};

export const getPaymentByOrderId = async (
  orderId: number
): Promise<PaymentResponse> => {
  const response = await apiClient.get<PaymentResponse>(
    `/api/payments/order/${orderId}`
  );

  return response.data;
};
