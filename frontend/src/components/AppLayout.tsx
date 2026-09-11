import type { ReactNode } from "react";
import { useNavigate } from "react-router-dom";

import { useAuth } from "../context/useAuth";

interface AppLayoutProps {
  children: ReactNode;
}

const AppLayout = ({
  children,
}: AppLayoutProps) => {
  const navigate = useNavigate();

  const {
    user,
    isAdmin,
    logout,
  } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="app-shell">
      <header className="app-navbar">
        <div
          className="app-brand"
          onClick={() =>
            navigate("/dashboard")
          }
        >
          SDET Commerce
        </div>

        <nav className="app-nav-links">
          <button
            onClick={() =>
              navigate("/products")
            }
          >
            Products
          </button>

          <button
            onClick={() =>
              navigate("/cart")
            }
          >
            Cart
          </button>

          <button
            onClick={() =>
              navigate("/orders")
            }
          >
            Orders
          </button>

          {isAdmin && (
            <button
              onClick={() =>
                navigate("/admin/products")
              }
            >
              Admin
            </button>
          )}
        </nav>

        <div className="app-user-section">
          <span>
            {user?.name}
          </span>

          <button
            className="logout-nav-button"
            onClick={handleLogout}
          >
            Logout
          </button>
        </div>
      </header>

      <main className="app-content">
        {children}
      </main>

      <footer className="app-footer">
        SDET Commerce · Designed & Developed by Animesh Pandey
      </footer>
    </div>
  );
};

export default AppLayout;
