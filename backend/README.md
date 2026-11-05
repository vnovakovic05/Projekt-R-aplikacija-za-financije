# Backend

Backend je REST API izrađen u Javi 25 koristeći Spring Boot. Maven Wrapper pokreće Maven bez zasebne instalacije; PostgreSQL je planirana baza, a Spring Data JPA pristup podacima.

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

Testovi se pišu u `src/test/java` koristeći JUnit 5 i Spring MockMvc. Početni test provjerava HTTP status i JSON odgovor zdravstvene provjere.
