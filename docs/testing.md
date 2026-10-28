# Testplan

## Geautomatiseerde unit tests
Bestand: `backend/src/test/java/be/bookstore/service/ReservationServiceTest.java`

1. **Voorraad daalt na reservatie**: voorraad 3, reserveer 2, verwachte voorraad 1.
2. **Onvoldoende voorraad**: voorraad 3, vraag 4, verwacht `IllegalArgumentException`, voorraad blijft 3 en er wordt geen reservatie opgeslagen.
3. **Ongeldig aantal**: vraag 0 of -1, verwacht `IllegalArgumentException` en geen interactie met repositories.

## Handmatige functionele tests
Voer deze tests uit nadat de backend en MySQL draaien en noteer de werkelijke uitkomst:
- `GET /api/books` geeft een lijst terug.
- Registreren met een nieuw e-mailadres maakt een gebruiker aan.
- Registreren met hetzelfde e-mailadres wordt geweigerd.
- Een reservatie verlaagt de voorraad met het gevraagde aantal.
- Een reservatie met onvoldoende voorraad wordt geweigerd.
- Een review met score 0 of 6 wordt geweigerd.
- Reviews verschijnen bij `GET /api/books/{id}/reviews`.

## Uitvoeren
Vanuit `backend`: `mvn test`

Vul hieronder de werkelijke resultaten aan:
- Datum/tijd:
- Commando:
- Resultaat:
- Eventuele fouten en oplossing:
