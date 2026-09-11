import {
  test,
  expect,
} from "../../fixtures/auth.fixture";

test.describe(
  "Admin RBAC @smoke @rbac",
  () => {
    test(
      "normal user cannot access admin products page",
      async ({
        authenticatedPage,
      }) => {
        await authenticatedPage.goto(
          "/admin/products"
        );

        await expect(
          authenticatedPage
        ).toHaveURL(
          /\/dashboard$/
        );

        await expect(
          authenticatedPage.getByRole(
            "button",
            {
              name: "Admin",
              exact: true,
            }
          )
        ).toHaveCount(0);
      }
    );
  }
);
