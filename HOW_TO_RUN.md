# How to run

Upute za lokalno pokretanje aplikacije. Potrebni su Node.js 22 ili noviji i Java 25. Maven Wrapper preuzima zadanu verziju Mavena automatski, stoga nije potrebno posebno instalirati Maven.

## Frontend

U prvom terminalu, iz korijena repozitorija:

```powershell
cd frontend
npm install
npm run dev
```

Ako se `npm install` dugo vrti ili koristi nedostupan registry koji je podešen na računalu, prekinite ga (`Ctrl+C`) i pokušajte instalaciju s javnim npm registryjem:

```powershell
npm install --registry=https://registry.npmjs.org/ --no-audit --no-fund
```

Ova opcija mijenja registry samo za tu naredbu i ne mijenja globalne npm postavke. Nakon instalacije pokrenite `npm run dev`.

Otvorite Vite adresu prikazanu u terminalu (uobičajeno `http://localhost:5173`).

## Backend

U drugom terminalu, iz korijena repozitorija:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Spring Boot API dostupan je na `http://localhost:8080`. Provjerite ga otvaranjem `http://localhost:8080/api/health`; očekivani odgovor je:

```json
{"status":"healthy"}
```

Za trenutno pokretanje nije potrebno pokrenuti Docker ni PostgreSQL. Baza će biti potrebna kada aplikacija počne koristiti pohranu podataka i kada se konfigurira veza s bazom.
