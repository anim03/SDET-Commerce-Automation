import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import {
  clearCart,
  getCart,
  removeCartItem,
  updateCartItem,
} from "../api/cartApi";

import type { CartItemResponse } from "../types/cart";

const CartPage = () => {
  const navigate = useNavigate();

  const [items, setItems] = useState<CartItemResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [updatingItemId, setUpdatingItemId] = useState<number | null>(null);

  const loadCart = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getCart();
      setItems(data);
    } catch {
      setError("Unable to load cart.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const timer = window.setTimeout(() => {
      void loadCart();
    }, 0);

    return () => window.clearTimeout(timer);
  }, []);

  const handleQuantityChange = async (
    cartItemId: number,
    quantity: number
  ) => {
    if (quantity < 1) {
      return;
    }

    try {
      setUpdatingItemId(cartItemId);
      setError("");

      const updatedItem = await updateCartItem(
        cartItemId,
        { quantity }
      );

      setItems((currentItems) =>
        currentItems.map((item) =>
          item.cartItemId === cartItemId
            ? updatedItem
            : item
        )
      );
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message ||
            "Unable to update cart item."
        );
      } else {
        setError("Unable to update cart item.");
      }
    } finally {
      setUpdatingItemId(null);
    }
  };

  const handleRemoveItem = async (
    cartItemId: number
  ) => {
    try {
      setError("");

      await removeCartItem(cartItemId);

      setItems((currentItems) =>
        currentItems.filter(
          (item) =>
            item.cartItemId !== cartItemId
        )
      );
    } catch {
      setError("Unable to remove cart item.");
    }
  };

  const handleClearCart = async () => {
    try {
      setError("");

      await clearCart();
      setItems([]);
    } catch {
      setError("Unable to clear cart.");
    }
  };

  const total = items.reduce(
    (sum, item) => sum + item.subtotal,
    0
  );

  if (loading) {
    return (
      <div className="page-container">
        <p>Loading cart...</p>
      </div>
    );
  }

  return (
    <div className="cart-page">
      <div className="cart-header">
        <div>
          <h1>Your Cart</h1>
          <p>Review your selected products</p>
        </div>

        <button
          className="secondary-button"
          onClick={() =>
            navigate("/products")
          }
        >
          Continue Shopping
        </button>
      </div>

      {error && (
        <p className="error-message">
          {error}
        </p>
      )}

      {items.length === 0 ? (
        <div className="empty-cart">
          <h2>Your cart is empty</h2>

          <p>
            Add products to continue.
          </p>

          <button
            onClick={() =>
              navigate("/products")
            }
          >
            Browse Products
          </button>
        </div>
      ) : (
        <>
          <div className="cart-items">
            {items.map((item) => (
              <div
                className="cart-item"
                key={item.cartItemId}
              >
                <div className="cart-item-info">
                  <h2>{item.productName}</h2>

                  <p>
                    <strong>Product ID:</strong>{" "}
                    {item.productId}
                  </p>

                  <p>
                    <strong>Price:</strong>{" "}
                    ${item.price}
                  </p>
                </div>

                <div className="cart-item-actions">
                  <label
                    htmlFor={`quantity-${item.cartItemId}`}
                  >
                    Quantity
                  </label>

                  <input
                    id={`quantity-${item.cartItemId}`}
                    type="number"
                    min="1"
                    value={item.quantity}
                    disabled={
                      updatingItemId ===
                      item.cartItemId
                    }
                    onChange={(event) =>
                      handleQuantityChange(
                        item.cartItemId,
                        Number(
                          event.target.value
                        )
                      )
                    }
                  />

                  <p>
                    <strong>Subtotal:</strong>{" "}
                    ${item.subtotal}
                  </p>

                  <button
                    className="danger-button"
                    onClick={() =>
                      handleRemoveItem(
                        item.cartItemId
                      )
                    }
                  >
                    Remove
                  </button>
                </div>
              </div>
            ))}
          </div>

          <div className="cart-summary">
            <h2>Cart Summary</h2>

            <p className="cart-total">
              <strong>Total:</strong>{" "}
              ${total.toFixed(2)}
            </p>

            <button
              onClick={() =>
                navigate("/checkout")
              }
            >
              Proceed to Checkout
            </button>

            <button
              className="danger-button clear-cart-button"
              onClick={handleClearCart}
            >
              Clear Cart
            </button>
          </div>
        </>
      )}
    </div>
  );
};

export default CartPage;
