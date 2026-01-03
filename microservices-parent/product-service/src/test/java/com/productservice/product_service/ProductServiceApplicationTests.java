package com.productservice.product_service;

import com.productservice.product_service.dto.ProductRequest;
import com.productservice.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;
import com.fasterxml.jackson.databind.*;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
    /*
    We are using docker container for integration test. The test will start the start MongoDB container
    by downloading docker image, and then after starting container, we get replica set url
    and add it to the spring mongoDB uri property dynamically at the time of creating tests.
     */

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    /*
    MockMVC is a testing framework to test the controllers and the web layer to the application
    in isolation. Verifying aspects like status codes and response body, while running the test
    in-memory with a full spring context.
     ----> Simulates HTTP Requests
     ----> Test without server
     ----> Verifies Responses
     ----> Uses a full spring context.
     */
    @Autowired MockMvc mockMvc;

    /*
    Object Mapper is used to convert objects into JSON format and Vice versa.
     */
     ObjectMapper objectMapper = new ObjectMapper();

     @Autowired
    ProductRepository productRepository;

    /*
        Setting up the properties to load the mongoDB container for the tests.
        DynamicPropertyRegistry: It is a powerful testing utility that allows you to register
        properties with dynamically resolved values into Environment for an application context,
        especially in integration tests.
        It uses Supplier functions (like lambda expressions or method references) that are
        only executed when the property is actually needed, enabling things like setting
        database URLs from running Testcontainers or other bean-derived data, making tests
        more realistic and flexible.
     */
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
			dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}

    /*
    Integration test for Post method using MockMvc.
    MockMvc will make a call to the api end-point
    by providing the request string as a request object, and it will expect the status.
     */
	@Test
	void shouldCreateProduct() throws Exception {

       ProductRequest productRequest =  getProductRequest();
       String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString))
                .andExpect(status().isCreated());

      //  Assertions.assertEquals(1, productRepository.findAll().size());

	}

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Iphone 16")
                .description("Iphone-16")
                .price(BigDecimal.valueOf(66000))
                .build();
    }

}
