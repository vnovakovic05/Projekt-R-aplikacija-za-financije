import { Navbar } from "./Navbar";

type SidebarProps = {
  isOpen: boolean;
  onClose: () => void;
};

export function Sidebar({ isOpen, onClose }: SidebarProps) {
  return (
    <>
      <button
        aria-label="Zatvori navigaciju"
        className={`sidebar-backdrop${isOpen ? " is-open" : ""}`}
        hidden={!isOpen}
        onClick={onClose}
        type="button"
      />
      <aside
        aria-label="Bočna traka"
        className={`sidebar${isOpen ? " is-open" : ""}`}
        id="primary-sidebar"
      >
        <p className="sidebar-label">Izbornik</p>
        <Navbar onNavigate={onClose} />
      </aside>
    </>
  );
}
