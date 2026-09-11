import "dotenv/config";

import {
  defineConfig,
  devices,
} from "@playwright/test";

import { getUiBaseUrl } from "./utils/env";

const baseURL = getUiBaseUrl();

export default defineConfig({
  testDir: "./tests",

  timeout: 30_000,

  expect: {
    timeout: 5_000,
  },

  fullyParallel: false,

  retries:
    process.env.CI
      ? 2
      : 0,

  workers:
    process.env.CI
      ? 1
      : 1,

  reporter: [
    ["list"],
    [
      "html",
      {
        outputFolder:
          "playwright-report",
        open: "never",
      },
    ],
  ],

  use: {
    baseURL,

    trace: "retain-on-failure",

    screenshot: "only-on-failure",

    video: "retain-on-failure",
  },

  projects: [
    {
      name: "setup-user",
      testMatch: /.*user\.setup\.ts/,
    },

    {
      name: "setup-admin",
      testMatch: /.*admin\.setup\.ts/,
    },

    {
      name: "chromium-user",

      dependencies: [
        "setup-user",
      ],

      testIgnore: [
        /admin\/.*\.spec\.ts/,
        /setup\/.*\.setup\.ts/,
      ],

      use: {
        ...devices["Desktop Chrome"],
        storageState: "auth/user.json",
      },
    },

    {
      name: "chromium-admin",

      dependencies: [
        "setup-admin",
      ],

      testMatch: /admin\/.*\.spec\.ts/,

      use: {
        ...devices["Desktop Chrome"],
        storageState: "auth/admin.json",
      },
    },
  ]
});
