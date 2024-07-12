/**
 *
 */
package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.OrderTicket;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.models.User;
import edu.ncsu.csc.CoffeeMaker.services.OrderTicketService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
import edu.ncsu.csc.CoffeeMaker.services.UserService;

/**
 * JUnit testing for User
 */
@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
class UserTest {

    /** User Service to test */
    @Autowired
    private UserService        userService;

    @Autowired
    private OrderTicketService orderTicketService;

    private Recipe             r;

    @Autowired
    private RecipeService      recipeService;

    @BeforeEach
    public void setUp () {
        recipeService.deleteAll();
        userService.deleteAll();
        orderTicketService.deleteAll();
    }

    @Test
    @Transactional
    void testUser () {
        final User u1 = new User( "smoore", "jomama12", User.Type.CUSTOMER );
        final User u2 = new User( "wamma", "shlamma", User.Type.STAFF );

        userService.save( u1 );

        assertEquals( 1, userService.count() );
        assertNotNull( userService.findById( u1.getId() ) );

        userService.save( u2 );

        assertEquals( 2, userService.count() );
        assertNotNull( userService.findById( u2.getId() ) );

        assertEquals( "smoore", userService.findById( u1.getId() ).getname() );
        assertEquals( "jomama12".hashCode(), userService.findById( u1.getId() ).getPassword() );
        assertEquals( User.Type.CUSTOMER, userService.findById( u1.getId() ).getType() );

        assertEquals( "wamma", userService.findById( u2.getId() ).getname() );
        assertEquals( "shlamma".hashCode(), userService.findById( u2.getId() ).getPassword() );
        assertEquals( User.Type.STAFF, userService.findById( u2.getId() ).getType() );

    }

    @Test
    @Transactional
    void testCurrentUser () {
        final User u1 = new User( "smoore", "jomama12", User.Type.CUSTOMER );

        userService.save( u1 );

        User.setCurrentUser( u1 );

        assertEquals( "smoore", User.getCurrentUser().getname() );
        assertEquals( "jomama12".hashCode(), User.getCurrentUser().getPassword() );
        assertEquals( User.Type.CUSTOMER, User.getCurrentUser().getType() );

    }

    @Test
    @Transactional
    void testToString () {
        final User tester = new User( "smoore", "jomama12", User.Type.CUSTOMER );

        assertEquals( "smoore", tester.getname() );
        assertEquals( "jomama12".hashCode(), tester.getPassword() );
        assertEquals( User.Type.CUSTOMER, tester.getType() );

        userService.save( tester );

        assertEquals( "User [name=smoore, password=jomama12, type=CUSTOMER]", tester.toString() );
    }

    @Test
    @Transactional
    void testHashcodeEquals () {
        final User u1 = new User( "smoore", "jomama12", User.Type.CUSTOMER );
        final User u2 = new User( "wamma", "shlamma", User.Type.STAFF );
        final User u3 = new User( "smoore", "diffpass", User.Type.STAFF );
        final User u4 = new User( "bunger", "jomama12", User.Type.CUSTOMER );

        assertEquals( u1, u1 );
        assertEquals( u1, u3 );
        assertNotEquals( u1, u2 );
        assertNotEquals( u1, u4 );
        assertNotEquals( u1, 4 );
        assertNotEquals( u1, null );

        assertEquals( u1.hashCode(), u3.hashCode() );
        assertNotEquals( u1.hashCode(), u4.hashCode() );
    }

    @Test
    @Transactional
    void testOrderTicketUserFlow () {
        r = new Recipe();
        final Ingredient i1 = new Ingredient( "Coffee", 5 );
        final Ingredient i2 = new Ingredient( "Milk", 10 );
        r.addIngredient( i1 );
        r.addIngredient( i2 );
        r.setPrice( 10 );
        r.setName( "Mocha" );

        recipeService.save( r );

        final User customer = new User( "smoore", "jomama12", User.Type.CUSTOMER );
        final OrderTicket ot = customer.createOrderTicket( r, "Mocha Order" );
        orderTicketService.save( ot );

        assertEquals( customer.getname(), ot.getOwner() );
        assertEquals( r, ot.getRecipe() );

        assertThrows( Exception.class, () -> customer.fulfillOrderTicket( ot ) );

        final User staff = new User( "joe", "smooremama12", User.Type.STAFF );

        assertFalse( customer.pickupOrderTicket( ot ) );
        assertFalse( staff.pickupOrderTicket( ot ) );

        assertFalse( ot.isPickedUp() );

        staff.fulfillOrderTicket( ot );

        assertTrue( ot.isFulfilled() );
        assertEquals( staff.getname(), ot.getFulfilledBy() );

        assertFalse( staff.pickupOrderTicket( ot ) );

        customer.pickupOrderTicket( ot );

        assertTrue( ot.isPickedUp() );

    }

}
