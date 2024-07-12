package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.Assert.fail;

import java.util.LinkedList;
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

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class RecipeTest {

    @Autowired
    private RecipeService service;

    private Ingredient    ingredient1;
    private Ingredient    ingredient2;

    @BeforeEach
    public void setup () {
        ingredient1 = new Ingredient( "Coffee", 5 );
        ingredient2 = new Ingredient( "Milk", 10 );
        service.deleteAll();
    }

    @Test
    @Transactional
    public void testAddRecipe () {

        final Recipe r1 = new Recipe();
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        r1.addIngredient( ingredient1 );
        service.save( r1 );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( 1 );
        r2.addIngredient( ingredient2 );
        service.save( r2 );

        final List<Recipe> recipes = service.findAll();
        Assertions.assertEquals( 2, recipes.size(),
                "Creating two recipes should result in two recipes in the database" );

        Assertions.assertEquals( r1, recipes.get( 0 ), "The retrieved recipe should match the created one" );
    }

    @Test
    @Transactional
    public void testNoRecipes () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = new Recipe();
        r1.setName( "Tasty Drink" );
        r1.setPrice( 12 );
        r1.addIngredient( ingredient1 );

        final Recipe r2 = new Recipe();
        r2.setName( "Mocha" );
        r2.setPrice( -10 );
        r2.addIngredient( ingredient2 );

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

    @Test
    @Transactional
    public void testAddRecipe1 () {

        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, 50, "Milk", 3, "Chocolate", 4 );

        service.save( r1 );

        Assertions.assertEquals( 1, service.findAll().size(), "There should only one recipe in the CoffeeMaker" );
        Assertions.assertNotNull( service.findByName( name ) );

    }

    /* Test2 is done via the API for different validation */

    @Test
    @Transactional
    public void testAddRecipe3 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
        final String name = "Coffee";
        final Recipe r1 = createRecipe( name, -50, "Milk", 3, "Chocolate", 4 );

        try {
            service.save( r1 );

            Assertions.assertNull( service.findByName( name ),
                    "A recipe was able to be created with a negative price" );
        }
        catch ( final ConstraintViolationException cvee ) {
            // expected
        }

    }

    @Test
    @Transactional
    public void testAddRecipe13 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, "Milk", 5, "Chocolate", 12 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, "Sugar", 15, "Salt", 1 );
        service.save( r2 );

        Assertions.assertEquals( 2, service.count(),
                "Creating two recipes should result in two recipes in the database" );

    }

    @Test
    @Transactional
    public void testAddRecipe14 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, "Milk", 5, "Chocolate", 12 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, "Sugar", 15, "Salt", 1 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Sprite", 50, "Based", 12, "Awesome", 5 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(),
                "Creating three recipes should result in three recipes in the database" );

    }

    @Test
    @Transactional
    public void testDeleteRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, "Milk", 5, "Chocolate", 12 );
        service.save( r1 );

        Assertions.assertEquals( 1, service.count(), "There should be one recipe in the database" );

        service.delete( r1 );
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );
    }

    @Test
    @Transactional
    public void testDeleteRecipe2 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, "Milk", 5, "Chocolate", 12 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, "Ice", 3, "Chocolate", 8 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, "Coffee", 8, "Sugar", 5 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be three recipes in the database" );

        service.deleteAll();

        Assertions.assertEquals( 0, service.count(), "`service.deleteAll()` should remove everything" );

    }

    @Test
    @Transactional
    public void testDeleteRecipe3 () {
        try {
            service.delete( null );
            fail();
        }
        catch ( final Exception e ) {

        }

        final Recipe r1 = createRecipe( "Coffee", 50, "Milk", 1, "Sugar", 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, "Chocolate", 1, "Milk", 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, "Vanilla", 2, "Ice", 0 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.count(), "There should be two recipes in the database" );

        service.delete( r2 );

        Assertions.assertEquals( 2, service.count(), "There should still be two recipes in the database" );

        service.delete( r2 );

        Assertions.assertEquals( 2, service.count(), "There should still be two recipes in the database" );

    }

    @Test
    @Transactional
    public void testEditRecipe1 () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, "Milk", 5, "Sugar", 6 );
        service.save( r1 );

        r1.setPrice( 70 );

        service.save( r1 );

        final Recipe retrieved = service.findByName( "Coffee" );

        Assertions.assertEquals( 70, (int) retrieved.getPrice() );
        Assertions.assertEquals( 2, r1.getIngredients().size() );

        Assertions.assertEquals( 1, service.count(), "Editing a recipe shouldn't duplicate it" );

    }

    @Test
    @Transactional
    public void testToString () {
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker" );

        final Recipe r1 = createRecipe( "Coffee", 50, "Chocolate", 1, "Milk", 0 );
        service.save( r1 );
        final Recipe r2 = createRecipe( "Mocha", 50, "Chocolate", 1, "Milk", 2 );
        service.save( r2 );
        final Recipe r3 = createRecipe( "Latte", 60, "Chocolate", 2, "Milk", 0 );
        service.save( r3 );

        Assertions.assertEquals( 3, service.findAll().size(), "There should be two Recipes in the CoffeeMaker" );

        Assertions.assertEquals(
                "Coffee with ingredients [Ingredient [ingredient=Chocolate, amount=1], Ingredient [ingredient=Milk, amount=0]]",
                r1.toString(), "Check toString returns the name of the Coffee for the first recipe" );
        Assertions.assertEquals(
                "Mocha with ingredients [Ingredient [ingredient=Chocolate, amount=1], Ingredient [ingredient=Milk, amount=2]]",
                r2.toString(), "Check toString returns the name of the Mocha for the second recipe" );
        Assertions.assertEquals(
                "Latte with ingredients [Ingredient [ingredient=Chocolate, amount=2], Ingredient [ingredient=Milk, amount=0]]",
                r3.toString(), "Check toString returns the name of the Latte for the third recipe" );

        r3.setName( "Sprite" );
        Assertions.assertEquals(
                "Sprite with ingredients [Ingredient [ingredient=Chocolate, amount=2], Ingredient [ingredient=Milk, amount=0]]",
                r3.toString(), "Check toString now returns the name of the Sprite for the third recipe" );

    }

    private Recipe createRecipe ( final String name, final Integer price, final String name1, final Integer amount1,
            final String name2, final Integer amount2 ) {
        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );
        ingredient1 = new Ingredient( name1, amount1 );
        ingredient2 = new Ingredient( name2, amount2 );

        recipe.addIngredient( ingredient1 );
        recipe.addIngredient( ingredient2 );

        return recipe;
    }

    @Test
    @Transactional
    public void testCheckRecipe () {

        /* Tests the check recipe method in recipe */

        final Recipe rec = createRecipe( "Coffee", 50, "Milk", 5, "Chocolate", 15 );

        Assertions.assertFalse( rec.checkRecipe() );

        final Recipe rec2 = createRecipe( "Coffee", 50, "Milk", 0, "Chocolate", 0 );

        Assertions.assertTrue( rec2.checkRecipe() );
    }

    @Test
    @Transactional
    public void testRecipeEquals () {
        /* Tests the equals method for Recipe */

        final Recipe rec = createRecipe( "Mocha", 50, "Chocolate", 1, "Milk", 2 );

        final Recipe recAgain = createRecipe( "Mocha", 50, "Sugar", 9, "Chocolate", 2 );

        final Recipe notRec = createRecipe( "Bunger", 50, "Bunger", 1, "Bunga", 2 );

        final Recipe noName = createRecipe( null, 50, "Coffee", 1, "Milk", 2 );

        final Recipe noName2 = createRecipe( null, 34, "Chocolate", 13, "Sugar", 2 );

        final Recipe notReal = null;

        Assertions.assertTrue( rec.equals( recAgain ) );
        Assertions.assertFalse( rec.equals( notRec ) );
        Assertions.assertFalse( rec.equals( noName ) );
        Assertions.assertFalse( rec.equals( notReal ) );
        Assertions.assertTrue( noName.equals( noName2 ) );
        Assertions.assertFalse( noName.equals( rec ) );

    }

    /**
     * This will test the Delete Ingredient functionality of Edit Recipe
     */
    @Test
    @Transactional
    public void testRecipeEdit1 () {
        final Recipe r1 = new Recipe();
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        r1.addIngredient( ingredient1 );
        r1.addIngredient( ingredient2 );
        service.save( r1 );

        Assertions.assertEquals( 2, r1.getIngredients().size() );

        r1.deleteIngredient( ingredient1 );

        Assertions.assertEquals( 1, r1.getIngredients().size() );
        Assertions.assertEquals( "Milk", r1.getIngredients().get( 0 ).getName() );

        r1.deleteIngredient( ingredient1 );

        Assertions.assertEquals( 1, r1.getIngredients().size() );
        Assertions.assertEquals( "Milk", r1.getIngredients().get( 0 ).getName() );

        r1.deleteIngredient( ingredient2 );

        Assertions.assertEquals( 0, r1.getIngredients().size() );
    }

    /**
     * This will test valid Edit Unit functionality of Edit Recipe
     */
    @Test
    @Transactional
    public void testRecipeEdit2 () {
        final Recipe r1 = new Recipe();
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        r1.addIngredient( ingredient1 );
        r1.addIngredient( ingredient2 );
        service.save( r1 );

        Assertions.assertEquals( 5, r1.getIngredients().get( 0 ).getAmount() );
        Assertions.assertEquals( 10, r1.getIngredients().get( 1 ).getAmount() );

        final List<Integer> unitEdits = new LinkedList<Integer>();

        unitEdits.add( 15 );
        unitEdits.add( 23 );

        try {
            r1.editIngredientUnits( unitEdits );
        }
        catch ( final Exception e ) {
            fail( e.getMessage() );
        }

        Assertions.assertEquals( 15, r1.getIngredients().get( 0 ).getAmount() );
        Assertions.assertEquals( 23, r1.getIngredients().get( 1 ).getAmount() );
    }

    /**
     * This will test invalid Edit Unit functionality of Edit Recipe
     */
    @Test
    @Transactional
    public void testRecipeEdit3 () {
        final Recipe r1 = new Recipe();
        r1.setName( "Black Coffee" );
        r1.setPrice( 1 );
        r1.addIngredient( ingredient1 );
        r1.addIngredient( ingredient2 );
        service.save( r1 );

        Assertions.assertEquals( 5, r1.getIngredients().get( 0 ).getAmount() );
        Assertions.assertEquals( 10, r1.getIngredients().get( 1 ).getAmount() );

        final List<Integer> unitEdits = new LinkedList<Integer>();

        unitEdits.add( 15 );
        unitEdits.add( -2 );

        Assertions.assertThrows( IllegalArgumentException.class, () -> r1.editIngredientUnits( unitEdits ) );

        Assertions.assertEquals( 5, r1.getIngredients().get( 0 ).getAmount() );
        Assertions.assertEquals( 10, r1.getIngredients().get( 1 ).getAmount() );
    }

}
