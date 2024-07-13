/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.group;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Ticket;

/**
 * CLass used to test the Group Class
 * @author Carolina Adri Lima
 * @author Lalitha Edupuganti
 *
 */
public class GroupTest {

	/**
	 * Tests getGroupName method as well as setChanged, addCategory, and getCategoryName
	 */
	@Test
	public void testGetGroupName() {
		Group group = new Group("ITECS");
		assertEquals("ITECS", group.getGroupName());
		assertTrue(group.isChanged());
		group.setChanged(false);
		assertFalse(group.isChanged());
		Category newCategory = new Category("Laptop Issues", 0);
		Category newCategory1 = new Category("Firefox Issues", 0);
		group.addCategory(newCategory1);
		group.addCategory(newCategory);
		assertEquals("Laptop Issues", group.getCurrentCategory().getCategoryName());
	}
	
	/**
	 * Tests getActiveTicketList method, the Category Constructor, Ticket constructor,
	 * addTicket method, getCurrentCategory, getTcikets, and size
	 */
	@Test
	public void testGetActiveTicketList() {
		Group group = new Group("ITECS");
		assertEquals("ITECS", group.getGroupName());
		Category newCategory1 = new Category("Firefox Issues", 0);
		Ticket ticket1 = new Ticket("Image Desktop", "New computer for student needs to be imaged", true);
		Ticket ticket2 = new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer", true);
		Ticket ticket3 = new Ticket("EBII 1025 Laptop display won't work", "The projector will not show my laptop's display in EBII 1025. Using the podium computer works fine.", true);
		group.addCategory(newCategory1);
		group.addTicket(ticket1);
		group.addTicket(ticket2);
		group.addTicket(ticket3);
		assertEquals(3, group.getCurrentCategory().getTickets().size());
		
	}
	
	/**
	 * Tests addCategory method to Group, with both valid and invalid entries.
	 */
	@Test
	public void testAddCategory() {
		Group group = new Group("ITECS");
		Category category = new Category("School Tickets", 0);
		Category category1 = new Category("Lab Tickets", 0);
		Category duplicateCategory = new Category("School Tickets", 0);
		
		group.addCategory(category);
		group.addCategory(category1);
		
		assertEquals(3, group.getCategoriesNames().length);
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> group.addCategory(duplicateCategory));
		
		assertEquals("Invalid name.", e1.getMessage());
		
	}
	
	
	/**
	 * Tests editCategory method for both invalid and valid actions
	 */
	@Test
	public void testEditCategory() {
		Group group = new Group("ITECS");
		Category category = new Category("School Tickets", 0);
		Category category1 = new Category("Lab Tickets", 0);
		
		group.addCategory(category);
		group.addCategory(category1);
		
		assertEquals(3, group.getCategoriesNames().length);
		
		group.setCurrentCategory(category.getCategoryName());
		
		
		assertThrows(IllegalArgumentException.class, () -> group.editCategory("School Tickets"));
		
		assertThrows(IllegalArgumentException.class, () -> group.editCategory("Active tickets"));

		group.editCategory("Technology Updates");
//		
		assertEquals("Technology Updates", group.getCurrentCategory().getCategoryName());
//		
		
		
	}
		
	/**
	 * Tests getCurrentCategory method for a Group with 2 categories
	 */
	@Test
	public void testGetCurrentCategory() {
		Group group = new Group("ITECS");
		Category category = new Category("School Tickets", 0);
		Category category1 = new Category("Lab Tickets", 0);
		
		group.addCategory(category);
		group.addCategory(category1);
		assertEquals("Lab Tickets", group.getCurrentCategory().getCategoryName());
	}
	
	/**
	 * Tests removeCategory method
	 */
	@Test
	public void testRemoveCategory() {
		Group group = new Group("ITECS");
		Category category = new Category("School Tickets", 0);
		Category category1 = new Category("Lab Tickets", 0);
		
		group.addCategory(category);
		group.addCategory(category1);
		
		assertEquals(3, group.getCategoriesNames().length);
		assertEquals("Lab Tickets", group.getCurrentCategory().getCategoryName());
		group.removeCategory();
		assertEquals(2, group.getCategoriesNames().length);
		assertEquals("Active Tickets", group.getCurrentCategory().getCategoryName());
	}
	
	/**
	 * Tests editTicket method
	 */
	@Test
	public void testEditTicket() {
		Group group = new Group("Computers");
		Category category = new Category("School Tickets", 0);
		
		group.addCategory(category);
		Ticket ticket1 = new Ticket("Desktop Issues", "DesktopDescription", true);
		group.addTicket(ticket1);
		Ticket ticket2 = new Ticket("Light Issues", "LightIssuesDescription", false);
		group.addTicket(ticket2);
		Ticket ticket3 = new Ticket("Heater System", "HeaterSystemDescription", true);
		group.addTicket(ticket3);
		
		group.editTicket(1, "Fan Issues", "Fan needs to be fixed", true);
		
		assertEquals("Fan Issues", group.getCurrentCategory().getTicket(1).getTicketName());
		assertEquals("Fan needs to be fixed", group.getCurrentCategory().getTicket(1).getTicketDescription());
		
		assertTrue(group.isChanged());
	}
	
	
	/**
	 * TS test replication to help debug TS tests
	 */
	@Test
	public void testTSTest() {
		Group group = new Group("TS Test");
		Category category1 = new Category("Category1", 0);
		group.addCategory(category1);
		Ticket ticket1 = new Ticket("Ticket1", "Ticket1Description", true);
		group.addTicket(ticket1);
		Ticket ticket2 = new Ticket("Ticket2", "Ticket2Description", false);
		group.addTicket(ticket2);
		Ticket ticket3 = new Ticket("Ticket3", "Ticket3Description", true);
		group.addTicket(ticket3);
		Category category2 = new Category("ACategory", 0);
		group.addCategory(category2);
		Ticket ticket4 = new Ticket("Ticket4", "Ticket41Description", true);
		group.addTicket(ticket4);
		Ticket ticket5 = new Ticket("Ticket5", "Ticket5Description", false);
		group.addTicket(ticket5);
		assertEquals(2, group.getCurrentCategory().getTicketsAsArray().length);
		assertEquals("Active Tickets", group.getCategoriesNames()[0]);
		group.setCurrentCategory("Active Tickets");
		assertEquals(3, group.getCurrentCategory().getTicketsAsArray().length);

	}
}
