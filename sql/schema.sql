CREATE DATABASE IF NOT EXISTS bibliotheque_henock_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bibliotheque_henock_db;

CREATE TABLE IF NOT EXISTS genres (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(45) NOT NULL,
    UNIQUE KEY uk_genres_libelle (libelle)
);

CREATE TABLE IF NOT EXISTS livres (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre   VARCHAR(45) NOT NULL,
    pages INT NOT NULL,
    UNIQUE KEY uk_livres_titre (titre)
);

CREATE TABLE IF NOT EXISTS adherents (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    genre_pk BIGINT NOT NULL,
    noms         VARCHAR(100) NOT NULL,
    quartier        VARCHAR(45) NOT NULL,
    telephone    VARCHAR(20) NOT NULL,
    quota_max    INT NOT NULL,
    CONSTRAINT fk_adherents_genre
        FOREIGN KEY (genre_pk) REFERENCES genres(id)
);

CREATE TABLE IF NOT EXISTS emprunts (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    adherent_pk BIGINT NOT NULL,
    livre_pk   BIGINT NOT NULL,
    CONSTRAINT fk_emprunts_adherent
        FOREIGN KEY (adherent_pk) REFERENCES adherents(id),
    CONSTRAINT fk_emprunts_livre
        FOREIGN KEY (livre_pk) REFERENCES livres(id),
    UNIQUE KEY uk_adherent_livre (adherent_pk, livre_pk)
);

CREATE INDEX idx_adherents_genre ON adherents(genre_pk);
CREATE INDEX idx_emprunts_adherent ON emprunts(adherent_pk);
CREATE INDEX idx_emprunts_livre ON emprunts(livre_pk);
