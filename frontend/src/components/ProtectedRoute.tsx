import { Navigate } from "react-router-dom";
import type { ReactNode } from "react";

import { useAuth } from "../context/useAuth";
import AppLayout from "./AppLayout";

interface ProtectedRouteProps {
  children: ReactNode;
}

const ProtectedRoute = ({
  children,
}: ProtectedRouteProps) => {
  const {
    isAuthenticated,
    loading,
  } = useAuth();

  if (loading) {
    return (
      <div className="page-container">
        <p>Loading...</p>
      </div>
    );
  }

  if (!isAuthenticated) {
    return (
      <Navigate
        to="/login"
        replace
      />
    );
  }

  return (
    <AppLayout>
      {children}
    </AppLayout>
  );
};

export default ProtectedRoute;
