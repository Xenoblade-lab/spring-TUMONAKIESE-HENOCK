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

	private static Long genreId;
	private static Long livreId;
	private static Long adherentId;
	private static Long empruntId;

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
	void genresCrud() throws Exception {
		mockMvc.perform(get("/api/genres"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/genres")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Genre API " + suffix + "\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();

		genreId = readId(created);

		mockMvc.perform(get("/api/genres/" + genreId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Genre API " + suffix));

		mockMvc.perform(put("/api/genres/" + genreId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Genre MAJ " + suffix + "\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Genre MAJ " + suffix));
	}

	@Test
	@Order(2)
	void livresApiStillWorks() throws Exception {
		mockMvc.perform(get("/api/livres"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/livres")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"titre\":\"Test Livre API " + suffix + "\",\"pages\":95}"))
				.andExpect(status().isCreated())
				.andReturn();

		livreId = readId(created);

		mockMvc.perform(get("/api/livres/" + livreId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.pages").value(95));
	}

	@Test
	@Order(3)
	void adherentsWithGenreJoin() throws Exception {
		mockMvc.perform(get("/api/adherents"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].genre.libelle").exists());

		mockMvc.perform(get("/api/adherents?keyword=Henock"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].noms").value("Tumonakiese Henock"))
				.andExpect(jsonPath("$[0].genre.libelle").value("Roman"));

		MvcResult created = mockMvc.perform(post("/api/adherents")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "genrePk": 1,
								  "noms": "Test Adherent API",
								  "quartier": "Lubumbashi Katuba",
								  "telephone": "+243 900 000 000",
								  "quotaMax": 4
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.genre.libelle").value("Roman"))
				.andReturn();

		adherentId = readId(created);

		mockMvc.perform(get("/api/adherents/" + adherentId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.livres").isArray());
	}

	@Test
	@Order(4)
	void empruntEnrollment() throws Exception {
		mockMvc.perform(get("/api/emprunts"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/emprunts")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"adherentPk\":" + adherentId + ",\"livrePk\":" + livreId + "}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.adherent.noms").value("Test Adherent API"))
				.andExpect(jsonPath("$.livre.titre").value("Test Livre API " + suffix))
				.andReturn();

		empruntId = readId(created);

		mockMvc.perform(delete("/api/emprunts/" + empruntId))
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void webPages() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/genres"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/livres"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/adherents"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/adherents?keyword=Henock"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/emprunts"))
				.andExpect(status().isOk());
	}

	@AfterAll
	void cleanupTestData() throws Exception {
		cleanupCreatedEntities();
	}

	private void cleanupCreatedEntities() throws Exception {
		if (empruntId != null) {
			mockMvc.perform(delete("/api/emprunts/" + empruntId));
			empruntId = null;
		}
		if (adherentId != null) {
			mockMvc.perform(delete("/api/adherents/" + adherentId));
			adherentId = null;
		}
		if (livreId != null) {
			mockMvc.perform(delete("/api/livres/" + livreId));
			livreId = null;
		}
		if (genreId != null) {
			mockMvc.perform(delete("/api/genres/" + genreId));
			genreId = null;
		}
	}

}
