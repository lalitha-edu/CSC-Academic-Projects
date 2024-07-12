package edu.ncsu.csc.CoffeeMaker.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

    /** id for inventory entry */
    @Id
    @GeneratedValue
    private Long                          id;

    /** Initializes a Map on ingredients */
    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private final Map<String, Ingredient> ingredients;

    // @Autowired
    // private IngredientService ingredientService;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory () {
        this.ingredients = new HashMap<String, Ingredient>();
        // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
    }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns a list of ingredients in inventory
     *
     * @return List of ingredients
     */
    public List<Ingredient> getIngredients () {
        return new ArrayList<>( ingredients.values() );
    }

    /**
     * Checks the amount of a certain ingredient in the inventory
     *
     * @param ingredient
     *            Ingredient ingredient
     * @return Integer amount of ingredient
     */
    public Integer checkIngredient ( final Ingredient ingredient ) {
        //

        final Ingredient foundIngredient = ingredients.get( ingredient.getName() );
        return foundIngredient != null ? foundIngredient.getAmount() : 0;
    }

    /**
     * Sets the amount of a certain ingredient
     *
     * @param ingredient
     *            Ingredient ingredient
     * @param amount
     *            Integer amount
     */
    public void setIngredient ( final Ingredient ingredient, final Integer amount ) {

        final Ingredient foundIngredient = ingredients.get( ingredient.getName() );
        if ( foundIngredient != null ) {
            foundIngredient.setAmount( amount );
        }
        else {
            throw new IllegalArgumentException( "Ingredient not found: " + ingredient.getName() );
        }

    }
    // }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param r
     *            recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    public boolean enoughIngredients ( final Recipe r ) {

        for ( final Ingredient requiredIngredient : r.getIngredients() ) {
            final Ingredient inventoryIngredient = ingredients.get( requiredIngredient.getName() );
            if ( inventoryIngredient == null || inventoryIngredient.getAmount() < requiredIngredient.getAmount() ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param r
     *            recipe to make
     * @return true if recipe is made.
     */
    public boolean useIngredients ( final Recipe r ) {

        if ( enoughIngredients( r ) ) {
            for ( final Ingredient requiredIngredient : r.getIngredients() ) {
                final Ingredient inventoryIngredient = ingredients.get( requiredIngredient.getName() );
                final int remainingAmount = inventoryIngredient.getAmount() - requiredIngredient.getAmount();
                inventoryIngredient.setAmount( remainingAmount );
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gets the name of an Ingredient by name
     *
     * @param name
     *            String name
     * @return Ingredient that matches the name
     */
    public Ingredient getIngredient ( final String name ) {

        return ingredients.get( name );
    }

    @Override
    public String toString () {
        String s = "";

        for ( final Entry<String, Ingredient> entry : ingredients.entrySet() ) {
            s += entry.getValue().getName() + ": " + entry.getValue().getAmount() + "\n";
        }

        return s;
    }

    /**
     * Adds an amount to an Ingredient
     *
     * @param name
     *            String name
     * @param value
     *            int value
     * @return Ingredient after adding ingredient
     */
    public Ingredient addIngredient ( final String name, final int value ) {

        if ( value <= 0 ) {
            System.out.println( "Error: Amount is invalid" );
            return null;
        }

        if ( ingredients.containsKey( name ) ) {
            System.out.println( "Error: Ingredient with name " + name + " already exists." );
            return null;
        }

        final Ingredient ingredient = new Ingredient( name, value );
        ingredients.put( name, ingredient );
        return ingredient;

    }

    /**
     * Deletes an Ingredient
     *
     * @param name
     *            String name
     */
    public void deleteIngredient ( final String name ) {
        final Ingredient ingredientToRemove = ingredients.get( name );

        if ( ingredientToRemove != null ) {
            ingredients.remove( name );
        }
        else {
            throw new IllegalArgumentException( "Ingredient not found: " + name );
        }
    }

    /**
     * Updates the amount of an ingredient in the inventory
     *
     * @param updatedAmounts
     *
     */
    public void updateIngredientAmountList ( List<Integer> updatedAmounts ) {
        if ( updatedAmounts.size() != ingredients.size() ) {
            throw new IllegalArgumentException( "Number of updated amounts does not match the number of ingredients" );
        }

        int index = 0;
        for ( final Ingredient ingredient : ingredients.values() ) {
            final int val = updatedAmounts.get( index );
            if ( val < 0 ) {
                throw new IllegalArgumentException( "Number is invalid, less than zero." );
            }
            ingredient.setAmount( val );
            index++;
        }
    }
}
