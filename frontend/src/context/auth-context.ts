import { createContext } from "react";

import type {
  LoginRequest,
  LoginResponse,
  UserResponse,
} from "../types/auth";

export interface AuthContextType {
  user: UserResponse | null;
  role: string | null;
  isAuthenticated: boolean;
  isAdmin: boolean;
  loading: boolean;
  login: (
    credentials: LoginRequest
  ) => Promise<LoginResponse>;
  logout: () => void;
}

export const AuthContext =
  createContext<AuthContextType | undefined>(
    undefined
  );
