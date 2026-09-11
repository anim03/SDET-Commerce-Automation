import {
  expect,
  test,
} from "@playwright/test";

import { LoginPage } from "../../pages/LoginPage";

test.describe(
  "Authentication @smoke @auth",
  () => {

    test(
      "user can login successfully",
      async ({ page }) => {

        const email =
          process.env.TEST_USER_EMAIL;

        const password =
          process.env.TEST_USER_PASSWORD;

        if (!email || !password) {
          throw new Error(
            "TEST_USER_EMAIL and TEST_USER_PASSWORD are required."
          );
        }

        const loginPage =
          new LoginPage(page);

        await loginPage.goto();

        await loginPage.login(
          email,
          password
        );

        await expect(page).toHaveURL(
          /\/dashboard$/
        );

        await expect(
          page.getByText(
            "You are successfully authenticated."
          )
        ).toBeVisible();
      }
    );
  }
);
