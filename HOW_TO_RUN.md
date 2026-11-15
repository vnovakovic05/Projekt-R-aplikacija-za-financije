# Pokretanje aplikacije

Za pokretanje cijelog razvojnog okruženja potrebno je instalirati Docker Desktop (ili kompatibilan Docker Engine s Composeom).

1. U korijenu repozitorija kopirajte `.env.example` u `.env` i po potrebi prilagodite lokalne postavke baze.
2. Pokrenite servise:

   ```powershell
   docker compose up --build
   ```

   Pri prvom pokretanju Flyway primjenjuje verzionirane migracije prije nego što
   Spring Boot provjeri mapiranja baze.

3. Otvorite frontend na `http://localhost:5173`. Spring Boot API dostupan je na `http://localhost:8080`, a PostgreSQL na `localhost:5432`.
4. Za provjeru API-ja otvorite `http://localhost:8080/api/health`; očekivani odgovor je:

   ```json
   {"status":"healthy"}
   ```

Compose pokreće PostgreSQL, backend i React razvojni poslužitelj (Vite). Backend
čeka da PostgreSQL prođe provjeru zdravlja. Podaci baze ostaju u Docker volumenu
`postgres_data`. Flyway mijenja shemu, a Hibernate je samo provjerava
(`ddl-auto=validate`).

Za zaustavljanje servisa pritisnite `Ctrl+C`, a zatim pokrenite
`docker compose down`. Time se podaci baze čuvaju. `docker compose down --volumes`
briše i bazne podatke i koristite ga samo kada namjerno želite čistu lokalnu bazu.

Lozinka iz `.env.example` namijenjena je samo lokalnom razvojnom okruženju. Nemojte koristiti tu vrijednost za produkciju.

Za postupak migracija i siguran prelazak postojeće baze na Flyway pogledajte
[upute za bazu](./db/Readme.md).
