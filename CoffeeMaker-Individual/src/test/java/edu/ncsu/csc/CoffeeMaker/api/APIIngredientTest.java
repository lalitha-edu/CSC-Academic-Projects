package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

/**
 * This test file tests the APICoffeeController.java class and is extended with
 * SpringExtension.
 *
 */
@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIIngredientTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    @Autowired
    private MockMvc               mvc;

    /**
     * WebApplicationContext object, to be autowired in by Spring
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService     iService;

    /** Declares the vanilla ingredient */
    final Ingredient              vanilla = new Ingredient( "Vanilla", 6 );

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

    }

    /**
     * Tests all of the API calls working together to create ingredientscand
     * edit them
     *
     * @throws Exception
     *             if API calls returns an error
     */
    @Test
    @Transactional
    public void apiIngredient () throws Exception {

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( vanilla ) ) ).andExpect( status().isOk() );

        Assertions.assertEquals( 1, iService.findAll().size() );

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( vanilla ) ) ).andExpect( status().is4xxClientError() );

        mvc.perform( put( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( new Ingredient( "Vanilla", 8 ) ) ) ).andExpect( status().isOk() );

        mvc.perform( put( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( new Ingredient( "Coffee", 8 ) ) ) )
                .andExpect( status().is4xxClientError() );

        // mvc.perform( delete( "/api/v1/ingredients/{name}" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( name ) ) ).andExpect(
        // status().isOk() );
    }

}
