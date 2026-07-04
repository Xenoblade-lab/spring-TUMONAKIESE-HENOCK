# Bibliothèque Municipale Henock — Tumonakiese Henock

Application Spring Boot : gestion de bibliothèque (genres, adhérents, livres et emprunts) à Lubumbashi.

## Prérequis

- Java 21+
- MySQL (Laragon, port 3306)

## Installation base de données

Exécuter dans HeidiSQL ou MySQL :

1. `sql/schema.sql` — crée la base `bibliotheque_henock_db`
2. `sql/reset-data.sql` — données de démonstration

## Lancer l'application

```bash
./mvnw spring-boot:run
```

Interface : http://localhost:8081  
API REST : http://localhost:8081/api/

| Ressource  | Web              | API                   |
|------------|------------------|-----------------------|
| Genres     | `/genres`        | `/api/genres`         |
| Adhérents  | `/adherents`     | `/api/adherents`      |
| Livres     | `/livres`        | `/api/livres`         |
| Emprunts   | `/emprunts`      | `/api/emprunts`       |

## Tests

```bash
./mvnw test
```
