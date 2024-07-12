package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith ( SpringExtension.class )
public class APIRecipeTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RecipeService         service;

    @Autowired
    private IngredientService     ingredientService;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        service.deleteAll();
        ingredientService.deleteAll();
    }

    @Test
    @Transactional
    public void ensureRecipe () throws Exception {
        service.deleteAll();

        final Recipe r = new Recipe();
        final Ingredient i1 = new Ingredient( "Coffee", 5 );
        final Ingredient i2 = new Ingredient( "Milk", 10 );
        r.addIngredient( i1 );
        r.addIngredient( i2 );
        r.setPrice( 10 );
        r.setName( "Mocha" );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void testRecipeAPI () throws Exception {

        service.deleteAll();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        final Ingredient i1 = new Ingredient( "Coffee", 5 );
        final Ingredient i2 = new Ingredient( "Milk", 10 );

        recipe.addIngredient( i1 );
        recipe.addIngredient( i2 );
        recipe.setPrice( 5 );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( recipe ) ) );

        Assertions.assertEquals( 1, (int) service.count() );

    }

    @Test
    @Transactional
    public void testAddRecipe2 () throws Exception {

        /* Tests a recipe with a duplicate name to make sure it's rejected */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50 );

        service.save( r1 );

        final Recipe r2 = createRecipe( name, 50 );
        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().is4xxClientError() );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one recipe in the CoffeeMaker" );
    }

    @Test
    @Transactional
    public void testAddRecipe15 () throws Exception {

        /* Tests to make sure that our cap of 3 recipes is enforced */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipes in the database" );

        final Recipe r4 = createRecipe( "Hot Chocolate", 75 );

        mvc.perform( post( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r4 ) ) ).andExpect( status().isInsufficientStorage() );

        Assertions.assertEquals( 3, service.count(), "Creating a fourth recipe should not get saved" );
    }

    @Test
    @Transactional
    public void testDeleteRecipe1 () throws Exception {

        /*
         * Tests to see if a recipe can be deleted with api
         */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );

        Assertions.assertEquals( 1, service.count(), "Creating a recipe should result in one recipe in the database" );

        mvc.perform( delete( "/api/v1/recipes/Coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r1 ) ) ).andExpect( status().isOk() );

        Assertions.assertEquals( 0, service.count(),
                "Deleting the recipe should remove it from the list, making it empty" );

        final String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( !recipe.contains( "Coffee" ), "The recipes should not contain the Coffee recipe" );
    }

    @Test
    @Transactional
    public void testDeleteRecipe2 () throws Exception {

        /*
         * Tests to see if a specific recipe can be deleted with api from a full
         * list of recipes
         */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipe in the database" );

        mvc.perform( delete( "/api/v1/recipes/Mocha" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r2 ) ) ).andExpect( status().isOk() );

        Assertions.assertEquals( 2, service.count(),
                "Deleting the recipe should remove it from the list, leaving three recipes" );

        String recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() )
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( !recipe.contains( "Mocha" ), "The recipes should not contain the Mocha recipe" );
        Assertions.assertTrue( recipe.contains( "Coffee" ), "The recipes should contain the Coffee recipe" );
        Assertions.assertTrue( recipe.contains( "Latte" ), "The recipes should contain the Latte recipe" );

        mvc.perform( delete( "/api/v1/recipes/Coffee" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r1 ) ) ).andExpect( status().isOk() );

        mvc.perform( delete( "/api/v1/recipes/Latte" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r3 ) ) ).andExpect( status().isOk() );

        recipe = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() ).andExpect( status().isOk() ).andReturn()
                .getResponse().getContentAsString();

        Assertions.assertTrue( !recipe.contains( "Mocha" ), "The recipes should not contain the Mocha recipe" );
        Assertions.assertTrue( !recipe.contains( "Coffee" ), "The recipes not should contain the Coffee recipe" );
        Assertions.assertTrue( !recipe.contains( "Latte" ), "The recipes not should contain the Latte recipe" );

        Assertions.assertEquals( 0, service.count(),
                "Deleting the recipes should remove them from the list, making it empty" );
    }

    @Test
    @Transactional
    public void testEditRecipe1 () throws Exception {

        /* Tests editing a recipe through API */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );

        Assertions.assertEquals( 1, service.count(), "Creating a recipe should result in one recipe in the database" );

        r1.getIngredients().get( 0 ).setAmount( 45 );

        Assertions.assertEquals( 1, service.count(), "Checking to make sure the service count is unchanged" );

        Assertions.assertEquals( 45, r1.getIngredients().get( 0 ).getAmount(),
                "The choclate amount of the recipe should now be 45" );

        final String recipeString = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( !recipeString.contains( "\"name\":\"Coffee\",\"amount\":0" ),
                "Sees if the recipe no longer has 0 chocolate in the API" );
        Assertions.assertTrue( recipeString.contains( "\"name\":\"Coffee\",\"amount\":45" ),
                "Sees if the recipe got updated in the API" );
    }

    @Test
    @Transactional
    public void testEditRecipe2 () throws Exception {

        /*
         * Tests editing a recipe and ensures it does not edit all recipes
         * through API
         */

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipe in the database" );

        r2.getIngredients().get( 0 ).setAmount( 45 );

        Assertions.assertEquals( 5, r1.getIngredients().get( 0 ).getAmount(),
                "The choclate amount of the Coffee should remain unchanged" );
        Assertions.assertEquals( 45, r2.getIngredients().get( 0 ).getAmount(),
                "The choclate amount of the recipe should now be 45" );
        Assertions.assertEquals( 5, r3.getIngredients().get( 0 ).getAmount(),
                "The choclate amount of the Latte should remain unchanged" );

        Assertions.assertEquals( 3, service.count(), "Checking to make sure the service count is unchanged" );

        final String recipeString = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( !recipeString.contains( "\"name\":\"Coffee\",\"amount\":2" ),
                "Sees if the Mocha recipe no longer has 2 chocolate in the API" );
        Assertions.assertTrue( recipeString.contains( "\"name\":\"Coffee\",\"amount\":45" ),
                "Sees if the Mocha recipe got updated in the API" );

        Assertions.assertTrue( recipeString.contains( "\"name\":\"Coffee\",\"amount\":5" ),
                "Ensure the Coffee recipe still has 5 coffee in the API" );
        Assertions.assertTrue( recipeString.contains( "\"name\":\"Coffee\",\"amount\":5" ),
                "Ensure the Latte recipe still has 5 coffee in the API" );
    }

    /**
     * Testing Adding an Ingredient to a recipe through an endpoint
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEditRecipe3 () throws Exception {
        final Recipe r1 = new Recipe();
        r1.setName( "Mocha" );
        r1.setPrice( 50 );
        service.save( r1 );

        final Ingredient i = new Ingredient( "Chocolate", 37 );
        ingredientService.save( i );

        mvc.perform( post( String.format( "/api/v1/recipes/Mocha" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isOk() );

        final String recipeString = mvc.perform( get( "/api/v1/recipes" ) ).andDo( print() )
                .andExpect( status().isOk() ).andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( recipeString.contains( "Chocolate" ), "Sees if the Chocolate was added to Mocha" );
    }

    /**
     * Tests Delete Ingredient from Recipe
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEditRecipe4 () throws Exception {
        final Recipe r1 = new Recipe();
        r1.setName( "Mocha" );
        r1.setPrice( 50 );

        final Ingredient i = new Ingredient( "Chocolate", 37 );

        r1.addIngredient( i );
        service.save( r1 );

        mvc.perform( delete( "/api/v1/recipes/Mocha/Chocolate" ) ).andExpect( status().isOk() );
    }

    /**
     * Testing editing Ingredient units
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void testEditRecipe5 () throws Exception {
        final Recipe r1 = new Recipe();
        r1.setName( "Mocha" );
        r1.setPrice( 50 );

        final Ingredient i = new Ingredient( "Chocolate", 37 );

        r1.addIngredient( i );
        service.save( r1 );

        mvc.perform( put( "/api/v1/recipes/Mocha/Chocolate" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 25 ) ) ).andExpect( status().isOk() );

    }

    @Test
    @Transactional
    public void testEditRecipe6 () throws Exception {
        final Recipe r1 = new Recipe();
        r1.setName( "Mocha" );
        r1.setPrice( 50 );

        final Ingredient i = new Ingredient( "Chocolate", 37 );

        r1.addIngredient( i );
        service.save( r1 );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 35 );

        final Ingredient i2 = new Ingredient( "Chocolate", 15 );
        final Ingredient i3 = new Ingredient( "Joe", 3 );

        r2.addIngredient( i2 );
        r2.addIngredient( i3 );

        final Recipe r3 = new Recipe();
        r3.setName( "Brocha" );

        mvc.perform( put( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r3 ) ) ).andExpect( status().is4xxClientError() );

        final Recipe r4 = new Recipe();
        r4.setName( "Mocha" );
        r4.setPrice( -2 );

        mvc.perform( put( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r4 ) ) ).andExpect( status().is4xxClientError() );

        final Recipe r5 = new Recipe();
        r5.setName( "Mocha" );
        r5.setPrice( 25 );

        final Ingredient i4 = new Ingredient( "Joe", -3 );

        r5.addIngredient( i4 );

        mvc.perform( put( "/api/v1/recipes" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r5 ) ) ).andExpect( status().is4xxClientError() );

    }

    private Recipe createRecipe ( final String name, final Integer price ) {
        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );

        final Ingredient i1 = new Ingredient( "Coffee", 5 );
        final Ingredient i2 = new Ingredient( "Milk", 10 );

        recipe.addIngredient( i1 );
        recipe.addIngredient( i2 );

        return recipe;
    }

}
