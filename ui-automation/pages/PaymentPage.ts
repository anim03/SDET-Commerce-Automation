import {
  expect,
  type Page,
} from "@playwright/test";

export class PaymentPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly paymentMethod =
    this.page.getByLabel(
      "Payment Method"
    );

  readonly payNowButton =
    this.page.getByRole(
      "button",
      {
        name: "Pay Now",
        exact: true,
      }
    );

  readonly paymentSuccessfulHeading =
    this.page.getByRole(
      "heading",
      {
        name: "Payment Successful",
        exact: true,
      }
    );

  readonly backToOrderButton =
    this.page
      .getByRole(
        "button",
        {
          name: "Back to Order",
          exact: true,
        }
      )
      .first();

  async verifyLoaded() {
    await expect(
      this.page
    ).toHaveURL(/\/payments\/\d+$/);
  }

  async makePayment(
    method = "CARD"
  ) {
    await this.paymentMethod.selectOption(
      method
    );

    await this.payNowButton.click();
  }

  async verifySuccessful() {
    await expect(
      this.paymentSuccessfulHeading
    ).toBeVisible();

    await expect(
      this.page.getByText(
        "Status: SUCCESS"
      )
    ).toBeVisible();
  }

  async backToOrder() {
    await this.backToOrderButton.click();
  }
}
