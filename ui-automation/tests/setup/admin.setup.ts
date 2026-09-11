import {
  test as setup,
  expect,
} from "@playwright/test";

import {
  LoginPage,
} from "../../pages/LoginPage";

setup(
  "authenticate admin user",
  async ({ page }) => {
    const email =
      process.env.ADMIN_USER_EMAIL;

    const password =
      process.env.ADMIN_USER_PASSWORD;

    if (!email || !password) {
      throw new Error(
        "ADMIN_USER_EMAIL and ADMIN_USER_PASSWORD are required."
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

    await page.context().storageState({
      path: "auth/admin.json",
    });
  }
);
