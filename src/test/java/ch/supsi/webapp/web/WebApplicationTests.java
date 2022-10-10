package ch.supsi.webapp.web;


import ch.supsi.webapp.web.item.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class WebApplicationTests {

	@Autowired
	private MockMvc mvc;

	private static int id;
	private static int id2;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@Order(1)
	// GET /blogpost -> size == 1
	public void test01() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(1)));
	}

	@Test
	@Order(2)
	// POST /blogpost
	public void test02() throws Exception {
		MvcResult mvcResult = this.mvc.perform(post("/items").header("Content-Type", "application/json").content("{\"title\":\"titolo\", \"description\":\"testo\", \"author\":\"admin\", \"category\":\"Veicoli\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("titolo"))
				.andExpect(jsonPath("$.description").value("testo"))
				.andExpect(jsonPath("$.author").value("admin"))
				.andExpect(jsonPath("$.category").value("Veicoli"))
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
		id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Item.class).getId(); System.out.println(id);
	}

	@Test
	@Order(3)
	// GET /blogpost -> size == 2
	public void test03() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(2)))
				.andExpect(jsonPath("$[1].title").value("titolo"))
				.andExpect(jsonPath("$[1].author").value("admin"))
				.andExpect(jsonPath("$[1].category").value("Veicoli"))
				.andExpect(jsonPath("$[1].description").value("testo"));
	}

	@Test
	@Order(4)
	// GET /blogpost/{id}
	public void test04() throws Exception {
		this.mvc.perform(get("/items/"+id).accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$.title").value("titolo"))
				.andExpect(jsonPath("$.description").value("testo"))
				.andExpect(jsonPath("$.author").value("admin"))
				.andExpect(jsonPath("$.category").value("Veicoli"))
				.andExpect(jsonPath("$.id").value(id));
	}

	@Test
	@Order(5)
	// GET /blogpost/{id}+100 -> 404
	public void test05() throws Exception {
		this.mvc.perform(get("/blogpost/"+(id+100)).accept("application/json"))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(6)
	// PUT /blogpost/{id}
	public void test06() throws Exception {
		this.mvc.perform(put("/items/"+id).header("Content-Type", "application/json").content("{\"title\":\"titolo2\", \"description\":\"testo2\", \"author\":\"admin\", \"category\":\"Veicoli\" }"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$.title").value("titolo2"))
				.andExpect(jsonPath("$.description").value("testo2"))
				.andExpect(jsonPath("$.author").value("admin"))
				.andExpect(jsonPath("$.category").value("Veicoli"))
				.andExpect(jsonPath("$.id").value(id));
	}

	@Test
	@Order(7)
	// PUT /blogpost/{id}+1 -> 404
	public void test07() throws Exception {
		this.mvc.perform(put("/items/"+(id+1)).header("Content-Type", "application/json").content("{\"title\":\"titolo2\", \"description\":\"testo2\", \"author\":\"admin\", \"category\":\"Veicoli\"}"))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(8)
	// GET /blogpost -> size == 2
	public void test08() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(2)))
				.andExpect(jsonPath("$[1].title").value("titolo2"))
				.andExpect(jsonPath("$[1].author").value("admin"))
				.andExpect(jsonPath("$[1].category").value("Veicoli"))
				.andExpect(jsonPath("$[1].description").value("testo2"));
	}

	@Test
	@Order(9)
	// POST /blogpost
	public void test09() throws Exception {
		MvcResult mvcResult = this.mvc.perform(post("/items").header("Content-Type", "application/json").content("{\"title\":\"titolo3\", \"description\":\"testo3\", \"author\":\"admin\", \"category\":\"Veicoli\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("titolo3"))
				.andExpect(jsonPath("$.description").value("testo3"))
				.andExpect(jsonPath("$.author").value("admin"))
				.andExpect(jsonPath("$.category").value("Veicoli"))
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
		id2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Item.class).getId(); System.out.println(id);
	}

	@Test
	@Order(10)
	// DELETE /blogpost/{id}
	public void test10() throws Exception {
		this.mvc.perform(delete("/items/"+id))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	@Order(11)
	// DELETE /blogpost/{id}+100 -> 404
	public void test11() throws Exception {
		this.mvc.perform(delete("/items/"+(id+100)))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(12)
	// GET /blogpost -> size == 2
	public void test12() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(2)))
				.andExpect(jsonPath("$[1].title").value("titolo3"))
				.andExpect(jsonPath("$[1].author").value("admin"))
				.andExpect(jsonPath("$[1].category").value("Veicoli"))
				.andExpect(jsonPath("$[1].description").value("testo3"));
	}

	@Test
	@Order(13)
	// DELETE /blogpost/{id2}
	public void test13() throws Exception {
		this.mvc.perform(delete("/items/"+id2))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	@Order(14)
	// GET /blogpost -> size == 1
	public void test14() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(1)));
	}

}

