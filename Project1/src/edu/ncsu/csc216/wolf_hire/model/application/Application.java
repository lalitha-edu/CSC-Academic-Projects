package edu.ncsu.csc216.wolf_hire.model.application;



import edu.ncsu.csc216.wolf_hire.model.command.Command;


/** 
 *
 *  Class is the parent of the state hierarchy(the interface ApplicationState)
 *  Application reperesents an application managed through the hiring system
 *  class takes care of the interface ApplicationState and the state classes 
 *  that take care of the state of an application.
 *  
 *  @author Lalitha Edupuganti
 */
public class Application {
	
	/** 
	 *  Unique id for an application.
	 */
	private int applicationId = 0;
	
	/** 
	 *  First name of the applicant.
	 */
	private String firstName = "";
	
	/** 
	 * Surname of the applicant.
	 */
	private String surname = "";
	
	/** 
	 *  Unity id of the applicant.
	 */
	private String unityId = "";
	
	/** 
	 *  Reviewer assigned to review the application. 
	 */
	private String reviewer = null;
	
	/** 
	 *   Contains the rejection reason OR termination 
	 *   reason for the application, if appropriate..
	 */
	private String note = null;
	
	/** 
	 *  Incremented value used in accordance with creating
	 *  a new applicationid 
	 */
	private static int counter = 0;
	
	/** 
	 *  Represents the current state of the Application.
	 */
	private ApplicationState currentState;
	
	/** 
	 *  A constant string for the submitted state’s name with the value “Submitted”.
	 */
	public static final String SUBMITTED_NAME = "Submitted";
	
	/** 
	 *  A constant string for the rejected state’s name with the value “Rejected”.
	 */
	public static final String REJECTED_NAME = "Rejected";
	
	/** 
	 *  A constant string for the reviewing state’s name with the value “Reviewing”.
	 */
	public static final String REVIEWING_NAME = "Reviewing";
	
	/** 
	 *  A constant string for the interviewing state’s name with the value “Interviewing”.
	 */
	public static final String INTERVIEWING_NAME = "Interviewing";
	
	/** 
	 *  A constant string for the processing state’s name with the value “Processing”.
	 */
	public static final String PROCESSING_NAME = "Processing";
	
	/** 
	 *  A constant string for the hired state’s name with the value “Hired”.
	 */
	public static final String HIRED_NAME = "Hired";
	
	/** 
	 *  A constant string for the inactive state’s name with the value “Inactive”.
	 */
	public static final String INACTIVE_NAME = "Inactive";
	
	/** 
	 *  A constant string for the rejection reason of “Qualifications”.
	 */
	public static final String QUALIFICATIONS_REJECTION = "Qualifications";
	
	/** 
	 *  A constant string for the rejection reason of “Incomplete”.
	 */
	public static final String INCOMPLETE_REJECTION = "Incomplete";
	
	/** 
	 *  A constant string for the rejection reason of “Positions”.
	 */
	public static final String POSITIONS_REJECTION = "Positions";
	
	/** 
	 *  A constant string for the rejection reason of “Duplicate”.
	 */
	public static final String DUPLICATE_REJECTION = "Duplicate";
	
	/** 
	 *  A constant string for the priority of “Completed”.
	 */
	public static final String COMPLETED_TERMINATION = "Completed";
	
	/** 
	 * A constant string for the priority of “Resigned”.
	 */
	public static final String RESIGNED_TERMINATION = "Resigned";
	
	/** 
	 * A constant string for the priority of “Fired”.
	 */
	public static final String FIRED_TERMINATION = "Fired";
	
	/** 
	 *  Final instance of the SubmittedState inner class. 
	 *  The reference type is ApplicationState
	 */
	private SubmittedState submittedState = new SubmittedState();
	
	/** 
	 *  Final instance of the RejectedState inner class. 
	 */
	private RejectedState rejectedState = new RejectedState();
	
	/** 
	 *  Final instance of the ReviewingState inner class. 
	 */
	private ReviewingState reviewingState = new ReviewingState();
	
	/** 
	 *  Final instance of the InterviewingState inner class.
	 */
	private InterviewingState interviewingState = new InterviewingState();
	
	/** 
	 *  Final instance of the ProcessingState inner class.
	 */
	private ProcessingState processingState = new ProcessingState();
	
	/** 
	 *  RFinal instance of the HiredState inner class. 
	 */
	private HiredState hiredState = new HiredState();
	
	/** 
	 *   Final instance of the InactiveState inner class.
	 */
	private InactiveState inactiveState = new InactiveState();
	
	
	/** 
	 * Constructs a Application from the provided firstName, surname, 
	 * and unityId. 
	 * @param firstName the firstname of an applicant
	 * @param surname the lastname of an applicatn
	 * @param unityId of the applicant
	 * @throws IllegalArgumentException if any of the parameters are 
	 * null or empty strings
	 */
	public Application(String firstName, String surname, String unityId) {
		
		if(firstName == null || surname == null || unityId == null ) {
			throw new IllegalArgumentException("Invalid application.");
		} else if ("".equals(firstName) || "".equals(surname) || "".equals(unityId)) {
			throw new IllegalArgumentException("Invalid application.");
		} 
		
		setFirstName(firstName);
		setSurname(surname);
		setUnityId(unityId);
		this.currentState = submittedState;
		
		
		this.applicationId = Application.counter;
		Application.incrementCounter();
		this.reviewer = null;
		this.note = null;
	}
	
	/** 
	 * Constructs a Application from the provided firstName, surname, 
	 * and unityId along with reviewer and note
	 * @param id of application
	 * @param state of application
	 * @param firstName the firstname of an applicant
	 * @param surname the lastname of an applicatn
	 * @param unityId of the applicant
	 * @param reviewer the name/id of the reviewer
	 * @param note the note left on the application if any
	 * @throws IllegalArgumentException if any of the parameters are 
	 * null or empty strings, or if any other issues occur
	 */
	public Application(int id, String state, String firstName, String surname,
			String unityId, String reviewer, String note) {
		
		if(firstName == null || surname == null || unityId == null || state == null ) {
			throw new IllegalArgumentException("Invalid application.");
		} else if ("".equals(firstName) || "".equals(surname) || "".equals(unityId)) {
			throw new IllegalArgumentException("Invalid application.");
		} 
		
		setFirstName(firstName);
		setSurname(surname);
		setUnityId(unityId);
		
		setReviewer(reviewer);
		setState(state);
		if(SUBMITTED_NAME.equals(getState()) && !("".equals(getReviewer())) ||
				REJECTED_NAME.equals(getState()) && !("".equals(getReviewer()))) {
			throw new IllegalArgumentException("Invalid application.");
		} else if (REVIEWING_NAME.equals(getState()) && "".equals(getReviewer()) || INTERVIEWING_NAME.equals(getState()) &&  "".equals(getReviewer()) ||
				PROCESSING_NAME.equals(getState()) &&  "".equals(getReviewer()) ||  HIRED_NAME.equals(getState()) &&  "".equals(getReviewer()) ||
				INACTIVE_NAME.equals(getState()) &&  "".equals(getReviewer()) ) {
			throw new IllegalArgumentException("Invalid application.");
		}
		setNote(note);
			if (SUBMITTED_NAME.equals(getState()) && !("".equals(getNote())) || INTERVIEWING_NAME.equals(getState()) && !("".equals(getNote())) ||
					PROCESSING_NAME.equals(getState()) && !("".equals(getNote())) ||  HIRED_NAME.equals(getState()) &&  !("".equals(getNote())) ||
					REVIEWING_NAME.equals(getState()) &&  !("".equals(getNote()) )) {
					 throw new IllegalArgumentException ("Invalid application");
			} else if (REJECTED_NAME.equals(getState()) &&  ("".equals(getNote())) || INACTIVE_NAME.equals(getState()) &&  ("".equals(getNote()))) {
				throw new IllegalArgumentException("Invalid application");
			} else if (REJECTED_NAME.equals(getState()) &&  !(QUALIFICATIONS_REJECTION.equals(getNote())) &&
					REJECTED_NAME.equals(getState()) &&  !(INCOMPLETE_REJECTION.equals(getNote())) &&
				REJECTED_NAME.equals(getState()) &&  !(DUPLICATE_REJECTION.equals(getNote())) &&
				REJECTED_NAME.equals(getState()) &&  !(POSITIONS_REJECTION.equals(getNote()))) {
				throw new IllegalArgumentException("Invalid application");
			} else if (INACTIVE_NAME.equals(getState()) &&  !(COMPLETED_TERMINATION.equals(getNote())) &&
					INACTIVE_NAME.equals(getState()) &&  !(RESIGNED_TERMINATION.equals(getNote())) &&
				INACTIVE_NAME.equals(getState()) &&  !(FIRED_TERMINATION.equals(getNote()))) {
				throw new IllegalArgumentException("Invalid application");
			} 
		
		if(id <= 0) {
			throw new IllegalArgumentException("Invalid application.");
		}
		if (id > Application.counter) {
			setCounter(id);
		}
		setId(id);
		Application.incrementCounter();
		
		
	}
	
	/**
	 * Sets the application's id.
	 * @param id the integer value of the id
	 */
	private void setId(int id) {
		this.applicationId = id;
	}
	
	/**
	 * Sets the application's state.
	 * @param stateValue the string statevalue of an application
	 */
	private void setState(String stateValue) {
		switch(stateValue) {
		case SUBMITTED_NAME:
			this.currentState = submittedState;
			break;
			
		case REJECTED_NAME:
			this.currentState = rejectedState;
			break;
			
		case REVIEWING_NAME:
			this.currentState = reviewingState;
			break;
			
		case INTERVIEWING_NAME:
			this.currentState = interviewingState;
			break;
			
		case PROCESSING_NAME:
			this.currentState = processingState;
			break;
			
			
		case HIRED_NAME:
			this.currentState = hiredState;
			break;
			
			
		case INACTIVE_NAME:
			this.currentState = inactiveState;
			break;
			
		default:
			throw new IllegalArgumentException("Application cannot be created.");
			
		}
	}
	
	/**
	 * Gets the application's id.
	 * @return 0 numerical value of id
	 */
	public int getId() {
		return this.applicationId;
	}
	
	/**
	 * Gets the application's state.
	 * @return "" applicationstate
	 */
	public String getState() {
		return this.currentState.getStateName();
	}
	
	/**
	 * Gets the applicant's firstname.
	 * @return "" the applicant's firstname
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Sets the applicant's firstname.
	 * @param firstName the applicant's firstname
	 */
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Gets the applicant's surnname.
	 * @return "" the applicant's surname
	 */
	public String getSurname() {
		return this.surname;
	}
	
	/**
	 * Sets the applicant's surname.
	 * @param surname the applicant's surname
	 */
	private void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * Gets the applicant's unityId.
	 * @return "" the applicant's unityId
	 */
	public String getUnityId() {
		return this.unityId;
	}
	
	/**
	 * Sets the applicant's unityId
	 * @param unityId the applicant's unityId
	 */
	private void setUnityId(String unityId) {
		this.unityId = unityId;
	}
	
	/**
	 * Gets the applicantion's reviewer.
	 * @return null the application's reviewer
	 */
	public String getReviewer() {
		if (this.reviewer == null) {
			return "";
		} else {
			return this.reviewer;
		}
	}
	
	
	/**
	 * Sets the application's reviewer.
	 * @param reviewer the application's reviewer
	 */
	private void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	
	/**
	 * Gets the applicantion's note.
	 * @return null the application's note
	 */
	public String getNote() {
		if (this.note == null) {
			return "";
		} else {
			return this.note;
		}
	}
	
	/**
	 * Sets the application's noge.
	 * @param note the application's note
	 */
	private void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * Method works to increment the counter
	 */
	public static void incrementCounter() {
		Application.counter++;
	}
	
	/**
	 * Method works to set the counter
	 * @param newCount to set the counter value
	 */
	public static void setCounter(int newCount) {
		Application.counter = newCount;
	}
	
	/**
	 * Returns the string representation of the Application 
	 * that is printed during file save operations
	 * @return null the string representation of an application
	 */
	public String toString() {
		return "* " + getId() + "," + getState() + "," + getFirstName() + "," + getSurname() + "," + getUnityId() + "," + getReviewer() + "," + getNote(); 
	}
	
	/**
	 * Drives the finite state machine by delegating to the currentState’s
	 * updateState(Command) method.
	 * @param c the current command
	 * 
	 */
	public void update(Command c) {
		try {
		currentState.updateState(c);
		} catch (IllegalArgumentException e) {
			throw new UnsupportedOperationException("Invalid command.");
		}
		
	}
	
	/**
	 * Interface for states in the Application State Pattern.  All 
	 * concrete Application states must implement the ApplicationState interface.
	 * The ApplicationState interface should be a private interface of the 
	 * Application class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface ApplicationState {
		
		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command command);
			
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}
	
	/** 
	 *  Class is a child class of ApplicationState interface
	 *  The class represents the submitted state of an application,
	 *  one of the states an application may transition into
	 */
	private class SubmittedState implements ApplicationState {
		
		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command command) {
			
//			if (command.getCommand() != Command.CommandValue.ASSIGN 
//				&& command.getCommand() != Command.CommandValue.REJECT) {
//				
//				throw new UnsupportedOperationException("Invalid command.");
//				
			 if (command.getCommand() == Command.CommandValue.ASSIGN) {
				 setReviewer(command.getCommandInformation());
//				 if ("".equals(getReviewer())) {
//						setNote(null);
//						currentState = submittedState;
//				
//						throw new IllegalArgumentException("Invalid information.");
//						
//					}
//				setReviewer(command.getCommandInformation());
				setNote(null);
				currentState = reviewingState;
				
			} else if (command.getCommand() == Command.CommandValue.REJECT) {
				setNote(command.getCommandInformation());
			
				
				if (!QUALIFICATIONS_REJECTION.equals(getNote()) && !INCOMPLETE_REJECTION.equals(getNote()) 
						&&  !POSITIONS_REJECTION.equals(getNote()) && !DUPLICATE_REJECTION.equals(getNote())) {
					setNote(null);
					currentState = submittedState;
			
					throw new IllegalArgumentException("Invalid information.");
					
				}
				setReviewer(null);
				currentState = rejectedState;
			} else {

				currentState = submittedState;
				
				throw new UnsupportedOperationException("Invalid command.");
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			String currentStateName = "";
			currentStateName = SUBMITTED_NAME;
			
			return currentStateName;
		}
		
	}
	
	/** 
	 *  Class is a child class of ApplicationState interface
	 *  The class represents the rejected state of an application,
	 *  one of the states an application may transition into
	 */
	private class RejectedState implements ApplicationState {
	
		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command command) {
			
			
//			if (command.getCommand() != Command.CommandValue.RESUBMIT ) {
//					
//				throw new IllegalArgumentException("Invalid command.");
					
			if (command.getCommand() == Command.CommandValue.RESUBMIT ) {
					setReviewer(null);
					setNote(null);
					currentState = submittedState;
			} else {
				throw new UnsupportedOperationException("Invalid command.");
			}
			
		}
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			String currentStateName = "";
			
			currentStateName = REJECTED_NAME;
			
				return currentStateName;
		}
		
	}
	
	/** 
	 *  Class is a child class of ApplicationState interface
	 *  The class represents the reviewing state of an application,
	 *  one of the states an application may transition into
	 */
	private class ReviewingState implements ApplicationState {

		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command command) {
			
			
//			if (command.getCommand() != Command.CommandValue.SCHEDULE  &&
//				command.getCommand() != Command.CommandValue.RETURN &&
//				command.getCommand() != Command.CommandValue.REJECT	) {
//				
//				throw new IllegalArgumentException("Invalid command.");
					
			if (command.getCommand() == Command.CommandValue.SCHEDULE) {
				setNote(null);
				currentState = interviewingState;
				
			} else if (command.getCommand() == Command.CommandValue.RETURN) {
				setReviewer(null);
				setNote(null);
				currentState = submittedState;
			
			} else if (command.getCommand() == Command.CommandValue.REJECT) {
				setNote(command.getCommandInformation());
				
				if (!QUALIFICATIONS_REJECTION.equals(getNote()) && !INCOMPLETE_REJECTION.equals(getNote()) 
						&&  !POSITIONS_REJECTION.equals(getNote()) && !DUPLICATE_REJECTION.equals(getNote())) {
					setNote(null);
					currentState = reviewingState;
					throw new IllegalArgumentException("Invalid information.");
					
				}
				setReviewer(null);
				currentState = rejectedState;
			} else {
				currentState = reviewingState;
				
				throw new UnsupportedOperationException("Invalid command.");
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			String currentStateName = "";
				currentStateName = REVIEWING_NAME;
			return currentStateName;
		}
		
	}
	
	/** 
	 *  Class is a child class of ApplicationState interface
	 *  The class represents the interviewing state of an application,
	 *  one of the states an application may transition into
	 */
	private class InterviewingState implements ApplicationState {

		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command command) {
			
			
//			if (command.getCommand() != Command.CommandValue.PROCESS  &&
//					command.getCommand() != Command.CommandValue.ASSIGN &&
//					command.getCommand() != Command.CommandValue.REJECT	) {
//					
//					throw new IllegalArgumentException("Invalid command.");
						
//			try {
			 if (command.getCommand() == Command.CommandValue.PROCESS) {
					setNote(null);
					currentState = processingState;
		
					
			} else if (command.getCommand() == Command.CommandValue.ASSIGN) {
				setReviewer(command.getCommandInformation());

//				if ("".equals(getReviewer())) {
//					setNote(null);
//					currentState = interviewingState;
//					throw new IllegalArgumentException("Invalid information.");
//					
//				}
					setNote(null);
					currentState = reviewingState;
				
			} else if (command.getCommand() == Command.CommandValue.REJECT) {
					setNote(command.getCommandInformation());
	
					if (!QUALIFICATIONS_REJECTION.equals(getNote()) && !INCOMPLETE_REJECTION.equals(getNote()) 
							&&  !POSITIONS_REJECTION.equals(getNote()) && !DUPLICATE_REJECTION.equals(getNote())) {
						setNote(null);
						currentState = interviewingState;
						throw new IllegalArgumentException("Invalid information.");
						
					}
					setReviewer(null);
					currentState = rejectedState;
			} else if (command.getCommand() == Command.CommandValue.SCHEDULE) {
				setNote(null);
				currentState = interviewingState;
			} else {
				currentState = interviewingState;
				throw new UnsupportedOperationException("Invalid command.");
			}
//			} catch (IllegalArgumentException e) {
//				throw new UnsupportedOperationException("Invalid command.");
//			}
		}
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			String currentStateName = "";
			
			currentStateName = INTERVIEWING_NAME;
			
			return currentStateName;
		}
		
	}
	
	/** 
	 *  Class is a child class of ApplicationState interface
	 *  The class represents the processing state of an application,
	 *  one of the states an application may transition into
	 */
	private class ProcessingState implements ApplicationState {

		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command command) {
			
			
//			if (command.getCommand() != Command.CommandValue.HIRE  &&
//					command.getCommand() != Command.CommandValue.REJECT	) {
//					
//					throw new IllegalArgumentException("Invalid command.");
//			try {		
			 if (command.getCommand() == Command.CommandValue.HIRE) {
					setNote(null);
					currentState = hiredState;
				
			} else if (command.getCommand() == Command.CommandValue.REJECT) {
					setNote(command.getCommandInformation());
	
					if (!QUALIFICATIONS_REJECTION.equals(getNote()) && !INCOMPLETE_REJECTION.equals(getNote()) 
							&&  !POSITIONS_REJECTION.equals(getNote()) && !DUPLICATE_REJECTION.equals(getNote())) {
						setNote(null);
						currentState = processingState;
						throw new IllegalArgumentException("Invalid information.");
						
					}
					setReviewer(null);
					currentState = rejectedState;
					
			}  else {
				currentState = processingState;
				throw new UnsupportedOperationException("Invalid command.");
			}
//			} catch (IllegalArgumentException e) {
//				throw new UnsupportedOperationException("Invalid command.");
//			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			String currentStateName = "";
		
			
			currentStateName = PROCESSING_NAME;
			
			return currentStateName;
		}
		
	}
	
	/** 
	 *  Class is a child class of ApplicationState interface
	 *  The class represents the hired state of an application,
	 *  one of the states an application may transition into
	 */
	private class HiredState implements ApplicationState {

		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command command) {
			
//			
//			if (command.getCommand() != Command.CommandValue.TERMINATE ) {
//					
//					throw new IllegalArgumentException("Invalid command.");
//			try {	
			 if (command.getCommand() == Command.CommandValue.TERMINATE) {
				setNote(command.getCommandInformation());
			
				if (!COMPLETED_TERMINATION.equals(getNote()) && !RESIGNED_TERMINATION.equals(getNote()) 
						&&  !FIRED_TERMINATION.equals(getNote()) ) {
					setNote(null);
					currentState = hiredState;
					throw new IllegalArgumentException("Invalid information.");
					
				}
					currentState = inactiveState;
				
			}  else {
				currentState = hiredState;
				throw new UnsupportedOperationException("Invalid command.");
			}
//			} catch (IllegalArgumentException e) {
//				throw new UnsupportedOperationException("Invalid command.");
//			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			String currentStateName = "";
			currentStateName = HIRED_NAME;
			
			return currentStateName;
		}
		
	}
	
	/** 
	 *  Class is a child class of ApplicationState interface
	 *  The class represents the inactive state of an application,
	 *  one of the states an application may transition into
	 */
	private class InactiveState implements ApplicationState {

		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command command) {

				currentState = inactiveState;
				throw new UnsupportedOperationException("Invalid command.");

			
		}

		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		@Override
		public String getStateName() {
			String currentStateName = "";
		
			currentStateName = INACTIVE_NAME;
			
			return currentStateName;
		}
		
	}
	
}
