package edu.ncsu.csc.CoffeeMaker.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The User object that decides what actions each user can do inside of
 * CoffeeMaker
 *
 * @author ajmoor22
 */
@Entity
public class User extends DomainObject {

    /** User id */
    @Id
    @GeneratedValue
    private Long        id;

    /** User name */
    private String      name;
    /** User password */
    private String      password;

    /** The currentUser who is using the application */
    private static User currentUser;

    /** The type of a user */
    public enum Type {
        /** Types of users that can exist */
        STAFF, CUSTOMER, ADMIN
    }

    /** User type */
    private Type type;

    /**
     * Constructor with name password and
     *
     * @param type
     *            of user
     * @param name
     *            of user
     * @param password
     *            of user
     */
    public User ( final String name, final String password, final Type type ) {
        super();
        setType( type );
        setname( name );
        setPassword( password );
    }

    /**
     * Constructor with name password and
     *
     */
    public User () {
        super();
        setType( Type.ADMIN );
        setname( "admin" );
        setPassword( "admin" );
    }

    /**
     * Sets the current user to the given user
     *
     * @param user
     *            to make current user
     */
    public static void setCurrentUser ( final User user ) {
        currentUser = user;
    }

    /**
     * Gets the current user for the application
     *
     * @return curreentUser for the application
     */
    public static User getCurrentUser () {
        return currentUser;
    }

    /**
     * gets name of user
     *
     * @return the name
     */
    public String getname () {
        return name;
    }

    /**
     * sets name of user
     *
     * @param name
     *            the name to set
     */
    public void setname ( final String name ) {
        this.name = name;
    }

    /**
     * Gets hashed password of user
     *
     * @return the hashed password
     */
    public int getPassword () {
        return password.hashCode();
    }

    /**
     * sets password of user
     *
     * @param password
     *            the password to set
     */
    public void setPassword ( final String password ) {
        this.password = password;
    }

    /**
     * gets type of user
     *
     * @return the type
     */
    public Type getType () {
        return type;
    }

    /**
     * sets type of user
     *
     * @param type
     *            the type to set
     */
    public void setType ( final Type type ) {
        this.type = type;
    }

    @Override
    public Long getId () {
        // TODO Auto-generated method stub
        return id;
    }

    /**
     * Sets the id of the user
     *
     * @param id
     *            Id of the user
     */
    public void setId ( final long id ) {
        this.id = id;
    }

    @Override
    public String toString () {
        return "User [name=" + name + ", password=" + password + ", type=" + type + "]";
    }

    @Override
    public int hashCode () {
        return Objects.hash( name );
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
        final User other = (User) obj;
        return Objects.equals( name, other.name );
    }

    /**
     * Fulfill Order Ticket, if the user is a staff member, then order can be
     * fulfilled if order ticket's field is set to completed
     *
     * @param orderTicket
     *            the order ticket to be fulfilled
     * @throws UnsupportedOperationException
     *             if user is not a staff
     */
    public void fulfillOrderTicket ( final OrderTicket orderTicket ) {
        if ( type == User.Type.STAFF ) {
            if ( orderTicket.isFulfilled() ) {
                throw new UnsupportedOperationException( "This order has already been fulfilled" );
            }

            orderTicket.setFulfilledBy( name );
            orderTicket.setFulfilled( true );
        }
        else {
            throw new UnsupportedOperationException( "Only staff users can fulfill order tickets" );
        }
    }

    /**
     * Create Order Ticket, if the user is a customer member, then order can be
     * created.
     *
     * @param recipe
     *            the recipe use to create order ticket
     * @param name
     *            the name of the order ticket
     * @return new OrderTicket if successfully creates order
     * @throws UnsupportedOperationException
     *             if user is not a customer
     *
     */
    public OrderTicket createOrderTicket ( final Recipe recipe, final String name ) {
        if ( type == User.Type.CUSTOMER ) {
            return new OrderTicket( name, recipe, this.name );
        }
        else {
            throw new UnsupportedOperationException( "Only customers may order" );
        }

    }

    /**
     * Boolean method that checks if an orderTicket has been fulfilled and
     * return status
     *
     * @param orderTicket
     *            the ticket to pick up
     * @return true if the ticket has been completed
     */
    public boolean pickupOrderTicket ( final OrderTicket orderTicket ) {
        if ( type == User.Type.CUSTOMER ) {
            if ( orderTicket.isFulfilled() && !orderTicket.isPickedUp() && name.equals( orderTicket.getOwner() ) ) {
                orderTicket.setPickedUp( true );
                return true;
            }
        }

        return false;
    }

}
