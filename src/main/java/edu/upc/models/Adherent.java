package edu.upc.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class Adherent implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long genrePk;

	private String noms, quartier, telephone;

	private int quotaMax;

	private Genre genre;

	@Builder.Default
	private List<Livre> livres = new ArrayList<>();

}
