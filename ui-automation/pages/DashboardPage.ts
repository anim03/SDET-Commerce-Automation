import {
  expect,
  type Page,
} from "@playwright/test";

export class DashboardPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly welcomeHeading =
    this.page.getByRole(
      "heading",
      { name: /Welcome,/ }
    );

  readonly productsLink =
    this.page.getByRole(
      "button",
      {
        name: "Products",
        exact: true,
      }
    );

  async verifyLoaded() {
    await expect(
      this.welcomeHeading
    ).toBeVisible();

    await expect(
      this.page
    ).toHaveURL(/\/dashboard$/);
  }

  async openProducts() {
    await this.productsLink.click();
  }
}
