import {
  test as base,
  expect,
  type Page,
} from "@playwright/test";

type AdminFixtures = {
  adminPage: Page;
};

export const test =
  base.extend<AdminFixtures>({
    adminPage: async (
      { page },
      use
    ) => {
      await page.goto("/dashboard");

      await expect(
        page
      ).toHaveURL(/\/dashboard$/);

      await expect(
        page.getByText(
          "Role: Administrator"
        )
      ).toBeVisible();

      await use(page);
    },
  });

export { expect };
