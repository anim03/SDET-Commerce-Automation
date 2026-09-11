import {
  test,
} from "../../fixtures/auth.fixture";

import {
  DashboardPage,
} from "../../pages/DashboardPage";

import {
  ProductsPage,
} from "../../pages/ProductsPage";

import {
  ProductDetailsPage,
} from "../../pages/ProductDetailsPage";

import {
  CartPage,
} from "../../pages/CartPage";

import {
  createTestProduct,
  deleteTestProduct,
  type TestProduct,
} from "../../utils/productTestData";

test.describe(
  "Product Details @regression @products",
  () => {
    let product: TestProduct;

    test.beforeEach(async () => {
      product =
        await createTestProduct();
    });

    test.afterEach(
      async ({
        authenticatedPage,
      }) => {
        /*
         * This test adds the product to the cart.
         *
         * Clear the cart first so the backend no longer
         * has a cart-item reference to the test product.
         * Then safely delete the product through the API.
         */
        const cartPage =
          new CartPage(
            authenticatedPage
          );

        try {
          await authenticatedPage.goto(
            "/cart"
          );

          await cartPage.clearCart();
        } catch {
          console.log(
            "Cart cleanup skipped or cart already empty."
          );
        }

        if (product?.id) {
          await deleteTestProduct(
            product.id
          );
        }
      }
    );

    test(
      "authenticated user can view product and add it to cart",
      async ({
        authenticatedPage,
      }) => {
        const dashboardPage =
          new DashboardPage(
            authenticatedPage
          );

        const productsPage =
          new ProductsPage(
            authenticatedPage
          );

        const productDetailsPage =
          new ProductDetailsPage(
            authenticatedPage
          );

        await dashboardPage
          .openProducts();

        await productsPage
          .verifyLoaded();

        await productsPage
          .search(product.name);

        await productsPage
          .verifyProductVisible(
            product.name
          );

        await productsPage
          .openProduct(
            product.name
          );

        await productDetailsPage
          .verifyLoaded(
            product.name
          );

        await productDetailsPage
          .setQuantity(1);

        await productDetailsPage
          .addToCart();

        await productDetailsPage
          .verifyAddedToCart(
            product.name
          );
      }
    );
  }
);
