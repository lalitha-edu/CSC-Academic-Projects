package edu.ncsu.csc.CoffeeMaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;

/**
 * Class for the ingredient repository. Will find the ingredients in the
 * repository by name.
 *
 * @author Andrew Moore (ajmoor22)
 * @author Carson Cole (cfcole)
 * @author Brock Pittman (bjpittm2)
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * Finds a Ingredient object with the provided name. Spring will generate
     * code to make this happen.
     *
     * @param name
     *            Name of the recipe
     * @return Found ingredient, null if none.
     */
    Ingredient findByName ( String name );
}
