package edu.ncsu.csc.CoffeeMaker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.CoffeeMaker.models.OrderTicket;

/**
 * Class for the orderticket repository. Will find the tickets in the repository
 * by name.
 *
 * @author Lalitha Edupuganti
 */
public interface OrderTicketRepository extends JpaRepository<OrderTicket, Long> {

    /**
     * Finds a OrderTicket object with the provided name. Spring will generate
     * code to make this happen.
     *
     * @param name
     *            Name of the ticket
     * @return Found ticket, null if none.
     */
    OrderTicket findByName ( String name );

    /**
     * finds the orders if they've been fulfilled
     *
     * @param fulfilled
     *            boolean of the list of of complete or incomplete orders to
     *            find
     * @return list of order tickets
     */
    List<OrderTicket> findByFulfilled ( boolean fulfilled );
}
