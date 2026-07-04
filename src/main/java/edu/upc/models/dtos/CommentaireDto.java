package edu.upc.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentaireDto {

	@Positive(message = "Auteur invalide")
	private long auteurPk;

	@Positive(message = "Article invalide")
	private long articlePk;

	@NotBlank(message = "Commentaire invalide")
	@Size(min = 1, max = 255, message = "Commentaire invalide")
	private String texte;

}
