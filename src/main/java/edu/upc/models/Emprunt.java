package edu.upc.models;

import java.io.Serializable;

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
public class Emprunt implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long adherentPk, livrePk;

	private Adherent adherent;

	private Livre livre;

}
