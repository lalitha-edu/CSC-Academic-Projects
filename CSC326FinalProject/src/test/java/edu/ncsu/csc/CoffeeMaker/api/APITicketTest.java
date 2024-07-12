package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.OrderTicket;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.services.OrderTicketService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc

public class APITicketTest {

    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private OrderTicketService    orderService;

    @Autowired
    private RecipeService         recipeService;

    @Autowired
    private UserService           userService;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        orderService.deleteAll();
        recipeService.deleteAll();
    }

    @Test
    @Transactional
    void testCreateOrderTicket () throws Exception {

        /**
         * Tests creating a new ticket with the API
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );

        mvc.perform( post( String.format( "/api/v1/order" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ticket ) ) ).andExpect( status().is2xxSuccessful() );

    }

    @Test
    @Transactional
    void testCreateInvalidOrderTicket () throws Exception {

        /**
         * Tests creating an invalid order ticket with the API
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );

        orderService.save( ticket );

        mvc.perform( post( String.format( "/api/v1/order" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ticket ) ) ).andExpect( status().is4xxClientError() );

    }

    @Test
    @Transactional
    void testUpdateOrderTicket () throws Exception {

        /**
         * Tests updating a ticket with the API as a Staff user
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );
        orderService.save( ticket );

        final User staff = new User( "username", "password", User.Type.STAFF );
        userService.save( staff );
        User.setCurrentUser( staff );

        mvc.perform( put( String.format( "/api/v1/order/" + ticket.getName() ) )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( staff ) ) )
                .andExpect( status().is2xxSuccessful() );

    }

    @Test
    @Transactional
    void testUpdateInvalidOrderTicket1 () throws Exception {

        /**
         * Tests updating a ticket with the API as a Customer user
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );
        orderService.save( ticket );

        final User customer = new User( "username", "password", User.Type.CUSTOMER );
        userService.save( customer );
        User.setCurrentUser( customer );

        mvc.perform( put( String.format( "/api/v1/order/" + ticket.getName() ) )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( customer ) ) )
                .andExpect( status().is4xxClientError() );

    }

    @Test
    @Transactional
    void testUpdateInvalidOrderTicket2 () throws Exception {

        /**
         * Tests updating a ticket that has already been fulfilled with the API
         * as a Staff user
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );
        orderService.save( ticket );

        final User staff = new User( "username", "password", User.Type.STAFF );

        staff.fulfillOrderTicket( ticket );

        mvc.perform( put( String.format( "/api/v1/order/" + ticket.getName() ) )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( staff ) ) )
                .andExpect( status().is4xxClientError() );

    }

    @Test
    @Transactional
    void testUpdateInvalidOrderTicket3 () throws Exception {

        /**
         * Tests updating a ticket that does not exist with the API as a Staff
         * user
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );

        final User staff = new User( "username", "password", User.Type.STAFF );

        mvc.perform( put( String.format( "/api/v1/order/" + ticket.getName() ) )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( staff ) ) )
                .andExpect( status().is4xxClientError() );

    }

    @Test
    @Transactional
    void testPickUpOrderTicket () throws Exception {

        /**
         * Tests picking up an order with the API
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final User customer = new User( "Name1", "password", User.Type.CUSTOMER );
        userService.save( customer );
        User.setCurrentUser( customer );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, customer.getname() );
        orderService.save( ticket );

        final User staff = new User( "Name1", "password", User.Type.STAFF );
        userService.save( staff );
        User.setCurrentUser( staff );

        staff.fulfillOrderTicket( ticket );

        User.setCurrentUser( customer );

        mvc.perform( put( String.format( "/api/v1/pickupOrder/" + ticket.getName() ) )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( ticket ) ) )
                .andExpect( status().is2xxSuccessful() );

    }

    @Test
    @Transactional
    void testPickUpInvalidOrderTicket () throws Exception {

        /**
         * Tests picking up an invalid order ticket with the API
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );

        mvc.perform( put( String.format( "/api/v1/pickupOrder/" + ticket.getName() ) )
                .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( ticket ) ) )
                .andExpect( status().is4xxClientError() );

    }

    @Test
    @Transactional
    void testGetIncompleteTickets () throws Exception {

        /**
         * Tests getting the list of incomplete order tickets with the API
         */
        final Recipe r = new Recipe();
        r.setName( "recipe1" );
        r.setPrice( 5 );
        recipeService.save( r );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r, "Name1" );
        orderService.save( ticket );

        final OrderTicket ticket2 = new OrderTicket( "Ticket2", r, "Name2" );
        orderService.save( ticket2 );

        final User staff = new User( "username", "password", User.Type.STAFF );

        staff.fulfillOrderTicket( ticket );

        final String response = mvc
                .perform( get( String.format( "/api/v1/completeOrder" ) ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( false ) ) )
                .andExpect( status().is2xxSuccessful() ).andReturn().getResponse().getContentAsString();

        assertTrue( response.contains( "Ticket2" ) );

    }

    @Test
    @Transactional
    void testGetTicketRecipe () throws Exception {

        /**
         * Tests setting a new recipe for the ticket using the API
         */
        final Recipe r1 = new Recipe();
        r1.setName( "recipe1" );
        r1.setPrice( 5 );
        recipeService.save( r1 );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r1, "Name1" );
        orderService.save( ticket );

        final String recipe = mvc
                .perform( get( String.format( "/api/v1/orderRecipe/" + ticket.getName() ) )
                        .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( ticket ) ) )
                .andExpect( status().is2xxSuccessful() ).andReturn().getResponse().getContentAsString();

        assertTrue( recipe.contains( "recipe1" ) );

    }

    @Test
    @Transactional
    void testInvalidGetTicketRecipe () throws Exception {

        /**
         * Tests setting a new recipe for the ticket using the API
         */
        final Recipe r1 = new Recipe();
        r1.setName( "recipe1" );
        r1.setPrice( 5 );
        recipeService.save( r1 );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r1, "Name1" );
        orderService.save( ticket );

        mvc.perform( get( String.format( "/api/v1/orderRecipe/" + "Name" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ticket ) ) ).andExpect( status().is4xxClientError() );

    }

    @Test
    @Transactional
    void testSetTicketRecipe () throws Exception {

        /**
         * Tests setting a new recipe for the ticket using the API
         */
        final Recipe r1 = new Recipe();
        r1.setName( "recipe1" );
        r1.setPrice( 5 );
        recipeService.save( r1 );

        final Recipe r2 = new Recipe();
        r2.setName( "recipe2" );
        r2.setPrice( 7 );
        recipeService.save( r2 );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r1, "Name1" );
        orderService.save( ticket );

        final String recipe = mvc
                .perform( put( String.format( "/api/v1/orderRecipe/" + ticket.getName() ) )
                        .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( r2 ) ) )
                .andExpect( status().is2xxSuccessful() ).andReturn().getResponse().getContentAsString();

        assertTrue( recipe.contains( "recipe2" ) );

    }

    @Test
    @Transactional
    void testGetAllOrders () throws Exception {
        /**
         * Tests creating a new ticket with the API
         */
        final Recipe r1 = new Recipe();
        r1.setName( "recipe3" );
        r1.setPrice( 10 );
        recipeService.save( r1 );

        final OrderTicket ticket = new OrderTicket( "Ticket1", r1, "Name1" );

        mvc.perform( post( String.format( "/api/v1/order" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ticket ) ) ).andExpect( status().is2xxSuccessful() );

        final OrderTicket ticket2 = new OrderTicket( "Ticket2", r1, "Name2" );

        mvc.perform( post( String.format( "/api/v1/order" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( ticket2 ) ) ).andExpect( status().is2xxSuccessful() );

        mvc.perform( get( String.format( "/api/v1/order" ) ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().is2xxSuccessful() );

    }

}
