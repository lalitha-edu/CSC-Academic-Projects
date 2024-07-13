/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.io;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tickets.model.group.Group;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Ticket;
import edu.ncsu.csc216.wolf_tickets.model.util.ISortedList;
import edu.ncsu.csc216.wolf_tickets.model.util.SortedList;

/**
 * Class used to test the GroupWriter class
 * @author Carolina Adri Lima
 *
 */
public class GroupWriterTest {
	
	/**
	 * Test method for writeGroupToFile method
	 * Checks to see if the Group were written to the file, using arraylist
	 */
	@Test
	void testWriteGroupToFile() {
		
			File file = new File("test-files/actual_group1.txt");
		
			Group newAddingGroup = new Group("CSC IT");
	
			ISortedList<Category> categories = new SortedList<Category>();
			
			categories.add(new Category("Web", 5));
			categories.add(new Category("Classroom Tech", 10));
			categories.add(new Category("Desktop", 17));
			
			
			categories.get(0).addTicket(new Ticket("EBII 1025 Laptop display won't work", "The projector will not show my laptop's display in EBII 1025."
					+ " Using the podium computer works fine. My laptop shows the extra display, but I only see a black screen on the classroom screen.", true));
			categories.get(0).addTicket(new Ticket("EBII 1010 Podium monitor won't turn on.", "The monitor for the podium computer in EBII won't turn on.", true));
			categories.get(0).addTicket(new Ticket("EBII 1025 Replace lights", "Lighting panel 5 in EBII 1025 will need to be replaced soon.", false));
			categories.get(0).addTicket(new Ticket("LMP 200 update Firefox", "The computers in LMP 200 will need Firefox to be updated over the summer.", false));
			
			categories.get(1).addTicket(new Ticket("Dr. McLeod's computer won't charge.", "The laptop provided to me won't charge when I plug in the charger."
					+ " The charger works for other laptops though.", true));
			categories.get(1).addTicket(new Ticket("Microphone not detected through docking station.", "My provided laptop won't detect my microphone when "
					+ "the mic is plugged into the docking station. It works well when I plug it directly into the laptop though.", true));
			categories.get(2).addTicket(new Ticket("Dr. McLeod website pages won't update.", "I recently uploaded new versions of pages on my website, "
					+ "but the changes don't show up when I go to the URL.", true));
			
			assertEquals(3, categories.size());
			
			GroupWriter.writeGroupFile(file, newAddingGroup.getGroupName(), categories);
			
			
			checkFiles("test-files/expected_group1.txt", "test-files/actual_group1.txt");
		
		
	}
	
	
	/**
	 * Test method to try to write to an invalid file path
	 */
	@Test
	void testWriteToInvalidFile() {
		File file = new File("");
		Group newAddingGroup = new Group("CSC IT");
		
		ISortedList<Category> categories = new SortedList<Category>();
		
		categories.add(new Category("Web", 5));
		categories.add(new Category("Classroom Tech", 10));
		categories.add(new Category("Desktop", 17));
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> GroupWriter.writeGroupFile(file, newAddingGroup.getGroupName(), categories));
		assertEquals("Unable to save file.", e1.getMessage());
	}
	
	
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	
	
	
}
