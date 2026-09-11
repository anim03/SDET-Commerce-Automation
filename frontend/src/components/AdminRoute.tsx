import {
  Navigate,
} from "react-router-dom";

import type {
  ReactNode,
} from "react";

import {
  useAuth,
} from "../context/useAuth";

import AppLayout from "./AppLayout";

interface AdminRouteProps {
  children: ReactNode;
}

const AdminRoute = ({
  children,
}: AdminRouteProps) => {
  const {
    isAuthenticated,
    isAdmin,
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

  if (!isAdmin) {
    return (
      <Navigate
        to="/dashboard"
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

export default AdminRoute;
