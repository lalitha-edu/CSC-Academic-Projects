package edu.ncsu.csc216.wolf_tickets.model.tickets;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class that tests the ActiveTicketList class methods
 * @author Lalitha Edupuganti
 *
 */
class ActiveTicketListTest {

	/**
	 * Tests the setCategoryName method, throws IAE since ActiveTickets can't be edited
	 *
	 */
	@Test
	void testSetCategoryName() {
		ActiveTicketList newActiveList = new ActiveTicketList();
		
		assertThrows(IllegalArgumentException.class, () -> newActiveList.setCategoryName("Desktop Issues"));
	}

	/**
	 * Tests the addTicket method, addTicket, for valid (active) and invalid (not active) tickets
	 *
	 */
	@Test
	void testAddTicket() {
		ActiveTicketList newActiveList = new ActiveTicketList();
		Ticket ticket1 = new Ticket("Image Desktop", "New computer for student needs to be imaged", true);
		Ticket ticket2 = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		Ticket ticket3 = new Ticket("EBII 1025 Laptop display won't work", "The projector will not show my laptop's display in EBII 1025. Using the podium computer works fine.", true);
		
		newActiveList.addTicket(ticket1);
		newActiveList.addTicket(ticket3);
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> newActiveList.addTicket(ticket2));
		
		assertEquals("Cannot add ticket to Active Tickets.", e1.getMessage());
	}

	/**
	 * Tests the getTicketsAsArray method for 2 categories, checking that tickets and categories
	 * are being added correctly.
	 *
	 */
	@Test
	void testGetTicketsAsArray() {
		ActiveTicketList newActiveList = new ActiveTicketList();
		
		newActiveList.setCategoryName("Active Tickets");
		
		Category newCategory = new Category("Laptop Issues", 0);
		Category newCategory1 = new Category("Firefox Issues", 0);
		
		Ticket ticket1 = new Ticket("Image Desktop", "New computer for student needs to be imaged", true);
		Ticket ticket2 = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", true);
		Ticket ticket3 = new Ticket("EBII 1025 Laptop display won't work", "The projector will not show my laptop's display in EBII 1025. Using the podium computer works fine.", true);
		

		
		newCategory.addTicket(ticket1);
		newCategory1.addTicket(ticket2);
		
		newCategory.addTicket(ticket3);
		
		newActiveList.addTicket(ticket1);
		newActiveList.addTicket(ticket2);
		newActiveList.addTicket(ticket3);
		
		String [][] ticketArray = newActiveList.getTicketsAsArray();
		
		assertEquals("Firefox Issues", ticketArray[1][0]);
		
		assertEquals("Laptop Issues", ticketArray[0][0]);
		
		assertEquals("Laptop Issues", ticketArray[2][0]);
		
		assertEquals("EBII 1025 Laptop display won't work", ticketArray[2][1]);
		
		assertEquals("Image Desktop", ticketArray[0][1]);
		
		assertEquals("LMP 200 update Firefox", ticketArray[1][1]);
		
	}

	/**
	 * Tests the clear tickets method, after adding tickets making sure that it clears it out.
	 *
	 */
	@Test
	void testClearTickets() {
		ActiveTicketList newActiveList = new ActiveTicketList();
		
		newActiveList.setCategoryName("Active Tickets");
		
		Category newCategory = new Category("Laptop Issues", 0);
		Category newCategory1 = new Category("Firefox Issues", 0);
		
		Ticket ticket1 = new Ticket("Image Desktop", "New computer for student needs to be imaged", true);
		Ticket ticket2 = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", true);
		Ticket ticket3 = new Ticket("EBII 1025 Laptop display won't work", "The projector will not show my laptop's display in EBII 1025. Using the podium computer works fine.", true);
		
		newCategory.addTicket(ticket1);
		newCategory1.addTicket(ticket2);
		newCategory.addTicket(ticket3);
		newActiveList.addTicket(ticket1);
		newActiveList.addTicket(ticket2);
		newActiveList.addTicket(ticket3);

		assertEquals(2, newCategory.getTicketsAsArray().length);
		assertEquals(3, newActiveList.getTicketsAsArray().length);
		
		newActiveList.clearTickets(); 
		
		assertEquals(0, newActiveList.getTicketsAsArray().length); //fix this later
	}
	
	/**
	 * Tests to help debug the TS tests that are failing on Jenkins
	 */
	@Test
	public void testTSTests() {
		ActiveTicketList newActiveList = new ActiveTicketList();
		Ticket ticket1 = new Ticket("Ticket 1", "Ticket 1 Description", false);
		assertThrows(IllegalArgumentException.class, () -> newActiveList.addTicket(ticket1));
		Ticket ticket2 = new Ticket("Ticket 2", "Ticket 2 Description", false);
		assertThrows(IllegalArgumentException.class, () -> newActiveList.addTicket(ticket2));
		Ticket ticket3 = new Ticket("Ticket 3", "Ticket 3 Description", true);
		newActiveList.addTicket(ticket3);
		assertEquals("Active Tickets", newActiveList.getTicketsAsArray()[0][0]);
	}

	/**
	 * Tests the TS complete tickets method to help debug code
	 *
	 */
	@Test
	void testCompleteTickets() {
		ActiveTicketList newActiveList = new ActiveTicketList();
		
		Ticket ticket1 = new Ticket("Ticket 1", "Ticket 1 Description", true);
		Ticket ticket2 = new Ticket("Ticket 2", "Ticket 2 Description", true);
		Ticket ticket3 = new Ticket("Ticket 3", "Ticket 3 Description", true);
		Ticket ticket4 = new Ticket("Ticket 4", "Ticket 4 Description", true);
		Ticket ticket5 = new Ticket("Ticket 5", "Ticket 5 Description", true);
		
		newActiveList.addTicket(ticket1);
		newActiveList.addTicket(ticket2);
		newActiveList.addTicket(ticket3);
		newActiveList.addTicket(ticket4);
		newActiveList.addTicket(ticket5);
		
		assertEquals(5, newActiveList.getTickets().size());

		String [][] newArray = newActiveList.getTicketsAsArray();
		
		assertEquals("Active Tickets", newArray[0][0]);
		
		
		
	}

}
