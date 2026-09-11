import {
  test,
} from "../../fixtures/admin.fixture";

import {
  AdminProductsPage,
} from "../../pages/AdminProductsPage";

test.describe(
  "Admin Product Management @regression @admin",
  () => {
    test(
      "admin can create update and delete a product",
      async ({
        adminPage,
      }) => {
        const suffix =
          Date.now();

        const originalName =
          `PW Admin Product ${suffix}`;

        const updatedName =
          `PW Admin Updated ${suffix}`;

        const adminProductsPage =
          new AdminProductsPage(
            adminPage
          );

        await adminProductsPage.goto();

        await adminProductsPage
          .verifyLoaded();

        await adminProductsPage
          .createProduct(
            originalName,
            "Created by Playwright",
            199.99,
            10
          );

        await adminProductsPage
          .verifyProductVisible(
            originalName
          );

        await adminProductsPage
          .editProduct(
            originalName,
            updatedName,
            "Updated by Playwright",
            249.99,
            15
          );

        await adminProductsPage
          .verifyProductVisible(
            updatedName
          );

        await adminProductsPage
          .deleteProduct(
            updatedName
          );

        await adminProductsPage
          .verifyProductNotVisible(
            updatedName
          );
      }
    );
  }
);
