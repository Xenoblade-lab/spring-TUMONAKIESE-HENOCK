package edu.upc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	private static Long categorieId;
	private static Long articleId;
	private static Long auteurId;
	private static Long commentaireId;

	private static String suffix;

	private static long readId(MvcResult result) throws Exception {
		Object id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
		return ((Number) id).longValue();
	}

	@Test
	@Order(0)
	void initSuffix() {
		suffix = String.valueOf(System.currentTimeMillis());
	}

	@Test
	@Order(1)
	void categoriesCrud() throws Exception {
		mockMvc.perform(get("/api/categories"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Categorie API " + suffix + "\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();

		categorieId = readId(created);

		mockMvc.perform(get("/api/categories/" + categorieId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Categorie API " + suffix));

		mockMvc.perform(put("/api/categories/" + categorieId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Categorie MAJ " + suffix + "\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Categorie MAJ " + suffix));
	}

	@Test
	@Order(2)
	void articlesApiStillWorks() throws Exception {
		mockMvc.perform(get("/api/articles"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/articles")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"titre\":\"Test Article API " + suffix + "\",\"vues\":95}"))
				.andExpect(status().isCreated())
				.andReturn();

		articleId = readId(created);

		mockMvc.perform(get("/api/articles/" + articleId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vues").value(95));
	}

	@Test
	@Order(3)
	void auteursWithCategorieJoin() throws Exception {
		mockMvc.perform(get("/api/auteurs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].categorie.libelle").exists());

		mockMvc.perform(get("/api/auteurs?keyword=Henock"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].noms").value("Tumonakiese Henock"))
				.andExpect(jsonPath("$[0].categorie.libelle").value("Technologie"));

		MvcResult created = mockMvc.perform(post("/api/auteurs")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "categoriePk": 1,
								  "noms": "Test Auteur API",
								  "ville": "Lubumbashi",
								  "email": "test.auteur@blog.rdc",
								  "experience": 4
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.categorie.libelle").value("Technologie"))
				.andReturn();

		auteurId = readId(created);

		mockMvc.perform(get("/api/auteurs/" + auteurId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.articles").isArray());
	}

	@Test
	@Order(4)
	void commentaireEnrollment() throws Exception {
		mockMvc.perform(get("/api/commentaires"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/commentaires")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"auteurPk\":" + auteurId + ",\"articlePk\":" + articleId + ",\"texte\":\"Super article de test !\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.auteur.noms").value("Test Auteur API"))
				.andExpect(jsonPath("$.article.titre").value("Test Article API " + suffix))
				.andReturn();

		commentaireId = readId(created);

		mockMvc.perform(delete("/api/commentaires/" + commentaireId))
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void webPages() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/categories"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/articles"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/auteurs"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/auteurs?keyword=Henock"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/commentaires"))
				.andExpect(status().isOk());
	}

	@AfterAll
	void cleanupTestData() throws Exception {
		cleanupCreatedEntities();
	}

	private void cleanupCreatedEntities() throws Exception {
		if (commentaireId != null) {
			mockMvc.perform(delete("/api/commentaires/" + commentaireId));
			commentaireId = null;
		}
		if (auteurId != null) {
			mockMvc.perform(delete("/api/auteurs/" + auteurId));
			auteurId = null;
		}
		if (articleId != null) {
			mockMvc.perform(delete("/api/articles/" + articleId));
			articleId = null;
		}
		if (categorieId != null) {
			mockMvc.perform(delete("/api/categories/" + categorieId));
			categorieId = null;
		}
	}

}
