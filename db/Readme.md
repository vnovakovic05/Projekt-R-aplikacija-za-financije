
# Baza podataka

Shema PostgreSQL baze održava se Flyway migracijama u
`backend/src/main/resources/db/migration/`. Te su migracije jedini izvor istine za
izradu i nadogradnju sheme; Spring Boot ih primjenjuje pri pokretanju backend
kontejnera prije provjere mapiranja pomoću Hibernatea.

- `V1__create_existing_finance_schema.sql` stvara postojeće tablice korisnika,
  kategorija, transakcija i ciljeva štednje.
- `V2__add_budgets_and_recurring_rules.sql` dodaje mjesečne budžete i pravila
  ponavljanja te povezuje generirane transakcije s pravilom i datumom pojave.

Iznosi su u EUR i spremljeni su kao `numeric(12, 2)`. Budžeti su mjesečni i
vežu se uz kategoriju. Pravila troškova podržavaju `DAILY` i `MONTHLY`; mjesečna
pravila imaju pojavu prvog dana u mjesecu. Jedinstveno ograničenje na pravilu i
datumu pojave štiti od duplikata. Scheduler pokreće obradu svakog dana u 02:00
vremenske zone `Europe/Zagreb` (podesivo preko `FINANCE_RECURRING_ZONE` i
`FINANCE_RECURRING_CRON`) te nadoknađuje propuštene pojave nakon prekida rada.
Pravilo mora biti vezano uz kategoriju rashoda.

## Nove baze

Iz korijena repozitorija pokrenite `docker compose up --build`. Flyway primjenjuje
migracije automatski. Nemojte koristiti `docker compose down --volumes` ako želite
sačuvati lokalne podatke.

## Postojeće baze bez Flyway povijesti

Prije nadogradnje napravite sigurnosnu kopiju i provjerite da postojeća shema
odgovara V1 migraciji. Flyway prema zadanim postavkama neće automatski preuzeti
nepraznu bazu. Za potvrđenu postojeću V1 shemu privremeno postavite
`SPRING_FLYWAY_BASELINE_ON_MIGRATE=true` u `.env` i pokrenite backend: Flyway će
zabilježiti baseline verzije 1, a zatim primijeniti V2. Nakon toga vratite
vrijednost na `false` ili je uklonite. Ne koristite baseline na bazi nepoznate ili
djelomične sheme; prvo uskladite podatke i shemu ručno uz sigurnosnu kopiju.

## Buduće promjene

Svaku promjenu dodajte kao novu datoteku, primjerice
`V3__add_transaction_status.sql`. Ne mijenjajte već primijenjenu migraciju:
Flyway provjerava njezin checksum. Za promjenu postojećih podataka napišite
eksplicitni SQL korak migracije i provjerite ga nad kopijom baze prije deploya.
Hibernate `ddl-auto=validate` prijavljuje nesklad, ali ne mijenja shemu.
