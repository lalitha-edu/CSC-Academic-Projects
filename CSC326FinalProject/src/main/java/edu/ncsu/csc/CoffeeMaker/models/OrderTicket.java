package edu.ncsu.csc.CoffeeMaker.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * OrderTicket object class. OrderTicket contains a ticket name, recipe, owner
 * and name of staff who fulfills the order along with order status
 *
 * @author Lalitha Edupuganti (ledupug)
 */
@Entity
public class OrderTicket extends DomainObject {

    /** OrderTicket id */
    @Id
    @GeneratedValue
    private Long    id;

    /** User Name field for ticket order */
    private String  name;

    /** Recipe field for ticket order */
    @OneToOne
    private Recipe  recipe;

    /** Owner field for ticket order */
    private String  owner;

    /** String val for staff who fulfills order field for ticket order */
    private String  fulfilledBy;

    /** Boolean val to check if order was fulfilled */
    private boolean fulfilled;

    /** Boolean val to check if order was picked up 8 */
    private boolean pickedUp;

    /** String val for order date and time */
    private String  currentDateTime;

    @Override
    public Serializable getId () {
        // TODO Auto-generated method stub
        return null;
    }

    /** Default orderTicket constructor */
    public OrderTicket () {

    }

    /**
     * OrderTicket constructor with order name, recipe and owner
     *
     * @param name
     *            the name of the ticket
     * @param recipe
     *            the specific recipe the order ticket fulfills
     * @param owner
     *            of the order
     */
    public OrderTicket ( final String name, final Recipe recipe, final String owner ) {
        setName( name );
        setRecipe( recipe );
        setOwner( owner );

        final LocalDateTime currentDateTimeLocal = LocalDateTime.now();

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "MM-dd-yyyy -- HH:mm:ss" );

        currentDateTime = currentDateTimeLocal.format( formatter );

        setFulfilled( false );
        setPickedUp( false );
    }

    /**
     * Getter Methods - DateTime
     *
     * @return currentDateTime the name of the ticket
     */
    public String getDateTime () {
        return currentDateTime;
    }

    /**
     * Getter Methods - Name
     *
     * @return name the name of the ticket
     */
    public String getName () {
        return name;
    }

    /**
     * Setter Methods - Name
     *
     * @param name
     *            the name of the ticket
     */
    public void setName ( final String name ) {
        this.name = name;
    }

    /**
     * Getter Methods - getRecipe
     *
     * @return recipe the recipe of the ticket
     */
    public Recipe getRecipe () {
        return recipe;
    }

    /**
     * Setter Methods - setRecipe
     *
     * @param recipe
     *            the recipe of the ticket
     */
    public void setRecipe ( final Recipe recipe ) {
        this.recipe = recipe;
    }

    /**
     * Getter Methods - getOwner
     *
     * @return name of the owner of the ticket
     */
    public String getOwner () {
        return owner;
    }

    /**
     * Setter Methods - setOwner
     *
     * @param owner
     *            the owner name of the ticket
     */
    public void setOwner ( final String owner ) {
        this.owner = owner;
    }

    /**
     * Get the name of staff who fulfills order
     *
     * @return fulfilledBy the string of staff who fulfills order
     */
    public String getFulfilledBy () {
        return fulfilledBy;
    }

    /**
     * Set the name of staff who fulfills order
     *
     * @param fulfilledBy
     *            the name of the staff who fulfills the order
     */
    public void setFulfilledBy ( final String fulfilledBy ) {
        this.fulfilledBy = fulfilledBy;
    }

    /**
     * Check if ticket is fulfilled
     *
     * @return fulfilled return if ticket is fulfilled or not
     */
    public boolean isFulfilled () {
        return fulfilled;
    }

    /**
     * Set the fulfilled field for ticket on if ticket is fulfilled or not
     *
     * @param fulfilled
     *            the field to set for if a ticket is fulfilled
     */
    public void setFulfilled ( final boolean fulfilled ) {
        this.fulfilled = fulfilled;
    }

    /**
     * Check if ticket has been picked up
     *
     * @return picked up flag for the order
     */
    public boolean isPickedUp () {
        return pickedUp;
    }

    /**
     * Set the fulfilled field for ticket on if ticket is fulfilled or not
     *
     * @param pickedUp
     *            the field to set for if a ticket is fulfilled
     */
    public void setPickedUp ( final boolean pickedUp ) {
        this.pickedUp = pickedUp;
    }

    /**
     * ToString method to returns a an order ticket with fulfilled details of
     * name, owner, fulfilled
     */
    @Override
    public String toString () {
        return "OrderTicket{" + "Ticket Name: '" + name + '\'' + ", Owner: '" + owner + '\'' + ", FulfilledBy: '"
                + fulfilledBy + '\'' + ", Fulfilled: " + fulfilled + '}';
    }

}
