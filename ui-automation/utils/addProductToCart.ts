import type { Page } from "@playwright/test";

import {
  DashboardPage,
} from "../pages/DashboardPage";

import {
  ProductsPage,
} from "../pages/ProductsPage";

import {
  ProductDetailsPage,
} from "../pages/ProductDetailsPage";

export const addProductToCart = async (
  page: Page,
  productName: string,
  quantity = 1
) => {
  const dashboardPage =
    new DashboardPage(page);

  const productsPage =
    new ProductsPage(page);

  const productDetailsPage =
    new ProductDetailsPage(page);

  await dashboardPage.openProducts();

  await productsPage.verifyLoaded();

  await productsPage.openProduct(
    productName
  );

  await productDetailsPage.verifyLoaded(
    productName
  );

  await productDetailsPage.setQuantity(
    quantity
  );

  await productDetailsPage.addToCart();

  await productDetailsPage
    .verifyAddedToCart(
      productName
    );
};
