import {
  test as base,
  expect,
  type Page,
} from "@playwright/test";

type AuthFixtures = {
  authenticatedPage: Page;
};

export const test =
  base.extend<AuthFixtures>({
    authenticatedPage: async (
      { page },
      use
    ) => {
      await page.goto("/dashboard");

      await expect(
        page
      ).toHaveURL(/\/dashboard$/);

      await expect(
        page.getByText(
          "You are successfully authenticated."
        )
      ).toBeVisible();

      await use(page);
    },
  });

export { expect };
