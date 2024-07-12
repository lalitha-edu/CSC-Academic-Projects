package datageneration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

/**
 * Running the Test with SpringRunner
 */
@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class GenerateRecipeWithIngredients {

    /**
     * RecipeService object, to be autowired in by Spring to allow for
     * manipulating the Recipe model
     */
    @Autowired
    private RecipeService    recipeService;

    // /**
    // * IngredientService object, to be autowired in by Spring to allow for
    // * manipulating the Ingredient model
    // */
    // @Autowired
    // private IngredientService ingredientService;

    /**
     * InventoryService object, to be autowired in by Spring to allow for
     * manipulating the Inventory model
     */
    @Autowired
    private InventoryService inventoryService;

    /**
     * Deletes all the recipes from the database before running the tests
     */
    @Before
    public void setup () {
        recipeService.deleteAll();
    }

    /**
     * Tests adding ingredients to the inventory
     */
    @Test
    // @Transactional
    public void testInventory () {
        // final Ingredient whippedCream = new Ingredient( "Whipped Cream", 2 );
        final Ingredient vanilla = new Ingredient( "Vanilla", 5 );

        // final Ingredient updateVanilla = new Ingredient( "Vanilla", 15 );

        // final Inventory newInventory = new Inventory();

        inventoryService.addIngredient( vanilla );
        // inventoryService.getInventory().

        assertEquals( 5, inventoryService.getInventory().getIngredient( "Vanilla" ).getAmount() );

        // inventoryService.addIngredient( updateVanilla );

        assertEquals( 5, inventoryService.getInventory().getIngredient( "Vanilla" ).getAmount() );

        // ingredientService.save( new Ingredient( "Whipped Cream", 2 ) );
        // ingredientService.save( vanilla );

        // newInventory.addIngredient( "Vanilla", 6 );
        // newInventory.addIngredient( "Whipped Cream", 8 );
        final Ingredient whippedCream = new Ingredient( "Whipped Cream", 5 );
        inventoryService.addIngredient( whippedCream );
        assertEquals( 5, inventoryService.getInventory().getIngredient( "Whipped Cream" ).getAmount() );

        // inventoryService.addIngredient( "Vanilla", 6 );
        // inventoryService.addIngredient( "Whipped Cream", 8 );
        //
        // final List<Ingredient> list = ingredientService.findAll();
        // final List<Inventory> list2 = inventoryService.findAll();
        //
        // assertNotNull( ingredientService.findByName( "Vanilla" ) );

        // assertTrue( inventoryService.findAll().contains( "Vanilla" ) );
        // assertEquals( 8, inventoryService.findByName( "Whipped Cream"
        // ).getAmount() );

    }

}
