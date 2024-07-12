package edu.ncsu.csc.CoffeeMaker.unit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.OrderTicket;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.OrderTicketService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@SpringBootTest
public class OrderTicketTest {

    @Autowired
    private OrderTicketService orderTicketService;

    private Recipe             r;

    @Autowired
    private RecipeService      service;

    @BeforeEach
    public void setUp () {
        r = new Recipe();
        final Ingredient i1 = new Ingredient( "Coffee", 5 );
        final Ingredient i2 = new Ingredient( "Milk", 10 );
        r.addIngredient( i1 );
        r.addIngredient( i2 );
        r.setPrice( 10 );
        r.setName( "Mocha" );

        service.save( r );
    }

    @Test
    @Transactional
    public void testCreateOrderTicket () {

        final OrderTicket orderTicket = new OrderTicket( "TestOrder", r, "TestOwner" );
        orderTicketService.save( orderTicket );

        Assertions.assertEquals( "TestOrder", orderTicket.getName() );
        Assertions.assertEquals( r, orderTicket.getRecipe() );
        Assertions.assertEquals( "TestOwner", orderTicket.getOwner() );
        Assertions.assertFalse( orderTicket.isFulfilled() );
        Assertions.assertNull( orderTicket.getFulfilledBy() );
    }

    @Test
    @Transactional
    public void testFulfillOrderTicket () {
        final OrderTicket orderTicket = new OrderTicket( "TestOrder", r, "TestOwner" );
        orderTicketService.save( orderTicket );

        orderTicket.setFulfilledBy( "Staff1" );
        orderTicket.setFulfilled( true );
        orderTicketService.save( orderTicket );

        Assertions.assertTrue( orderTicket.isFulfilled() );
        Assertions.assertEquals( "Staff1", orderTicket.getFulfilledBy() );
    }

    @Test
    @Transactional
    public void testPickUpOrderTicket () {
        final OrderTicket orderTicket = new OrderTicket( "TestOrder", r, "TestOwner" );
        orderTicketService.save( orderTicket );

        orderTicket.setPickedUp( true );
        orderTicketService.save( orderTicket );

        Assertions.assertTrue( orderTicket.isPickedUp() );
    }

    @Test
    @Transactional
    public void testOrderingMultipleTickets () {
        // Create and save multiple order tickets with different statuses and
        // fulfillment information
        final OrderTicket orderTicket1 = new OrderTicket( "Ticket1", r, "Owner1" );
        orderTicket1.setFulfilled( true );
        orderTicket1.setFulfilledBy( "Staff1" );
        orderTicketService.save( orderTicket1 );

        final OrderTicket orderTicket2 = new OrderTicket( "Ticket2", r, "Owner2" );
        orderTicket2.setFulfilled( false );
        orderTicketService.save( orderTicket2 );

        final OrderTicket orderTicket3 = new OrderTicket( "Ticket3", r, "Owner3" );
        orderTicket3.setFulfilled( true );
        orderTicket3.setFulfilledBy( "Staff2" );
        orderTicketService.save( orderTicket3 );

        // Assert properties of each order ticket individually
        Assertions.assertEquals( "Ticket1", orderTicket1.getName() );
        Assertions.assertEquals( r, orderTicket1.getRecipe() );
        Assertions.assertEquals( "Owner1", orderTicket1.getOwner() );
        Assertions.assertTrue( orderTicket1.isFulfilled() );
        Assertions.assertEquals( "Staff1", orderTicket1.getFulfilledBy() );

        Assertions.assertEquals( "Ticket2", orderTicket2.getName() );
        Assertions.assertEquals( r, orderTicket2.getRecipe() );
        Assertions.assertEquals( "Owner2", orderTicket2.getOwner() );
        Assertions.assertFalse( orderTicket2.isFulfilled() );
        Assertions.assertNull( orderTicket2.getFulfilledBy() );

        Assertions.assertEquals( "Ticket3", orderTicket3.getName() );
        Assertions.assertEquals( r, orderTicket3.getRecipe() );
        Assertions.assertEquals( "Owner3", orderTicket3.getOwner() );
        Assertions.assertTrue( orderTicket3.isFulfilled() );
        Assertions.assertEquals( "Staff2", orderTicket3.getFulfilledBy() );
    }

    @Test
    @Transactional
    public void testToString () {
        final OrderTicket orderTicket = new OrderTicket( "TestOrder", r, "TestOwner" );
        orderTicketService.save( orderTicket );

        final String expected = "OrderTicket{Ticket Name: 'TestOrder', Owner: 'TestOwner', FulfilledBy: 'null', Fulfilled: false}";
        Assertions.assertEquals( expected, orderTicket.toString() );
    }

    @Test
    @Transactional
    public void testOrderDate () {
        // I'm gonna be honest, idk how else to test this
        // At least it is outputting a string that represents the date in some
        // way

        final OrderTicket orderTicket = new OrderTicket( "TestOrder", r, "TestOwner" );
        orderTicketService.save( orderTicket );

        final LocalDateTime currentDateTimeLocal = LocalDateTime.now();

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "MM-dd-yyyy" );

        final String expected = currentDateTimeLocal.format( formatter );

        Assertions.assertEquals( orderTicket.getDateTime().substring( 0, 10 ), expected );
    }
}
