/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.io;


import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;


import edu.ncsu.csc216.wolf_hire.model.manager.Position;



/**
 * Tests for PositionReader Class
 * @author Lalitha Edupuganti
 *
 */
class PositionReaderTest {

		/** Valid test file with positons */
		private final String validTestFile = "test-files/positions1.txt";
		
		/**
		 * Tests readPositionFile() to read position from file and ensure it has been added to arraylist.
		 */
		@Test
		public void testReadPositionFile() {
		
		ArrayList<Position> positions = PositionReader.readPositionFile(validTestFile);
		assertEquals(1, positions.size());

		
		}
		
		
		/**
		 * Tests readPositionFile() to read position from file and ensure it has been added to arraylist.
		 */
		@Test
		public void testReadPositionFile1() {
		
		ArrayList<Position> positions = PositionReader.readPositionFile("test-files/expected_positions.txt");
		assertEquals(3, positions.size());

		
		}
		
		/**
		 * Tests readPositionFile() to read position from file and ensure it has been added to arraylist.
		 */
		@Test
		public void testReadPositionFile2() {
		
		ArrayList<Position> positions = PositionReader.readPositionFile("test-files/positions2.txt");
		assertEquals(4, positions.size());
		

		
		}
		
		
		/**
		 * Tests readPositionFile() to read position from file and ensure it has been added to arraylist.
		 */
		@Test
		public void testReadPositionFile6() {
		
		ArrayList<Position> positions = PositionReader.readPositionFile("test-files/positions6.txt");
		assertEquals(0, positions.size());
		

		
		}
		
		
		

}
