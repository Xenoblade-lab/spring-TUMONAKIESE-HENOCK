USE blog_henock_db;

INSERT INTO categories (libelle) VALUES
('Technologie'),
('Voyage'),
('Culture'),
('Sport'),
('Cuisine')
ON DUPLICATE KEY UPDATE libelle = VALUES(libelle);

INSERT INTO articles (titre, vues) VALUES
('Spring Boot en 2026 : par où commencer ?', 1240),
('Lubumbashi vu du ciel : carnet de route', 890),
('Les 5 plats congolais à absolument goûter', 2100),
('Running au stade TP Mazembe : mon retour', 560),
('Comment lancer un blog avec Thymeleaf', 430)
ON DUPLICATE KEY UPDATE vues = VALUES(vues);

INSERT INTO auteurs (categorie_pk, noms, ville, email, experience) VALUES
(1, 'Tumonakiese Henock', 'Lubumbashi', 'henock.tumonakiese@blog.rdc', 3),
(2, 'Mukendi Grace', 'Kinshasa', 'grace.mukendi@blog.rdc', 5),
(1, 'Kabila Jean-Pierre', 'Goma', 'jp.kabila@blog.rdc', 2),
(3, 'Tshimanga Sarah', 'Bukavu', 'sarah.tshimanga@blog.rdc', 4),
(5, 'Mbuyi Paul', 'Kolwezi', 'paul.mbuyi@blog.rdc', 1);

INSERT INTO commentaires (auteur_pk, article_pk, texte) VALUES
(1, 1, 'Article très clair, merci pour les exemples Spring !'),
(1, 5, 'Exactement ce qu il me fallait pour mon projet.'),
(2, 2, 'Magnifiques photos, j ai envie de visiter Lubumbashi.'),
(3, 1, 'La partie JdbcClient m a beaucoup aidé.'),
(4, 3, 'Le pondu décrit ici est irresistible.'),
(5, 4, 'Belle motivation pour reprendre le sport !');
