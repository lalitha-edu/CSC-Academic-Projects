/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.tickets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class used to test the Ticket class
 * @author Carolina Adri Lima
 *
 */
public class TicketTest {

	/**
	 * Tests getTicketName method
	 */
	@Test
	public void testGetTicketName() {
		Ticket ticket = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		assertEquals("LMP 200 update Firefox", ticket.getTicketName());
	}
	
	/**
	 * Tests getTicketDescription method
	 */
	@Test
	public void testGetTickeDescription() {
		Ticket ticket = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		assertEquals("The computers in LMP 200 will need Firefox to be updated over the summer", ticket.getTicketDescription());
	}
	
	/**
	 * Tests getCategoryName method
	 */
	@Test
	public void testGetCategoryName() {
		Ticket ticket = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		assertEquals("", ticket.getCategoryName());
	}
	
	/**
	 * Tests isActive and setActive methods
	 */
	@Test
	public void testIsActive() {
		Ticket ticket = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		assertFalse(ticket.isActive());
		ticket.setActive(true);
		assertTrue(ticket.isActive());
	}
	
	/**
	 * Testing Category methods under the Ticket class
	 */
	@Test
	public void testCategoryTicket() {
		AbstractCategory category = new Category("Web", 5);
		Ticket ticket = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		ticket.addCategory(category);
		assertEquals(category.getCategoryName(), ticket.getCategoryName());
		assertThrows(IllegalArgumentException.class, () -> ticket.addCategory(null));
	}
	
	/**
	 * Testing completeTicket method under the Ticket class
	 */
	@Test
	public void testCompleteTicket() {
		AbstractCategory category = new Category("Web", 5);
		Ticket ticket = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		Ticket ticket1 = new Ticket("Image Desktop", "New computer for student needs to be imaged", true);
		ticket.addCategory(category);
		category.addTicket(ticket1);
		
		
		ticket1.completeTicket();
		
		assertEquals(6, category.getCompletedCount());
	}
	
	/**
	 * Tests the toString method
	 */
	@Test
	public void testToString() {
		Ticket ticket = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer.", false);
		String expected1 = "* LMP 200 update Firefox\n"
				+ "The computers in LMP 200 will need Firefox to be updated over the summer.";
		assertEquals(expected1, ticket.toString());
		
		Ticket ticket2 = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer.", true);
		String expected2 = "* LMP 200 update Firefox,active\n"
				+ "The computers in LMP 200 will need Firefox to be updated over the summer.";
		assertEquals(expected2, ticket2.toString());
		
	}
	
	/**
	 * Tests invalid calls for the Ticket class
	 */
	@Test
	public void testInvalidTicket() {
		assertThrows(IllegalArgumentException.class, () -> 	new Ticket("", "", true));
		assertThrows(IllegalArgumentException.class, () -> new Ticket("Invalid ticket", null, false));

	}
}
