import { useNavigate } from "react-router-dom";

import { useAuth } from "../context/useAuth";

const DashboardPage = () => {
  const navigate = useNavigate();

  const {
    user,
    isAdmin,
  } = useAuth();

  return (
    <div className="page-container dashboard-page">
      <div className="card dashboard-card">
        <h1>
          Welcome, {user?.name}
        </h1>

        <p>
          You are successfully authenticated.
        </p>

        <div className="profile-details">
          <p>
            <strong>User ID:</strong>{" "}
            {user?.id}
          </p>

          <p>
            <strong>Name:</strong>{" "}
            {user?.name}
          </p>

          <p>
            <strong>Email:</strong>{" "}
            {user?.email}
          </p>

          <p>
            <strong>Role:</strong>{" "}
            {isAdmin
              ? "Administrator"
              : "User"}
          </p>
        </div>

        <button
          onClick={() =>
            navigate("/products")
          }
        >
          Browse Products
        </button>

        {isAdmin && (
          <button
            className="admin-dashboard-button"
            onClick={() =>
              navigate("/admin/products")
            }
          >
            Manage Products
          </button>
        )}
      </div>
    </div>
  );
};

export default DashboardPage;
