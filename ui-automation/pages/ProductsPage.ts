import {
  expect,
  type Page,
} from "@playwright/test";

export class ProductsPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly heading =
    this.page.getByRole(
      "heading",
      { name: "Products" }
    );

  readonly searchInput =
    this.page.getByPlaceholder(
      "Search products..."
    );

  readonly searchButton =
    this.page.getByRole(
      "button",
      { name: "Search" }
    );

  readonly clearButton =
    this.page.getByRole(
      "button",
      { name: "Clear" }
    );

  async verifyLoaded() {
    await expect(
      this.heading
    ).toBeVisible();

    await expect(
      this.page
    ).toHaveURL(/\/products$/);
  }

  async search(
    productName: string
  ) {
    await this.searchInput.fill(
      productName
    );

    await this.searchButton.click();
  }

  async clearSearch() {
    await this.clearButton.click();
  }

  async verifyProductVisible(
    productName: string
  ) {
    await expect(
      this.page.getByRole(
        "heading",
        { name: productName }
      )
    ).toBeVisible();
  }

  async openProduct(
    productName: string
  ) {
    const card = this.page
      .locator(".product-card")
      .filter({
        has: this.page.getByRole(
          "heading",
          {
            name: productName,
            exact: true,
          }
        ),
      });

    await card
      .getByRole(
        "button",
        {
          name: "View Product",
          exact: true,
        }
      )
      .click();
  }
}
