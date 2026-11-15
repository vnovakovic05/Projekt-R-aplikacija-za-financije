# Frontend

Korisničko sučelje izrađeno je u Reactu i TypeScriptu, uz Vite za razvoj i izgradnju, React Router za navigaciju i Tailwind CSS za stiliziranje. Responzivni okvir aplikacije nalazi se u `src/components/`, početne stranice u `src/pages/`, a zajednički Axios klijent u `src/services/apiClient.ts`.

## Pokretanje

```bash
npm ci
npm run dev
```

## Testovi

```bash
npm test
```

Komponentni testovi koriste Vitest, jsdom i React Testing Library; nalaze se uz komponente u `src/`.

## API i autentikacija

Axios klijent koristi `VITE_API_BASE_URL` ako je postavljen, odnosno `/api` kao zadanu adresu. JWT se čita iz `localStorage` ključa `token` i šalje kao `Authorization: Bearer <token>` zaglavlje.
