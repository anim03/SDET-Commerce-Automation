import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
  useParams,
} from "react-router-dom";

import axios from "axios";

import { getProductById } from "../api/productApi";
import { addToCart } from "../api/cartApi";

import type { ProductResponse } from "../types/product";

const ProductDetailsPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [product, setProduct] =
    useState<ProductResponse | null>(null);

  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);
  const [adding, setAdding] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  useEffect(() => {
    const loadProduct = async () => {
      if (!id) {
        setError("Invalid product ID.");
        setLoading(false);
        return;
      }

      const productId = Number(id);

      if (Number.isNaN(productId)) {
        setError("Invalid product ID.");
        setLoading(false);
        return;
      }

      try {
        setLoading(true);
        setError("");

        const data = await getProductById(productId);

        setProduct(data);
      } catch {
        setError("Unable to load product.");
      } finally {
        setLoading(false);
      }
    };

    loadProduct();
  }, [id]);

  const handleAddToCart = async () => {
    if (!product) {
      return;
    }

    if (quantity < 1) {
      setError("Quantity must be at least 1.");
      return;
    }

    if (quantity > product.stock) {
      setError("Quantity cannot exceed available stock.");
      return;
    }

    try {
      setAdding(true);
      setError("");
      setSuccess("");

      await addToCart({
        productId: product.id,
        quantity,
      });

      setSuccess(
        `${product.name} added to cart successfully.`
      );
    } catch (err) {
      if (axios.isAxiosError(err)) {
        setError(
          err.response?.data?.message ||
            "Unable to add product to cart."
        );
      } else {
        setError("Unable to add product to cart.");
      }
    } finally {
      setAdding(false);
    }
  };

  if (loading) {
    return (
      <div className="page-container">
        <p>Loading product...</p>
      </div>
    );
  }

  if (error && !product) {
    return (
      <div className="page-container">
        <div className="card">
          <p className="error-message">
            {error}
          </p>

          <button
            onClick={() =>
              navigate("/products")
            }
          >
            Back to Products
          </button>
        </div>
      </div>
    );
  }

  if (!product) {
    return null;
  }

  return (
    <div className="page-container">
      <div className="product-details-card">
        <button
          className="secondary-button back-button"
          onClick={() =>
            navigate("/products")
          }
        >
          Back to Products
        </button>

        <h1>{product.name}</h1>

        <p className="product-description">
          {product.description ||
            "No description available"}
        </p>

        <div className="product-details-info">
          <p>
            <strong>Product ID:</strong>{" "}
            {product.id}
          </p>

          <p>
            <strong>Price:</strong>{" "}
            ${product.price}
          </p>

          <p>
            <strong>Available Stock:</strong>{" "}
            {product.stock}
          </p>
        </div>

        <div className="quantity-section">
          <label htmlFor="quantity">
            Quantity
          </label>

          <input
            id="quantity"
            type="number"
            min="1"
            max={product.stock}
            value={quantity}
            onChange={(event) =>
              setQuantity(
                Number(event.target.value)
              )
            }
          />
        </div>

        {error && (
          <p className="error-message">
            {error}
          </p>
        )}

        {success && (
          <p className="success-message">
            {success}
          </p>
        )}

        <button
          onClick={handleAddToCart}
          disabled={
            adding ||
            product.stock === 0
          }
        >
          {product.stock === 0
            ? "Out of Stock"
            : adding
              ? "Adding..."
              : "Add to Cart"}
        </button>

        <button
          className="secondary-button view-cart-button"
          onClick={() => navigate("/cart")}
        >
          View Cart
        </button>
      </div>
    </div>
  );
};

export default ProductDetailsPage;
