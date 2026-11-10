# Ovisnosti postojećih issueva

Pregled obuhvaća svih 15 otvorenih issueva repozitorija (issuevi #2–#16); zatvorenih issueva trenutačno nema. Ovisnosti su izvedene iz opisa i traženih rezultata pojedinih issueva.

| Issue | Blokiraju ga | Obrazloženje |
|---|---|---|
| #2 | — | Opis ne navodi dovoljno konteksta za pouzdano određivanje blokatora. |
| #3 | — | Temeljna infrastruktura repozitorija. |
| #4 | — | ER model baze može se projektirati neovisno o implementaciji aplikacije. |
| #5 | #3, #4 | Konfigurira PostgreSQL kontejner i JPA modele na temelju ER modela. |
| #6 | #5 | Autentifikacija zahtijeva korisnički entitet i repozitorij. |
| #7 | #5 | Transakcijski CRUD zahtijeva JPA modele i repozitorije. |
| #8 | #5, #7 | Ponavljajuća pravila zahtijevaju model baze, a generiranje troškova transakcijski API. |
| #9 | #5, #7 | Budžeti zahtijevaju JPA modele; analitika troškova zahtijeva transakcijske podatke. |
| #10 | #3 | Inicijalizacija i raspored React aplikacije nadograđuju osnovnu strukturu repozitorija. |
| #11 | #6, #10 | Prijava/registracija koristi autentifikacijski API i frontend raspored. |
| #12 | #7, #10 | Grafovi novčanog toka ovise o transakcijskim podacima i frontend rasporedu. |
| #13 | #7, #10 | Transakcijsko sučelje koristi transakcijski API i frontend raspored. |
| #14 | #9, #10 | Budžetsko sučelje koristi budžetski API i frontend raspored. |
| #15 | #6–#14 | End-to-end provjere svih korisničkih scenarija ovise o implementaciji API-ja i sučelja. |
| #16 | #3, #4, #15 | Završna dokumentacija treba pokretanje Composeom, ER model i provjerenu aplikaciju. |

Ove nativne GitHub veze blokatora postavljene su na temelju opisa issueva. Issue #7 ne navodi izričito autentifikaciju kao preduvjet, pa ovisnost o issueju #6 nije dodana. Issue #2 ostavljen je bez veze jer njegov opis ne daje dovoljno konteksta za pouzdano određivanje blokatora.
