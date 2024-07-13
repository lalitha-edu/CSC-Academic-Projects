/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



/**
 * Tests for Command Class
 * @author Lalitha Edupuganti
 *
 */
class CommandTest {

	/**
	 * Test method for Command Constructor invalids for 
	 * ASSIGN, REJECT, TERMINATE
	 */
	@Test
	void testCommandInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.ASSIGN, null));
		assertEquals("Invalid information.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.REJECT, ""));
		assertEquals("Invalid information.", e2.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.TERMINATE, null));
		assertEquals("Invalid information.", e3.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		
	}
	
	/**
	 * Test method for Command Constructor invalids for 
	 * RESUMBIT, RETURN, SCHEDULE, PROCESS, HIRE
	 */
	@Test
	void testCommandInvalid2() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.RESUBMIT, "Resubmit invalid"));
		assertEquals("Invalid information.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.RETURN, "Return invalid"));
		assertEquals("Invalid information.", e2.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.SCHEDULE, "Schedule invalid"));
		assertEquals("Invalid information.", e3.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.PROCESS, "Process invalid"));
		assertEquals("Invalid information.", e4.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Command(Command.CommandValue.HIRE, "Hire invalid"));
		assertEquals("Invalid information.", e5.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
	}
	
	/**
	 * Test method for Command Constructor valid paths
	 */
	@Test
	void testCommandValid() {
		
		Command command = new Command(Command.CommandValue.ASSIGN, "Assigned String");
		assertEquals(Command.CommandValue.ASSIGN, command.getCommand());
		assertEquals("Assigned String", command.getCommandInformation());
		
		Command command1 = new Command(Command.CommandValue.RESUBMIT, null);
		assertEquals(Command.CommandValue.RESUBMIT, command1.getCommand());
		assertEquals(null, command1.getCommandInformation());

		
	}
	
}
