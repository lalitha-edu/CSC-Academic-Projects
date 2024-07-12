package edu.ncsu.csc.CoffeeMaker.models;

import java.util.LinkedList;
import java.util.List;

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
    private Long             id;

    /** Ingredients in the inventory */
    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Ingredient> ingredients;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory () {
        // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
    }

    /**
     * Use this to create inventory with specified amts.
     *
     * @param ingredients
     *            List of ingredients
     * @throws Exception
     *             exception is thrown is an invalid ingredient is attempting to
     *             be added
     */
    public Inventory ( final LinkedList<Ingredient> ingredients ) throws Exception {
        this.setIngredients( ingredients );
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
     * Add the number of sugar units in the inventory to the current amount of
     * sugar units.
     *
     * @param amount
     *            Amount attempting to be added
     * @return checked amount of ingredient
     * @throws IllegalArgumentException
     *             if the parameter isn't a positive integer
     */
    public Integer checkIngredients ( final String amount ) throws IllegalArgumentException {
        Integer amtIngredient = 0;
        try {
            amtIngredient = Integer.parseInt( amount );
        }
        catch ( final NumberFormatException e ) {
            throw new IllegalArgumentException( "Units of ingredient must be a positive integer" );
        }
        if ( amtIngredient < 0 ) {
            throw new IllegalArgumentException( "Units of ingredient must be a positive integer" );
        }

        return amtIngredient;
    }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param r
     *            recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    public boolean enoughIngredients ( final Recipe r ) {
        boolean isEnough = true;
        final List<Ingredient> recipeIngredients = r.getIngredients();
        for ( final Ingredient ri : recipeIngredients ) {
            for ( final Ingredient i : ingredients ) {
                if ( i.getName().equals( ri.getName() ) && i.getAmount() < ri.getAmount() ) {
                    isEnough = false;
                }
            }
        }
        return isEnough;
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
            final List<Ingredient> recipeIngredients = r.getIngredients();
            for ( final Ingredient ri : recipeIngredients ) {
                for ( final Ingredient i : ingredients ) {
                    if ( i.getName().equals( ri.getName() ) ) {
                        i.setAmount( i.getAmount() - ri.getAmount() );
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds ingredients to the inventory
     *
     * @param list
     *            list of Ingredients being added
     * @throws Exception
     *             exception is thrown is an invalid ingredient is attempting to
     *             be added
     */
    public void setIngredients ( final List<Ingredient> list ) throws Exception {
        if ( this.ingredients == null ) {
            this.ingredients = list;
        }
        else {
            boolean found = false;
            for ( final Ingredient i : list ) {
                for ( final Ingredient j : this.ingredients ) {
                    if ( j.getName().equals( i.getName() ) ) {
                        if ( j.getAmount() + i.getAmount() < 0 ) {
                            throw new Exception( "Invalid ingredient" );
                        }
                        else {
                            j.setAmount( j.getAmount() + i.getAmount() );
                            found = true;
                        }
                    }
                }
                if ( !found ) {
                    if ( i.getAmount() < 0 ) {
                        throw new Exception( "Invalid ingredient" );
                    }
                    else {
                        this.ingredients.add( i );
                    }
                }
            }
        }
    }

    /**
     * Returns a string describing the current contents of the inventory.
     *
     * @return String
     */
    @Override
    public String toString () {
        final StringBuffer buf = new StringBuffer();
        for ( final Ingredient i : ingredients ) {
            buf.append( i.getName() );
            buf.append( ": " );
            buf.append( i.getAmount() );
            buf.append( "\n" );
        }
        return buf.toString();
    }

    /**
     * Gets the list of ingredients
     *
     * @return ingredients The list of ingredients
     */
    public List<Ingredient> getIngredients () {
        return ingredients;
    }

    /**
     * Gets one ingredient
     *
     * @param name
     *            Name of ingredient being obtained
     *
     * @return ingredient One ingredient from the inventory
     */
    public Ingredient getIngredient ( final String name ) {
        for ( final Ingredient i : ingredients ) {
            if ( i.getName().equals( name ) ) {
                return i;
            }
        }
        return null;
    }

}
