package edu.ncsu.csc.CoffeeMaker.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

/**
 * Ingredient for the coffee maker. Ingredient is tied to the database using
 * Hibernate libraries. See IngredientRepository and IngredientService for the
 * other two pieces used for database support.
 * 
 */
@Entity
public class Ingredient extends DomainObject {

    /** Ingredient ID */
    @Id
    @GeneratedValue
    private long    id;

    /** Ingredient name */
    private String  name;

    /** Ingredient amount */
    @Min ( 0 )
    private Integer amount;

    /** Ingredient default constructor */
    public Ingredient () {
        super();
    }

    /**
     * Ingredient constructor with params
     *
     * @param name
     *            String name
     * @param amount
     *            Integer amount
     */
    public Ingredient ( final String name, final Integer amount ) {
        super();

        setName( name );
        setAmount( amount );
    }

    @Override
    public Serializable getId () {
        return id;
    }

    /**
     * Sets the ID for the ingredient
     *
     * @param id
     *            Long id
     */
    public void setId ( final long id ) {
        this.id = id;
    }

    // public Ingredient getIngredient () {
    // return ingredient;
    // }

    // public void setIngredient ( final IngredientType ingredient ) {
    // this.ingredient = ingredient;
    // }

    /**
     * Returns the amount for the ingredient
     *
     * @return amount Integer
     */
    public Integer getAmount () {
        return amount;
    }

    /**
     * Sets the amount for the ingredient
     *
     * @param amount
     *            Integer amount
     */
    public void setAmount ( final Integer amount ) {
        if ( amount < 0 || amount == null ) {
            throw new IllegalArgumentException( "" );
        }

        if ( this.amount == null ) {
            this.amount = amount; // Initialize amount if it's null
        }
        else {
            this.amount += amount; // Add to existing amount
        }
    }

    /**
     * Returns the name for the ingredient
     *
     * @return name String
     */
    public String getName () {
        return name;
    }

    /**
     * Sets the names for the ingredient
     *
     * @param name
     *            String name
     */
    public void setName ( final String name ) {
        this.name = name;
    }

    @Override
    public String toString () {
        return "Ingredient [id=" + id + ", name" + name + ", amount=" + amount + "]";
    }

}
