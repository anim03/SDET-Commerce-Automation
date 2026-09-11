import {
  test,
} from "../../fixtures/auth.fixture";

import {
  addProductToCart,
} from "../../utils/addProductToCart";

import {
  ProductDetailsPage,
} from "../../pages/ProductDetailsPage";

import {
  CartPage,
} from "../../pages/CartPage";

import {
  CheckoutPage,
} from "../../pages/CheckoutPage";

import {
  OrderDetailsPage,
} from "../../pages/OrderDetailsPage";

import {
  PaymentPage,
} from "../../pages/PaymentPage";

import {
  createTestProduct,
  deleteTestProduct,
  type TestProduct,
} from "../../utils/productTestData";

test.describe(
  "Order and Payment @smoke @orders @payment",
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
         * Clear any leftover cart state first.
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

        /*
         * Product may be referenced by historical
         * order data after checkout. If backend
         * prevents deletion, do not fail the
         * completed business-flow test.
         */
        if (product?.id) {
          try {
            await deleteTestProduct(
              product.id
            );
          } catch {
            console.log(
              `Product ${product.id} retained because it is referenced by order history.`
            );
          }
        }
      }
    );

    test(
      "user can checkout, create order and complete payment",
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

        await productDetailsPage.openCart();

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
          .proceedToCheckout();

        const checkoutPage =
          new CheckoutPage(
            authenticatedPage
          );

        await checkoutPage.verifyLoaded();

        await checkoutPage.placeOrder();

        const orderDetailsPage =
          new OrderDetailsPage(
            authenticatedPage
          );

        await orderDetailsPage.verifyLoaded();

        await orderDetailsPage
          .verifyOrderStatus(
            "CREATED"
          );

        await orderDetailsPage.payNow();

        const paymentPage =
          new PaymentPage(
            authenticatedPage
          );

        await paymentPage.verifyLoaded();

        await paymentPage.makePayment(
          "CARD"
        );

        await paymentPage
          .verifySuccessful();

        await paymentPage.backToOrder();

        await orderDetailsPage.verifyLoaded();

        await orderDetailsPage
          .verifyPaymentSuccessful();
      }
    );
  }
);
