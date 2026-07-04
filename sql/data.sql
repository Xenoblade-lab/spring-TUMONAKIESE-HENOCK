USE bibliotheque_henock_db;

-- Jeu de données de démonstration — Bibliothèque Municipale Henock (Lubumbashi)

INSERT INTO genres (libelle) VALUES
('Roman'),
('Science-fiction'),
('Histoire'),
('Jeunesse'),
('Philosophie')
ON DUPLICATE KEY UPDATE libelle = VALUES(libelle);

INSERT INTO livres (titre, pages) VALUES
('Les Misérables', 1463),
('Dune', 688),
('Congo : une histoire', 512),
('Le Petit Prince', 96),
('Discours de la méthode', 128)
ON DUPLICATE KEY UPDATE pages = VALUES(pages);

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
