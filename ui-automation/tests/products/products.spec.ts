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

test.describe(
  "Products @smoke @products",
  () => {
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

        await expect(
          authenticatedPage
            .getByRole(
              "button",
              { name: "View Product" }
            )
            .first()
        ).toBeVisible();
      }
    );
  }
);
