package edu.upc.utils;

import org.springframework.stereotype.Component;

import edu.upc.models.Livre;
import edu.upc.models.Genre;
import edu.upc.models.Adherent;
import edu.upc.models.Emprunt;
import edu.upc.models.dtos.LivreDto;
import edu.upc.models.dtos.GenreDto;
import edu.upc.models.dtos.AdherentDto;
import edu.upc.models.dtos.EmpruntDto;

@Component
public class Mapper {

	public Livre mapToLivre(LivreDto dto) {
		return Livre.builder().titre(dto.getTitre()).pages(dto.getPages()).build();
	}

	public Genre mapToGenre(GenreDto dto) {
		return Genre.builder().libelle(dto.getLibelle()).build();
	}

	public Adherent mapToAdherent(AdherentDto dto) {
		return Adherent.builder()
				.genrePk(dto.getGenrePk())
				.noms(dto.getNoms())
				.quartier(dto.getQuartier())
				.telephone(dto.getTelephone())
				.quotaMax(dto.getQuotaMax())
				.build();
	}

	public Emprunt mapToEmprunt(EmpruntDto dto) {
		return Emprunt.builder()
				.adherentPk(dto.getAdherentPk())
				.livrePk(dto.getLivrePk())
				.build();
	}

	public GenreDto toGenreDto(Genre entity) {
		return GenreDto.builder().libelle(entity.getLibelle()).build();
	}

	public LivreDto toLivreDto(Livre entity) {
		return LivreDto.builder().titre(entity.getTitre()).pages(entity.getPages()).build();
	}

	public AdherentDto toAdherentDto(Adherent entity) {
		return AdherentDto.builder()
				.genrePk(entity.getGenrePk())
				.noms(entity.getNoms())
				.quartier(entity.getQuartier())
				.telephone(entity.getTelephone())
				.quotaMax(entity.getQuotaMax())
				.build();
	}

	public EmpruntDto toEmpruntDto(Emprunt entity) {
		return EmpruntDto.builder()
				.adherentPk(entity.getAdherentPk())
				.livrePk(entity.getLivrePk())
				.build();
	}

}
