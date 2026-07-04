package edu.upc.utils;

import org.springframework.stereotype.Component;

import edu.upc.models.Article;
import edu.upc.models.Categorie;
import edu.upc.models.Auteur;
import edu.upc.models.Commentaire;
import edu.upc.models.dtos.ArticleDto;
import edu.upc.models.dtos.CategorieDto;
import edu.upc.models.dtos.AuteurDto;
import edu.upc.models.dtos.CommentaireDto;

@Component
public class Mapper {

	public Article mapToArticle(ArticleDto dto) {
		return Article.builder().titre(dto.getTitre()).vues(dto.getVues()).build();
	}

	public Categorie mapToCategorie(CategorieDto dto) {
		return Categorie.builder().libelle(dto.getLibelle()).build();
	}

	public Auteur mapToAuteur(AuteurDto dto) {
		return Auteur.builder()
				.categoriePk(dto.getCategoriePk())
				.noms(dto.getNoms())
				.ville(dto.getVille())
				.email(dto.getEmail())
				.experience(dto.getExperience())
				.build();
	}

	public Commentaire mapToCommentaire(CommentaireDto dto) {
		return Commentaire.builder()
				.auteurPk(dto.getAuteurPk())
				.articlePk(dto.getArticlePk())
				.texte(dto.getTexte())
				.build();
	}

	public CategorieDto toCategorieDto(Categorie entity) {
		return CategorieDto.builder().libelle(entity.getLibelle()).build();
	}

	public ArticleDto toArticleDto(Article entity) {
		return ArticleDto.builder().titre(entity.getTitre()).vues(entity.getVues()).build();
	}

	public AuteurDto toAuteurDto(Auteur entity) {
		return AuteurDto.builder()
				.categoriePk(entity.getCategoriePk())
				.noms(entity.getNoms())
				.ville(entity.getVille())
				.email(entity.getEmail())
				.experience(entity.getExperience())
				.build();
	}

	public CommentaireDto toCommentaireDto(Commentaire entity) {
		return CommentaireDto.builder()
				.auteurPk(entity.getAuteurPk())
				.articlePk(entity.getArticlePk())
				.texte(entity.getTexte())
				.build();
	}

}
