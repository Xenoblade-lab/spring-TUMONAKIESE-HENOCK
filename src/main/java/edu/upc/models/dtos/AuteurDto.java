package edu.upc.models.dtos;

import jakarta.validation.constraints.Email;
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
public class AuteurDto {

	@Positive(message = "Catégorie invalide")
	private long categoriePk;

	@NotBlank(message = "Noms invalides")
	@Size(min = 1, max = 100, message = "Noms invalides")
	private String noms;

	@NotBlank(message = "Ville invalide")
	@Size(min = 1, max = 45, message = "Ville invalide")
	private String ville;

	@NotBlank(message = "Email invalide")
	@Email(message = "Email invalide")
	@Size(max = 80, message = "Email invalide")
	private String email;

	@Positive(message = "Expérience invalide")
	private int experience;

}
