-- Réinitialise la base avec le jeu de données de démonstration.
-- Exécuter dans HeidiSQL ou : mysql -u root --default-character-set=utf8mb4 < sql/reset-data.sql

USE bibliotheque_henock_db;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE emprunts;
TRUNCATE TABLE adherents;
TRUNCATE TABLE livres;
TRUNCATE TABLE genres;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO genres (libelle) VALUES
('Roman'),
('Science-fiction'),
('Histoire'),
('Jeunesse'),
('Philosophie');

INSERT INTO livres (titre, pages) VALUES
('Les Misérables', 1463),
('Dune', 688),
('Congo : une histoire', 512),
('Le Petit Prince', 96),
('Discours de la méthode', 128);

INSERT INTO adherents (genre_pk, noms, quartier, telephone, quota_max) VALUES
(1, 'Tumonakiese Henock', 'Lubumbashi Katuba', '+243 999 000 001', 5),
(2, 'Mukendi Grace', 'Lubumbashi Kenya', '+243 810 234 567', 4),
(1, 'Kabila Jean-Pierre', 'Lubumbashi Kamalondo', '+243 820 111 222', 6),
(3, 'Tshimanga Sarah', 'Lubumbashi Lubumbashi Centre', '+243 970 333 444', 3),
(4, 'Mbuyi Paul', 'Lubumbashi Golf', '+243 991 555 666', 4);

INSERT INTO emprunts (adherent_pk, livre_pk) VALUES
(1, 1),
(1, 2),
(1, 4),
(2, 2),
(3, 4),
(4, 3),
(5, 1);
