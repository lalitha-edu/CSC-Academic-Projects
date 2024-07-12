package edu.ncsu.csc.CoffeeMaker;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )

public class TestDatabaseInteraction {

    @Autowired
    private RecipeService recipeService;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        recipeService.deleteAll();
    }

    /**
     * Tests the RecipeService class
     */
    @Test
    @Transactional
    public void testRecipes () {
        /* We'll fill this out in a bit */
        final Recipe r = new Recipe();

        /* set fields here */
        r.setName( "Sprite" );
        r.setPrice( 10 );
        // r.setCoffee( 1 );
        // r.setMilk( 10 );
        // r.setSugar( 8 );
        // r.setChocolate( 1 );

        recipeService.save( r );

        // Create list of recipes from database
        final List<Recipe> dbRecipes = recipeService.findAll();

        assertEquals( 1, dbRecipes.size() );

        final Recipe dbRecipe = dbRecipes.get( 0 );

        assertEquals( r.getName(), dbRecipe.getName() );
        /* Test all of the fields! You can also us assertAll. */

        assertEquals( r.getPrice(), dbRecipe.getPrice() );
        // assertEquals( r.getCoffee(), dbRecipe.getCoffee() );
        // assertEquals( r.getMilk(), dbRecipe.getMilk() );
        // assertEquals( r.getSugar(), dbRecipe.getSugar() );
        // assertEquals( r.getChocolate(), dbRecipe.getChocolate() );

        // Tests getting a Recipe by name
        assertEquals( recipeService.findByName( "Sprite" ), r );

        dbRecipe.setPrice( 15 );
        // dbRecipe.setSugar( 12 );
        recipeService.save( dbRecipe );

        assertEquals( recipeService.findAll().size(), 1 );
        assertEquals( recipeService.findAll().get( 0 ).getName(), "Sprite" );
        // Check that new values are set
        assertEquals( recipeService.findAll().get( 0 ).getPrice(), (Integer) 15 );
        // assertEquals( recipeService.findAll().get( 0 ).getSugar(), (Integer)
        // 12 );
        // // Test that these parameters are unchanged
        // assertEquals( recipeService.findAll().get( 0 ).getCoffee(),
        // r.getCoffee() );
        // assertEquals( recipeService.findAll().get( 0 ).getMilk(), r.getMilk()
        // );
        // assertEquals( recipeService.findAll().get( 0 ).getChocolate(),
        // r.getChocolate() );
    }

}
