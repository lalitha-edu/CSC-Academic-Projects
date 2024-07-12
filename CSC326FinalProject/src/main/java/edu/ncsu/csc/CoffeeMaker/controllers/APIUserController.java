
package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.OrderTicket;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.services.OrderTicketService;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

/**
 * This is the controller that holds the REST endpoints that handle CRUD
 * operations for Recipes.
 *
 * Spring will automatically convert all of the ResponseEntity and List results
 * to JSON
 *
 * @author Brock Pittman (bjpittm2)
 * @author Esha Thorat (esthorat)
 *
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIUserController extends APIController {

    /**
     * UserService object, to be autowired in by Spring to allow for
     * manipulating the User model
     */
    @Autowired
    private UserService        userService;

    /**
     * OrderTicketService object, to be autowired in by Spring to allow for
     * manipulating the Order model
     */
    @Autowired
    private OrderTicketService orderService;

    /**
     * REST API method to provide GET access to a specific user. Allows for a
     * user to login
     *
     * @param name
     *            The username of the user being logged in
     * @param dummy
     *            The password of the user being logged in
     *
     * @return ResponseEntity indicating success if the User could be logged in,
     *         and an error if not.
     */
    @PutMapping ( BASE_PATH + "/users/{name}" )
    public ResponseEntity login ( @PathVariable ( "name" ) final String name, @RequestBody final User dummy ) {
        if ( "admin".equals( name ) && dummy.getPassword() == "admin".hashCode() ) {
            // First we check if admin and then create admin if needed
            User admin = userService.findByName( name );
            if ( admin == null ) {
                admin = new User();
                userService.save( admin );
            }
            User.setCurrentUser( admin );
            return new ResponseEntity( successResponse( admin.getType().toString() ), HttpStatus.OK );
        }
        else {
            // Otherwise standard login flow
            final User currentUser = userService.findByName( name );

            if ( currentUser == null ) {
                return new ResponseEntity( errorResponse( name + " does not match a known account" ),
                        HttpStatus.NOT_FOUND );
            }
            if ( currentUser.getPassword() == dummy.getPassword() ) {
                User.setCurrentUser( currentUser );
                return new ResponseEntity( successResponse( currentUser.getType().toString() ), HttpStatus.OK );
            }
            else {
                return new ResponseEntity( errorResponse( "Password does not match the username" ),
                        HttpStatus.NOT_FOUND );
            }
        }
    }

    /**
     * Returns the typing of the current user using the application
     *
     * @return response entity containing current user type string or error
     *         response if no user was found
     */
    @GetMapping ( BASE_PATH + "/users" )
    public ResponseEntity getCurrentUserType () {
        final User currentUser = User.getCurrentUser();
        if ( currentUser == null ) {
            return new ResponseEntity( errorResponse( "No user found" ), HttpStatus.NOT_FOUND );
        }
        else {
            return new ResponseEntity( successResponse( currentUser.getType().toString() ), HttpStatus.OK );
        }
    }

    /**
     * Returns the current user using the aplpication
     *
     * @return response entiry containing current user or error response if no
     *         user was found
     *
     */
    @GetMapping ( BASE_PATH + "/users/name" )
    public ResponseEntity getCurrentUser () {
        final User currentUser = User.getCurrentUser();
        if ( currentUser == null ) {
            return new ResponseEntity( errorResponse( "No user found" ), HttpStatus.NOT_FOUND );
        }
        else {
            return new ResponseEntity( currentUser, HttpStatus.OK );
        }
    }

    /**
     * REST API method to provide POST access to the User model. This is used to
     * create a new User to use the CoffeeMaker
     *
     * @param user
     *            The user being created
     * @return ResponseEntity indicating success if the User could be saved to
     *         the system, or an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/users" )
    public ResponseEntity createUser ( @RequestBody final User user ) {
        final User newUser = userService.findByName( user.getname() );

        if ( null != newUser ) {
            return new ResponseEntity( errorResponse( "Username: " + newUser.getname() + " already exists." ),
                    HttpStatus.NOT_ACCEPTABLE );
        }

        userService.save( user );

        return new ResponseEntity( successResponse( user.getname() + " successfully created." ), HttpStatus.OK );

    }

    /**
     * REST API method to provide Get access to the User model. This is used to
     * get a list of the users placed orders.
     *
     * @param name
     *            name of the user with the orders
     * @return ResponseEntity containing list of the orders the customer placed,
     *         an empty list if the customer has placed no orders, or an error
     *         response if the user is invalid
     */
    @GetMapping ( BASE_PATH + "/User/{name}" )
    public ResponseEntity getCustomerOrders ( @PathVariable ( "name" ) final String name ) {
        final User user = userService.findByName( name );
        if ( user == null ) {
            return new ResponseEntity( errorResponse( "No user found" ), HttpStatus.NOT_FOUND );
        }
        final List<OrderTicket> orderList = orderService.findAll();
        final List<OrderTicket> customerOrders = new LinkedList<OrderTicket>();
        for ( int i = 0; i < orderList.size(); i++ ) {
            final OrderTicket order = orderList.get( i );
            if ( name.equals( order.getOwner() ) ) {
                customerOrders.add( order );
            }
        }
        return new ResponseEntity( customerOrders, HttpStatus.OK );
    }

    /**
     * REST API method to provide Put access to the User model. This is used to
     * logout the current user.
     *
     * @return ResponseEntity with a success or failure based on if it could
     *         logout the current user
     */
    @PutMapping ( BASE_PATH + "/users" )
    public ResponseEntity logout () {
        if ( User.getCurrentUser() == null ) {
            return new ResponseEntity( errorResponse( "No user found" ), HttpStatus.NOT_FOUND );
        }
        final String name = User.getCurrentUser().getname();
        User.setCurrentUser( null );
        return new ResponseEntity( successResponse( name + " was signed out" ), HttpStatus.OK );
    }

}
