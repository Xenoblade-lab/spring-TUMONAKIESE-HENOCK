-- Corrige les accents si MySQL affiche des ? au lieu de é, è, à…
USE bibliotheque_henock_db;
SET NAMES utf8mb4;

UPDATE livres SET titre = 'Les Misérables' WHERE titre LIKE 'Les Mis%';
UPDATE livres SET titre = 'Congo : une histoire' WHERE titre LIKE 'Congo%';
UPDATE livres SET titre = 'Discours de la méthode' WHERE titre LIKE 'Discours%';
UPDATE adherents SET quartier = 'Lubumbashi Katuba' WHERE noms = 'Tumonakiese Henock';
