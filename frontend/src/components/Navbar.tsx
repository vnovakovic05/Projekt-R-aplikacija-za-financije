import { NavLink } from "react-router-dom";

const navigationItems = [
  { to: "/", label: "Pregled", icon: "⌂", end: true },
  { to: "/transakcije", label: "Transakcije", icon: "↔" },
  { to: "/budzeti", label: "Budžeti", icon: "▤" },
  { to: "/izvjestaji", label: "Izvještaji", icon: "▥" },
];

type NavbarProps = {
  onNavigate: () => void;
};

export function Navbar({ onNavigate }: NavbarProps) {
  return (
    <nav aria-label="Glavna navigacija" className="primary-nav">
      {navigationItems.map(({ to, label, icon, end }) => (
        <NavLink
          className={({ isActive }) => `nav-link${isActive ? " active" : ""}`}
          end={end}
          key={to}
          onClick={onNavigate}
          to={to}
        >
          <span aria-hidden="true" className="nav-icon">
            {icon}
          </span>
          {label}
        </NavLink>
      ))}
    </nav>
  );
}
