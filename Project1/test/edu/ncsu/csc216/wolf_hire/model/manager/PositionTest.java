/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.manager;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.application.Application;


/**
 * Tests for Position Class
 * @author Lalitha Edupuganti
 *
 */
class PositionTest {

	/**
	 * Test method for testing invalid values for field values of a position object
	 * Tests boundary values, null and empty values
	 */
	@Test
	void testPositionInvalidParameters() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Position(null, 15, 20));
		assertEquals("Position cannot be created.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Position("", 15, 20));
		assertEquals("Position cannot be created.", e2.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Position("CSC 216 - TA", 4, 20));
		assertEquals("Position cannot be created.", e3.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Position("CSC 216 - TA", 21, 20));
		assertEquals("Position cannot be created.", e4.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Position("CSC 216 - TA", 10, 6));
		assertEquals("Position cannot be created.", e5.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Position("CSC 216 - TA", 10, 36));
		assertEquals("Position cannot be created.", e6.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
	}

	
	/**
	 * Test method for position constructor, tests for assertEquals field values
	 * for position name, hours per week and payrate
	 */
	@Test
	void testPositionConstructor() {
		Position position = new Position("CSC 216 - TA", 10, 25);
		assertEquals("CSC 216 - TA", position.getPositionName());
		assertEquals(10, position.getHoursPerWeek());
		assertEquals(25, position.getPayRate());
	}
	
	
	/**
	 * Test method for addApplication method, asserts that applications were added
	 * to an arraylist, gets application's id
	 */
	@Test
	void testAddApplication() {
		Position position = new Position("CSC 216 - TA", 10, 25);
	
		assertEquals(1, position.addApplication("Tom", "Jerry", "tjerry"));
	
		assertEquals(1, position.getApplications().size());
		assertEquals(2, position.addApplication("John", "Stein", "jstein"));
		assertEquals(2, position.getApplications().size());
	}

	/**
	 * Test method to get application by id from arraylist
	 */
	@Test
	void testGetApplicationById() {
		
		Position position = new Position("CSC 216 - TA", 10, 25);
		
		Application newApplication1 = new Application("Tony", "Stark", "tstark");
		
		assertEquals(1, position.addApplication("Tony", "Stark", "tstark"));
		assertEquals(1, position.getApplications().size());
		assertEquals(2, position.addApplication("John", "Stein", "jstein"));
		assertEquals(2, position.getApplications().size());
		assertEquals(3, position.addApplication("Tom", "Jerry", "tjerry"));
		assertEquals(3, position.getApplications().size());
		
		assertEquals(newApplication1.getFirstName(), position.getApplicationById(1).getFirstName());
		
		
	}

	/**
	 * Test method to set/get an application id using setcounter
	 */
	@Test
	void testGetApplicationId() {
		
		Position position = new Position("CSC 216 - TA", 10, 25);
		
		Application newApplication = new Application(8, "Submitted", "Tony", "Stark", "tstark", "", "");
		Application newApplication1 = new Application(6, "Submitted", "John", "Stein", "jstein", "", "");
		Application newApplication2 = new Application(7, "Submitted", "Tom", "Jerry", "tjerry", "", "");
		
		
		assertEquals(8, position.addApplication(newApplication));
		assertEquals(1, position.getApplications().size());
		assertEquals(6, position.addApplication(newApplication1));
		assertEquals(2, position.getApplications().size());
		assertEquals(7, position.addApplication(newApplication2));
		assertEquals(3, position.getApplications().size());
		
		assertEquals(newApplication1, position.getApplicationById(6));
		
		
	}
	

	/**
	 * Test method to add one Application to a position
	 */
	@Test
	void testAddApplication1() {
		Position position = new Position("CSC 216 - TA", 10, 25);
		assertEquals(0, position.getApplications().size());
		assertEquals(1, position.addApplication("Carol", "Schmidt", "cschmid"));
		
		
	}
	
	/**
	 * Test method to test delete application by id method
	 */
	@Test
	void testDeleteApplicationById() {
		Position position = new Position("Sample Position", 20, 30);
		Application newApplication = new Application(2, "Rejected", "Clinton", "Armstrong", "carmstr", null, "Qualifications");
		Application newApplication1 = new Application(7, "Inactive", "Audrey", "Kemp", "akemp", "tnmacnei", "Fired");
		Application newApplication2 = new Application(5, "Hired", "Craig", "Armstrong", "carmstr", "tnmacnei", null);
		assertEquals(0, position.getApplications().size());

		assertEquals(1, position.addApplication("Carol", "Schmidt", "cschmid"));
		assertEquals(2, position.addApplication(newApplication));
		assertEquals(7, position.addApplication(newApplication1));
		assertEquals(5, position.addApplication(newApplication2));
		assertEquals(8, position.addApplication("Cailin", "Roach", "cvroach"));
		assertEquals("Carol", position.getApplications().get(0).getFirstName());
		assertEquals("Clinton", position.getApplications().get(1).getFirstName());
		assertEquals("Craig", position.getApplications().get(2).getFirstName());
		assertEquals("Audrey", position.getApplications().get(3).getFirstName());
		assertEquals("Cailin", position.getApplications().get(4).getFirstName());
		
		position.deleteApplicationById(1);
		
	}
	
	
	

}
