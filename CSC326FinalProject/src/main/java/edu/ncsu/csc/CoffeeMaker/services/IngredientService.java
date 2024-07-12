package edu.ncsu.csc.CoffeeMaker.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.repositories.IngredientRepository;

/**
 * Service for the ingredients
 *
 * @author Andrew Moore (ajmoor22)
 * @author Carson Cole (cfcole)
 * @author Brock Pittman (bjpittm2)
 */
@Component
@Transactional
public class IngredientService extends Service<Ingredient, Long> {

    /**
     * Instance of the ingredient repository
     */
    @Autowired
    private IngredientRepository repository;

    @Override
    protected JpaRepository<Ingredient, Long> getRepository () {
        return repository;
    }

    /**
     * Find a recipe with the provided name
     *
     * @param name
     *            Name of the recipe to find
     * @return found recipe, null if none
     */
    public Ingredient findByName ( final String name ) {
        return repository.findByName( name );
    }

}
