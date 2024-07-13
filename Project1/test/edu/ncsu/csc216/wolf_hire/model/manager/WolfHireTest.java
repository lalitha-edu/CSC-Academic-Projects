/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.command.Command;


/**
 * Tests for WolfHire Class
 * @author Lalitha Edupuganti
 *
 */
class WolfHireTest {


	
	/**
	 * Test method for adding new position, testing invalid situations
	 */
	@Test
	void testAddNewPositionInvalids() {
		WolfHire newWolfHire = WolfHire.getInstance();
		
		//invalid tests
		Exception e1 = assertThrows(IllegalArgumentException.class,
			() -> newWolfHire.addNewPosition(null, 10, 30));
		assertEquals("Position cannot be created.", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> newWolfHire.addNewPosition("", 10, 30));
		assertEquals("Position cannot be created.", e2.getMessage());
		
		newWolfHire.addNewPosition("CSC 216 PTA", 12, 15);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> newWolfHire.addNewPosition("CSC 216 PTA", 18, 20));
			assertEquals("Position cannot be created.", e3.getMessage());
		
		
	}
	
	
	/**
	 * Test method to test getApplication by Id for an active position
	 */
	@Test
	void testGetApplicationById() {
		WolfHire newWolfHire = WolfHire.getInstance();
		newWolfHire.loadPositionsFromFile("test-files/positions1.txt");	
		
		
		assertEquals(8, newWolfHire.getApplicationById(8).getId());
		assertEquals("Interviewing", newWolfHire.getApplicationById(8).getState());
		
	}
	
	/**
	 * Test method to test delete application by Id for an active position
	 */
	@Test
	void testDeleteApplicationById() {
		WolfHire newWolfHire = WolfHire.getInstance();
		newWolfHire.loadPositionsFromFile("test-files/positions1.txt");	
		
		newWolfHire.deleteApplicationById(4);
		
		assertEquals(5, newWolfHire.getActivePosition().getApplications().size());

		
	}
	
	/**
	 * Test method to test execute command by Id for an active position
	 */
	@Test
	void testExecuteCommand() {
		WolfHire newWolfHire = WolfHire.getInstance();
		newWolfHire.loadPositionsFromFile("test-files/positions1.txt");	
		
		Command command1 = new Command (Command.CommandValue.REJECT, "Incomplete");
		
		newWolfHire.executeCommand(2, command1);
		
		assertEquals("Rejected", newWolfHire.getActivePosition().getApplicationById(2).getState());

		
	}
	

	/**
	 * Test method to test Saving positions to a file
	 */
	@Test
	void testSavePositionsToFile() {
		WolfHire newWolfHire = WolfHire.getInstance();
		
		newWolfHire.loadPositionsFromFile("test-files/positions1.txt");	
		
		newWolfHire.savePositionsToFile("test-files/actual_position_list.txt");
		newWolfHire.addNewPosition("CSC 216 TA", 15, 20);
		newWolfHire.addNewPosition("MA 305 PTF", 12, 30);
		assertEquals(3, newWolfHire.getPositionList().length);
		
	}

	/**
	 * Test method for getApplicationsAsArray method, moves application array into a specific 
	 * 2D array format
	 */
	@Test
	void testGetApplicationsAsArray() {
		WolfHire newWolfHire = WolfHire.getInstance();
		
		
		String[][] applicationInfo = {{"2", "Submitted", "cschmid", ""}};
		
		
		newWolfHire.loadPositionsFromFile("test-files/positions1.txt");
		
		String [][] applicationsAsArray =  newWolfHire.getApplicationsAsArray("Submitted");
		
		assertEquals(applicationInfo.length, applicationsAsArray.length);
		
	
		
		for (int i = 0; i < applicationInfo.length; i++) {
			assertEquals(applicationInfo[i].length, applicationsAsArray[i].length);
			for(int j = 0; j < applicationInfo.length; j++) {
					assertEquals(applicationInfo[i][j], applicationsAsArray[i][j]);

			}
		}
		
	}
	
	/**
	 * Test method to test AddApplicationToPosition method
	 */
	@Test
	void testAddApplicationToPosition() {
		WolfHire newWolfHire = WolfHire.getInstance();
		newWolfHire.loadPositionsFromFile("test-files/positions1.txt");
		
		newWolfHire.addApplicationToPosition("John", "Stein", "jstein");
		ArrayList<Application> newApplications = newWolfHire.getActivePosition().getApplications();
		assertEquals("John", newApplications.get(6).getFirstName());
		
	}
	

}
