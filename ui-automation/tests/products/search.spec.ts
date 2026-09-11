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
  createTestProduct,
  deleteTestProduct,
  type TestProduct,
} from "../../utils/productTestData";

test.describe(
  "Product Search @regression @products",
  () => {
    let product: TestProduct;

    test.beforeEach(async () => {
      product =
        await createTestProduct();
    });

    test.afterEach(async () => {
      if (product?.id) {
        await deleteTestProduct(
          product.id
        );
      }
    });

    test(
      "authenticated user can search products",
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
      }
    );
  }
);
