import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
  useParams,
} from "react-router-dom";

import axios from "axios";

import {
  createPayment,
  getPaymentByOrderId,
} from "../api/paymentApi";

import type { PaymentResponse } from "../types/payment";

const PaymentPage = () => {
  const { orderId } = useParams();
  const navigate = useNavigate();

  const [paymentMethod, setPaymentMethod] =
    useState("CARD");

  const [payment, setPayment] =
    useState<PaymentResponse | null>(null);

  const [loading, setLoading] =
    useState(true);

  const [processing, setProcessing] =
    useState(false);

  const [error, setError] =
    useState("");

  useEffect(() => {
    const loadExistingPayment = async () => {
      if (!orderId) {
        setError("Invalid order ID.");
        setLoading(false);
        return;
      }

      const parsedOrderId = Number(orderId);

      if (Number.isNaN(parsedOrderId)) {
        setError("Invalid order ID.");
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        setError("");

        const existingPayment =
          await getPaymentByOrderId(
            parsedOrderId
          );

        setPayment(existingPayment);
      } catch (err) {
        /*
         * 404 means this order has no payment yet.
         * In that case show the payment form.
         */
        if (
          axios.isAxiosError(err) &&
          err.response?.status === 404
        ) {
          setPayment(null);
        } else {
          setError(
            "Unable to check payment status."
          );
        }
      } finally {
        setLoading(false);
      }
    };

    loadExistingPayment();
  }, [orderId]);

  const handlePayment = async () => {
    if (!orderId) {
      setError("Invalid order ID.");
      return;
    }

    const parsedOrderId = Number(orderId);

    if (Number.isNaN(parsedOrderId)) {
      setError("Invalid order ID.");
      return;
    }

    try {
      setProcessing(true);
      setError("");

      const response = await createPayment({
        orderId: parsedOrderId,
        paymentMethod,
      });

      setPayment(response);
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message ||
            "Payment failed."
        );
      } else {
        setError("Payment failed.");
      }
    } finally {
      setProcessing(false);
    }
  };

  if (loading) {
    return (
      <div className="page-container">
        <p>Loading payment...</p>
      </div>
    );
  }

  return (
    <div className="page-container">
      <div className="payment-card">
        <button
          className="secondary-button back-button"
          onClick={() =>
            navigate(`/orders/${orderId}`)
          }
        >
          Back to Order
        </button>

        <h1>Payment</h1>

        <p className="subtitle">
          Payment details for Order #{orderId}
        </p>

        {error && (
          <p className="error-message">
            {error}
          </p>
        )}

        {!payment && !error && (
          <>
            <div className="form-group">
              <label htmlFor="paymentMethod">
                Payment Method
              </label>

              <select
                id="paymentMethod"
                value={paymentMethod}
                onChange={(event) =>
                  setPaymentMethod(
                    event.target.value
                  )
                }
              >
                <option value="CARD">
                  Card
                </option>

                <option value="UPI">
                  UPI
                </option>

                <option value="NET_BANKING">
                  Net Banking
                </option>
              </select>
            </div>

            <button
              onClick={handlePayment}
              disabled={processing}
            >
              {processing
                ? "Processing..."
                : "Pay Now"}
            </button>
          </>
        )}

        {payment && (
          <div className="payment-success">
            <h2>Payment Successful</h2>

            <p>
              <strong>Payment ID:</strong>{" "}
              {payment.paymentId}
            </p>

            <p>
              <strong>Order ID:</strong>{" "}
              {payment.orderId}
            </p>

            <p>
              <strong>Amount:</strong>{" "}
              ${payment.amount.toFixed(2)}
            </p>

            <p>
              <strong>Method:</strong>{" "}
              {payment.paymentMethod}
            </p>

            <p>
              <strong>Status:</strong>{" "}
              {payment.status}
            </p>

            <p>
              <strong>Transaction ID:</strong>{" "}
              {payment.transactionId}
            </p>

            <p>
              <strong>Created:</strong>{" "}
              {new Date(
                payment.createdAt
              ).toLocaleString()}
            </p>

            <button
              onClick={() =>
                navigate(
                  `/orders/${payment.orderId}`
                )
              }
            >
              Back to Order
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default PaymentPage;
