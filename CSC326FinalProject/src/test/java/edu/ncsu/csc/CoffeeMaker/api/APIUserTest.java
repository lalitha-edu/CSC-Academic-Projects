package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
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
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

@ExtendWith ( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc

public class APIUserTest {

    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService           userService;

    /**
     * Sets up the tests.
     */
    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        userService.deleteAll();
        User.setCurrentUser( null );
    }

    @Test
    @Transactional
    void testCreateCustomer () throws Exception {

        /**
         * Tests creating a new user with the API, specifically a customer.
         */

        final User user = new User( "username", "password", User.Type.CUSTOMER );

        mvc.perform( post( String.format( "/api/v1/users" ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( user ) ) ).andExpect( status().is2xxSuccessful() );

    }

    @Test
    @Transactional
    void testInvalidCreateStaff () throws Exception {

        /**
         * Tests creating two new Staff users with the API who have the same
         * username. This should result in an error.
         */

        final User staff1 = new User( "staff1", "pass1", User.Type.STAFF );

        userService.save( staff1 );

        final User staff2 = new User( "staff1", "pass2", User.Type.STAFF );

        final String err = mvc
                .perform( post( String.format( "/api/v1/users" ) ).contentType( MediaType.APPLICATION_JSON )
                        .content( TestUtils.asJsonString( staff2 ) ) )
                .andExpect( status().is4xxClientError() ).andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( err.contains( "Username: " + staff2.getname() + " already exists." ) );

    }

    @Test
    @Transactional
    void testLogin () throws Exception {

        /**
         * Tests logging in as a user with the API.
         */

        final User user = new User( "username", "password", User.Type.CUSTOMER );

        userService.save( user );

        mvc.perform( put( String.format( "/api/v1/users/" + user.getname() ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( user ) ) ).andExpect( status().is2xxSuccessful() );

        final User admin = new User();

        final String type = mvc
                .perform( put( String.format( "/api/v1/users/" + admin.getname() ) )
                        .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( admin ) ) )
                .andExpect( status().is2xxSuccessful() ).andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( type.contains( type ) );

        Assertions.assertNotNull( userService.findByName( "admin" ) );

    }

    @Test
    @Transactional
    void testInvalidUserLogin () throws Exception {

        /**
         * Tests logging in as an invalid user with the API. This should result
         * in an error because the username does not exist.
         */

        final User user = new User( "username", "password", User.Type.CUSTOMER );

        userService.save( user );

        final String invalidUsername = "username1";

        final User dummy = new User( invalidUsername, "password1", User.Type.CUSTOMER );

        final String err = mvc
                .perform( put( String.format( "/api/v1/users/" + invalidUsername ) )
                        .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( dummy ) ) )
                .andExpect( status().is4xxClientError() ).andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( err.contains( invalidUsername + " does not match a known account" ) );

    }

    @Test
    @Transactional
    void testInvalidPasswordLogin () throws Exception {

        /**
         * Tests logging in with an incorrect password with the API. This should
         * result in an error because the password does not match the username.
         */

        final User user = new User( "username", "password", User.Type.CUSTOMER );

        userService.save( user );

        final User dummy = new User( "username", "password1", User.Type.CUSTOMER );

        final String err = mvc
                .perform( put( String.format( "/api/v1/users/" + user.getname() ) )
                        .contentType( MediaType.APPLICATION_JSON ).content( TestUtils.asJsonString( dummy ) ) )
                .andExpect( status().is4xxClientError() ).andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( err.contains( "Password does not match the username" ) );

    }

    @Test
    @Transactional
    void testGetCurrentUser () throws Exception {
        String returned = mvc.perform( get( String.format( "/api/v1/users/" ) ) )
                .andExpect( status().is4xxClientError() ).andReturn().getResponse().getContentAsString();

        final User user = new User( "jo", "swama", User.Type.CUSTOMER );

        userService.save( user );
        User.setCurrentUser( user );

        returned = mvc.perform( get( String.format( "/api/v1/users/" ) ) ).andExpect( status().is2xxSuccessful() )
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue( returned.contains( "CUSTOMER" ) );
    }

    @Test
    @Transactional
    void testLogoutCurrentUser () throws Exception {
        mvc.perform( put( String.format( "/api/v1/users" ) ) ).andExpect( status().is4xxClientError() );

        final User user = new User( "username", "password", User.Type.CUSTOMER );

        userService.save( user );

        mvc.perform( put( String.format( "/api/v1/users/" + user.getname() ) ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( user ) ) ).andExpect( status().is2xxSuccessful() );

        mvc.perform( put( String.format( "/api/v1/users" ) ) ).andExpect( status().is2xxSuccessful() );

        assertNull( User.getCurrentUser() );
    }

}
