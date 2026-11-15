import { fireEvent, render, screen } from "@testing-library/react";
import { describe, expect, it } from "vitest";
import App from "./App";

describe("App", () => {
  it("shows the overview page and its navigation", () => {
    render(<App />);

    expect(screen.getByRole("heading", { name: "Pregled" })).toBeInTheDocument();
    expect(screen.getByRole("navigation", { name: "Glavna navigacija" })).toBeInTheDocument();
  });

  it("navigates to transactions", () => {
    render(<App />);

    fireEvent.click(screen.getByRole("link", { name: "Transakcije" }));

    expect(
      screen.getByRole("heading", { name: "Transakcije" }),
    ).toBeInTheDocument();
  });

  it("opens and closes the mobile navigation", () => {
    render(<App />);

    fireEvent.click(screen.getByRole("button", { name: "Otvori navigaciju" }));
    expect(screen.getByRole("complementary")).toHaveClass("is-open");

    fireEvent.click(screen.getByRole("link", { name: "Budžeti" }));
    expect(screen.getByRole("complementary")).not.toHaveClass("is-open");
    expect(screen.getByRole("heading", { name: "Budžeti" })).toBeInTheDocument();
  });
});
