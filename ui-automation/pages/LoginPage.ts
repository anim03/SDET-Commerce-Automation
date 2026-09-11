import {
  expect,
  type Page,
} from "@playwright/test";

export class LoginPage {
  constructor(
    private readonly page: Page
  ) {}

  readonly emailInput =
    this.page.getByLabel("Email");

  readonly passwordInput =
    this.page.getByLabel("Password");

  readonly signInButton =
    this.page.getByRole(
      "button",
      { name: "Sign In" }
    );

  async goto() {
    await this.page.goto("/login");
  }

  async login(
    email: string,
    password: string
  ) {
    await this.emailInput.fill(email);
    await this.passwordInput.fill(password);
    await this.signInButton.click();
  }

  async verifyLoginPage() {
    await expect(
      this.page.getByRole(
        "heading",
        { name: "SDET Commerce" }
      )
    ).toBeVisible();

    await expect(
      this.signInButton
    ).toBeVisible();
  }
}
