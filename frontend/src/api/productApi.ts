import apiClient from "./apiClient";
import type {
  ProductRequest,
  ProductResponse,
} from "../types/product";

export const getAllProducts = async (): Promise<ProductResponse[]> => {
  const response = await apiClient.get<ProductResponse[]>(
    "/api/products"
  );

  return response.data;
};

export const getProductById = async (
  id: number
): Promise<ProductResponse> => {
  const response = await apiClient.get<ProductResponse>(
    `/api/products/${id}`
  );

  return response.data;
};

export const searchProducts = async (
  name: string
): Promise<ProductResponse[]> => {
  const response = await apiClient.get<ProductResponse[]>(
    "/api/products/search",
    {
      params: {
        name,
      },
    }
  );

  return response.data;
};

export const createProduct = async (
  request: ProductRequest
): Promise<ProductResponse> => {
  const response = await apiClient.post<ProductResponse>(
    "/api/products",
    request
  );

  return response.data;
};

export const updateProduct = async (
  id: number,
  request: ProductRequest
): Promise<ProductResponse> => {
  const response = await apiClient.put<ProductResponse>(
    `/api/products/${id}`,
    request
  );

  return response.data;
};

export const deleteProduct = async (
  id: number
): Promise<void> => {
  await apiClient.delete(`/api/products/${id}`);
};
