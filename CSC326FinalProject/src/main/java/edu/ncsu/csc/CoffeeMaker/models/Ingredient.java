package edu.ncsu.csc.CoffeeMaker.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Ingredient object class. Ingredients contain an id, name, and amount.
 *
 * @author Andrew Moore (ajmoor22)
 * @author Carson Cole (cfcole)
 * @author Brock Pittman (bjpittm2)
 */
@Entity
public class Ingredient extends DomainObject {

    /** Ingredient id */
    @Id
    @GeneratedValue
    private Long    id;

    /** Ingredient name */
    private String  name;

    /** Ingredient amount */
    private Integer amount;

    @Override
    public Long getId () {
        // TODO Auto-generated method stub
        return id;
    }

    /**
     * Default ingredient constructor
     */
    public Ingredient () {
        this( "", 0 );
    }

    /**
     * Main ingredient constructor
     *
     * @param ingredient
     *            Name of the ingredient
     * @param amount
     *            Amount of the ingredient
     */
    public Ingredient ( final String ingredient, final Integer amount ) {
        super();
        this.name = ingredient;
        this.amount = amount;
    }

    /**
     * Gets the ingredient name
     *
     * @return name The name of the ingredient
     */
    public String getName () {
        return name;
    }

    /**
     * Sets the name of the ingredient
     *
     * @param ingredient
     *            Name of the ingredient
     */
    public void setName ( final String ingredient ) {
        this.name = ingredient;
    }

    /**
     * Gets the amount of the ingredient
     *
     * @return amount The amount of the ingredient
     */
    public Integer getAmount () {
        return amount;
    }

    /**
     * Sets the amount of the ingredient
     *
     * @param amount
     *            The amount of the ingredient
     */
    public void setAmount ( final Integer amount ) {
        this.amount = amount;
    }

    /**
     * Sets the id of the ingredient
     *
     * @param id
     *            Id of the ingredient
     */
    public void setId ( final long id ) {
        this.id = id;
    }

    @Override
    public String toString () {
        return "Ingredient [ingredient=" + name + ", amount=" + amount + "]";
    }

}
