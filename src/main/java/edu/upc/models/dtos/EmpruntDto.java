package edu.upc.models.dtos;

import jakarta.validation.constraints.Positive;
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
public class EmpruntDto {

	@Positive(message = "Adherent invalide")
	private long adherentPk;

	@Positive(message = "Livre invalide")
	private long livrePk;

}
