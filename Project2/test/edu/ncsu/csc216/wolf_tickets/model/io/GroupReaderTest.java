/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.io;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tickets.model.group.Group;

/**
 * Class used to test the GroupReader class
 * @author Carolina Adri Lima
 * @author Lalitha Edupuganti
 *
 */
public class GroupReaderTest {
	
	/** Valid test file with group */
	File file = new File("test-files/group1.txt");
	
	/** Invalid test file with group */
	File invalidFile = new File("test-files/group7.txt");

	/**
	 * Tests readGroupFile method for valid files, making sure it is reading them correctly.
	 */
	@Test
	public void testReadGroupFile() {
		
		Group newGroup = GroupReader.readGroupFile(file);
		
		String [] categoryNames = {"Active Tickets", "Classroom Tech", "Desktop", "Web"}; 
		String [] actualCategoryNames = newGroup.getCategoriesNames();
		
		assertEquals("CSC IT", newGroup.getGroupName());
		
		assertEquals(categoryNames[0], actualCategoryNames[0]);
		assertEquals(categoryNames[1], actualCategoryNames[1]);
		assertEquals(categoryNames[2], actualCategoryNames[2]);
		assertEquals(categoryNames[3], actualCategoryNames[3]);
		
		assertEquals(5, newGroup.getCurrentCategory().getTickets().size());
		
		newGroup.setCurrentCategory("Classroom Tech");
		assertEquals(4, newGroup.getCurrentCategory().getTickets().size());
		
		newGroup.setCurrentCategory("Desktop");
		assertEquals(2, newGroup.getCurrentCategory().getTickets().size());
		
		newGroup.setCurrentCategory("Web");
		assertEquals(1, newGroup.getCurrentCategory().getTickets().size());
		
//		newGroup.setCurrentCategory("Active Tickets");
		
		
	}
	
	
	/**
	 * Tests readGroupFile method for InvalidFile Group7.txt
	 * Note: Use of split was referenced from a suggestion in a piazza post
	 * found during debugging
	 */
	@Test
	public void testInvalidReadGroupFile() {
		Group newGroup = GroupReader.readGroupFile(invalidFile);
		
		assertEquals("OIT", newGroup.getGroupName());
		
		assertEquals(2, newGroup.getCategoriesNames().length);
		String [] categoryNames = {"Active Tickets", "License Renewals", "Desktop", "Web"}; 
		String [] actualCategoryNames = newGroup.getCategoriesNames();
		
		assertEquals(categoryNames[0], actualCategoryNames[0]);
		assertEquals(categoryNames[1], actualCategoryNames[1]);
		
		assertEquals(2, actualCategoryNames.length);
		
		
		assertEquals("Active Tickets", newGroup.getCurrentCategory().getCategoryName());
		
		assertEquals(1, newGroup.getCurrentCategory().getTickets().size());
		
		newGroup.setCurrentCategory("License Renewals");
		
		assertEquals("License Renewals", newGroup.getCurrentCategory().getCategoryName());
		
		assertEquals(1, newGroup.getCurrentCategory().getTickets().size());
		
		
		
	}
	
}
