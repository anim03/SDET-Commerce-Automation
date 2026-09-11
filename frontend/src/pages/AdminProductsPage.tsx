import {
  useEffect,
  useState,
  type FormEvent,
} from "react";

import {
  createProduct,
  deleteProduct,
  getAllProducts,
  updateProduct,
} from "../api/productApi";

import type {
  ProductRequest,
  ProductResponse,
} from "../types/product";

const emptyForm: ProductRequest = {
  name: "",
  description: "",
  price: 0,
  stock: 0,
};

const AdminProductsPage = () => {
  const [products, setProducts] =
    useState<ProductResponse[]>([]);

  const [form, setForm] =
    useState<ProductRequest>(emptyForm);

  const [editingId, setEditingId] =
    useState<number | null>(null);

  const [loading, setLoading] =
    useState(true);

  const [saving, setSaving] =
    useState(false);

  const [error, setError] =
    useState("");

  const [success, setSuccess] =
    useState("");

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

  const handleSubmit = async (
    event: FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();

    if (!form.name.trim()) {
      setError("Product name is required.");
      return;
    }

    if (form.price < 0.01) {
      setError("Price must be at least 0.01.");
      return;
    }

    if (form.stock < 0) {
      setError("Stock cannot be negative.");
      return;
    }

    try {
      setSaving(true);
      setError("");
      setSuccess("");

      if (editingId !== null) {
        const updated = await updateProduct(
          editingId,
          form
        );

        setProducts((current) =>
          current.map((product) =>
            product.id === editingId
              ? updated
              : product
          )
        );

        setSuccess(
          "Product updated successfully."
        );
      } else {
        const created = await createProduct(
          form
        );

        setProducts((current) => [
          ...current,
          created,
        ]);

        setSuccess(
          "Product created successfully."
        );
      }

      setForm(emptyForm);
      setEditingId(null);
    } catch {
      setError(
        editingId !== null
          ? "Unable to update product."
          : "Unable to create product."
      );
    } finally {
      setSaving(false);
    }
  };

  const handleEdit = (
    product: ProductResponse
  ) => {
    setEditingId(product.id);

    setForm({
      name: product.name,
      description:
        product.description || "",
      price: product.price,
      stock: product.stock,
    });

    setError("");
    setSuccess("");

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const handleDelete = async (
    productId: number
  ) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this product?"
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");
      setSuccess("");

      await deleteProduct(productId);

      setProducts((current) =>
        current.filter(
          (product) =>
            product.id !== productId
        )
      );

      if (editingId === productId) {
        setEditingId(null);
        setForm(emptyForm);
      }

      setSuccess(
        "Product deleted successfully."
      );
    } catch {
      setError("Unable to delete product.");
    }
  };

  const handleCancelEdit = () => {
    setEditingId(null);
    setForm(emptyForm);
    setError("");
    setSuccess("");
  };

  return (
    <div className="admin-products-page">
      <div className="admin-products-header">
        <div>
          <h1>Admin Product Management</h1>
          <p>
            Create, update and delete products
          </p>
        </div>
      </div>

      <div className="admin-product-form-card">
        <h2>
          {editingId !== null
            ? "Edit Product"
            : "Create Product"}
        </h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="productName">
              Product Name
            </label>

            <input
              id="productName"
              type="text"
              value={form.name}
              onChange={(event) =>
                setForm({
                  ...form,
                  name: event.target.value,
                })
              }
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="description">
              Description
            </label>

            <input
              id="description"
              type="text"
              value={form.description || ""}
              onChange={(event) =>
                setForm({
                  ...form,
                  description:
                    event.target.value,
                })
              }
            />
          </div>

          <div className="form-group">
            <label htmlFor="price">
              Price
            </label>

            <input
              id="price"
              type="number"
              min="0.01"
              step="0.01"
              value={form.price === 0 ? "" : form.price}
              onChange={(event) =>
                setForm({
                  ...form,
                  price: Number(
                    event.target.value
                  ),
                })
              }
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="stock">
              Stock
            </label>

            <input
              id="stock"
              type="number"
              min="0"
              value={form.stock === 0 ? "" : form.stock}
              onChange={(event) =>
                setForm({
                  ...form,
                  stock: Number(
                    event.target.value
                  ),
                })
              }
              required
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
            type="submit"
            disabled={saving}
          >
            {saving
              ? "Saving..."
              : editingId !== null
                ? "Update Product"
                : "Create Product"}
          </button>

          {editingId !== null && (
            <button
              type="button"
              className="secondary-button admin-cancel-edit"
              onClick={handleCancelEdit}
            >
              Cancel Edit
            </button>
          )}
        </form>
      </div>

      <div className="admin-products-list">
        <h2>Existing Products</h2>

        {loading && (
          <p>Loading products...</p>
        )}

        {!loading &&
          products.length === 0 && (
            <p>No products found.</p>
          )}

        <div className="admin-products-grid">
          {products.map((product) => (
            <div
              className="admin-product-card"
              key={product.id}
            >
              <h3>{product.name}</h3>

              <p>
                {product.description ||
                  "No description"}
              </p>

              <p>
                <strong>Price:</strong>{" "}
                ${product.price}
              </p>

              <p>
                <strong>Stock:</strong>{" "}
                {product.stock}
              </p>

              <div className="admin-product-actions">
                <button
                  onClick={() =>
                    handleEdit(product)
                  }
                >
                  Edit
                </button>

                <button
                  className="danger-button"
                  onClick={() =>
                    handleDelete(product.id)
                  }
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default AdminProductsPage;
