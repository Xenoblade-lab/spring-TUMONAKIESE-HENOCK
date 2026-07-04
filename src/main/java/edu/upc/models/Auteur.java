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
public class Auteur implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long categoriePk;

	private String noms, ville, email;

	private int experience;

	private Categorie categorie;

	@Builder.Default
	private List<Article> articles = new ArrayList<>();

}
