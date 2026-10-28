# Architectuur

## 1. Overzicht

```text
Browser (HTML / CSS / JavaScript)
              |
              | HTTP + JSON
              v
Spring Boot REST API
  Controllers
      |
      v
  Services (bedrijfslogica)
      |
      v
  Repositories (Spring Data JPA)
      |
      v
  MySQL database
```

De frontend communiceert via HTTP-verzoeken met de REST API. De backend verwerkt deze verzoeken en gebruikt Spring Data JPA en MySQL Connector/J om gegevens in de MySQL-database te lezen en op te slaan.

## 2. Verantwoordelijkheden

- **Frontend:** toont de boekencatalogus, boekdetails, reviews, registratie en reservatieformulieren.
- **Controllers:** verwerken HTTP-verzoeken, valideren invoer en sturen verzoeken door naar de services.
- **Services:** bevatten de bedrijfslogica, waaronder gebruikersregistratie, voorraadcontrole en reservaties.
- **Repositories:** verzorgen de database-interactie via Spring Data JPA.
- **Models/entities:** `Book`, `AppUser`, `Reservation` en `Review` vertegenwoordigen de gegevens en hun relaties.
- **ApiExceptionHandler:** zet ondersteunde uitzonderingen om naar HTTP-foutresponses.

## 3. Design patterns en ontwerpprincipes

- **Repository pattern:** databasebewerkingen zijn ondergebracht in repository-interfaces, zodat controllers niet rechtstreeks met de database communiceren.
- **Service layer:** bedrijfsregels zijn gescheiden van de HTTP-laag en de database-interactie.
- **REST-controller pattern:** controllers bieden API-endpoints aan waarmee de frontend gegevens kan opvragen en wijzigen.
- **Dependency Injection / Inversion of Control:** Spring beheert de componenten en injecteert benodigde afhankelijkheden via constructors.
- **Separation of concerns:** de frontend, controllers, services, repositories en entiteiten hebben afzonderlijke verantwoordelijkheden.

## 4. Datamodel en relaties

- **Book:** vertegenwoordigt een boek met onder andere ISBN, titel, auteurs, beschrijving, prijs en voorraad.
- **AppUser:** vertegenwoordigt een gebruiker van de applicatie.
- **Reservation:** verwijst naar één gebruiker en één boek en bewaart de gereserveerde hoeveelheid.
- **Review:** verwijst naar één gebruiker en één boek en bevat een score van 1 tot en met 5 en een tekst.

Een boek heeft een uniek ISBN. Een gebruiker kan meerdere reservaties en reviews hebben. Een boek kan eveneens meerdere reservaties en reviews hebben.

## 5. Reservaties en voorraadbeheer

Wanneer een reservatie wordt aangemaakt, controleert de backend of de gevraagde hoeveelheid beschikbaar is. De reservatie wordt opgeslagen en de voorraad wordt verminderd binnen een transactionele servicebewerking.

Het doel hiervan is te vermijden dat een reservatie wordt opgeslagen zonder de bijbehorende voorraadwijziging. De correcte werking moet worden gecontroleerd met tests.

## 6. Beperkingen en beveiliging

Deze applicatie is een academische demo en is niet bedoeld voor productiegebruik.

- De login controleert gebruikersgegevens, maar geeft geen beveiligde sessie of JWT uit.
- Authenticatie en autorisatie zijn niet volledig uitgewerkt.
- De reviewfunctionaliteit controleert nog niet of de gebruiker het boek eerder heeft gekocht.
- `spring.jpa.hibernate.ddl-auto=update` wordt gebruikt voor lokale ontwikkeling. Voor productie zijn gecontroleerde databaseschema-migraties aangewezen.

Deze beperkingen kunnen in een volgende versie worden aangepakt.
