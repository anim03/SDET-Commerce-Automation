import apiClient from "./apiClient";
import type {
  AddToCartRequest,
  CartItemResponse,
  UpdateCartItemRequest,
} from "../types/cart";

export const getCart = async (): Promise<CartItemResponse[]> => {
  const response = await apiClient.get<CartItemResponse[]>(
    "/api/cart"
  );

  return response.data;
};

export const addToCart = async (
  request: AddToCartRequest
): Promise<CartItemResponse> => {
  const response = await apiClient.post<CartItemResponse>(
    "/api/cart/items",
    request
  );

  return response.data;
};

export const updateCartItem = async (
  cartItemId: number,
  request: UpdateCartItemRequest
): Promise<CartItemResponse> => {
  const response = await apiClient.put<CartItemResponse>(
    `/api/cart/items/${cartItemId}`,
    request
  );

  return response.data;
};

export const removeCartItem = async (
  cartItemId: number
): Promise<void> => {
  await apiClient.delete(
    `/api/cart/items/${cartItemId}`
  );
};

export const clearCart = async (): Promise<void> => {
  await apiClient.delete("/api/cart");
};
