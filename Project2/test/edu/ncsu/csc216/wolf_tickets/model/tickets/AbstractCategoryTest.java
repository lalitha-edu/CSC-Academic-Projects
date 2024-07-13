package edu.ncsu.csc216.wolf_tickets.model.tickets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class that tests the AbstractCategory class
 * @author Carolina Adri Lima
 *
 */
class AbstractCategoryTest {

	/**
	 * Tests the constructor for the AbstractCategory class and getCompletedCount
	 *
	 */
	@Test
	void testAbstractCategory() {
		AbstractCategory category = new Category("Web", 5);
		assertEquals("Web", category.getCategoryName());
		assertEquals(5, category.getCompletedCount());


	}

	/**
	 * Tests the getCategoryName method, addTicket, getCategoryName, removeTicket, and completeTicket
	 *
	 */
	@Test
	void testTicketCategory() {
		AbstractCategory category = new Category("Web", 5);
		Ticket ticket1 = new Ticket("Image Desktop", "New computer for student needs to be imaged", true);
		Ticket ticket2 = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		category.addTicket(ticket1);
		assertEquals("Web", ticket1.getCategoryName());
		category.addTicket(ticket2);
		assertEquals(ticket1, category.removeTicket(0));
		assertEquals(ticket2, category.getTicket(0));
		category.completeTicket(ticket2);
		assertEquals(6, category.getCompletedCount());

	}

}
