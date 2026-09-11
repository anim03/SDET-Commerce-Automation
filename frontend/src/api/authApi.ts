import apiClient from "./apiClient";
import type {
  LoginRequest,
  LoginResponse,
  UserRegistrationRequest,
  UserResponse,
} from "../types/auth";

export const login = async (
  request: LoginRequest
): Promise<LoginResponse> => {
  const response = await apiClient.post<LoginResponse>(
    "/api/users/login",
    request
  );

  return response.data;
};

export const register = async (
  request: UserRegistrationRequest
): Promise<UserResponse> => {
  const response = await apiClient.post<UserResponse>(
    "/api/users/register",
    request
  );

  return response.data;
};

export const getProfile = async (): Promise<UserResponse> => {
  const response = await apiClient.get<UserResponse>(
    "/api/users/profile"
  );

  return response.data;
};
