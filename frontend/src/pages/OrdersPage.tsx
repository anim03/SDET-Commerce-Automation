import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { getOrders } from "../api/orderApi";
import type { OrderResponse } from "../types/order";

const OrdersPage = () => {
  const navigate = useNavigate();

  const [orders, setOrders] = useState<OrderResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadOrders = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await getOrders();
        setOrders(data);
      } catch {
        setError("Unable to load orders.");
      } finally {
        setLoading(false);
      }
    };

    loadOrders();
  }, []);

  if (loading) {
    return (
      <div className="page-container">
        <p>Loading orders...</p>
      </div>
    );
  }

  return (
    <div className="orders-page">
      <div className="orders-header">
        <div>
          <h1>My Orders</h1>
          <p>View your order history</p>
        </div>

        <button
          className="secondary-button"
          onClick={() => navigate("/products")}
        >
          Products
        </button>
      </div>

      {error && (
        <p className="error-message">
          {error}
        </p>
      )}

      {!error && orders.length === 0 && (
        <div className="empty-cart">
          <h2>No orders found</h2>

          <button
            onClick={() => navigate("/products")}
          >
            Browse Products
          </button>
        </div>
      )}

      <div className="orders-grid">
        {orders.map((order) => (
          <div
            className="order-card"
            key={order.orderId}
          >
            <h2>
              Order #{order.orderId}
            </h2>

            <p>
              <strong>Status:</strong>{" "}
              {order.status}
            </p>

            <p>
              <strong>Total:</strong>{" "}
              ${order.totalAmount.toFixed(2)}
            </p>

            <p>
              <strong>Created:</strong>{" "}
              {new Date(
                order.createdAt
              ).toLocaleString()}
            </p>

            <button
              onClick={() =>
                navigate(
                  `/orders/${order.orderId}`
                )
              }
            >
              View Order
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default OrdersPage;
