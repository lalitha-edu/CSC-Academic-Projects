package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

/**
 * This test file tests the APITest.java class and is extended with
 * SpringExtension.
 */
@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APITest {

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
     * InventoryService object, to be autowired in by Spring to allow for
     * manipulating the Inventory model
     */
    @Autowired
    private InventoryService      invenService;
    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService     ingredientService;
    // /**
    // * RecipeService object, to be autowired in by Spring to allow for
    // * manipulating the Recipe model
    // */
    // @Autowired
    // private RecipeService service;

    /** Declares the milk ingredient */
    final Ingredient              milk = new Ingredient( "Milk", 5 );

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
    public void apiRecipe () throws Exception {

        ingredientService.deleteAll();

        final Ingredient existingIngredient = new Ingredient( "Whipped Cream", 6 );
        // ingredientService.save( existingIngredient );
        // final Ingredient updateIngredient = new Ingredient( "Whipped Cream",
        // 10 );

        final Ingredient newIngredient = new Ingredient( "Vanilla", 5 );

        final Ingredient caramelIngredient = new Ingredient( "Caramel", 10 );

        // final Inventory newInventory = invenService.getInventory();
        // newInventory.addIngredient( "Vanilla", 5 );
        // invenService.addIngredient( "Vanilla", 5 );
        // random
        final String jsonRequestBody = TestUtils.asJsonString( existingIngredient.getName() );
        System.out.println( "JSON Request Body: " + jsonRequestBody );

        mvc.perform( put( "/api/v1/addToInventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( existingIngredient ) ) ).andExpect( status().isOk() );

        Assert.assertEquals( 1, ingredientService.count() );

        // mvc.perform( put( "/api/v1/updateInventory" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( updateIngredient ) ) ).andExpect(
        // status().isOk() );
        // Assert.assertEquals( 1, ingredientService.count() );

        mvc.perform( put( "/api/v1/addToInventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( newIngredient ) ) ).andExpect( status().isOk() );

        Assert.assertEquals( 2, ingredientService.count() );

        mvc.perform( put( "/api/v1/addToInventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( caramelIngredient ) ) ).andExpect( status().isOk() );
        Assert.assertEquals( 3, ingredientService.count() );

        mvc.perform( delete( "/api/v1/removeInventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( newIngredient ) ) ).andExpect( status().isOk() );
        //
        Assert.assertEquals( 1, invenService.count() );

        mvc.perform( put( "/api/v1/addToInventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( new Ingredient( "Agave", 0 ) ) ) )
                .andExpect( status().isNotAcceptable() );

        mvc.perform( put( "/api/v1/addToInventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( new Ingredient( "Caramel", 1 ) ) ) )
                .andExpect( status().isNotAcceptable() );

        mvc.perform( delete( "/api/v1/removeInventory" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( new Ingredient( "Agave", 1 ) ) ) ).andExpect( status().isNotFound() );

        // Verify that addIngredient was called with the correct arguments
        // verify( ingredientService ).addIngredient( newIngredient );

        // Assert the number of ingredients after adding to inventory

        // final Ingredient whippedCream = new Ingredient( "Whipped Cream", 2 );
        // final Ingredient vanilla = new Ingredient( "Vanilla", 5 );
        //
        // final Inventory newInventory = new Inventory();
        //
        // ingredientService.save( whippedCream );
        //
        // ingredientService.save( vanilla );
        //
        // Assert.assertEquals( 2, ingredientService.count() );
        //
        // mvc.perform( post( "/api/v1/inventory" ).contentType(
        // MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( newInventory ) ) ).andExpect(
        // status().isOk() );

        // // changing from
        // String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo(
        // print() ).andExpect( status().isOk() )
        // .andReturn().getResponse().getContentAsString();
        // // System.out.println( recipe + "hey!!!!!!!!!!!!!!!!!!!!" );
        //
        // /* Figure out if the recipe we want is present */
        // if ( !recipe.contains( "Mocha" ) ) {
        // final Recipe r = new Recipe();
        //
        // r.setPrice( 10 );
        // r.setName( "Mocha" );
        // r.addIngredient( whippedCream );
        //
        // // changing from "/api/v1/recipes" to /ingredients/{name}
        //
        // service.save( r );
        //
        // }
        //
        // recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print()
        // ).andExpect( status().isOk() ).andReturn()
        // .getResponse().getContentAsString();
        //
        // assertTrue( recipe.contains( "Mocha" ) );
        // // final Inventory inventory = invenService.getInventory();
        // // inventory.addIngredients( Milk );
        // System.out.println( recipe );
        //
        // mvc.perform( post( String.format( "/api/v1/makecoffee/%s", "Mocha" )
        // ).contentType( MediaType.APPLICATION_JSON )
        // .content( TestUtils.asJsonString( 100 ) ) ).andExpect(
        // status().isOk() ).andDo( print() );

    }

}
