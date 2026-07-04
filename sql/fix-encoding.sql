USE blog_henock_db;
SET NAMES utf8mb4;

UPDATE articles SET titre = 'Les 5 plats congolais à absolument goûter' WHERE id = 3;
UPDATE commentaires SET texte = 'Article très clair, merci pour les exemples Spring !' WHERE auteur_pk = 1 AND article_pk = 1;
