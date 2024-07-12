package edu.ncsu.csc.CoffeeMaker.unit;

import java.util.LinkedList;

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

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class InventoryTest {

    @Autowired
    private InventoryService inventoryService;

    @BeforeEach
    public void setup () {
        final Inventory ivt = inventoryService.getInventory();

        final LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
        ingredients.add( new Ingredient( "Chocolate", 500 ) );
        ingredients.add( new Ingredient( "Coffee", 500 ) );
        ingredients.add( new Ingredient( "Milk", 500 ) );
        ingredients.add( new Ingredient( "Sugar", 500 ) );

        try {
            ivt.setIngredients( ingredients );
        }
        catch ( final Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        inventoryService.save( ivt );
    }

    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        recipe.addIngredient( new Ingredient( "Chocolate", 10 ) );
        recipe.addIngredient( new Ingredient( "Milk", 20 ) );
        recipe.addIngredient( new Ingredient( "Sugar", 5 ) );
        recipe.addIngredient( new Ingredient( "Coffee", 1 ) );

        recipe.setPrice( 5 );

        i.useIngredients( recipe );

        /*
         * Make sure that all of the inventory fields are now properly updated
         */

        Assertions.assertEquals( 490, (int) i.getIngredient( "Chocolate" ).getAmount() );
        Assertions.assertEquals( 480, (int) i.getIngredient( "Milk" ).getAmount() );
        Assertions.assertEquals( 495, (int) i.getIngredient( "Sugar" ).getAmount() );
        Assertions.assertEquals( 499, (int) i.getIngredient( "Coffee" ).getAmount() );
    }

    @Test
    @Transactional
    public void testAddInventory1 () {
        Inventory ivt = inventoryService.getInventory();

        final LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
        ingredients.add( new Ingredient( "Chocolate", 5 ) );
        ingredients.add( new Ingredient( "Coffee", 3 ) );
        ingredients.add( new Ingredient( "Milk", 7 ) );
        ingredients.add( new Ingredient( "Sugar", 2 ) );

        try {
            ivt.setIngredients( ingredients );
        }
        catch ( final Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        Assertions.assertEquals( 503, (int) ivt.getIngredient( "Coffee" ).getAmount(),
                "Adding to the inventory should result in correctly-updated values for coffee" );
        Assertions.assertEquals( 507, (int) ivt.getIngredient( "Milk" ).getAmount(),
                "Adding to the inventory should result in correctly-updated values for milk" );
        Assertions.assertEquals( 502, (int) ivt.getIngredient( "Sugar" ).getAmount(),
                "Adding to the inventory should result in correctly-updated values sugar" );
        Assertions.assertEquals( 505, (int) ivt.getIngredient( "Chocolate" ).getAmount(),
                "Adding to the inventory should result in correctly-updated values chocolate" );

    }

    @Test
    @Transactional
    public void testAddInventory2 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            final LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
            ingredients.add( new Ingredient( "Chocolate", -5 ) );
            ingredients.add( new Ingredient( "Coffee", 3 ) );
            ingredients.add( new Ingredient( "Milk", 7 ) );
            ingredients.add( new Ingredient( "Sugar", 2 ) );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getIngredient( "Coffee" ).getAmount(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- coffee" );
            Assertions.assertEquals( 500, (int) ivt.getIngredient( "Milk" ).getAmount(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- milk" );
            Assertions.assertEquals( 500, (int) ivt.getIngredient( "Sugar" ).getAmount(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- sugar" );
            Assertions.assertEquals( 500, (int) ivt.getIngredient( "Chocolate" ).getAmount(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- chocolate" );
        }
    }

    /**
     * Tests for valid inputs for checkChocolate, checkCoffee, checkSugar,
     * checkMilk
     */
    @Test
    @Transactional
    public void testCheckInventory1 () {
        final Inventory ivt = inventoryService.getInventory();

        // Test the Check Methods
        Assertions.assertEquals( 5, (int) ivt.checkIngredients( "5" ),
                "Trying to check a valid string that can be turned into a number -- 5" );
        Assertions.assertEquals( 7, (int) ivt.checkIngredients( "7" ),
                "Trying to check a valid string that can be turned into a number -- 7" );
        Assertions.assertEquals( 3, (int) ivt.checkIngredients( "3" ),
                "Trying to check a valid string that can be turned into a number -- 3" );
        Assertions.assertEquals( 2, (int) ivt.checkIngredients( "2" ),
                "Trying to check a valid string that can be turned into a number -- 2" );
    }

    /**
     * Tests for invalid inputs for checkChocolate, checkCoffee, checkSugar,
     * checkMilk
     */
    @Test
    @Transactional
    public void testCheckInventory2 () {
        final Inventory ivt = inventoryService.getInventory();

        // Test the with a non number/integer input
        final Exception e1 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "number" );
        } );
        Assertions.assertEquals( e1.getMessage(), "Units of ingredient must be a positive integer" );

        final Exception e2 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "number" );
        } );
        Assertions.assertEquals( e2.getMessage(), "Units of ingredient must be a positive integer" );

        final Exception e3 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "number" );
        } );
        Assertions.assertEquals( e3.getMessage(), "Units of ingredient must be a positive integer" );

        final Exception e4 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "number" );
        } );
        Assertions.assertEquals( e4.getMessage(), "Units of ingredient must be a positive integer" );

        // Test the with a negative number/integer input
        final Exception err1 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "-5" );
        } );
        Assertions.assertEquals( err1.getMessage(), "Units of ingredient must be a positive integer" );

        final Exception err2 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "-5" );
        } );
        Assertions.assertEquals( err2.getMessage(), "Units of ingredient must be a positive integer" );

        final Exception err3 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "-5" );
        } );
        Assertions.assertEquals( err3.getMessage(), "Units of ingredient must be a positive integer" );

        final Exception err4 = Assertions.assertThrows( IllegalArgumentException.class, () -> {
            ivt.checkIngredients( "-5" );
        } );
        Assertions.assertEquals( err4.getMessage(), "Units of ingredient must be a positive integer" );

    }

    @Test
    @Transactional
    public void testToString () {
        final Inventory ivt = inventoryService.getInventory();

        Assertions.assertEquals( ivt.toString(), "Chocolate: 500\nCoffee: 500\nMilk: 500\nSugar: 500\n" );

        // Check updated values
        final LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
        ingredients.add( new Ingredient( "Chocolate", 5 ) );
        ingredients.add( new Ingredient( "Coffee", 5 ) );
        ingredients.add( new Ingredient( "Milk", 5 ) );
        ingredients.add( new Ingredient( "Sugar", 5 ) );
        try {
            ivt.setIngredients( ingredients );
        }
        catch ( final Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assertions.assertEquals( ivt.toString(), "Chocolate: 505\nCoffee: 505\nMilk: 505\nSugar: 505\n" );

    }

}
