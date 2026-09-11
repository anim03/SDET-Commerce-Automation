import {
  request,
} from "@playwright/test";

import {
  getApiBaseUrl,
} from "./env";

interface LoginResponse {
  token: string;
}

const loginForToken = async (
  email: string,
  password: string
): Promise<string> => {
  const context =
    await request.newContext({
      baseURL: getApiBaseUrl(),
    });

  try {
    const response =
      await context.post(
        "/api/users/login",
        {
          data: {
            email,
            password,
          },
        }
      );

    if (!response.ok()) {
      throw new Error(
        `API login failed: ${response.status()}`
      );
    }

    const body =
      (await response.json()) as LoginResponse;

    return body.token;
  } finally {
    await context.dispose();
  }
};

export const getAdminToken =
  async (): Promise<string> => {
    const email =
      process.env.ADMIN_USER_EMAIL;

    const password =
      process.env.ADMIN_USER_PASSWORD;

    if (!email || !password) {
      throw new Error(
        "ADMIN_USER_EMAIL and ADMIN_USER_PASSWORD are required."
      );
    }

    return loginForToken(
      email,
      password
    );
  };
