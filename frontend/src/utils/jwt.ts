interface JwtPayload {
  sub?: string;
  userId?: number;
  role?: string;
  iat?: number;
  exp?: number;
}

export const decodeJwtPayload = (
  token: string
): JwtPayload | null => {
  try {
    const payload = token.split(".")[1];

    if (!payload) {
      return null;
    }

    const normalized = payload
      .replace(/-/g, "+")
      .replace(/_/g, "/");

    const decoded = atob(normalized);

    return JSON.parse(decoded) as JwtPayload;
  } catch {
    return null;
  }
};

export const getRoleFromToken = (
  token: string
): string | null => {
  return decodeJwtPayload(token)?.role ?? null;
};
