import { Link } from "react-router-dom";

type HeaderProps = {
  isNavigationOpen: boolean;
  onMenuClick: () => void;
};

export function Header({ isNavigationOpen, onMenuClick }: HeaderProps) {
  return (
    <header className="app-header">
      <div className="flex items-center gap-3">
        <button
          aria-controls="primary-sidebar"
          aria-expanded={isNavigationOpen}
          aria-label={isNavigationOpen ? "Zatvori navigaciju" : "Otvori navigaciju"}
          className="menu-toggle"
          onClick={onMenuClick}
          type="button"
        >
          ☰
        </button>
        <Link
          aria-label="Osobne financije - početna stranica"
          className="brand"
          to="/"
        >
          <span aria-hidden="true" className="brand-mark">
            ↗
          </span>
          <span>Osobne financije</span>
        </Link>
      </div>
      <div className="header-actions">
        <span>Moj račun</span>
        <div aria-label="Korisnički račun" className="avatar" role="img">
          K
        </div>
      </div>
    </header>
  );
}
