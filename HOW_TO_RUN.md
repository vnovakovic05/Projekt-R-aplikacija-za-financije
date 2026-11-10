# Pokretanje aplikacije

Za pokretanje cijelog razvojnog okruženja potrebno je instalirati Docker Desktop (ili kompatibilan Docker Engine s Composeom).

1. U korijenu repozitorija kopirajte `.env.example` u `.env` i po potrebi prilagodite lokalne postavke baze.
2. Pokrenite servise:

   ```powershell
   docker compose up --build
   ```

3. Otvorite frontend na `http://localhost:5173`. Spring Boot API dostupan je na `http://localhost:8080`, a PostgreSQL na `localhost:5432`.
4. Za provjeru API-ja otvorite `http://localhost:8080/api/health`; očekivani odgovor je:

   ```json
   {"status":"healthy"}
   ```

Compose pokreće PostgreSQL, backend i React razvojni poslužitelj (Vite). Backend čeka da PostgreSQL prođe provjeru zdravlja. Podaci baze ostaju u Docker volumenu `postgres_data`.

Za zaustavljanje servisa pritisnite `Ctrl+C`, a zatim pokrenite `docker compose down`. Za brisanje i podataka baze koristite `docker compose down --volumes`; time se trajno brišu podaci pohranjeni u volumenu.

Lozinka iz `.env.example` namijenjena je samo lokalnom razvojnom okruženju. Nemojte koristiti tu vrijednost za produkciju.
