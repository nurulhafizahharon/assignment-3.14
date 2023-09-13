package com.skillsunion.shoppingcartapi.controller;

import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillsunion.shoppingcartapi.entity.Cart;
import com.skillsunion.shoppingcartapi.entity.Catalogue;
import com.skillsunion.shoppingcartapi.repository.CatalogueRepository;

@WebMvcTest(CatalogueController.class)
public class CatalogueControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CatalogueRepository mockRepo;

	@Autowired
	private ObjectMapper objectMapper;

	// Add code here

	// SCENARIO 1: CATALOGUE ID NOT FOUND, RETURN STATUS 404 NOT FOUND
	@Test
	public void getCatalogueIdNotFoundTest() throws Exception {
		Integer catalogueId = 1;
		when(mockRepo.findById(catalogueId)).thenReturn(Optional.empty());

		mockMvc.perform(MockMvcRequestBuilders.get("/catalogues/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	// SCENARIO 2: CATALOGUE ID PRESENT, RETURN STATUS 200 OK AND RESPONSE BODY
	@Test
	public void getCatalogueIdTest() throws Exception {

		Catalogue catalogue = new Catalogue();
		catalogue.setId(1);

		when(mockRepo.findById(1)).thenReturn(Optional.of(catalogue));

		RequestBuilder request = MockMvcRequestBuilders.get("/catalogues/1");

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1));
	}

	// SCENARIO 3: DATABASE CONNECTION IS LOST, RETURN STATUS 500 INTERNAL SERVER
	// ERROR
	@Test
	public void databaseConnectionLostTest() throws Exception {

		Catalogue catalogue = new Catalogue();
		catalogue.setId(1);
		// Cart cart = new Cart();
		// cart.setItem(catalogue);
		// cart.setQuantity(10);

		String invalidCartAsJSON = objectMapper.writeValueAsString(catalogue);

		when(mockRepo.save(Mockito.any(Catalogue.class))).thenThrow(new RuntimeException("Database	connection lost"));

		RequestBuilder request = MockMvcRequestBuilders.post("/catalogues")
				.content(invalidCartAsJSON)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());

	}

	@Test
	public void testCreateCatalogueWhenDatabaseConnectionLost() throws Exception {
		// Create a sample request body
		Catalogue catalogue = new Catalogue();
		catalogue.setId(1);
		// Cart cart = new Cart();
		// cart.setItem(catalogue);
		// cart.setQuantity(10);

		String invalidCartAsJSON = objectMapper.writeValueAsString(catalogue);

		// Mock the repository behavior to throw an exception
		Mockito.when(mockRepo.save(Mockito.any(Catalogue.class)))
				.thenThrow(new RuntimeException("Database connection lost"));

		// Perform the POST request and expect a 500 Internal Server Error
		mockMvc.perform(MockMvcRequestBuilders.post("/catalogues")
				.content(invalidCartAsJSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}
}
