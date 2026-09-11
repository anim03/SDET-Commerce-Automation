import {
  useEffect,
  useState,
  type ReactNode,
} from "react";

import {
  getProfile,
  login as loginApi,
} from "../api/authApi";

import { getRoleFromToken } from "../utils/jwt";

import { AuthContext } from "./auth-context";

import type {
  LoginRequest,
  LoginResponse,
  UserResponse,
} from "../types/auth";

export const AuthProvider = ({
  children,
}: {
  children: ReactNode;
}) => {
  const [user, setUser] =
    useState<UserResponse | null>(null);

  const [role, setRole] =
    useState<string | null>(null);

  const [loading, setLoading] =
    useState(true);

  useEffect(() => {
    const loadUser = async () => {
      const token =
        localStorage.getItem("authToken");

      if (!token) {
        setLoading(false);
        return;
      }

      try {
        const profile = await getProfile();

        setUser(profile);
        setRole(getRoleFromToken(token));
      } catch {
        localStorage.removeItem("authToken");

        setUser(null);
        setRole(null);
      } finally {
        setLoading(false);
      }
    };

    const timer = window.setTimeout(() => {
      void loadUser();
    }, 0);

    return () =>
      window.clearTimeout(timer);
  }, []);

  const login = async (
    credentials: LoginRequest
  ): Promise<LoginResponse> => {
    const response =
      await loginApi(credentials);

    localStorage.setItem(
      "authToken",
      response.token
    );

    setUser({
      id: response.id,
      name: response.name,
      email: response.email,
    });

    setRole(
      getRoleFromToken(response.token)
    );

    return response;
  };

  const logout = () => {
    localStorage.removeItem("authToken");

    setUser(null);
    setRole(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        role,
        isAuthenticated: !!user,
        isAdmin: role === "ROLE_ADMIN",
        loading,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
