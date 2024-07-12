package edu.ncsu.csc.CoffeeMaker.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.repositories.UserRepository;

/**
 * Service for the Users
 *
 * @author Andrew Moore (ajmoor22)
 */
@Component
@Transactional
public class UserService extends Service<User, Long> {

    /**
     * Instance of the User repository
     */
    @Autowired
    private UserRepository repository;

    @Override
    protected JpaRepository<User, Long> getRepository () {
        return repository;
    }

    /**
     * Find a recipe with the provided name
     *
     * @param name
     *            Name of the recipe to find
     * @return found recipe, null if none
     */
    public User findByName ( final String name ) {
        return repository.findByName( name );
    }

}
