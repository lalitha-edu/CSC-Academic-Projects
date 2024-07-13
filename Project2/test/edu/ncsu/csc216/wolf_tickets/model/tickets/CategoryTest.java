package edu.ncsu.csc216.wolf_tickets.model.tickets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class that test the Category Class
 * @author Lalitha Edupuganti
 *
 */
class CategoryTest {

	/**
	 * Tests Category constructor to construct a category object,
	 * checks that the new category has the correct assigned name
	 *
	 */
	@Test
	void testCategory() {
		Category newCategory = new Category("School", 0);
		
		assertEquals("School", newCategory.getCategoryName());
	}
	
	/**
	 * Tests getTicketsAsArray method
	 * Tests the creation of a new category, adding two tickeets
	 * Checks if the tickets are in order of index, and checks if the contents
	 * of the array are as expected
	 *
	 */
	@Test
	void testGetTicketsAsArray() {
		Category newCategory = new Category("School", 0);
		Ticket ticket1 = new Ticket("Image Desktop", "New computer for student needs to be imaged", true);
		Ticket ticket2 = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", false);
		
		newCategory.addTicket(ticket1);
		newCategory.addTicket(ticket2);
		
		String [][] ticketArray = newCategory.getTicketsAsArray();
		
		assertEquals("0", ticketArray[0][0]);
		
		assertEquals("1", ticketArray[1][0]);
		
		assertEquals("Image Desktop", ticketArray[0][1]);
		
		assertEquals("LMP 200 update Firefox", ticketArray[1][1]);
		
	}

	/**
	 * Tests the compareTo method, tests for category names, that are
	 * the same which should return a value of zero, while a category name
	 * that is alphabetically later should return 1, and a category name that is alphabetically
	 * earlier should return -1
	 *
	 */
	@Test
	void testCompareTo() {
		Category newCategory = new Category("School", 0);
		Category compareCategory = new Category("School", 0);
		Category compareCategory1 = new Category("Active", 0);
		
		assertEquals(0, newCategory.compareTo(compareCategory));
		
		assertEquals(1, newCategory.compareTo(compareCategory1));
		
		assertEquals(-1, compareCategory1.compareTo(compareCategory));
	}

}
