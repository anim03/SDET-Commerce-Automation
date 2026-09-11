import {
  test as setup,
  expect,
} from "@playwright/test";

import {
  LoginPage,
} from "../../pages/LoginPage";

setup(
  "authenticate normal user",
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

    await page.context().storageState({
      path: "auth/user.json",
    });
  }
);
