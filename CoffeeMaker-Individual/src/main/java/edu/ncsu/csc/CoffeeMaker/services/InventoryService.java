package edu.ncsu.csc.CoffeeMaker.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.repositories.InventoryRepository;

/**
 * The InventoryService is used to handle CRUD operations on the Inventory
 * model. In addition to all functionality in `Service`, we also manage the
 * Inventory singleton.
 *
 * @author Kai Presler-Marshall
 *
 */
@Component
@Transactional
public class InventoryService extends Service<Inventory, Long> {

    /**
     * InventoryRepository, to be autowired in by Spring and provide CRUD
     * operations on Inventory model.
     */
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    protected JpaRepository<Inventory, Long> getRepository () {
        return inventoryRepository;
    }

    /**
     * Retrieves the singleton Inventory instance from the database, creating it
     * if it does not exist.
     *
     * @return the Inventory, either new or fetched
     */
    public synchronized Inventory getInventory () {
        final List<Inventory> inventoryList = findAll();
        if ( inventoryList != null && inventoryList.size() == 1 ) {
            return inventoryList.get( 0 );
        }
        else {
            // initialize the inventory with 0 of everything
            final Inventory i = new Inventory();
            save( i );
            return i;
        }
    }

    /**
     * Adds the Ingredient to the Inventory
     *
     * @param ingredient
     *            Ingredient ingredient
     */
    public synchronized void addIngredient ( Ingredient ingredient ) {
        final Inventory inventory = getInventory();

        if ( inventory.checkIngredient( ingredient ) == 0 ) {
            inventory.addIngredient( ingredient.getName(), ingredient.getAmount() );
        }
        else {
            throw new IllegalArgumentException( "Ingredient already exists" );

        }

        // inventoryRepository.save( inventory );
        save( inventory );
    }

    /**
     * Removes the Ingredient from the Inventory
     *
     * @param ingredient
     *            Ingredient ingredient
     */
    public synchronized void removeIngredient ( Ingredient ingredient ) {
        final Inventory inventory = getInventory();

        inventory.deleteIngredient( ingredient.getName() );
        // inventoryRepository.save( inventory );
        save( inventory );
    }

    /**
     * Checks if the Ingredient is in the Inventory
     *
     * @param ingredient
     *            Ingredient ingredient
     * @return boolean if Ingredient is present
     */
    public synchronized boolean checkIngredient ( Ingredient ingredient ) {
        final Inventory inventory = getInventory();

        final List<Ingredient> ingredients = inventory.getIngredients();

        for ( final Ingredient checkIngredient : ingredients ) {
            if ( checkIngredient.getName().equals( ingredient ) ) {
                return true;
            }

        }

        return false;
    }

}
