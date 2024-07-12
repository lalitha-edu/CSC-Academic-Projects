package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

/**
 * Using @SpringBootTest to test the methods
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith ( SpringExtension.class )
public class APIRecipeTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    /**
     * WebApplicationContext object, to be autowired in by Spring
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * RecipeService object, to be autowired in by Spring
     */
    @Autowired
    private RecipeService         service;

    /** Declares the milk ingredient */
    private final Ingredient      milk   = new Ingredient( "Milk", 4 );

    /** Declares the coffee ingredient */
    private final Ingredient      coffee = new Ingredient( "Coffee", 4 );

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        service.deleteAll();
    }

    /**
     * Tests if a new recipe can be saved to the database with API calls
     *
     * @throws Exception
     *             if the API calls returns an error
     */
    @Test
    @Transactional
    public void ensureRecipe () throws Exception {
        service.deleteAll();

        final Recipe r = new Recipe( "Mocha", 10 );
        r.setPrice( 10 );
        r.addIngredient( coffee );
        // r.setName( "Mocha" );
        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

    }

    /**
     * Tests if a new recipe can have ingredients added to it and then can be
     * saved to the database with API calls
     *
     * @throws Exception
     *             if the API calls returns an error
     */
    @Test
    @Transactional
    public void testRecipeAPI () throws Exception {

        service.deleteAll();

        final Recipe recipe = new Recipe( "Delicious Not-Coffee", 5 );
        // final Ingredient existingIngredient = new Ingredient( "Whipped
        // Cream", 6 );

        recipe.addIngredient( milk );
        recipe.addIngredient( coffee );
        // recipe.setName( "Delicious Not-Coffee" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipe ) ) ).andExpect( status().isOk() );

        Assertions.assertEquals( 1, (int) service.count() );

    }

    /**
     * Tests if two recipes with the same name could be saved to the database
     * with API calls
     *
     * @throws Exception
     *             if the API calls returns an error
     */
    @Test
    @Transactional
    public void testAddRecipe2 () throws Exception {

        /* Tests a recipe with a duplicate name to make sure it's rejected */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = new Recipe( name, 20 );
        r1.addIngredient( milk );

        service.save( r1 );
        // mvc.perform( post( "/api/v1/recipes" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( r1 ) ) ).andExpect( status().isOk()
        // );
        final Recipe r2 = new Recipe( name, 60 );
        r2.addIngredient( coffee );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().is4xxClientError() );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one recipe in the CoffeeMaker" );
    }

    /**
     * Tests if a fourth recipe could be saved to the database with API calls
     *
     * @throws Exception
     *             if the API calls returns an error
     */
    @Test
    @Transactional
    public void testAddRecipe15 () throws Exception {

        /* Tests to make sure that our cap of 3 recipes is enforced */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, 3, 1, 1, 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, 3, 1, 1, 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, 3, 2, 2, 0 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipes in the database" );

        final Recipe r4 = createRecipe( "Hot Chocolate", 75, 0, 2, 1, 2 );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r4 ) ) ).andExpect( status().isInsufficientStorage() );

        Assertions.assertEquals( 3, service.count(), "Creating a fourth recipe should not get saved" );
    }

    /**
     * Test delete one recipe functionality Written by Alex
     */
    @Test
    @Transactional
    public void testDeleteRecipeOne () throws Exception {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        // Create a recipe
        final Recipe originalRecipe = createRecipe( "Coffee", 2, 3, 1, 1, 0 );
        service.save( originalRecipe );

        // // First user deletes the recipe
        service.delete( originalRecipe );

        mvc.perform( get( "/api/v1/recipes/Coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( originalRecipe ) ) ).andExpect( status().isNotFound() );
    }

    /**
     * Test method for deleting recipe Test delete all recipes (maximum number
     * of recipes exist) And test delete when no recipes exist Written by
     * ledupug
     */
    @Test
    @Transactional
    public void testDeleteAllRecipe () throws Exception {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        // Create a recipe
        final Recipe originalRecipe = createRecipe( "Coffee", 2, 3, 1, 1, 0 );
        final Recipe secondRecipe = createRecipe( "Chocolate", 5, 3, 2, 1, 0 );
        final Recipe thirdRecipe = createRecipe( "Mocha", 3, 3, 2, 2, 1 );

        service.save( originalRecipe );
        service.save( secondRecipe );
        service.save( thirdRecipe );

        mvc.perform( delete( "/api/v1/recipes/Chocolate" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( "Chocolate" ) ) ).andExpect( status().isOk() );

        // test for a recipe that was never existed in the database
        mvc.perform( delete( "/api/v1/recipes/Latte" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( "Latte" ) ) ).andExpect( status().isNotFound() );

        mvc.perform( delete( "/api/v1/recipes/Coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( "Coffee" ) ) ).andExpect( status().isOk() );

        mvc.perform( delete( "/api/v1/recipes/Mocha" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( "Mocha" ) ) ).andExpect( status().isOk() );

        // test when zero recipes exist in the database
        mvc.perform( delete( "/api/v1/recipes/Mocha" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( "Mocha" ) ) ).andExpect( status().isNotFound() );

    }

    private Recipe createRecipe ( final String name, final Integer price, final Integer coffee, final Integer milk,
            final Integer sugar, final Integer chocolate ) {
        final Recipe recipe = new Recipe( name, price );
        // recipe.setName( name );
        // recipe.setPrice( price );

        return recipe;
    }

}
