/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.manager.Position;


/**
 * Tests for PositionWriter Class
 * @author Lalitha Edupuganti
 *
 */
class PositionWriterTest {

	/**
	 * Test method for writePositionsToFile method
	 * Checks to see if the positions were written to the file, using arraylist
	 */
	@Test
	void testWritePositionsToFile() {
	
			ArrayList<Position> positions = new ArrayList<Position>();
			positions.add(new Position("CSC 216 PTF", 12, 15));
			
			positions.get(0).addApplication(new Application(2, "Submitted", "Carol", "Schmidt", "cschmid", "", ""));
			positions.get(0).addApplication(new Application(3, "Rejected", "Kathleen", "Gillespie", "kgilles", "", "Incomplete"));
			positions.get(0).addApplication(new Application(4, "Hired", "Fiona", "Rosario", "frosari", "sesmith5", ""));
			positions.get(0).addApplication(new Application(7, "Inactive", "Deanna", "Sanders", "dsander", "sesmith5", "Completed"));
			positions.get(0).addApplication(new Application(8, "Interviewing", "Benjamin", "Nieves", "bmnieves", "sesmith5", ""));
			positions.get(0).addApplication(new Application(11, "Processing", "Quemby", "Mullen", "qmullen", "sesmith5", ""));
			
			assertEquals(1, positions.size());
			
			PositionWriter.writePositionsToFile("test-files/actual_position_list.txt", positions);
			
			
			checkFiles("test-files/positions1.txt", "test-files/actual_position_list.txt");
		
		
	}
	
	/**
	 * Test method for writePositionsToFile method
	 * Checks to see if the MULTIPLE positions were written to the file
	 */
	@Test
	void testWriteMultiplePositions() {
	
			ArrayList<Position> positions = new ArrayList<Position>();
			positions.add(new Position("Position A", 18, 20));
			positions.add(new Position("Position B", 10, 12));
			positions.add(new Position("Position C", 12, 10));
			positions.add(new Position("Position D", 11, 11));
			
			positions.get(0).addApplication(new Application(2, "Submitted", "Carol", "Schmidt", "cschmid", "", ""));
			positions.get(0).addApplication(new Application(4, "Hired", "Fiona", "Rosario", "frosari", "sesmith5", ""));
			
			positions.get(1).addApplication(new Application(7, "Inactive", "Deanna", "Sanders", "dsander", "tmbarnes", "Completed"));
			positions.get(1).addApplication(new Application(8, "Interviewing", "Benjamin", "Nieves", "bmnieves", "sesmith5", ""));
			positions.get(1).addApplication(new Application(11, "Processing", "Quemby", "Mullen", "qmullen", "sesmith5", ""));
			
			positions.get(3).addApplication(new Application(3, "Rejected", "Kathleen", "Gillespie", "kgilles", "", "Incomplete"));
			
			
			assertEquals(4, positions.size());
			assertEquals(2, positions.get(0).getApplications().size());
			assertEquals(3, positions.get(1).getApplications().size());
			assertEquals(0, positions.get(2).getApplications().size());
			assertEquals(1, positions.get(3).getApplications().size());
			
			PositionWriter.writePositionsToFile("test-files/actual_positions.txt", positions);
			
			
			checkFiles("test-files/expected_positions.txt", "test-files/actual_positions.txt");
		
		
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
