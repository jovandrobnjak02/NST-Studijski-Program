# StudijskiProgram

Projekat je Spring Boot backend za upravljanje studijskim programima, modulima i planom nastave (obavezni i izborni predmeti). Baza podataka je PostgreSQL, a migracije i inicijalni podaci se rade preko Liquibase.

## Pokretanje

- Lokano: pokrenuti aplikaciju iz IDE-a.
- Docker: `docker compose up --build`

Swagger UI: `http://localhost:8080/test-ui.html`

## Funkcionalnosti

- CRUD nad studijskim programima sa ugnijezdenim modulima i planom nastave
- Izborne grupe sa pravilima (broj predmeta za izbor i potreban ESPB)
- Predmeti se koriste iz vec inicijalizovane baze

## API (primjeri)

- `GET /api/studijski-programi`
- `GET /api/studijski-programi/{sifra}`
- `POST /api/studijski-programi`
- `PUT /api/studijski-programi/{sifra}`
- `DELETE /api/studijski-programi/{sifra}`

## Tehnologije

- Java 17, Spring Boot
- PostgreSQL
- Liquibase
- OpenAPI/Swagger UI
