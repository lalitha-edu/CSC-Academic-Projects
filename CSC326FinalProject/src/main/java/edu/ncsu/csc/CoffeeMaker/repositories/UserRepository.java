package edu.ncsu.csc.CoffeeMaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import edu.ncsu.csc.CoffeeMaker.models.User;

/**
 * Class for the User repository. Will find the Users in the repository by name.
 *
 * @author Brock Pittman (bjpittm2) & Esha Thorat
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User object with the provided name. Spring will generate code to
     * make this happen.
     *
     * @param name
     *            Username of the user
     * @return Found user, null if none.
     */
    User findByName ( String name );
}
