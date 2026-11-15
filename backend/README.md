# Backend

Backend je REST API izrađen u Javi 25 koristeći Spring Boot. Maven Wrapper pokreće Maven bez zasebne instalacije. PostgreSQL je baza, Flyway verzionira promjene sheme, a Spring Data JPA mapira podatke uz Hibernate `ddl-auto=validate`.

## Pokretanje

Iz korijena repozitorija pokrenite:

```bash
Uđite u direktorij `backend`, zatim pokrenite na Windowsu:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Na macOS/Linuxu:

```bash
cd backend
./mvnw spring-boot:run
```
```

Provjera dostupnosti API-ja: `GET http://localhost:8080/api/health`.

## Testovi

```bash
Iz direktorija `backend`, na Windowsu:

```powershell
.\mvnw.cmd test
```

Na macOS/Linuxu:

```bash
./mvnw test
```
```

Testovi se pišu u `src/test/java` koristeći JUnit 5 i Spring MockMvc; uključuju
provjeru zdravstvene rute te dnevno/mjesečno generiranje i idempotentnost
ponavljajućih transakcija za kategorije prihoda i rashoda.

Flyway migracije nalaze se u `src/main/resources/db/migration`. Pojedinosti o
lokalnom pokretanju, baselineu postojeće baze i dodavanju budućih migracija
nalaze se u [`../db/Readme.md`](../db/Readme.md).
