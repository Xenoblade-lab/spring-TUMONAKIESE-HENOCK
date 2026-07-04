CREATE DATABASE IF NOT EXISTS blog_henock_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE blog_henock_db;

CREATE TABLE IF NOT EXISTS categories (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(45) NOT NULL,
    UNIQUE KEY uk_categories_libelle (libelle)
);

CREATE TABLE IF NOT EXISTS articles (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre   VARCHAR(100) NOT NULL,
    vues    INT NOT NULL,
    UNIQUE KEY uk_articles_titre (titre)
);

CREATE TABLE IF NOT EXISTS auteurs (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    categorie_pk BIGINT NOT NULL,
    noms         VARCHAR(100) NOT NULL,
    ville        VARCHAR(45) NOT NULL,
    email        VARCHAR(80) NOT NULL,
    experience   INT NOT NULL,
    CONSTRAINT fk_auteurs_categorie
        FOREIGN KEY (categorie_pk) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS commentaires (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    auteur_pk  BIGINT NOT NULL,
    article_pk BIGINT NOT NULL,
    texte      VARCHAR(255) NOT NULL,
    CONSTRAINT fk_commentaires_auteur
        FOREIGN KEY (auteur_pk) REFERENCES auteurs(id),
    CONSTRAINT fk_commentaires_article
        FOREIGN KEY (article_pk) REFERENCES articles(id),
    UNIQUE KEY uk_auteur_article (auteur_pk, article_pk)
);

CREATE INDEX idx_auteurs_categorie ON auteurs(categorie_pk);
CREATE INDEX idx_commentaires_auteur ON commentaires(auteur_pk);
CREATE INDEX idx_commentaires_article ON commentaires(article_pk);
