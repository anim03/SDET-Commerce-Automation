import {
  useEffect,
  useState,
  type FormEvent,
} from "react";

import { useNavigate } from "react-router-dom";

import {
  getAllProducts,
  searchProducts,
} from "../api/productApi";

import type { ProductResponse } from "../types/product";

const ProductsPage = () => {
  const navigate = useNavigate();

  const [products, setProducts] = useState<ProductResponse[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadProducts = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllProducts();
      setProducts(data);
    } catch {
      setError("Unable to load products.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const timer = window.setTimeout(() => {
      void loadProducts();
    }, 0);

    return () => window.clearTimeout(timer);
  }, []);

  const handleSearch = async (
    event: FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();

    const value = searchTerm.trim();

    if (!value) {
      await loadProducts();
      return;
    }

    try {
      setLoading(true);
      setError("");

      const data = await searchProducts(value);
      setProducts(data);
    } catch {
      setError("Unable to search products.");
    } finally {
      setLoading(false);
    }
  };

  const handleClearSearch = async () => {
    setSearchTerm("");
    await loadProducts();
  };

  return (
    <div className="products-page">
      <div className="products-header">
        <div>
          <h1>Products</h1>
          <p>Browse available products</p>
        </div>
      </div>

      <form
        className="product-search"
        onSubmit={handleSearch}
      >
        <input
          type="text"
          placeholder="Search products..."
          value={searchTerm}
          onChange={(event) =>
            setSearchTerm(event.target.value)
          }
        />

        <button type="submit">
          Search
        </button>

        <button
          type="button"
          className="secondary-button"
          onClick={handleClearSearch}
        >
          Clear
        </button>
      </form>

      {loading && (
        <p>Loading products...</p>
      )}

      {error && (
        <p className="error-message">
          {error}
        </p>
      )}

      {!loading &&
        !error &&
        products.length === 0 && (
          <p>No products found.</p>
        )}

      <div className="product-grid">
        {products.map((product) => (
          <div
            className="product-card"
            key={product.id}
          >
            <h2>{product.name}</h2>

            <p className="product-description">
              {product.description ||
                "No description available"}
            </p>

            <p>
              <strong>Price:</strong>{" "}
              ${product.price}
            </p>

            <p>
              <strong>Stock:</strong>{" "}
              {product.stock}
            </p>

            <button
              onClick={() =>
                navigate(`/products/${product.id}`)
              }
            >
              View Product
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductsPage;
