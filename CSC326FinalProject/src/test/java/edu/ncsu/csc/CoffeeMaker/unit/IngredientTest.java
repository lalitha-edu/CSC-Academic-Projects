package edu.ncsu.csc.CoffeeMaker.unit;

import javax.validation.ConstraintViolationException;

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
// import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class IngredientTest {

    @Autowired
    private IngredientService ingredientService;

    @BeforeEach
    public void setup () {
        ingredientService.deleteAll();
    }

    /**
     * Tests the creation of Ingredients with valid inputs
     */
    @Test
    @Transactional
    public void testCreateIngredients () {
        ingredientService.deleteAll();

        final Ingredient i1 = new Ingredient( "SPRITE", 5 );

        ingredientService.save( i1 );

        final Ingredient i2 = new Ingredient( "COKE", 3 );

        ingredientService.save( i2 );
        Assertions.assertEquals( 2, ingredientService.count() );
        Assertions.assertNotNull( ingredientService.findById( i1.getId() ) );
        Assertions.assertNotNull( ingredientService.findById( i2.getId() ) );

    }

    @Test
    @Transactional
    public void testCreateInvalidIngredients1 () {
        ingredientService.deleteAll();
        // First Test that there are no ingredients in the system
        Assertions.assertEquals( 0, ingredientService.findAll().size() );

        // Create an Ingredient with null amount
        final Ingredient i1 = new Ingredient( "MILK", null );
        final Long id = i1.getId();

        try {
            ingredientService.save( i1 );

            Assertions.assertNull( ingredientService.findById( id ) );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }
    }

    @Test
    @Transactional
    public void testCreateInvalidIngredients2 () {
        ingredientService.deleteAll();
        // First Test that there are no ingredients in the system
        Assertions.assertEquals( 0, ingredientService.findAll().size() );

        // Create an Ingredient with a negative amount
        final Ingredient i1 = new Ingredient( "MILK", -5 );
        final Long id = i1.getId();

        try {
            ingredientService.save( i1 );

            Assertions.assertNull( ingredientService.findById( id ) );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }
    }

    @Test
    @Transactional
    public void testEditIngredient () {
        ingredientService.deleteAll();
        // First Test that there are no ingredients in the system
        Assertions.assertEquals( 0, ingredientService.findAll().size() );

        // Add an Ingredient to the system
        final Ingredient i1 = new Ingredient( "Brogurt", 21 );
        ingredientService.save( i1 );
        final Long id = i1.getId();

        // Check that it's in the system
        Assertions.assertEquals( 1, ingredientService.findAll().size() );
        Assertions.assertEquals( "Brogurt", ingredientService.findById( id ).getName() );
        Assertions.assertEquals( 21, ingredientService.findById( id ).getAmount() );

        // Change the Name and Amount
        i1.setAmount( 8 );
        i1.setName( "Bunger" );
        ingredientService.save( i1 );

        // Test for new values
        Assertions.assertEquals( 1, ingredientService.findAll().size() );
        Assertions.assertEquals( "Bunger", ingredientService.findById( id ).getName() );
        Assertions.assertEquals( 8, ingredientService.findById( id ).getAmount() );
    }

    @Test
    @Transactional
    public void testDeleteIngredient () {
        ingredientService.deleteAll();
        // First Test that there are no ingredients in the system
        Assertions.assertEquals( 0, ingredientService.findAll().size() );

        // Add an Ingredient to the system
        final Ingredient i1 = new Ingredient( "Brogurt", 21 );
        ingredientService.save( i1 );
        final long id = i1.getId();

        Assertions.assertEquals( 1, ingredientService.findAll().size() );

        // Now Delete the ingredient from the system
        ingredientService.delete( i1 );

        // Check that it's gone
        Assertions.assertEquals( 0, ingredientService.findAll().size() );
        Assertions.assertNull( ingredientService.findById( id ) );
    }

    @Test
    @Transactional
    public void testToString () {
        ingredientService.deleteAll();
        // First Test that there are no ingredients in the system
        Assertions.assertEquals( 0, ingredientService.findAll().size() );

        final Ingredient i1 = new Ingredient( "Milk", 5 );
        ingredientService.save( i1 );

        Assertions.assertEquals( "Ingredient [ingredient=" + "Milk" + ", amount=" + 5 + "]", i1.toString() );

    }

}
