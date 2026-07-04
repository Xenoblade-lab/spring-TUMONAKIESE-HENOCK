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
public class ArticleDto {

	@NotBlank(message = "Titre invalide")
	@Size(min = 1, max = 100, message = "Titre invalide")
	private String titre;

	@Positive(message = "Nombre de vues invalide")
	private int vues;

}
