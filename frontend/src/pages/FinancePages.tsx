type SectionPageProps = {
  title: string;
  description: string;
};

function SectionPage({ title, description }: SectionPageProps) {
  return (
    <section>
      <p className="page-eyebrow">Osobne financije</p>
      <h1 className="page-title">{title}</h1>
      <p className="page-description">{description}</p>
      <div className="content-card">
        <div className="empty-state">
          <p>Ovdje će se prikazivati vaši podaci.</p>
        </div>
      </div>
    </section>
  );
}

export function OverviewPage() {
  return (
    <section>
      <p className="page-eyebrow">Osobne financije</p>
      <h1 className="page-title">Pregled</h1>
      <p className="page-description">
        Dobro došli. Ovdje ćete na jednom mjestu pratiti prihode, rashode i
        stanje svojih financija.
      </p>
      <div className="stats-grid">
        {["Ukupno stanje", "Prihodi", "Rashodi"].map((label) => (
          <article className="stat-card" key={label}>
            <span className="stat-label">{label}</span>
            <p aria-label={`${label}: podaci još nisu dostupni`} className="stat-value">
              —
            </p>
          </article>
        ))}
      </div>
      <section aria-labelledby="recent-transactions-title" className="content-card">
        <h2 id="recent-transactions-title">Nedavne transakcije</h2>
        <div className="empty-state">
          <p>Dodajte transakcije kako bi se ovdje prikazale.</p>
        </div>
      </section>
    </section>
  );
}

export function TransactionsPage() {
  return (
    <SectionPage
      description="Pregledajte i organizirajte svoje prihode i rashode."
      title="Transakcije"
    />
  );
}

export function BudgetsPage() {
  return (
    <SectionPage
      description="Pratite planirane iznose i potrošnju po kategorijama."
      title="Budžeti"
    />
  );
}

export function ReportsPage() {
  return (
    <SectionPage
      description="Analizirajte svoje financije kroz izvještaje i trendove."
      title="Izvještaji"
    />
  );
}
