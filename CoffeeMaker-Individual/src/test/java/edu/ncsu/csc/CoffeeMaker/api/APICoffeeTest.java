package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

/**
 * This test file tests the APICoffeeController.java class and is extended with
 * SpringExtension.
 *
 */
@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APICoffeeTest {

    /**
     * MockMvc uses Spring's testing framework to handle requests to the REST
     * API
     */
    @Autowired
    private MockMvc          mvc;

    /**
     * RecipeService object, to be autowired in by Spring to allow for
     * manipulating the Recipe model
     */
    @Autowired
    private RecipeService    service;

    /**
     * InventoryService object, to be autowired in by Spring to allow for
     * manipulating the Inventory model
     */
    @Autowired
    private InventoryService iService;

    /** Declares the milk ingredient */
    private final Ingredient milk      = new Ingredient( "Milk", 5 );
    /** Declares the coffee ingredient */
    private final Ingredient coffee    = new Ingredient( "Coffee", 5 );
    /** Declares the sugar ingredient */
    private final Ingredient sugar     = new Ingredient( "Sugar", 5 );
    /** Declares the chocolate ingredient */
    private final Ingredient chocolate = new Ingredient( "Chocolate", 5 );

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        service.deleteAll();
        iService.deleteAll();

        final Inventory ivt = iService.getInventory();

        ivt.addIngredient( "Milk", 100 );
        ivt.addIngredient( "Sugar", 100 );
        ivt.addIngredient( "Chocolate", 100 );

        iService.save( ivt );

        final Recipe recipe = new Recipe( "Mocha", 50 );
        recipe.addIngredient( chocolate );
        recipe.addIngredient( sugar );
        recipe.addIngredient( coffee );
        recipe.addIngredient( milk );

        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );
        service.save( recipe );

    }

    /**
     * Tests purchasing a beverage with more than enough funds
     *
     * @throws Exception
     *             if the API calls returns an error
     */
    @Test
    @Transactional
    public void testPurchaseBeverage1 () throws Exception {

        final Inventory ivt = iService.getInventory();
        ivt.addIngredient( "Coffee", 100 );
        iService.save( ivt );

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 100 ) ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$.message" ).value( "50" ) );

    }

    /**
     * Tests purchasing a beverage with insufficient funds
     *
     * @throws Exception
     *             if the API calls returns an error
     */
    @Test
    @Transactional
    public void testPurchaseBeverage2 () throws Exception {
        /* Insufficient amount paid */

        final Inventory ivt = iService.getInventory();
        ivt.addIngredient( "Coffee", 100 );
        iService.save( ivt );

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 40 ) ) ).andExpect( status().is4xxClientError() )
                .andExpect( jsonPath( "$.message" ).value( "Not enough money paid" ) );

    }

    /**
     * Tests purchasing a beverage with insufficient inventory
     *
     * @throws Exception
     *             if the API calls returns an error
     */
    @Test
    @Transactional
    public void testPurchaseBeverage3 () throws Exception {
        /* Insufficient inventory */

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 50 ) ) ).andExpect( status().is4xxClientError() )
                .andExpect( jsonPath( "$.message" ).value( "Not enough inventory" ) );

    }

}
