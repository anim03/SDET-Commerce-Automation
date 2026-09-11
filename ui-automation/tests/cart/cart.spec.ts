import {
  test,
} from "../../fixtures/auth.fixture";

import {
  CartPage,
} from "../../pages/CartPage";

import {
  ProductDetailsPage,
} from "../../pages/ProductDetailsPage";

import {
  addProductToCart,
} from "../../utils/addProductToCart";

import {
  createTestProduct,
  deleteTestProduct,
  type TestProduct,
} from "../../utils/productTestData";

test.describe(
  "Cart @regression @cart",
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
         * Safety cleanup:
         * If the test fails before removing the item,
         * clear the cart first so the product can be
         * safely deleted from the backend.
         */
        try {
          await authenticatedPage.goto(
            "/cart"
          );

          const clearCartButton =
            authenticatedPage.getByRole(
              "button",
              {
                name: "Clear Cart",
                exact: true,
              }
            );

          if (
            await clearCartButton
              .isVisible()
              .catch(() => false)
          ) {
            await clearCartButton.click();
          }
        } catch {
          console.log(
            "Cart cleanup skipped."
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
      "user can add product, update quantity and remove it",
      async ({
        authenticatedPage,
      }) => {
        await addProductToCart(
          authenticatedPage,
          product.name,
          1
        );

        const productDetailsPage =
          new ProductDetailsPage(
            authenticatedPage
          );

        await productDetailsPage
          .openCart();

        const cartPage =
          new CartPage(
            authenticatedPage
          );

        await cartPage.verifyLoaded();

        await cartPage
          .verifyProductVisible(
            product.name
          );

        await cartPage
          .updateQuantityForProduct(
            product.name,
            2
          );

        await cartPage
          .removeProduct(
            product.name
          );

        await cartPage
          .verifyProductNotVisible(
            product.name
          );
      }
    );
  }
);
