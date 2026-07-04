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
public class AdherentDto {

	@Positive(message = "Genre invalide")
	private long genrePk;

	@NotBlank(message = "Noms invalides")
	@Size(min = 1, max = 100, message = "Noms invalides")
	private String noms;

	@NotBlank(message = "Quartier invalide")
	@Size(min = 1, max = 45, message = "Quartier invalide")
	private String quartier;

	@NotBlank(message = "Téléphone invalide")
	@Size(min = 1, max = 20, message = "Téléphone invalide")
	private String telephone;

	@Positive(message = "Quota invalide")
	private int quotaMax;

}
