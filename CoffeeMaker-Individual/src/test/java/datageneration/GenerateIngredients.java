package datageneration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

/**
 * Running the Test with SpringRunner
 */
@RunWith ( SpringRunner.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class GenerateIngredients {

    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService ingredientService;

    /**
     * Tests creating ingredients and then saving it
     */
    @Test
    // @Transactional
    public void testCreateIngredients () {
        ingredientService.deleteAll();

        final Ingredient whippedCream = new Ingredient( "Whipped Cream", 2 );
        final Ingredient vanilla = new Ingredient( "Vanilla", 5 );

        ingredientService.save( whippedCream );

        ingredientService.save( vanilla );

        Assert.assertEquals( 2, ingredientService.count() );

    }
}
