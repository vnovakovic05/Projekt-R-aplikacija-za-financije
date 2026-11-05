# How to test

Frontend i backend testiraju se odvojeno. GitHub Actions automatski pokreće provjere pri otvaranju ili ažuriranju pull requesta prema `main`.

## Frontend testovi

Iz korijena repozitorija:

```powershell
cd frontend
npm install
npm test
npm run build
```

Testovi koriste Vitest, jsdom i React Testing Library. Datoteke testova nalaze se uz frontend-kod, primjerice `frontend/src/App.test.tsx`.

## Backend testovi

Iz korijena repozitorija:

```powershell
cd backend
.\mvnw.cmd test
```

Ili pokrenite sve Maven provjere i izradite aplikacijski paket:

```powershell
cd backend
.\mvnw.cmd verify
```

Backend testovi koriste JUnit 5 i Spring MockMvc; nalaze se u `backend/src/test/java`. Trenutačni početni test provjerava da `GET /api/health` vraća HTTP 200 i JSON status `healthy`. Testovi ne trebaju Docker ni PostgreSQL.

## Provjere na GitHubu prije spajanja

Workflow `.github/workflows/ci.yml` pokreće frontend testove i build te backend Maven provjere na pull requestima prema `main`. Za obaveznu blokadu spajanja otvorite GitHubovo **Settings → Rules → Rulesets** (ili **Settings → Branches**), dodajte pravilo za `main` te, nakon što se workflow barem jednom uspješno pokrene, označite **Require status checks to pass** i odaberite provjere **Frontend tests** i **Backend tests**. Bez tog pravila GitHub pokreće workflow, ali samo pokretanje ne sprječava spajanje neuspješnog PR-a.
