# Blog Henock — Tumonakiese Henock

Application Spring Boot : gestion de blog (catégories, auteurs, articles et commentaires).

## Prérequis

- Java 21+
- MySQL (Laragon, port 3306)

## Installation base de données

1. `sql/schema.sql` — crée la base `blog_henock_db`
2. `sql/reset-data.sql` — données de démonstration

## Lancer l'application

```bash
./mvnw spring-boot:run
```

Interface : http://localhost:8081  
API REST : http://localhost:8081/api/

| Ressource     | Web              | API                    |
|---------------|------------------|------------------------|
| Catégories    | `/categories`    | `/api/categories`      |
| Auteurs       | `/auteurs`       | `/api/auteurs`         |
| Articles      | `/articles`      | `/api/articles`        |
| Commentaires  | `/commentaires`  | `/api/commentaires`    |

## Tests

```bash
./mvnw test
```
