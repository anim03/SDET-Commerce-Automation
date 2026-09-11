import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import { getCart } from "../api/cartApi";
import { createOrder } from "../api/orderApi";

import type { CartItemResponse } from "../types/cart";

const CheckoutPage = () => {
  const navigate = useNavigate();

  const [items, setItems] = useState<CartItemResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [creatingOrder, setCreatingOrder] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadCart = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await getCart();
        setItems(data);
      } catch {
        setError("Unable to load checkout information.");
      } finally {
        setLoading(false);
      }
    };

    loadCart();
  }, []);

  const total = items.reduce(
    (sum, item) => sum + item.subtotal,
    0
  );

  const handleCreateOrder = async () => {
    try {
      setCreatingOrder(true);
      setError("");

      const order = await createOrder();

      navigate(`/orders/${order.orderId}`);
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message ||
            "Unable to create order."
        );
      } else {
        setError("Unable to create order.");
      }
    } finally {
      setCreatingOrder(false);
    }
  };

  if (loading) {
    return (
      <div className="page-container">
        <p>Loading checkout...</p>
      </div>
    );
  }

  return (
    <div className="checkout-page">
      <div className="checkout-card">
        <button
          className="secondary-button back-button"
          onClick={() => navigate("/cart")}
        >
          Back to Cart
        </button>

        <h1>Checkout</h1>

        <p className="subtitle">
          Review your order before placing it.
        </p>

        {error && (
          <p className="error-message">
            {error}
          </p>
        )}

        {items.length === 0 ? (
          <div>
            <p>Your cart is empty.</p>

            <button
              onClick={() => navigate("/products")}
            >
              Browse Products
            </button>
          </div>
        ) : (
          <>
            <div className="checkout-items">
              {items.map((item) => (
                <div
                  className="checkout-item"
                  key={item.cartItemId}
                >
                  <div>
                    <strong>
                      {item.productName}
                    </strong>

                    <p>
                      ${item.price} × {item.quantity}
                    </p>
                  </div>

                  <strong>
                    ${item.subtotal.toFixed(2)}
                  </strong>
                </div>
              ))}
            </div>

            <div className="checkout-total">
              <span>Total</span>

              <strong>
                ${total.toFixed(2)}
              </strong>
            </div>

            <button
              onClick={handleCreateOrder}
              disabled={creatingOrder}
            >
              {creatingOrder
                ? "Creating Order..."
                : "Place Order"}
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default CheckoutPage;
