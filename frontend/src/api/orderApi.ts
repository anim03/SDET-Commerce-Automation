import apiClient from "./apiClient";
import type { OrderResponse } from "../types/order";

export const createOrder = async (): Promise<OrderResponse> => {
  const response = await apiClient.post<OrderResponse>(
    "/api/orders"
  );

  return response.data;
};

export const getOrders = async (): Promise<OrderResponse[]> => {
  const response = await apiClient.get<OrderResponse[]>(
    "/api/orders"
  );

  return response.data;
};

export const getOrderById = async (
  orderId: number
): Promise<OrderResponse> => {
  const response = await apiClient.get<OrderResponse>(
    `/api/orders/${orderId}`
  );

  return response.data;
};

export const cancelOrder = async (
  orderId: number
): Promise<OrderResponse> => {
  const response = await apiClient.put<OrderResponse>(
    `/api/orders/${orderId}/cancel`
  );

  return response.data;
};
