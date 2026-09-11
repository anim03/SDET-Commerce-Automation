import {
  expect,
  type Page,
} from "@playwright/test";

export class CheckoutPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly heading =
    this.page.getByRole(
      "heading",
      {
        name: "Checkout",
        exact: true,
      }
    );

  readonly placeOrderButton =
    this.page.getByRole(
      "button",
      {
        name: "Place Order",
        exact: true,
      }
    );

  async verifyLoaded() {
    await expect(
      this.heading
    ).toBeVisible();

    await expect(
      this.page
    ).toHaveURL(/\/checkout$/);
  }

  async placeOrder() {
    await this.placeOrderButton.click();
  }
}
