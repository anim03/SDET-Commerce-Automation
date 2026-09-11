import {
  expect,
  type Page,
} from "@playwright/test";

export class AdminProductsPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly heading =
    this.page.getByRole(
      "heading",
      {
        name: "Admin Product Management",
        exact: true,
      }
    );

  readonly nameInput =
    this.page.getByLabel(
      "Product Name"
    );

  readonly descriptionInput =
    this.page.getByLabel(
      "Description"
    );

  readonly priceInput =
    this.page.getByLabel(
      "Price"
    );

  readonly stockInput =
    this.page.getByLabel(
      "Stock"
    );

  readonly createButton =
    this.page.getByRole(
      "button",
      {
        name: "Create Product",
        exact: true,
      }
    );

  readonly updateButton =
    this.page.getByRole(
      "button",
      {
        name: "Update Product",
        exact: true,
      }
    );

  async goto() {
    await this.page.goto(
      "/admin/products"
    );
  }

  async verifyLoaded() {
    await expect(
      this.heading
    ).toBeVisible();

    await expect(
      this.page
    ).toHaveURL(
      /\/admin\/products$/
    );
  }

  async createProduct(
    name: string,
    description: string,
    price: number,
    stock: number
  ) {
    await this.nameInput.fill(name);

    await this.descriptionInput.fill(
      description
    );

    await this.priceInput.fill(
      String(price)
    );

    await this.stockInput.fill(
      String(stock)
    );

    await this.createButton.click();

    await expect(
      this.page.getByText(
        "Product created successfully."
      )
    ).toBeVisible();
  }

  private productCard(
    productName: string
  ) {
    return this.page
      .locator(".admin-product-card")
      .filter({
        has: this.page.getByRole(
          "heading",
          {
            name: productName,
            exact: true,
          }
        ),
      });
  }

  async verifyProductVisible(
    productName: string
  ) {
    await expect(
      this.productCard(productName)
    ).toBeVisible();
  }

  async editProduct(
    currentName: string,
    newName: string,
    description: string,
    price: number,
    stock: number
  ) {
    const card =
      this.productCard(currentName);

    await card
      .getByRole(
        "button",
        {
          name: "Edit",
          exact: true,
        }
      )
      .click();

    await expect(
      this.updateButton
    ).toBeVisible();

    await this.nameInput.fill(
      newName
    );

    await this.descriptionInput.fill(
      description
    );

    await this.priceInput.fill(
      String(price)
    );

    await this.stockInput.fill(
      String(stock)
    );

    await this.updateButton.click();

    await expect(
      this.page.getByText(
        "Product updated successfully."
      )
    ).toBeVisible();
  }

  async deleteProduct(
    productName: string
  ) {
    const card =
      this.productCard(productName);

    this.page.once(
      "dialog",
      async (dialog) => {
        await dialog.accept();
      }
    );

    await card
      .getByRole(
        "button",
        {
          name: "Delete",
          exact: true,
        }
      )
      .click();

    await expect(
      this.page.getByText(
        "Product deleted successfully."
      )
    ).toBeVisible();
  }

  async verifyProductNotVisible(
    productName: string
  ) {
    await expect(
      this.productCard(productName)
    ).toHaveCount(0);
  }
}
