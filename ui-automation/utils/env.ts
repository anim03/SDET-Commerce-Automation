export type TestEnvironment =
  | "local"
  | "qa"
  | "stage";

export const testEnv =
  (process.env.TEST_ENV ||
    "local") as TestEnvironment;

const uiUrls: Record<
  TestEnvironment,
  string
> = {
  local:
    process.env.LOCAL_UI_BASE_URL ||
    "http://localhost:5173",

  qa:
    process.env.QA_UI_BASE_URL ||
    "",

  stage:
    process.env.STAGE_UI_BASE_URL ||
    "",
};

const apiUrls: Record<
  TestEnvironment,
  string
> = {
  local:
    process.env.LOCAL_API_BASE_URL ||
    "http://localhost:8080",

  qa:
    process.env.QA_API_BASE_URL ||
    "",

  stage:
    process.env.STAGE_API_BASE_URL ||
    "",
};

export const getUiBaseUrl = () => {
  const url = uiUrls[testEnv];

  if (!url) {
    throw new Error(
      `UI base URL is not configured for environment: ${testEnv}`
    );
  }

  return url;
};

export const getApiBaseUrl = () => {
  const url = apiUrls[testEnv];

  if (!url) {
    throw new Error(
      `API base URL is not configured for environment: ${testEnv}`
    );
  }

  return url;
};
