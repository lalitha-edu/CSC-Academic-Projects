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
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

/**
 * This is the controller that holds the REST endpoints that handle CRUD
 * operations for Recipes.
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
public class APIRecipeController extends APIController {

    /**
     * RecipeService object, to be autowired in by Spring to allow for
     * manipulating the Recipe model
     */
    @Autowired
    private RecipeService     service;

    /**
     * RecipeService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService ingredientService;

    /**
     * REST API method to provide GET access to all recipes in the system
     *
     * @return JSON representation of all recipies
     */
    @GetMapping ( BASE_PATH + "/recipes" )
    public List<Recipe> getRecipes () {
        return service.findAll();
    }

    /**
     * REST API method to provide GET access to a specific recipe, as indicated
     * by the path variable provided (the name of the recipe desired)
     *
     * @param name
     *            recipe name
     * @return response to the request
     */
    @GetMapping ( BASE_PATH + "/recipes/{name}" )
    public ResponseEntity getRecipe ( @PathVariable ( "name" ) final String name ) {
        final Recipe recipe = service.findByName( name );
        return null == recipe
                ? new ResponseEntity( errorResponse( "No recipe found with name " + name ), HttpStatus.NOT_FOUND )
                : new ResponseEntity( recipe, HttpStatus.OK );
    }

    /**
     * REST API method to provide POST access to the Recipe model. This is used
     * to create a new Recipe by automatically converting the JSON RequestBody
     * provided to a Recipe object. Invalid JSON will fail.
     *
     * @param recipe
     *            The valid Recipe to be saved.
     * @return ResponseEntity indicating success if the Recipe could be saved to
     *         the inventory, or an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/recipes" )
    public ResponseEntity createRecipe ( @RequestBody final Recipe recipe ) {
        if ( null != service.findByName( recipe.getName() ) ) {
            return new ResponseEntity( errorResponse( "Recipe with the name " + recipe.getName() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        if ( recipe.getIngredients().size() < 1 ) {
            return new ResponseEntity( errorResponse( "A recipe must have at least 1 ingredient" ),
                    HttpStatus.BAD_REQUEST );
        }
        if ( service.findAll().size() < 3 ) {
            service.save( recipe );
            return new ResponseEntity( successResponse( recipe.getName() + " successfully created" ), HttpStatus.OK );
        }
        else {
            return new ResponseEntity(
                    errorResponse( "Insufficient space in recipe book for recipe " + recipe.getName() ),
                    HttpStatus.INSUFFICIENT_STORAGE );
        }

    }

    /**
     * REST API method to provide PUT access to the Recipe model. This is used
     * to update a recipe as selected if it is the recipe the user wishes to
     * purchase
     *
     * @param recipe
     *            recipe potentially being selected by the user
     * @return ResponseEntity indicating success if the Recipe could be selected
     *         or if an error occurred
     */
    @PutMapping ( BASE_PATH + "/recipes/select" )
    public ResponseEntity putSelectedRecipe ( @RequestBody final Recipe recipe ) {
        if ( null == recipe ) {
            return new ResponseEntity( errorResponse( "Recipe does not exist." ), HttpStatus.NOT_FOUND );
        }
        else {
            Recipe.setCurrentRecipe( recipe );
            service.save( recipe );
            return new ResponseEntity( successResponse( recipe.getName() + " successfully selected" ), HttpStatus.OK );
        }
    }

    /**
     * REST API method to provide GET access to the Recipe model. This is used
     * to return the recipe that was selected by the user
     *
     * @return ResponseEntity indicating success if the Recipe could be returned
     *         or error if not
     */
    @GetMapping ( BASE_PATH + "/recipes/selectedRecipe" )
    public ResponseEntity getSelectedRecipe () {
        if ( Recipe.getCurrentRecipe() == null ) {
            System.out.println( "yoo hoo!" );
            return new ResponseEntity( errorResponse( "A recipe has not been selected." ), HttpStatus.NOT_FOUND );
        }
        else {
            return new ResponseEntity( Recipe.getCurrentRecipe(), HttpStatus.OK );
        }
    }

    /**
     * REST API method to allow deleting a Recipe from the CoffeeMaker's
     * Inventory, by making a DELETE request to the API endpoint and indicating
     * the recipe to delete (as a path variable)
     *
     * @param name
     *            The name of the Recipe to delete
     * @return Success if the recipe could be deleted; an error if the recipe
     *         does not exist
     */
    @DeleteMapping ( BASE_PATH + "/recipes/{name}" )
    public ResponseEntity deleteRecipe ( @PathVariable final String name ) {
        final Recipe recipe = service.findByName( name );
        if ( null == recipe ) {
            return new ResponseEntity( errorResponse( "No recipe found for name " + name ), HttpStatus.NOT_FOUND );
        }
        service.delete( recipe );

        return new ResponseEntity( successResponse( name + " was deleted successfully" ), HttpStatus.OK );
    }

    /**
     * REST API method to allow deleting an Ingredient from the Recipe, by
     * making a DELETE request to the API endpoint and indicating the recipe to
     * delete (as a path variable)
     *
     * @param name
     *            The name of the Recipe to edit
     * @param ingredientName
     *            The name of the Ingredient in Recipe to delete
     * @return Success if the recipe could be deleted; an error if the recipe
     *         does not exist
     */
    @DeleteMapping ( BASE_PATH + "/recipes/{name}/{ingredient}" )
    public ResponseEntity deleteIngredientInRecipe ( @PathVariable ( "name" ) final String name,
            @PathVariable ( "ingredient" ) final String ingredientName ) {
        final Recipe recipe = service.findByName( name );
        final Ingredient ingredient = ingredientService.findByName( ingredientName );
        if ( null == recipe ) {
            return new ResponseEntity( errorResponse( "No recipe found for name " + name ), HttpStatus.NOT_FOUND );
        }
        if ( null == ingredientName ) {
            return new ResponseEntity( errorResponse( "No recipe found for ingredient name " + name ),
                    HttpStatus.NOT_FOUND );
        }
        recipe.deleteIngredient( ingredient );
        // service.save( recipe );

        return new ResponseEntity( successResponse( name + " was deleted successfully" ), HttpStatus.OK );
    }

    /**
     * REST API method to provide POST access to the Recipe model. This is used
     * to create an Ingredient to add to the given recipe
     *
     * @param name
     *            The name of the recipe to add the Ingredient to
     * @param ingredient
     *            The ingredient to add to the recipe
     * @return ResponseEntity indicating success if the Ingredient could be
     *         saved to the inventory, or an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/recipes/{name}" )
    public ResponseEntity createIngredient ( @PathVariable ( "name" ) final String name,
            @RequestBody final Ingredient ingredient ) {
        final Recipe recipe = service.findByName( name );
        if ( null == recipe ) {
            return new ResponseEntity( errorResponse( "No recipe found for name " + name ), HttpStatus.NOT_FOUND );
        }
        recipe.addIngredient( ingredient );
        service.save( recipe );

        return new ResponseEntity(
                successResponse( ingredient.getName() + " successfully added to " + recipe.getName() ), HttpStatus.OK );

    }

    /**
     * REST API method to allow editing Ingredients the Recipe, by making a PUT
     * request to the API endpoint and indicating the recipe to delete (as a
     * path variable)
     *
     * @param name
     *            The name of the Recipe to edit
     * @param ingredientName
     *            The name of the ingredient to edit
     * @param editTo
     *            The Integer to edit the Ingredient with
     * @return Success if the recipe could be deleted; an error if the recipe
     *         does not exist
     */
    @PutMapping ( BASE_PATH + "/recipes/{name}/{ingredient}" )
    public ResponseEntity editIngredients ( @PathVariable ( "name" ) final String name,
            @PathVariable ( "ingredient" ) final String ingredientName, @RequestBody final Integer editTo ) {
        final Recipe recipe = service.findByName( name );
        if ( null == recipe ) {
            return new ResponseEntity( errorResponse( "No recipe found for name " + name ), HttpStatus.NOT_FOUND );
        }
        final Ingredient ingredient = ingredientService.findByName( ingredientName );
        if ( null == ingredient ) {
            return new ResponseEntity( errorResponse( "No ingredient found for name " + ingredientName ),
                    HttpStatus.NOT_FOUND );
        }
        try {
            recipe.editIngredientUnit( ingredient, editTo );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "Invalid integer values for editing Ingredients" ),
                    HttpStatus.BAD_REQUEST );
        }
        // service.save( recipe );

        return new ResponseEntity( successResponse( name + " was edited successfully" ), HttpStatus.OK );
    }

    /**
     * Edits the recipe given a dummy recipe from the user
     *
     * @param editTo
     *            recipe to edit the current recipe to
     * @return ResponseEntity of edited recipe
     */
    @PutMapping ( BASE_PATH + "/recipes" )
    public ResponseEntity editRecipe ( @RequestBody final Recipe editTo ) {
        final Recipe recipe = service.findByName( editTo.getName() );
        if ( null == recipe ) {
            return new ResponseEntity( errorResponse( "No recipe found for name " + editTo.getName() ),
                    HttpStatus.NOT_FOUND );
        }
        if ( editTo.getPrice() < 0 ) {
            return new ResponseEntity( errorResponse( "Invalid price" ), HttpStatus.BAD_REQUEST );
        }
        if ( editTo.getIngredients().size() < 1 ) {
            return new ResponseEntity( errorResponse( "A recipe must have at least 1 ingredient" ),
                    HttpStatus.BAD_REQUEST );
        }
        recipe.setPrice( editTo.getPrice() );
        try {
            recipe.editIngredients( editTo.getIngredients() );
        }
        catch ( final Exception e ) {
            return new ResponseEntity( errorResponse( "Invalid ingredients" ), HttpStatus.BAD_REQUEST );
        }
        service.save( recipe );
        return new ResponseEntity( successResponse( recipe.getName() + " was edited successfully" ), HttpStatus.OK );

    }

}
