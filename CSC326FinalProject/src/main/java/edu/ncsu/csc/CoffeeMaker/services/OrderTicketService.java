package edu.ncsu.csc.CoffeeMaker.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.CoffeeMaker.models.OrderTicket;
import edu.ncsu.csc.CoffeeMaker.repositories.OrderTicketRepository;

/**
 * Service for the OrderTicket class
 *
 * @author Lalitha Edupuganti (ledupug)
 * @author Andrew Moore (ajmoor22)
 */
@Component
@Transactional
public class OrderTicketService extends Service<OrderTicket, Long> {

    /**
     * Instance of the ingredient repository
     */
    @Autowired
    private OrderTicketRepository repository;

    @Override
    protected JpaRepository<OrderTicket, Long> getRepository () {
        return repository;
    }

    /**
     * Find a recipe with the provided name
     *
     * @param name
     *            Name of the ticket to find
     * @return found ticket, null if none
     */
    public OrderTicket findByName ( final String name ) {
        return repository.findByName( name );
    }

    /**
     * finds list of orders if they've been fulfilled
     *
     * @param fulfilled
     *            boolean of the list of of complete or incomplete orders to
     *            find
     * @return list of order tickets
     */
    public List<OrderTicket> findByFulfilled ( final boolean fulfilled ) {
        return repository.findByFulfilled( fulfilled );
    }

}
