package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

/**
 * This is the controller that holds the REST endpoints that handle add and
 * update operations for the Inventory.
 *
 * Spring will automatically convert all of the ResponseEntity and List results
 * to JSON
 *
 * @author Kai Presler-Marshall
 * @author Michelle Lemons
 *
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIInventoryController extends APIController {

    /**
     * InventoryService object, to be autowired in by Spring to allow for
     * manipulating the Inventory model
     */
    @Autowired
    private InventoryService  service;

    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService ingredientService;

    /**
     * REST API endpoint to provide GET access to the CoffeeMaker's singleton
     * Inventory. This will convert the Inventory to JSON.
     *
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "/inventory" )
    public ResponseEntity getInventory () {
        final Inventory inventory = service.getInventory();
        return new ResponseEntity( inventory, HttpStatus.OK );
    }

    /**
     * REST API endpoint to provide GET access to the CoffeeMaker's inventory
     * ingredients. This will convert the inventory ingredients to JSON.
     *
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "/inventoryIngredients" )
    public ResponseEntity getInventoryIngredients () {
        final Inventory inventory = service.getInventory();

        final List<Ingredient> list = inventory.getIngredients();

        return new ResponseEntity( list, HttpStatus.OK );
    }

    /**
     * REST API endpoint to provide PUT access to the CoffeeMaker's inventory.
     * This will convert the inventory to JSON.
     *
     * @param ingredient
     *            Ingredient ingredient
     * @return response to the request
     */
    @PutMapping ( BASE_PATH + "/addToInventory" )
    public ResponseEntity addInventory ( @RequestBody final Ingredient ingredient ) {
        // final Ingredient ingredient = new Ingredient( name, amount );
        // ingredientService.save( ingredient );

        if ( ingredient.getAmount() == null ) {
            return new ResponseEntity( errorResponse( "Invalid ingredient value. Minimum value is 1." ),
                    HttpStatus.NOT_ACCEPTABLE );
        }
        if ( ingredient.getAmount() < 1 ) {
            return new ResponseEntity( errorResponse( "Invalid ingredient value. Minimum value is 1." ),
                    HttpStatus.NOT_ACCEPTABLE );
        }
        else if ( service.getInventory().getIngredient( ingredient.getName() ) != null ) {
            return new ResponseEntity( errorResponse( "Ingredient already in inventory." ), HttpStatus.NOT_ACCEPTABLE );
        }

        service.addIngredient( ingredient );
        // service.save( service.getInventory() );
        // service.getInventory().updateIngredientAmount( name, amount );

        return new ResponseEntity( successResponse( ingredient.getName() + " was added successfully!" ),
                HttpStatus.OK );
    }

    /**
     * REST API endpoint to provide PUT access to the CoffeeMaker's inventory.
     * This will convert the inventory to JSON.
     *
     * @param updatedAmounts
     *            Ingredient ingredient
     * @return response to the request
     */
    @PutMapping ( BASE_PATH + "/updateInventory" )
    public ResponseEntity updateInventory ( @RequestBody final List<Integer> updatedAmounts ) {
        try {
            final Inventory currentInventory = service.getInventory();
            currentInventory.updateIngredientAmountList( updatedAmounts );
            service.save( currentInventory );
            return new ResponseEntity<>( currentInventory, HttpStatus.OK );
        }
        catch ( final Exception e ) {

            return new ResponseEntity<>( errorResponse( "Ingredient amount cannot be negative." ),
                    HttpStatus.CONFLICT );
        }
    }

    /**
     * REST API endpoint to provide DELETE access to the CoffeeMaker's
     * inventory. This will convert the inventory to JSON.
     *
     * @param ingredient
     *            Ingredient ingredient
     * @return response to the request
     */
    @DeleteMapping ( BASE_PATH + "/removeInventory" )
    public ResponseEntity removeInventory ( @RequestBody final Ingredient ingredient ) {
        final Ingredient removeIngredient = ingredientService.findByName( ingredient.getName() );
        if ( removeIngredient != null ) {
            // service.removeIngredient( removeIngredient );
            service.removeIngredient( removeIngredient );
            return new ResponseEntity( successResponse( ingredient.getName() + " was deleted successfully" ),
                    HttpStatus.OK );
        }
        else {
            return new ResponseEntity<>( "Ingredient not found", HttpStatus.NOT_FOUND );
        }
    }

}
