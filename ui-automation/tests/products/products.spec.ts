import {
  test,
  expect,
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
} from "../../utils/productTestData";

test.describe(
  "Products @smoke @products",
  () => {

    let productId: number;
    let productName: string;

    test.beforeEach(async () => {
      const product =
        await createTestProduct();

      productId = product.id;
      productName = product.name;
    });

    test.afterEach(async () => {
      if (productId) {
        await deleteTestProduct(
          productId
        );
      }
    });

    test(
      "authenticated user can view products",
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

        await dashboardPage.verifyLoaded();

        await dashboardPage.openProducts();

        await productsPage.verifyLoaded();

        await productsPage.verifyProductVisible(
          productName
        );

        await expect(
          authenticatedPage
            .getByRole(
              "button",
              {
                name: "View Product",
                exact: true,
              }
            )
            .first()
        ).toBeVisible();
      }
    );
  }
);
