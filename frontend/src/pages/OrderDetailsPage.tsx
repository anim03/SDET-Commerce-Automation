import { useEffect, useState } from "react";
import {
  useNavigate,
  useParams,
} from "react-router-dom";
import axios from "axios";

import {
  cancelOrder,
  getOrderById,
} from "../api/orderApi";

import { getPaymentByOrderId } from "../api/paymentApi";

import type { OrderResponse } from "../types/order";
import type { PaymentResponse } from "../types/payment";

const OrderDetailsPage = () => {
  const { orderId } = useParams();
  const navigate = useNavigate();

  const [order, setOrder] =
    useState<OrderResponse | null>(null);

  const [payment, setPayment] =
    useState<PaymentResponse | null>(null);

  const [loading, setLoading] = useState(true);
  const [cancelling, setCancelling] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadOrderDetails = async () => {
      if (!orderId) {
        setError("Invalid order ID.");
        setLoading(false);
        return;
      }

      const id = Number(orderId);

      if (Number.isNaN(id)) {
        setError("Invalid order ID.");
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        setError("");

        const orderData = await getOrderById(id);
        setOrder(orderData);

        try {
          const paymentData =
            await getPaymentByOrderId(id);

          setPayment(paymentData);
        } catch (paymentError) {
          /*
           * No payment may exist yet.
           * In that case the order can still be paid.
           */
          if (
            axios.isAxiosError(paymentError) &&
            paymentError.response?.status !== 404
          ) {
            console.error(
              "Unable to load payment:",
              paymentError
            );
          }

          setPayment(null);
        }
      } catch {
        setError("Unable to load order.");
      } finally {
        setLoading(false);
      }
    };

    loadOrderDetails();
  }, [orderId]);

  const handleCancel = async () => {
    if (!order) {
      return;
    }

    try {
      setCancelling(true);
      setError("");

      const updatedOrder =
        await cancelOrder(order.orderId);

      setOrder(updatedOrder);
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message ||
            "Unable to cancel order."
        );
      } else {
        setError("Unable to cancel order.");
      }
    } finally {
      setCancelling(false);
    }
  };

  if (loading) {
    return (
      <div className="page-container">
        <p>Loading order...</p>
      </div>
    );
  }

  if (!order) {
    return (
      <div className="page-container">
        <div className="card">
          <p className="error-message">
            {error || "Order not found."}
          </p>

          <button
            onClick={() => navigate("/orders")}
          >
            Back to Orders
          </button>
        </div>
      </div>
    );
  }

  const paymentSuccessful =
    payment?.status === "SUCCESS";

  const orderCancelled =
    order.status === "CANCELLED";

  return (
    <div className="order-details-page">
      <div className="order-details-card">
        <button
          className="secondary-button back-button"
          onClick={() => navigate("/orders")}
        >
          Back to Orders
        </button>

        <h1>Order #{order.orderId}</h1>

        {error && (
          <p className="error-message">
            {error}
          </p>
        )}

        <div className="order-meta">
          <p>
            <strong>Order Status:</strong>{" "}
            {order.status}
          </p>

          <p>
            <strong>Created:</strong>{" "}
            {new Date(
              order.createdAt
            ).toLocaleString()}
          </p>
        </div>

        {paymentSuccessful && payment && (
          <div className="payment-status-box">
            <h3>Payment Successful</h3>

            <p>
              <strong>Payment Status:</strong>{" "}
              {payment.status}
            </p>

            <p>
              <strong>Payment Method:</strong>{" "}
              {payment.paymentMethod}
            </p>

            <p>
              <strong>Transaction ID:</strong>{" "}
              {payment.transactionId}
            </p>
          </div>
        )}

        <div className="order-items">
          {order.items.map((item) => (
            <div
              className="order-item-row"
              key={item.productId}
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
          <span>Total Amount</span>

          <strong>
            ${order.totalAmount.toFixed(2)}
          </strong>
        </div>

        {paymentSuccessful && payment && (
          <button
            onClick={() =>
              navigate(
                `/payments/${order.orderId}`
              )
            }
          >
            View Payment
          </button>
        )}

        {!paymentSuccessful &&
          !orderCancelled && (
            <>
              <button
                onClick={() =>
                  navigate(
                    `/payments/${order.orderId}`
                  )
                }
              >
                Pay Now
              </button>

              <button
                className="danger-button order-cancel-button"
                onClick={handleCancel}
                disabled={cancelling}
              >
                {cancelling
                  ? "Cancelling..."
                  : "Cancel Order"}
              </button>
            </>
          )}

        {orderCancelled && (
          <div className="cancelled-status-box">
            Order Cancelled
          </div>
        )}
      </div>
    </div>
  );
};

export default OrderDetailsPage;
