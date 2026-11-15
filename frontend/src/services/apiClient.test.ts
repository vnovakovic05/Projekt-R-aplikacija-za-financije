import { afterEach, describe, expect, it } from "vitest";
import apiClient from "./apiClient";

describe("apiClient", () => {
  afterEach(() => {
    localStorage.clear();
  });

  it("adds the stored JWT as a Bearer authorization header", async () => {
    localStorage.setItem("token", "example-jwt");
    let authorizationHeader: string | undefined;

    await apiClient.get("/test", {
      adapter: async (config) => {
        authorizationHeader = config.headers.get("Authorization")?.toString();
        return {
          config,
          data: {},
          headers: {},
          status: 200,
          statusText: "OK",
        };
      },
    });

    expect(authorizationHeader).toBe("Bearer example-jwt");
  });

  it("does not add an authorization header without a token", async () => {
    let authorizationHeader: string | undefined;

    await apiClient.get("/test", {
      adapter: async (config) => {
        authorizationHeader = config.headers.get("Authorization")?.toString();
        return {
          config,
          data: {},
          headers: {},
          status: 200,
          statusText: "OK",
        };
      },
    });

    expect(authorizationHeader).toBeUndefined();
  });
});
