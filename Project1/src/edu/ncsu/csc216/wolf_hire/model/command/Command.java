package edu.ncsu.csc216.wolf_hire.model.command;

/** 
 *  Class creates objects that encapsulate user actions 
 *  (or transitions) that cause the state of a Application to update
 *  
 *  @author Lalitha Edupuganti
 */
public class Command {

	/** Enumeration for the possible command values that can cause transitions */
	public enum CommandValue { 
		
		/** 
		 *  Assign command to assign a reviewer and move state to interviewing 
		 */
		ASSIGN, 
		
		/** 
		 *  Reject command to move an application to the rejected state
		 */
		REJECT, 
		
		/** 
		 *  Resubmit command to resubmit an application
		 */
		RESUBMIT, 
		
		/** 
		 *  Return command to return application back to a submitted state
		 */
		RETURN, 
		
		/** 
		 *  Schedule command, keeping the application in the interviewing state
		 */
		SCHEDULE, 
		
		/** 
		 *  Process command, to process an application an move it to the processing state
		 */
		PROCESS, 
	
		/** 
		 *  Hired command, to move an application to the hired state
		 */
		HIRE, 

		/** 
		 *  Terminate command, to move an application to the inactive state
		 */
		TERMINATE }
	

	
	/** 
	 *  CommandValue command used in the constructor of command
	 *  and enumeration value
	 */
	private CommandValue command = null;
	
	/** 
	 *  String with commandInformation
	 */
	private String commandInformation = null;
	
	/** 
	 *  Command constructor that constructors command with parameters
	 *  of command and the command information
	 *  @param command the commandValue
	 *  @param commandInformation the information of a command
	 */
	public Command(CommandValue command, String commandInformation) {
		if(command == null) {
			throw new IllegalArgumentException("Invalid information.");
		} else if (command == Command.CommandValue.ASSIGN && commandInformation == null ||
				command == Command.CommandValue.REJECT && commandInformation == null || 
				command == Command.CommandValue.TERMINATE && commandInformation == null) {
			throw new IllegalArgumentException("Invalid information.");	
		} else if (command == Command.CommandValue.ASSIGN && "".equals(commandInformation) ||
				command == Command.CommandValue.REJECT && "".equals(commandInformation) || 
				command == Command.CommandValue.TERMINATE && "".equals(commandInformation)) {
			throw new IllegalArgumentException("Invalid information.");	
		} else if (command == Command.CommandValue.RESUBMIT && commandInformation != null ||
				command == Command.CommandValue.RETURN && commandInformation != null || 
				command == Command.CommandValue.SCHEDULE && commandInformation != null ||
				command == Command.CommandValue.PROCESS && commandInformation != null ||
				command == Command.CommandValue.HIRE && commandInformation != null) {
			throw new IllegalArgumentException("Invalid information.");	
		} else {
			this.command = command;
			this.commandInformation = commandInformation;
		}
	}

	/** 
	 * Method works to get the command
	 * @return the commandvalue
	 */
	public CommandValue getCommand() {
		return this.command;
	}
	
	/** 
	 * Method works to get the command information
	 * @return the command information
	 */
	public String getCommandInformation() {
		return this.commandInformation;
	}

	
}
