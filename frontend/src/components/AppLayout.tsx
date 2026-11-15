import { useState } from "react";
import { Outlet } from "react-router-dom";
import { Header } from "./Header";
import { Sidebar } from "./Sidebar";

export function AppLayout() {
  const [isNavigationOpen, setIsNavigationOpen] = useState(false);

  return (
    <div className="app-shell">
      <Header
        isNavigationOpen={isNavigationOpen}
        onMenuClick={() => setIsNavigationOpen((isOpen) => !isOpen)}
      />
      <div className="app-body">
        <Sidebar
          isOpen={isNavigationOpen}
          onClose={() => setIsNavigationOpen(false)}
        />
        <main className="main-content">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
