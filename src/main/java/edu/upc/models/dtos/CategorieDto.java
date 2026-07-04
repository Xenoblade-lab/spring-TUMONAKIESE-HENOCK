package edu.upc.models.dtos;

import jakarta.validation.constraints.NotBlank;
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
public class CategorieDto {

	@NotBlank(message = "Libellé invalide")
	@Size(min = 1, max = 45, message = "Libellé invalide")
	private String libelle;

}
