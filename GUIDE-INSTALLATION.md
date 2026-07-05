# Guide d'installation — Blog Henock (autre machine)

> Projet : **spring-TUMONAKIESE-HENOCK** — Tumonakiese Henock  
> Repo : https://github.com/Xenoblade-lab/spring-TUMONAKIESE-HENOCK

---

## 1. Utilisateurs, identifiants et rôles

### Connexion à l'application web — **aucun compte**

Ce projet **n'a pas de système de login** (pas de page de connexion, pas de rôles Admin/User).

Dans `SecurityConfig.java`, toutes les pages sont ouvertes :

```java
http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
```

| Type | Identifiant | Mot de passe | Rôle |
|------|-------------|--------------|------|
| **Application web** | — | — | **Aucun** — tout est accessible sans connexion |

Tu ouvres http://localhost:8081 et tu utilises directement le site.

---

### Connexion MySQL (base de données)

Configurée dans `src/main/resources/application-dev.properties` :

| Paramètre | Valeur par défaut (Laragon) |
|-----------|----------------------------|
| **Utilisateur** | `root` |
| **Mot de passe** | *(vide)* |
| **Hôte** | `localhost` |
| **Port** | `3306` |
| **Base** | `blog_henock_db` |

Si ta machine a un mot de passe MySQL, modifie `application-dev.properties` :

```properties
spring.datasource.username=root
spring.datasource.password=TON_MOT_DE_PASSE
```

---

### Données de démonstration (auteurs du blog)

Ce ne sont **pas** des comptes de connexion — ce sont les **rédacteurs** enregistrés dans la table `auteurs` :

| ID | Nom | Ville | Email | Catégorie (ID) | Expérience (ans) |
|----|-----|-------|-------|----------------|------------------|
| 1 | Tumonakiese Henock | Lubumbashi | henock.tumonakiese@blog.rdc | Technologie (1) | 3 |
| 2 | Mukendi Grace | Kinshasa | grace.mukendi@blog.rdc | Voyage (2) | 5 |
| 3 | Kabila Jean-Pierre | Goma | jp.kabila@blog.rdc | Technologie (1) | 2 |
| 4 | Tshimanga Sarah | Bukavu | sarah.tshimanga@blog.rdc | Culture (3) | 4 |
| 5 | Mbuyi Paul | Kolwezi | paul.mbuyi@blog.rdc | Cuisine (5) | 1 |

#### Catégories (table `categories`)

| ID | Libellé |
|----|---------|
| 1 | Technologie |
| 2 | Voyage |
| 3 | Culture |
| 4 | Sport |
| 5 | Cuisine |

#### Articles (table `articles`)

| ID | Titre | Vues |
|----|-------|------|
| 1 | Spring Boot en 2026 : par où commencer ? | 1240 |
| 2 | Lubumbashi vu du ciel : carnet de route | 890 |
| 3 | Les 5 plats congolais à absolument goûter | 2100 |
| 4 | Running au stade TP Mazembe : mon retour | 560 |
| 5 | Comment lancer un blog avec Thymeleaf | 430 |

#### Commentaires (table `commentaires`)

| ID | Auteur (ID) | Article (ID) | Texte (résumé) |
|----|-------------|--------------|----------------|
| 1 | 1 | 1 | Article très clair, merci pour les exemples Spring ! |
| 2 | 1 | 5 | Exactement ce qu'il me fallait pour mon projet. |
| 3 | 2 | 2 | Magnifiques photos, envie de visiter Lubumbashi. |
| 4 | 3 | 1 | La partie JdbcClient m'a beaucoup aidé. |
| 5 | 4 | 3 | Le pondu décrit ici est irrésistible. |
| 6 | 5 | 4 | Belle motivation pour reprendre le sport ! |

---

## 2. Prérequis sur la nouvelle machine

| Logiciel | Version minimale |
|----------|------------------|
| **Java JDK** | 21 ou plus |
| **MySQL** | 8.x (ex. Laragon, XAMPP, WAMP) |
| **Git** | Pour cloner le repo |
| **Maven** | Inclus via `mvnw` (pas besoin d'installer Maven) |

### Vérifier Java

```powershell
java -version
```

Tu dois voir `21` ou supérieur.

### Vérifier MySQL

- Laragon : démarrer Laragon → **Start All**
- Le service MySQL doit écouter sur le port **3306**

---

## 3. Installation pas à pas

### Étape 1 — Cloner le projet

```powershell
cd c:\laragon\www
git clone https://github.com/Xenoblade-lab/spring-TUMONAKIESE-HENOCK.git
cd spring-TUMONAKIESE-HENOCK
```

### Étape 2 — Créer la base de données

**Option A — HeidiSQL (recommandé)**

1. Ouvrir HeidiSQL → connexion `root` (mot de passe selon ta machine)
2. Fichier → Exécuter un fichier SQL :
   - D'abord `sql/schema.sql`
   - Puis `sql/reset-data.sql`

**Option B — Ligne de commande**

```powershell
cd c:\laragon\www\spring-TUMONAKIESE-HENOCK

# Adapter le chemin mysql.exe selon ton installation Laragon
& "C:\laragon\bin\mysql\mysql-8.4.3-winx64\bin\mysql.exe" -u root --default-character-set=utf8mb4 -e "source c:/laragon/www/spring-TUMONAKIESE-HENOCK/sql/schema.sql"

& "C:\laragon\bin\mysql\mysql-8.4.3-winx64\bin\mysql.exe" -u root --default-character-set=utf8mb4 -e "source c:/laragon/www/spring-TUMONAKIESE-HENOCK/sql/reset-data.sql"
```

Si MySQL a un mot de passe : ajouter `-p` et entrer le mot de passe.

### Étape 3 — Configurer la connexion (si besoin)

Ouvrir `src/main/resources/application-dev.properties` et vérifier :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_henock_db?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_unicode_ci
spring.datasource.username=root
spring.datasource.password=
```

Modifier `username` / `password` si ta machine est différente.

### Étape 4 — Lancer l'application

```powershell
cd c:\laragon\www\spring-TUMONAKIESE-HENOCK
.\mvnw.cmd spring-boot:run
```

Attendre le message : `Started BlogHenockApplication`

### Étape 5 — Vérifier dans le navigateur

| Page | URL |
|------|-----|
| Accueil | http://localhost:8081 |
| Catégories | http://localhost:8081/categories |
| Auteurs | http://localhost:8081/auteurs |
| Articles | http://localhost:8081/articles |
| Commentaires | http://localhost:8081/commentaires |
| API JSON | http://localhost:8081/api/auteurs |

### Étape 6 — Tests automatiques (optionnel)

```powershell
.\mvnw.cmd test -DforkCount=0
```

Résultat attendu : **7 tests, 0 échecs**.

---

## 4. Problèmes fréquents

| Problème | Solution |
|----------|----------|
| `Communications link failure` | MySQL n'est pas démarré → lancer Laragon |
| `Unknown database blog_henock_db` | Exécuter `sql/schema.sql` |
| Tables vides | Exécuter `sql/reset-data.sql` |
| Accents en `?` | Exécuter `sql/fix-encoding.sql` |
| Port 8081 déjà utilisé | Changer `server.port` dans `application.properties` |
| `java` introuvable | Installer JDK 21 et redémarrer le terminal |

---

## 5. Réinitialiser les données

Pour repartir des données de démo propres :

```sql
-- Dans HeidiSQL, exécuter :
source c:/chemin/vers/spring-TUMONAKIESE-HENOCK/sql/reset-data.sql
```

---

## 6. Fichiers importants

| Fichier / dossier | Rôle |
|-------------------|------|
| `sql/schema.sql` | Crée la base et les tables |
| `sql/reset-data.sql` | Données de démonstration |
| `application-dev.properties` | Connexion MySQL locale |
| `GUIDE-PROJET.md` | Documentation technique Spring |
| `README.md` | Résumé rapide |

**Postman** : non inclus, non obligatoire. L'API se teste dans le navigateur.

---

*Guide pour Tumonakiese Henock — Blog Henock*
