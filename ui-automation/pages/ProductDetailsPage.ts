import {
  expect,
  type Page,
} from "@playwright/test";

export class ProductDetailsPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly quantityInput =
    this.page.getByLabel("Quantity");

  readonly addToCartButton =
    this.page.getByRole(
      "button",
      { name: "Add to Cart" }
    );

  readonly viewCartButton =
    this.page.getByRole(
      "button",
      { name: "View Cart" }
    );

  async verifyLoaded(
    productName: string
  ) {
    await expect(
      this.page.getByRole(
        "heading",
        { name: productName }
      )
    ).toBeVisible();

    await expect(
      this.page
    ).toHaveURL(/\/products\/\d+$/);
  }

  async setQuantity(
    quantity: number
  ) {
    await this.quantityInput.fill(
      String(quantity)
    );
  }

  async addToCart() {
    await this.addToCartButton.click();
  }

  async verifyAddedToCart(
    productName: string
  ) {
    await expect(
      this.page.getByText(
        `${productName} added to cart successfully.`
      )
    ).toBeVisible();
  }

  async openCart() {
    await this.viewCartButton.click();
  }
}
