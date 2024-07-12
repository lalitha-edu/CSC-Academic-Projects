package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

/**
 * This test file tests the Inventory.java class and is extended with
 * SpringExtension.
 *
 */
@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class InventoryTest {

    /**
     * InventoryService object, to be autowired in by Spring to allow for
     * manipulating the Inventory model
     */
    @Autowired
    private InventoryService inventoryService;
    /** Declares the whipped cream ingredient */
    final Ingredient         whippedCream = new Ingredient( "Whipped Cream", 500 );
    /** Declares the vanilla ingredient */
    final Ingredient         vanilla      = new Ingredient( "Vanilla", 500 );

    /**
     * Sets up the inventory before running each test
     */
    @BeforeEach
    public void setup () {
        final Inventory ivt = inventoryService.getInventory();

        ivt.addIngredient( "Whipped Cream", 500 );
        ivt.addIngredient( "Vanilla", 500 );

        inventoryService.save( ivt );
    }

    /**
     * Tests consumption of inventory
     */
    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe( "Delicious Not-Coffee", 5 );
        // recipe.setName( "Delicious Not-Coffee" );

        // recipe.setPrice( 5 );

        recipe.addIngredient( whippedCream );
        recipe.addIngredient( vanilla );

        /*
         * Make sure that all of the inventory fields are now properly updated
         */

        Assertions.assertEquals( 500, (int) i.getIngredients().get( 0 ).getAmount() );
        Assertions.assertEquals( 500, (int) i.getIngredients().get( 1 ).getAmount() );

        i.useIngredients( recipe );
        i.deleteIngredient( "Vanilla" );

        Assertions.assertEquals( 1, i.getIngredients().size() );

    }

    /**
     * Tests adding ingredient amounts to inventory
     */
    @Test
    @Transactional
    public void testAddInventory1 () {
        Inventory ivt = inventoryService.getInventory();

        ivt.setIngredient( vanilla, 5 );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        Assertions.assertEquals( 505, (int) ivt.checkIngredient( vanilla ),
                "Adding to the inventory should result in correctly-updated values for vanilla" );
        Assertions.assertEquals( 500, (int) ivt.checkIngredient( whippedCream ),
                "Adding to the inventory should result in correctly-updated values for whippedCream" );

    }

    /**
     * Tests adding invalid (negative) ingredient amounts to inventory
     */
    @Test
    @Transactional
    public void testAddInventory2 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.setIngredient( vanilla, -5 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.checkIngredient( vanilla ),
                    "Adding to the inventory should result in no changes to the value for vanilla" );
            Assertions.assertEquals( 500, (int) ivt.checkIngredient( whippedCream ),
                    "Adding to the inventory should result in no changes to the value for whippedCream" );
        }
    }

    /**
     * Tests the toString() method -- Written by esthorat
     */
    @Test
    @Transactional
    public void testToString () {
        final Inventory ivt = inventoryService.getInventory();

        final String s1 = ivt.toString();

        Assertions.assertEquals( s1, "Whipped Cream: 500\nVanilla: 500\n" );

        ivt.setIngredient( vanilla, 5 );

        final String s2 = ivt.toString();

        Assertions.assertEquals( s2, "Whipped Cream: 500\nVanilla: 505\n" );

    }

    /**
     * Tests the inventory for enough ingredients for a recipe, checks for when
     * enough ingredients for a recipe don't exist
     *
     * @author ledupug
     */
    @Test
    @Transactional
    public void testEnoughIngredients () {
        final Inventory ivt = inventoryService.getInventory();

        ivt.setIngredient( vanilla, 30 );
        ivt.setIngredient( whippedCream, 30 );

        inventoryService.save( ivt );

        final Recipe recipe = new Recipe( "Chocolate Coffee", 50 );
        recipe.addIngredient( vanilla );

    }

    @Test
    @Transactional
    public void testUpdateIngredientAmountList () {
        final Inventory ivt = inventoryService.getInventory();

        final List<Integer> newList = new ArrayList<>( List.of( 10, 20 ) );
        ivt.updateIngredientAmountList( newList );

        assertEquals( (int) ivt.getIngredient( "Whipped Cream" ).getAmount(), 510 );
        assertEquals( (int) ivt.getIngredient( "Vanilla" ).getAmount(), 520 );
    }
    // Assuming
    // each
    // amount
    // has
    // been
    // increased
    // by 10

}
