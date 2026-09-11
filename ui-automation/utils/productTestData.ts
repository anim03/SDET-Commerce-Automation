import {
  request,
} from "@playwright/test";

import {
  getApiBaseUrl,
} from "./env";

import {
  getAdminToken,
} from "./apiAuth";

export interface TestProduct {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
}

export const createTestProduct =
  async (): Promise<TestProduct> => {
    const token =
      await getAdminToken();

    const context =
      await request.newContext({
        baseURL: getApiBaseUrl(),

        extraHTTPHeaders: {
          Authorization:
            `Bearer ${token}`,
        },
      });

    const name =
      `PW Product ${Date.now()}`;

    try {
      const response =
        await context.post(
          "/api/products",
          {
            data: {
              name,
              description:
                "Created by Playwright API setup",
              price: 199.99,
              stock: 20,
            },
          }
        );

      if (!response.ok()) {
        throw new Error(
          `Unable to create test product: ${response.status()}`
        );
      }

      return await response.json();
    } finally {
      await context.dispose();
    }
  };

export const deleteTestProduct =
  async (
    productId: number
  ): Promise<void> => {
    const token =
      await getAdminToken();

    const context =
      await request.newContext({
        baseURL: getApiBaseUrl(),

        extraHTTPHeaders: {
          Authorization:
            `Bearer ${token}`,
        },
      });

    try {
      const response =
        await context.delete(
          `/api/products/${productId}`
        );

      if (!response.ok()) {
        throw new Error(
          `Unable to delete test product: ${response.status()}`
        );
      }
    } finally {
      await context.dispose();
    }
  };
