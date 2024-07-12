/**
 *
 */
package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

/**
 *
 * The APIIngredientController is responsible for making the Ingredients Spring
 * will automatically convert all of the ResponseEntity and List results to JSON
 *
 * @author Andrew Moore (ajmoor22)
 * @author Carson Cole (cfcole)
 * @author Brock Pittman (bjpittm2)
 *
 */
@SuppressWarnings ( { "unchecked", "rawtypes" } )
@RestController
public class APIIngredientController extends APIController {

    /** Ingredient service */
    @Autowired
    private IngredientService ingredientService;

    /**
     * Get API method to retrieve the ingredient with the given name for the
     * update use case
     *
     * @param name
     *            ingredient name
     * @param amountToAdd
     *            amount of units to add to ingredient units
     * @return response entity of Ingredient
     */
    @PutMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity addToIngredient ( @PathVariable ( "name" ) final String name,
            @RequestBody final int amountToAdd ) {

        Ingredient ingr = null;
        try {
            ingr = ingredientService.findByName( name );

        }
        catch ( final NullPointerException e ) {
            ingr = null;
        }

        if ( null == ingr ) {
            return new ResponseEntity( HttpStatus.NOT_FOUND );
        }

        if ( amountToAdd < 0 ) {
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }

        ingr.setAmount( ingr.getAmount() + amountToAdd );

        return new ResponseEntity( ingr, HttpStatus.OK );
    }

    /**
     * Get API method to retrieve and delete the ingredient with the given name
     * for the delete use case
     *
     * @param name
     *            ingredient name
     * @return response entity
     */
    @DeleteMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity deleteIngredient ( @PathVariable ( "name" ) final String name ) {

        Ingredient ingr = null;
        try {
            ingr = ingredientService.findByName( name );

        }
        catch ( final NullPointerException e ) {
            ingr = null;
        }

        if ( null == ingr ) {
            return new ResponseEntity( HttpStatus.NOT_FOUND );
        }

        ingredientService.delete( ingr );

        return new ResponseEntity( ingr, HttpStatus.OK );
    }

    /**
     * Post API method
     *
     * @param ingredient
     *            to add
     * @return The response entity of creation
     */
    @PostMapping ( BASE_PATH + "/ingredients" )
    public ResponseEntity createIngredient ( @RequestBody final Ingredient ingredient ) {

        final Ingredient db = ingredientService.findByName( ingredient.getName() );

        if ( null != db ) {
            return new ResponseEntity( HttpStatus.CONFLICT );
        }

        // Checks for invalid inputs
        if ( ingredient.getAmount() < 0 ) {
            return new ResponseEntity( errorResponse( "Invalid amount for an ingredient." ), HttpStatus.CONFLICT );
        }

        try {
            ingredientService.save( ingredient );
            return new ResponseEntity( HttpStatus.CREATED );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "Ingredient could not be saved" ), HttpStatus.BAD_REQUEST ); // HttpStatus.FORBIDDEN
            // would be OK
            // too.
        }

    }

    /**
     * Get API method to retrieve the ingredients in the service
     *
     * @return List of Ingredients from service
     */
    @GetMapping ( BASE_PATH + "/ingredients" )
    public List<Ingredient> getAllIngredients () {

        return ingredientService.findAll();
    }

}
