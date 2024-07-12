
package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.OrderTicket;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.services.OrderTicketService;

/**
 * This is the controller that holds the REST endpoints that handle CRUD
 * operations for OrderTickets.
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
public class APITicketController extends APIController {

    /**
     * OrderTicketService object, to be autowired in by Spring to allow for
     * manipulating the Order model
     */
    @Autowired
    private OrderTicketService orderService;

    /**
     * REST API method to provide POST access to a specific order. Allows for a
     * user to create an order ticket.
     *
     * @param orderTicket
     *            order ticket being created
     * @return ResponseEntity containing a success response if the order could
     *         be created and an error response if the order already exists.
     */
    @PostMapping ( BASE_PATH + "/order" )
    public ResponseEntity createOrderTicket ( @RequestBody final OrderTicket orderTicket ) {
        final OrderTicket ticket = orderService.findByName( orderTicket.getName() );
        if ( ticket == null ) {
            orderService.save( orderTicket );
            return new ResponseEntity( successResponse( orderTicket.getName() + " successfully added" ),
                    HttpStatus.OK );
        }
        else {
            return new ResponseEntity( errorResponse( orderTicket.getName() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
    }

    /**
     * REST API method to provide PUT access to a specific order. Allows for
     * staff member to fulfill customer orders.
     *
     * @param name
     *            name of the order ticket
     * @return ResponseEntity containing a success response if the order could
     *         be fulfilled successfully and an error response if the order was
     *         already fulfilled, the order could not be found, or the user
     *         attempting to fulfill the order is not a staff member
     */
    @PutMapping ( BASE_PATH + "/order/{order_ticket}" )
    public ResponseEntity updateOrderTicket ( @PathVariable ( "order_ticket" ) final String name ) {
        System.out.println( name );
        final OrderTicket ticket = orderService.findByName( name );
        if ( ticket == null ) {
            return new ResponseEntity( errorResponse( name + " does not exist" ), HttpStatus.NOT_FOUND );
        }
        try {
            User.getCurrentUser().fulfillOrderTicket( ticket );
        }
        catch ( final Exception e ) {
            if ( "Only staff users can fulfill order tickets".equals( e.getMessage() ) ) {
                return new ResponseEntity( errorResponse( name + " could not be updated. "
                        + User.getCurrentUser().getname() + " is not a staff member." ), HttpStatus.CONFLICT );
            }
            else if ( "This order has already been fulfilled".equals( e.getMessage() ) ) {
                return new ResponseEntity( errorResponse( name + " has already been fulfilled. " ),
                        HttpStatus.CONFLICT );
            }
        }
        orderService.save( ticket );
        return new ResponseEntity(
                successResponse( name + " successfully updated by " + User.getCurrentUser().getname() + "." ),
                HttpStatus.OK );
    }

    /**
     * REST API method to provide PUT access to a specific order. Allows for an
     * order to be picked up from the system.
     *
     * @param name
     *            name of the order being picked up from the system
     * @return ResponseEntity containing a success response if the order was
     *         picked up or an error response if the order name does not exist
     *         in the system.
     */
    @PutMapping ( BASE_PATH + "/pickupOrder/{order_ticket}" )
    public ResponseEntity pickupOrderTicket ( @PathVariable ( "order_ticket" ) final String name ) {
        final OrderTicket ticket = orderService.findByName( name );
        if ( ticket == null ) {
            return new ResponseEntity( errorResponse( name + " does not exist" ), HttpStatus.NOT_FOUND );
        }

        if ( User.getCurrentUser().pickupOrderTicket( ticket ) ) {
            orderService.save( ticket );
            return new ResponseEntity( successResponse( name + " successfully picked up." ), HttpStatus.OK );
        }
        else {
            return new ResponseEntity( errorResponse( "Pick up failed" ), HttpStatus.CONFLICT );
        }
    }

    /**
     * REST API method to provide GET access to a all the orders. Allows for an
     * order to be deleted from the system.
     *
     * @return ResponseEntity containing a list of all the order tickets in the
     *         system or an empty list if no orders are in the system
     */
    @GetMapping ( BASE_PATH + "/order" )
    public ResponseEntity getAllOrders () {
        final List<OrderTicket> orders = orderService.findAll();
        return new ResponseEntity<>( orders, HttpStatus.OK );
    }

    /**
     * REST API method to provide GET access to all the complete/incomplete
     * orders, so it can be fulfilled.
     *
     * @param fulfilled
     *            * to check if the order is fulfilled ponseEntity containing a
     *            list of all the complete/incomplete
     *
     *            * complete/incomplete orders are in the system
     * @return ResponseEntity containing a list of Order tickets with either all
     *         completed or all uncompleted orders
     *
     */
    @GetMapping ( BASE_PATH + "/completeOrder" )
    public ResponseEntity getCompleteOrders (
            @RequestParam ( value = "fulfilled", required = false ) final boolean fulfilled ) {
        final List<OrderTicket> orders = orderService.findByFulfilled( fulfilled );
        return new ResponseEntity<>( orders, HttpStatus.OK );
    }

    /**
     * REST API method to provide GET access to the recipe selected in the order
     * ticket
     *
     * @param name
     *            name of the order ticket
     * @return ResponseEntity the recipe that is assigned to the order ticket
     */
    @GetMapping ( BASE_PATH + "/orderRecipe/{order_ticket}" )
    public ResponseEntity getOrderRecipe ( @PathVariable ( "order_ticket" ) final String name ) {
        final OrderTicket ticket = orderService.findByName( name );
        if ( ticket == null ) {
            return new ResponseEntity( errorResponse( name + " does not exist" ), HttpStatus.NOT_FOUND );
        }
        return new ResponseEntity( ticket.getRecipe(), HttpStatus.OK );
    }

    /**
     * REST API method to provide GET access to the recipe selected in the order
     * ticket
     *
     * @param name
     *            name of the order ticket
     *
     * @param recipe
     *            recipe for the ticket to be updated to
     *
     * @return ResponseEntity success response for updating the recipe
     */
    @PutMapping ( BASE_PATH + "/orderRecipe/{order_ticket}" )
    public ResponseEntity setOrderRecipe ( @PathVariable ( "order_ticket" ) final String name,
            @RequestBody final Recipe recipe ) {
        final OrderTicket ticket = orderService.findByName( name );
        if ( ticket == null ) {
            return new ResponseEntity( errorResponse( name + " does not exist" ), HttpStatus.NOT_FOUND );
        }
        ticket.setRecipe( recipe );
        orderService.save( ticket );
        return new ResponseEntity( successResponse( name + "'s recipe successfully updated to be " + recipe.getName() ),
                HttpStatus.OK );
    }

}
