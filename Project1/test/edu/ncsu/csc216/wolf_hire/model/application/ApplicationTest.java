/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.command.Command;


/**
 * Tests for Application Class
 * @author Lalitha Edupuganti
 *
 */
class ApplicationTest {

	/**
	 * Tests the application class constructor, tests invalid paths
	 */
	@Test
	void testApplicationConstructorInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Application(null, "Stark", "tstark"));
		assertEquals("Invalid application.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Application("Tony", "", "tstark"));
		assertEquals("Invalid application.", e2.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Application("Tony", "Stark", null));
		assertEquals("Invalid application.", e3.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
	}
	
	/**
	 * Test method for valid Application constructor
	 */
	@Test
	void testApplicationConstructor() {
		Application application = new Application(2, "Submitted", "Tony", "Stark", "tstark", "", "");
		
		assertEquals(2, application.getId());
		assertEquals("Submitted", application.getState());
		assertEquals("Tony", application.getFirstName());
		assertEquals("Stark", application.getSurname());
		assertEquals("tstark", application.getUnityId());
		assertEquals("", application.getReviewer());
		assertEquals("", application.getNote());
		
	}
	
	/**
	 * Test method SubmittedState inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationSubmittedUpdateState() {
		
		//Submitted state moving application to reviewed
		Application application = new Application(2, "Submitted", "Tony", "Stark", "tstark", "", "");
		assertEquals("Submitted", application.getState());
		
		Command command = new Command (Command.CommandValue.ASSIGN, "jhanna");
			application.update(command);
			
		assertEquals("Reviewing", application.getState());
		
		//Submitted state moving application to rejected
		Application application1 = new Application(3, "Submitted", "Purple", "Smith", "psmith", "", "");
		assertEquals("Submitted", application1.getState());
		
		Command command1 = new Command (Command.CommandValue.REJECT, "Incomplete");
			application1.update(command1);
			
		assertEquals("Rejected", application1.getState());
		
		
	}
	
	/**
	 * Test method RejectedState inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationRejectedUpdateState() {
		
		//Rejected state move to submitted state
		Application application = new Application(2, "Rejected", "Tony", "Stark", "tstark", "", "Duplicate");
		assertEquals("Rejected", application.getState());
		
		Command command = new Command (Command.CommandValue.RESUBMIT, null);
			application.update(command);
			
		assertEquals("Submitted", application.getState());
		
		
	}
	
	
	/**
	 * Test method ReviewingState inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationReviewingUpdateState() {
		
		//Submitted state moving application to reviewed
		Application application4 = new Application(4, "Reviewing", "Tony", "Stark", "tstark", "johnna6", "");
		assertEquals("Reviewing", application4.getState());
		
		Command command = new Command (Command.CommandValue.SCHEDULE, null);
			application4.update(command);
			
		assertEquals("Interviewing", application4.getState());
		
		//Submitted state moving application to rejected
		Application application5 = new Application(5, "Reviewing", "Purple", "Smith", "psmith", "johnna6", "");
		assertEquals("Reviewing", application5.getState());
		
		Command command1 = new Command (Command.CommandValue.RETURN, null);
			application5.update(command1);
			
		assertEquals("Submitted", application5.getState());
		
		//application for invalid test
		Application application6 = new Application(4, "Reviewing", "Tony", "Stark", "tstark", "johnna6", "");
		assertEquals("Reviewing", application6.getState());
		//Command for invalid test
		Command command2 = new Command (Command.CommandValue.REJECT, "Nothing");
		//Invalid test
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> application6.update(command2));
		assertEquals("Invalid command.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		
	}
	
	/**
	 * Test method InterviewingState inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationInterviewingUpdateState() {
		
		//Interviewing state moving application to reviewed
		Application application4 = new Application(4, "Interviewing", "Tony", "Stark", "tstark", "sesmith5", "");
		assertEquals("Interviewing", application4.getState());
		
		Command command = new Command (Command.CommandValue.PROCESS, null);
			application4.update(command);
			
		assertEquals("Processing", application4.getState());
		
		//Interviewing state moving application to rejected
		Application application5 = new Application(5, "Interviewing", "Purple", "Smith", "psmith", "sesmith5", "");
		assertEquals("Interviewing", application5.getState());
		
		Command command1 = new Command (Command.CommandValue.ASSIGN, "khanna");
			application5.update(command1);
			
		assertEquals("Reviewing", application5.getState());
		
		//application for invalid test
		Application application6 = new Application(4, "Interviewing", "Tony", "Stark", "tstark", "sesmith5", "");
		assertEquals("Interviewing", application6.getState());
		//Command for invalid test
		Command command2 = new Command (Command.CommandValue.REJECT, "Nothing");
		//Invalid test
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> application6.update(command2));
		assertEquals("Invalid command.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
	}
	
	
	
	/**
	 * Test method Submitted State inner class, tests different paths of change states.
	 * Tests state change without required note.
	 */
	@Test
	void testApplicationSubmittedRejectWithWrongNote() {
	
		//Interviewing state moving application to rejected
		Application application5 = new Application(5, "Submitted", "Purple", "Smith", "psmith", "", "");
		assertEquals("Submitted", application5.getState());
		
	
			Command command1 = new Command (Command.CommandValue.REJECT, "Reject1");
			
			assertEquals("Submitted", application5.getState());
			
			Exception e1 = assertThrows(UnsupportedOperationException.class,
					() -> application5.update(command1));
			assertEquals("Invalid command.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		
	}
	
	
	
	/**
	 * Test method ProcessingState inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationProcessingUpdateState() {
		
		//Processing state moving application to reviewed
		Application application4 = new Application(4, "Processing", "Tony", "Stark", "tstark", "johanna6", "");
		assertEquals("Processing", application4.getState());
		
		Command command = new Command (Command.CommandValue.HIRE, null);
			application4.update(command);
			
		assertEquals("Hired", application4.getState());
		
		//Processing state moving application to rejected
		Application application5 = new Application(5, "Processing", "Purple", "Smith", "psmith", "johanna6", "");
		assertEquals("Processing", application5.getState());
		
		Command command1 = new Command (Command.CommandValue.REJECT, "Incomplete");
			application5.update(command1);
			
		assertEquals("Rejected", application5.getState());
		
		
		//application for invalid test
		Application application6 = new Application(4, "Processing", "Tony", "Stark", "tstark", "johanna", "");
		assertEquals("Processing", application6.getState());
		
		//Command for invalid test
		Command command2 = new Command (Command.CommandValue.REJECT, "Nothing");
		//Invalid test
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> application6.update(command2));
		assertEquals("Invalid command.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		
	}
	
	
	/**
	 * Test method HiredState inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationHiredUpdateState() {
		
		//Hired state moving application to reviewed
		Application application4 = new Application(4, "Hired", "Tony", "Stark", "tstark", "sesmith5", "");
		assertEquals("Hired", application4.getState());
		
		Command command = new Command (Command.CommandValue.TERMINATE, "Resigned");
			application4.update(command);
			
		assertEquals("Inactive", application4.getState());
		
		
		//application for invalid test
		Application application5 = new Application(4, "Hired", "Tony", "Stark", "tstark", "sesmith5", "");
		assertEquals("Hired", application5.getState());
		//Command for invalid test
		Command command2 = new Command (Command.CommandValue.TERMINATE, "Not important");
		//Invalid test
		Exception e1 = assertThrows(UnsupportedOperationException.class,
				() -> application5.update(command2));
		assertEquals("Invalid command.", e1.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
	}
	
	
	/**
	 * Test method Inactive inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationInactiveUpdateState() {
		//invalid Result for inactive method, testing resumbit command
		Application application4 = new Application(4, "Inactive", "Tony", "Stark", "tstark", "kdella3", "Completed");
		assertEquals("Inactive", application4.getState());
		
		Command command = new Command (Command.CommandValue.RESUBMIT, null);
		
		
		Exception e3 = assertThrows(UnsupportedOperationException.class,
				() -> application4.update(command));
		assertEquals("Invalid command.", e3.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
	
		
		//invalid Result for inactive method, testing Hire
		Application application5 = new Application(5, "Inactive", "Purple", "Smith", "psmith", "kdella3", "Resigned");
		assertEquals("Inactive", application5.getState());
		
		Command command1 = new Command (Command.CommandValue.HIRE, null);
			
			
		Exception e4 = assertThrows(UnsupportedOperationException.class,
					() -> application5.update(command1));
		assertEquals("Invalid command.", e4.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
		
		
	}
	

	/**
	 * Test method Interviewing inner class, tests different paths of change states.
	 */
	@Test
	void testApplicationInterviewingDState() {
		Application application = new Application("Georgia", "Kinder", "gkinder");
		assertEquals("Submitted", application.getState());
		
		Command command = new Command (Command.CommandValue.ASSIGN, "sesmith6");
		
		application.update(command);
		
		assertEquals("Reviewing", application.getState());
		
		assertEquals("sesmith6", application.getReviewer());
		
		
		Command command1 = new Command (Command.CommandValue.SCHEDULE, null);
		
		application.update(command1);
		
		assertEquals("Interviewing", application.getState());
		
		Command command2 = new Command (Command.CommandValue.REJECT, "Qualifications");
		
		application.update(command2);
		
		assertEquals("Rejected", application.getState());
		
		assertEquals("", application.getReviewer());
		
		assertEquals("Qualifications", application.getNote());
		
	}
	
	/**
	 * Test method testing invalid paths from Processing state.
	 */
	 
	@Test
	void testInvalidTransitionsFromProcessing() {
		Application application = new Application("Georgia", "Kinder", "gkinder");
		assertEquals("Submitted", application.getState());
		
		Command command = new Command (Command.CommandValue.ASSIGN, "sesmith6");
		
		application.update(command);
		
		assertEquals("Reviewing", application.getState());
		
		assertEquals("sesmith6", application.getReviewer());
		
		
		Command command1 = new Command (Command.CommandValue.SCHEDULE, null);
		
		application.update(command1);
		
		assertEquals("Interviewing", application.getState());
		
		Command command2 = new Command (Command.CommandValue.PROCESS, null);
		
		application.update(command2);
		
		assertEquals("Processing", application.getState());
		
		assertEquals("sesmith6", application.getReviewer());
		
		assertEquals("", application.getNote());
		
		Command command3 = new Command (Command.CommandValue.HIRE, null);
		
		application.update(command3);	
		
		assertEquals("Hired", application.getState());
		
		Command command4 = new Command (Command.CommandValue.ASSIGN, "jeanne5");
		
		Exception e4 = assertThrows(UnsupportedOperationException.class,
				() -> application.update(command4));
		assertEquals("Invalid command.", e4.getMessage(), "Incorrect exception thrown with invalid command name - " + null);
		
	}


}
