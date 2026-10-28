# Bookstore — Softwarearchitectuur van de boekhandelapplicatie

## Status

Dit project is een startversie van de applicatie voor de fictieve boekhandelopdracht. Controleer elke functie lokaal voordat je het project indient. Dit is een leerproject en geen volledig productieklare webshop.

## Gebruikte technologieën

- Java 17 of hoger
- Spring Boot 3.5.x
- Spring Web, Spring Data JPA en Bean Validation
- MySQL
- HTML, CSS en JavaScript
- JUnit 5 en Spring Boot Test

## Projectstructuur

- `backend/`: Spring Boot REST API, services, repositories, entiteiten en tests.
- `frontend/`: gebruikersinterface met HTML, CSS en JavaScript.
- `docs/`: documentatie over de architectuur, planning en tests.

## Vereisten

Installeer de volgende software voordat je het project start:

- JDK 17 of hoger
- MySQL 8 of hoger
- Maven 3.6.3 of hoger
- Git

## 1. De database aanmaken

Voer de volgende SQL-opdrachten uit in MySQL Workbench of een andere MySQL-client:

```sql
CREATE DATABASE bookstore_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE USER 'bookstore_user'@'localhost'
IDENTIFIED BY 'change_me';

GRANT ALL PRIVILEGES ON bookstore_db.* 
TO 'bookstore_user'@'localhost';

FLUSH PRIVILEGES;
```

Je kunt de gebruikersnaam en het wachtwoord aanpassen aan je lokale configuratie. Gebruik nooit echte of gevoelige inloggegevens in een openbare repository.

## 2. De backend configureren en starten

Open het bestand `backend/src/main/resources/application.properties` en stel de databasegebruikersnaam en het wachtwoord in volgens je lokale MySQL-configuratie.

Open vervolgens een terminal in de map `backend` en voer uit:

```bash
mvn spring-boot:run
```

De API zou beschikbaar moeten zijn op:

`http://localhost:8080`

Tijdens het opstarten maakt JPA de benodigde databasetabellen aan of werkt ze bij. Dit gebeurt via `ddl-auto=update`, dat hier bedoeld is voor lokale ontwikkeling.

## 3. De frontend openen

Open `frontend/index.html` in je browser.

Als je browser problemen ondervindt met CORS wanneer je een pagina via `file://` opent, kun je een eenvoudige lokale webserver gebruiken. Voer vanuit de map `frontend` het volgende commando uit:

```bash
python -m http.server 5500
```

Open daarna:

`http://localhost:5500`

De backend heeft CORS ingeschakeld voor lokale ontwikkeling.

## 4. De tests uitvoeren

Open een terminal in de map `backend` en voer uit:

```bash
mvn test
```

De unittests voor de reserveringsservice gebruiken Mockito en vereisen geen actieve MySQL-server. Om de volledige applicatie te starten, moet MySQL wel actief zijn.

## Belangrijkste API-endpoints

| Methode | Endpoint | Beschrijving |
|---|---|---|
| `GET` | `/api/books` | Alle boeken ophalen |
| `GET` | `/api/books/{id}` | Details van een boek ophalen |
| `POST` | `/api/books` | Een boek toevoegen |
| `POST` | `/api/users/register` | Een gebruiker registreren |
| `POST` | `/api/users/login` | Inloggegevens controleren |
| `POST` | `/api/reservations` | Een boek reserveren en de voorraad verlagen |
| `GET` | `/api/users/{id}/reservations` | Reserveringen van een gebruiker ophalen |
| `POST` | `/api/books/{id}/reviews` | Een beoordeling toevoegen |
| `GET` | `/api/books/{id}/reviews` | Beoordelingen van een boek ophalen |

**Opmerking:** de loginfunctie controleert momenteel alleen de inloggegevens. Er wordt geen beveiligde sessie of JWT-token aangemaakt.

## Voorbeeldgegevens

### Een boek toevoegen

```json
{
  "isbn": "9780132350884",
  "title": "Clean Code",
  "authors": "Robert C. Martin",
  "description": "Een handboek over agile softwareontwikkeling.",
  "price": 34.99,
  "stock": 5
}
```

### Een boek reserveren

```json
{
  "userId": 1,
  "bookId": 1,
  "quantity": 1
}
```

### Een beoordeling toevoegen

```json
{
  "userId": 1,
  "score": 5,
  "text": "Een duidelijk en nuttig boek."
}
```

## Bekende beperkingen

- Dit is een kleine academische demonstratie, geen productieklare applicatie.
- De loginfunctie controleert de inloggegevens, maar maakt geen beveiligde sessie of JWT-token aan. Gebruik deze functie niet voor echte accounts.
- De reserverings-API registreert een reservering en verlaagt de voorraad. Het project gaat ervan uit dat gereserveerde boeken later worden opgehaald.
- Het beoordelingsendpoint controleert of de gebruiker en het boek bestaan, maar controleert momenteel nog niet of de gebruiker het boek daadwerkelijk heeft gekocht.
- `ddl-auto=update` is handig voor lokale ontwikkeling, maar is geen geschikte migratiestrategie voor een productieomgeving.
- De gebruikersinterface is bewust eenvoudig gehouden en bevat nog niet alle mogelijke accountfuncties.

## Planning

Raadpleeg `docs/planning.md` voor de projectplanning en de voortgang.

## Architectuur

Raadpleeg `docs/architecture.md` voor de architectuur, de verantwoordelijkheden van de verschillende onderdelen en de gebruikte ontwerpprincipes.

## Tests

Raadpleeg `docs/testing.md` voor de testscenario's en testresultaten.

Test de applicatie vóór de indiening en vermeld alleen resultaten die daadwerkelijk zijn gecontroleerd.

## Bronnen en gebruikte hulpmiddelen

Bij het opzetten van de projectstructuur, de implementatie en de documentatie werd gebruikgemaakt van ChatGPT (OpenAI). ChatGPT werd ingezet voor codevoorbeelden, uitleg en ondersteuning bij de documentatie. De student blijft verantwoordelijk voor het controleren, begrijpen, testen en aanpassen van de code.

- **Spring REST Guide — Building a RESTful Web Service:** https://spring.io/guides/gs/rest-service/
- **Spring Boot — Systeemvereisten:** https://docs.spring.io/spring-boot/system-requirements.html
- **Spring Boot Guide — Building an Application:** https://spring.io/guides/gs/spring-boot/
- **MySQL Connector/J — Maven-configuratie:** https://dev.mysql.com/doc/connector-j/en/connector-j-installing-maven.html
- **JUnit 5 — Gebruikershandleiding:** https://junit.org/junit5/docs/current/user-guide/


