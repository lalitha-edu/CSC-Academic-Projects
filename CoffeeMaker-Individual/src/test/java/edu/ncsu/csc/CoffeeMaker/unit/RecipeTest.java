package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

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
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

/**
 * This test file tests the Recipe.java class and is extended with
 * SpringExtension.
 *
 */
@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class RecipeTest {

    /**
     * RecipeService object, to be autowired in by Spring to allow for
     * manipulating the Recipe model
     */
    @Autowired
    private RecipeService service;

    /**
     * Sets up the recipe before running each test
     */
    @BeforeEach
    public void setup () {
        service.deleteAll();
    }

    /**
     * Tests adding and saving new recipes
     */
    @Test
    @Transactional
    public void testAddRecipe () {

        final Recipe r1 = new Recipe( "Black Coffee", 1 );
        // r1.setName( "Black Coffee" );
        // r1.setPrice( 1 );
        service.save( r1 );

        final Recipe r2 = new Recipe( "Mocha", 1 );
        // r2.setName( "Mocha" );
        // r2.setPrice( 1 );
        service.save( r2 );

        final List<Recipe> recipes = service.findAll();
        Assertions.assertEquals( 2, recipes.size(),
                "Creating two recipes should result in two recipes in the database" );

        Assertions.assertEquals( r1, recipes.get( 0 ), "The retrieved recipe should match the created one" );
    }

    /**
     * Tests the save all functionality when one of the Recipes is invalid
     */
    @Test
    @Transactional
    public void testNoRecipes () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = new Recipe( "Tasty Drink", 12 );
        // r1.setName( "Tasty Drink" );
        // r1.setPrice( 12 );

        final Recipe r2 = new Recipe( "Mocha", -1 );
        // r2.setName( "Mocha" );
        // r2.setPrice( -1 );

        final List<Recipe> recipes = List.of( r1, r2 );

        try {
            service.saveAll( recipes );
            Assertions.assertEquals( 0, service.count(),
                    "Trying to save a collection of elements where one is invalid should result in neither getting saved" );
        }
        catch ( final Exception e ) {
            Assertions.assertTrue( e instanceof ConstraintViolationException );
        }

    }

    /**
     * Tests creating a recipe then adding it
     */
    @Test
    @Transactional
    public void testAddRecipe1 () {

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50 );

        service.save( r1 );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one recipe in the CoffeeMaker" );
        Assertions.assertNotNull( service.findByName( name ) );

    }

    /**
     * Tests creating an invalid recipe then adding it
     */
    @Test
    @Transactional
    public void testAddRecipe3 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, -50 );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative price" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    /**
     * Tests trying to create and save an invalid recipe
     */
    @Test
    @Transactional
    public void testAddRecipe4 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50 );

        try {
            final Ingredient milk = new Ingredient( "Milk", -3 );
            // final Ingredient Milk = new Ingredient( IngredientType.MILK, 5 );

            r1.addIngredient( milk );

            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative amount of coffee" );
        }
        catch ( final Exception cvee ) {
            // expected
        }

    }

    /**
     * Tests trying to create and save two recipes
     */
    @Test
    @Transactional
    public void testAddRecipe13 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );

        Assertions.assertEquals( 2, service.count(),
                "Creating two recipes should result in two recipes in the database" );

    }

    /**
     * Tests trying to create and save three recipes
     */
    @Test
    @Transactional
    public void testAddRecipe14 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipes in the database" );

    }

    /**
     * Tests deleting a recipe from the database
     */
    @Test
    @Transactional
    public void testDeleteRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );

        Assertions.assertEquals( 1, service.count(), "There should be one recipe in the database" );

        service.delete( r1 );
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
    }

    /**
     * Tests deleting all recipes from the database when there are three recipes
     */
    @Test
    @Transactional
    public void testDeleteRecipe2 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );

    }

    /**
     * Tests deleting all recipes from the database when there are two recipes
     *
     * @author esthorat
     */
    @Test
    @Transactional
    public void testDeleteRecipe3 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );

        Assertions.assertEquals( 2, service.count(), "There should be three recipes in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );
    }

    /**
     * Tests deleting all recipes from the database when there are one recipes
     *
     * @author esthorat
     */
    @Test
    @Transactional
    public void testDeleteRecipe4 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );

        Assertions.assertEquals( 1, service.count(), "There should be three recipes in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );
    }

    /**
     * Tests deleting the first recipes from the database when there are three
     * recipes
     *
     * @author esthorat
     */
    @Test
    @Transactional
    public void testDeleteRecipe5 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        service.delete( r1 );

        Assertions.assertEquals( 2, service.count(),
                "Deleting one recipe should result in two recipes in the database" );
    }

    /**
     * Tests deleting the second recipe from the database when there are two
     * recipes
     *
     * @author esthorat
     */
    @Test
    @Transactional
    public void testDeleteRecipe6 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50 );
        service.save( r2 );

        Assertions.assertEquals( 2, service.count(), "There should be three recipes in the database" );

        service.delete( r2 );

        Assertions.assertEquals( 1, service.count(),
                "Deleting one recipe should result in one recipe in the database" );
    }

    /**
     * Tests saving a recipe, and then altering the price, then saving again.
     *
     * @author esthorat
     */
    @Test
    @Transactional
    public void testEditRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50 );
        service.save( r1 );

        r1.setPrice( 70 );

        service.save( r1 );

        final Recipe retrieved = service.findByName( "Coffee" );

        Assertions.assertEquals( 70, (int) retrieved.getPrice() );

        Assertions.assertEquals( 1, service.count(), "Editing a recipe shouldn't duplicate it" );

    }

    private Recipe createRecipe ( final String name, final Integer price ) {
        final Recipe recipe = new Recipe( name, price );
        // recipe.setName( name );
        // recipe.setPrice( price );
        // service.save( recipe );

        return recipe;
    }

    /**
     * Test written by amjone34
     */
    @Test
    @Transactional
    public void testToString () {
        final Recipe r1 = createRecipe( "Coffee", 2 );

        final Recipe r2 = createRecipe( "Latte", 4 );

        assertEquals( r1.toString(), "Recipe: Coffee" + "\n" + "Ingredients:\n" );
        assertEquals( r2.toString(), "Recipe: Latte" + "\n" + "Ingredients:\n" );

        r1.hashCode();
        assertFalse( r1.equals( r2 ) );
    }

    /**
     * Test written by amjone34
     */
    @Test
    @Transactional
    public void testCheckRecipe () {
        final Recipe r1 = createRecipe( "Coffee", 2 );
        final Recipe r2 = createRecipe( "Latte", 0 );

        assertEquals( "Latte", r2.getName() );

        service.save( r1 );
        service.save( r2 );

        r1.getIngredients();

    }

    /**
     * Test written by amjone34
     */
    @Test
    @Transactional
    public void testUpdateRecipe () {
        final Recipe r1 = createRecipe( "Coffee", 2 );
        final Recipe r2 = createRecipe( "Latte", 0 );
        // final Recipe r3 = createRecipe( "Coffee", 2, 3, 1, 1, 0 );

        assertNotEquals( r1, r2 );
    }

    /**
     * Test written by ledupug Test tests the use case for adding duplicate
     * recipes or recipes with the same name which should should not add the
     * duplicate to the service.
     */
    @Test
    @Transactional
    public void testDuplicateRecipe () {
        final Recipe r2 = createRecipe( "Coffee", 2 );
        final Recipe r1 = createRecipe( "Coffee", 2 );

        assertEquals( r1, r2 );
        service.save( r1 );
        service.save( r2 );

        assertEquals( 2, service.count() );

        final List<Recipe> savedRecipes = service.findAll();

        assertTrue( savedRecipes.contains( r1 ) );
        assertTrue( savedRecipes.contains( r2 ) );

    }

    /**
     * Tests adding an ingredient
     */
    @Test
    @Transactional
    public void testAddIngredient () {
        final Recipe r = new Recipe( "Coffee", 3 );
        final Ingredient caramel = new Ingredient( "Caramel", 5 );

        r.addIngredient( caramel );
    }
}
