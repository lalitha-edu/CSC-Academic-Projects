package edu.ncsu.csc.CoffeeMaker.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

/**
 * Recipe for the coffee maker. Recipe is tied to the database using Hibernate
 * libraries. See RecipeRepository and RecipeService for the other two pieces
 * used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Recipe extends DomainObject {

    /** Recipe id */
    @Id
    @GeneratedValue
    private Long             id;

    /** Recipe name */
    private String           name;

    /** Recipe price */
    @Min ( 0 )
    private Integer          price;

    /** Recipe description */
    private String           description;

    /** Currently selected recipe */
    private static Recipe    currentRecipe;

    /** Recipe ingredients */
    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Ingredient> ingredients;

    /**
     * Creates a default recipe for the coffee maker.
     */
    public Recipe () {
        this.name = "";
        this.ingredients = new LinkedList<Ingredient>();
    }

    /**
     * returns the current recipe to put into an order
     *
     * @return the currentRecipe
     */
    public static Recipe getCurrentRecipe () {
        return currentRecipe;
    }

    /**
     * sets the selected recipe for making an order
     *
     * @param currentRecipe
     *            the currentRecipe to set
     */
    public static void setCurrentRecipe ( final Recipe currentRecipe ) {
        Recipe.currentRecipe = currentRecipe;
    }

    /**
     * Check if all ingredient fields in the recipe are 0
     *
     * @return true if all ingredient fields are 0, otherwise return false
     */
    public boolean checkRecipe () {
        for ( int i = 0; i < ingredients.size(); i++ ) {
            if ( ingredients.get( i ).getAmount() > 0 ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the ID of the Recipe
     *
     * @return the ID
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Recipe (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    @SuppressWarnings ( "unused" )
    private void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns name of the recipe.
     *
     * @return Returns the name.
     */
    public String getName () {
        return name;
    }

    /**
     * Sets the recipe name.
     *
     * @param name
     *            The name to set.
     */
    public void setName ( final String name ) {
        this.name = name;
    }

    /**
     * Returns the price of the recipe.
     *
     * @return Returns the price.
     */
    public Integer getPrice () {
        return price;
    }

    /**
     * Sets the recipe price.
     *
     * @param price
     *            The price to set.
     */
    public void setPrice ( final Integer price ) {
        this.price = price;
    }

    /**
     * gets the recipe description
     *
     * @return the description
     */
    public String getDescription () {
        return description;
    }

    /**
     * sets the recipe description
     *
     * @param description
     *            the description to set
     */
    public void setDescription ( final String description ) {
        this.description = description;
    }

    /**
     * Adds one ingredient to the ingredients list
     *
     * @param ingredient
     *            The ingredient being added to the list
     */
    public void addIngredient ( final Ingredient ingredient ) {
        ingredients.add( ingredient );
    }

    /**
     * Deletes an ingredient from the ingredients list
     *
     * @param ingredient
     *            to delete
     */
    public void deleteIngredient ( final Ingredient ingredient ) {
        ingredients.remove( ingredient );
    }

    /**
     * Gets a list of all ingredients in the recipe
     *
     * @return ingredients List of all ingredients in the recipe
     */
    public List<Ingredient> getIngredients () {
        return ingredients;
    }

    /**
     * Changes the amounts of the Ingredients by the amounts given in the List
     *
     * @param editBy
     *            the List matching the Ingredients to edit the Ingredients by
     * @throws Exception
     *             if the size of the List of Integers is different than the
     *             size of the List of Ingredients
     */
    public void editIngredientUnits ( final List<Integer> editBy ) throws Exception {
        if ( editBy.size() != ingredients.size() ) {
            throw new Exception( "List of Integers do not match the List of Ingredients" );
        }

        int count = 0;

        for ( final Integer x : editBy ) {
            if ( x < 0 ) {
                throw new IllegalArgumentException( "Cannot edit Ingredient to a negative integer" );
            }
        }

        for ( final Ingredient i : ingredients ) {
            i.setAmount( editBy.get( count++ ) );
        }

    }

    /**
     * Changes the amounts of the Ingredients by the amounts given in the List
     *
     * @param editBy
     *            the List matching the Ingredients to edit the Ingredients by
     * @throws Exception
     *             if the size of the List of Integers is different than the
     *             size of the List of Ingredients
     */
    public void editIngredients ( final List<Ingredient> editBy ) throws Exception {
        for ( final Ingredient x : editBy ) {
            if ( x.getAmount() < 0 ) {
                throw new IllegalArgumentException( "Cannot edit Ingredient to a negative integer" );
            }
        }

        ingredients = editBy;

    }

    /**
     * Edit the Units of one Ingredient
     *
     * @param ingredient
     *            to edit
     * @param editBy
     *            amount to edit to
     */
    public void editIngredientUnit ( final Ingredient ingredient, final Integer editBy ) {
        if ( editBy < 0 ) {
            throw new IllegalArgumentException( "Cannot edit Ingredient to a negative integer" );
        }
        for ( final Ingredient i : ingredients ) {
            if ( i.equals( ingredient ) ) {
                i.setAmount( editBy );
                break;
            }
        }
    }

    /**
     * Returns the name of the recipe.
     *
     * @return String
     */
    @Override
    public String toString () {
        final StringBuffer buf = new StringBuffer();
        buf.append( name + " with ingredients [" );
        final int size = ingredients.size();
        for ( int i = 0; i < size; i++ ) {
            buf.append( ingredients.get( i ).toString() );
            if ( i != size - 1 ) {
                buf.append( ", " );
            }
        }
        buf.append( "]" );
        return buf.toString();
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        Integer result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        }
        else if ( !name.equals( other.name ) ) {
            return false;
        }
        return true;
    }

}
