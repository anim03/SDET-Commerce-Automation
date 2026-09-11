import {
  expect,
  type Page,
} from "@playwright/test";

export class CartPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly heading =
    this.page.getByRole(
      "heading",
      { name: "Your Cart" }
    );

  async verifyLoaded() {
    await expect(
      this.heading
    ).toBeVisible();

    await expect(
      this.page
    ).toHaveURL(/\/cart$/);
  }

  async verifyProductVisible(
    productName: string
  ) {
    await expect(
      this.page.getByRole(
        "heading",
        {
          name: productName,
          exact: true,
        }
      )
    ).toBeVisible();
  }

  async updateQuantityForProduct(
    productName: string,
    quantity: number
  ) {
    const item = this.page
      .locator(".cart-item")
      .filter({
        has: this.page.getByRole(
          "heading",
          {
            name: productName,
            exact: true,
          }
        ),
      });

    const quantityInput =
      item.getByLabel("Quantity");

    await quantityInput.fill(
      String(quantity)
    );

    await expect(
      quantityInput
    ).toHaveValue(
      String(quantity)
    );
  }

  async removeProduct(
    productName: string
  ) {
    const item = this.page
      .locator(".cart-item")
      .filter({
        has: this.page.getByRole(
          "heading",
          {
            name: productName,
            exact: true,
          }
        ),
      });

    await item
      .getByRole(
        "button",
        {
          name: "Remove",
          exact: true,
        }
      )
      .click();
  }

  async verifyProductNotVisible(
    productName: string
  ) {
    await expect(
      this.page.getByRole(
        "heading",
        {
          name: productName,
          exact: true,
        }
      )
    ).toHaveCount(0);
  }

  async proceedToCheckout() {
    await this.page
      .getByRole(
        "button",
        {
          name: "Proceed to Checkout",
          exact: true,
        }
      )
      .click();
  }


  async clearCart() {
    await this.page
      .getByRole(
        "button",
        {
          name: "Clear Cart",
          exact: true,
        }
      )
      .click();
  }

  async verifyEmptyCart() {
    await expect(
      this.page.getByRole(
        "heading",
        {
          name: "Your cart is empty",
          exact: true,
        }
      )
    ).toBeVisible();
  }
}
