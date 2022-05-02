package com.ecommerceapp.productservice;

import com.ecommerceapp.productservice.dto.ProductRequestDto;
import com.ecommerceapp.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	private static final String GET_MAPPING = "/api/v1/product";
	private static final String POST_MAPPING = "/api/v1/product";
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private final MockMvc mockMvc;
	private final ProductRepository productRepository;

	@Autowired
	ProductServiceApplicationTests(MockMvc mockMvc, ProductRepository productRepository) {
		this.mockMvc = mockMvc;
		this.productRepository = productRepository;
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void createProduct() {
		ProductRequestDto productRequest = getProductRequest();

		try {
			String productRequestAsString = objectMapper.writeValueAsString(productRequest);

			mockMvc.perform(
					MockMvcRequestBuilders.post(POST_MAPPING)
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestAsString)
							.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isCreated());
			Assertions.assertEquals(1, productRepository.findAll().size());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Test
	void getProducts() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get(GET_MAPPING))
					.andExpect(status().isOk());
			Assertions.assertEquals(0, productRepository.findAll().size());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private ProductRequestDto getProductRequest() {
		return ProductRequestDto.builder()
				.name("product example")
				.description("software product")
				.price(BigDecimal.valueOf(13.99))
				.build();
	}

}
