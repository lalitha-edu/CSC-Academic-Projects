package edu.ncsu.csc.CoffeeMaker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

/**
 * Running the Test with SpringRunner
 */
@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )

public class TestDatabaseInteraction {

    /**
     * RecipeService object, to be autowired in by Spring to allow for
     * manipulating the Recipe model
     */
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
        final Recipe r1 = new Recipe( "Mocha", 70 );

        final Ingredient whippedCream = new Ingredient( "Whipped Cream", 2 );
        final Ingredient vanilla = new Ingredient( "Vanilla", 5 );

        // r.setName( "Mocha" );
        // r.setPrice( 350 );
        r1.addIngredient( whippedCream );
        r1.addIngredient( vanilla );

        recipeService.save( r1 );

        final List<Recipe> dbRecipes = recipeService.findAll();

        assertEquals( 1, dbRecipes.size() );

        final Recipe r2 = dbRecipes.get( 0 );

        assertEquals( r1.getName(), r2.getName() );
        assertEquals( r1.getPrice(), r2.getPrice() );

        assertEquals( r1.getIngredients().get( 0 ).getName(), r2.getIngredients().get( 0 ).getName() );

        assertEquals( r1.getIngredients().get( 1 ).getName(), r2.getIngredients().get( 1 ).getName() );
        // assertEquals( r.getMilk(), dbRecipe.getMilk() );
        // assertEquals( r.getSugar(), dbRecipe.getSugar() );
        // assertEquals( r.getChocolate(), dbRecipe.getChocolate() );

        assertEquals( r2, recipeService.findByName( "Mocha" ) );

        r2.setPrice( 15 );

        recipeService.save( r2 );

        assertEquals( 1, recipeService.count() );

        assertEquals( 5, (int) ( ( recipeService.findAll().get( 0 ).getIngredients().get( 1 ).getAmount() ) ) );

        assertEquals( 2, (int) ( ( recipeService.findAll().get( 0 ).getIngredients().get( 0 ).getAmount() ) ) );
        // assertEquals( 1, (int) ( ( recipeService.findAll().get( 0 ).getMilk()
        // ) ) );

    }

}
