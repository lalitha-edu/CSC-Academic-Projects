package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedList;
import java.util.List;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith ( SpringExtension.class )
public class APICoffeeTest {

    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RecipeService         service;

    @Autowired
    private InventoryService      iService;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        service.deleteAll();
        service.deleteAll();
        iService.deleteAll();

        final Inventory ivt = iService.getInventory();

        final Ingredient chocolate = new Ingredient( "Chocolate", 15 );
        final Ingredient coffee = new Ingredient( "Coffee", 15 );
        final Ingredient milk = new Ingredient( "Milk", 15 );
        final Ingredient sugar = new Ingredient( "Sugar", 15 );

        final List<Ingredient> forInventory = new LinkedList<Ingredient>();

        forInventory.add( chocolate );
        forInventory.add( coffee );
        forInventory.add( milk );
        forInventory.add( sugar );

        try {
            ivt.setIngredients( forInventory );
        }
        catch ( final Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        iService.save( ivt );

        final Recipe recipe = new Recipe();
        recipe.setName( "Coffee" );
        recipe.setPrice( 50 );

        final Ingredient recChocolate = new Ingredient( "Chocolate", 3 );
        final Ingredient recCoffee = new Ingredient( "Coffee", 1 );
        final Ingredient recMilk = new Ingredient( "Milk", 1 );
        final Ingredient recSugar = new Ingredient( "Sugar", 0 );

        recipe.addIngredient( recChocolate );
        recipe.addIngredient( recCoffee );
        recipe.addIngredient( recMilk );
        recipe.addIngredient( recSugar );

        service.save( recipe );
    }

    @Test
    @Transactional
    public void testPurchaseBeverage1 () throws Exception {

        final String name = "Coffee";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 60 ) ) ).andExpect( status().isOk() )
                .andExpect( jsonPath( "$.message" ).value( "We good" ) );

    }

    @Test
    @Transactional
    public void testPurchaseBeverage3 () throws Exception {
        /* Insufficient inventory */

        final Inventory ivt = iService.getInventory();

        final Ingredient coffee = new Ingredient( "Coffee", 0 );

        final LinkedList<Ingredient> coffeeList = new LinkedList<Ingredient>();

        coffeeList.add( coffee );

        ivt.setIngredients( coffeeList );

        iService.save( ivt );

        final Recipe recipe = new Recipe();
        recipe.setName( "Mocha" );
        recipe.setPrice( 50 );

        final Ingredient recChocolate = new Ingredient( "Chocolate", 3 );
        final Ingredient recCoffee = new Ingredient( "Coffee", 50000 );
        final Ingredient recMilk = new Ingredient( "Milk", 1 );
        final Ingredient recSugar = new Ingredient( "Sugar", 0 );

        recipe.addIngredient( recChocolate );
        recipe.addIngredient( recCoffee );
        recipe.addIngredient( recMilk );
        recipe.addIngredient( recSugar );

        service.save( recipe );

        final String name = "Mocha";

        mvc.perform( post( String.format( "/api/v1/makecoffee/%s", name ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( 50 ) ) ).andExpect( status().is4xxClientError() )
                .andExpect( jsonPath( "$.message" ).value( "Not enough inventory" ) );

    }

}
