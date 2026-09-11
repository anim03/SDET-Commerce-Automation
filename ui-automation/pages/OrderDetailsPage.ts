import {
  expect,
  type Page,
} from "@playwright/test";

export class OrderDetailsPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly payNowButton =
    this.page.getByRole(
      "button",
      {
        name: "Pay Now",
        exact: true,
      }
    );

  readonly viewPaymentButton =
    this.page.getByRole(
      "button",
      {
        name: "View Payment",
        exact: true,
      }
    );

  async verifyLoaded() {
    await expect(
      this.page
    ).toHaveURL(/\/orders\/\d+$/);

    await expect(
      this.page.getByRole(
        "heading",
        {
          name: /Order #\d+/,
        }
      )
    ).toBeVisible();
  }

  async verifyOrderStatus(
    status: string
  ) {
    await expect(
      this.page.getByText(
        `Order Status: ${status}`
      )
    ).toBeVisible();
  }

  async payNow() {
    await this.payNowButton.click();
  }

  async viewPayment() {
    await this.viewPaymentButton.click();
  }

  async verifyPaymentSuccessful() {
    await expect(
      this.page.getByRole(
        "heading",
        {
          name: "Payment Successful",
          exact: true,
        }
      )
    ).toBeVisible();

    await expect(
      this.payNowButton
    ).toHaveCount(0);
  }
}
