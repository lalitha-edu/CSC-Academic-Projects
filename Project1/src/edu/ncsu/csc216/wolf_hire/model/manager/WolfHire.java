/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.command.Command;
import edu.ncsu.csc216.wolf_hire.model.io.PositionReader;
import edu.ncsu.csc216.wolf_hire.model.io.PositionWriter;

/**
 * The WolfHire class controls the creation and modification of many positions
 * including adding a position, loading positions, saving positions, getting applications.
 * The class interacts with the other classes of application and posiition to create the
 * hiring system through which applications move.
 *  
 * @author Lalitha Edupuganti
 *
 */
public class WolfHire {
	
	/**
	 * The only one instance of WolfHire to be created.
	 * static instance of the class
	 */
	private static WolfHire singleton;
	
	/**
	 * The active position in the system
	 */
	private Position activePosition;
	
	/**
	 * The list of positions in the system
	 */
	private ArrayList<Position> positions;
	
	/**
	 * Default WolfHire constructor
	 */
	private WolfHire() {
		positions = new ArrayList<Position>();
		activePosition = null;
	}
	
	/**
	 * getInstance will check if the singleton is null. 
	 * If the singleton is null, then getInstance() calls 
	 * the private WolfHire() constructor to create the single instance.
	 * @return singleton the singleton used in creating the instance 
	 */
	public static WolfHire getInstance() {
		if (singleton == null) {
			singleton = new WolfHire();
		}
		return singleton;
	}
	
	/**
	 * Creates a new Position with the given info and adds it to the end 
	 * of the positions list. The position is then loaded as the activePosition
	 * by calling the loadPosition(String positionName) method.
	 * 
	 * @param positionName the positionName of the new position
	 * @param hoursPerWeek the hours per week of the new position
	 * @param payRate the pay rate of the new position
	 * @throws IllegalArgumentException if the positionName parameter is null,
	 * empty string, or a duplicate of an existing position name (case-insensitive)
	 */
	public void addNewPosition(String positionName, int hoursPerWeek, int payRate) {
		Position newPosition = new Position(positionName, hoursPerWeek, payRate);
		for(int i = 0; i < positions.size(); i++) {
			if (newPosition.getPositionName() == null || newPosition.getPositionName().equals("")) {
				throw new IllegalArgumentException("Position cannot be created.");
			} else if(newPosition.getPositionName().equals(positions.get(i).getPositionName())) {
				throw new IllegalArgumentException("Position cannot be created.");
			}
			
		}
		positions.add(newPosition);
		
		loadPosition(newPosition.getPositionName());
		
		
		
		
	}
	
	/**
	 *  Find the Position with the given name in the list, makes it active or activePosition,
	 *  and sets the application id for that position so that any new Applications added 
	 *  to the position are created with the next correct id.
	 * 
	 * @param positionName the positionName of the position to be made active
	 * 
	 * @throws IllegalArgumentException if there is no position with the given name.
	 */
	public void loadPosition(String positionName) {
		boolean positionFound = false;
		for(int i = 0; i < positions.size(); i++) {
			if (positionName.equals(positions.get(i).getPositionName())) {
				this.activePosition = positions.get(i);
				this.activePosition.setApplicationId();
				positionFound = true;
			}
		}
		if (!positionFound)  {
		throw new IllegalArgumentException("Position not available.");
		}
	}
	
	/**
	 * Returns the activePosition or null if the is no activePosition.
	 * @return the ActivePosition
	 */
	public Position getActivePosition() {
		if (this.activePosition == null) {
			return null;
		} else {
			return this.activePosition;
		}
	}
	
	/**
	 * Returns the active position name 
	 * 
	 * @return the name of the active position
	 */
	public String getActivePositionName() {
		if (this.activePosition == null) {
			return null;
		} else {
			return this.activePosition.getPositionName();
		}
	}
	
	/**
	 * Returns a String array of position names in the order 
	 * they are listed in the positions list. 
	 * @return the string array of position names
	 */
	public String [] getPositionList() {
		String [] positionList = new String[positions.size()];
		if (positions.isEmpty()) {
			return positionList;
		} else {
			for (int i = 0; i < positions.size(); i++) {
				positionList[i] = positions.get(i).getPositionName();
			}
		}
		return positionList;
	}
	
	/**
	 * Uses the PositionReader to read the given fileName. 
	 * The returned list of Positions are added to the positions field.
	 * The first position in the list returned 
	 * from PositionReader is made the activePosition
	 * 
	 * @param fileName the file to read from
	 */
	public void loadPositionsFromFile(String fileName) {
			this.positions = PositionReader.readPositionFile(fileName);
			this.activePosition = this.positions.get(0);
	}
	
	/**
	 * Write the list of Positions to the file using the PositionWriter class.
	 * 
	 * @param fileName the file to write to
	 * @throws IllegalArgumentException if the activePosition is null 
	 */
	public void savePositionsToFile(String fileName) {
		if(this.activePosition == null) {
			throw new IllegalArgumentException("Unable to save file.");
		} else {
			PositionWriter.writePositionsToFile(fileName, positions);
		}
	}
	
	/**
	 * method returns a 2D Object array that is used to populate the ApplicationTableModel
	 * with information about the applications
	 * 
	 * @param filter provides a string to filter on 
	 * @return 2d object array with applications
	 */
	public String[][] getApplicationsAsArray(String filter) {
		ArrayList<Application> applicationList = new ArrayList<Application>();
		ArrayList<Application> filteredList = new ArrayList<Application>();
		if (this.activePosition != null) {
		applicationList = this.activePosition.getApplications();
		
			for(int i = 0; i < applicationList.size(); i++) {
				if(applicationList.get(i).getState().equals(filter)) {
					filteredList.add(applicationList.get(i));
				} else if ("All".equals(filter)) {
					filteredList = applicationList;
				}
			}
		

		String [][] applicationsAsArray = new String [filteredList.size()][4];
			for(int i = 0; i < filteredList.size(); i++) {
				String applicationid = filteredList.get(i).getId() + "";
				applicationsAsArray[i][0] = applicationid;
				applicationsAsArray[i][1] = filteredList.get(i).getState();
				applicationsAsArray[i][2] = filteredList.get(i).getUnityId();
				applicationsAsArray[i][3] = filteredList.get(i).getReviewer();
			}
		
		return applicationsAsArray;
		}
		return null;
	}
	
	/**
	 * Returns the given application or null if there is no 
	 * application with the given id in the activePosition.
	 * 
	 * @param id that is used to get an application with the specific id
	 * @return the given applicaton or null if no application with given id
	 */
	public Application getApplicationById(int id) {
		if (this.activePosition != null) {
			this.activePosition.getApplicationById(id);
			return this.activePosition.getApplicationById(id);
		}
		
		return null;
	}
		
		

	
	/**
	 * Method takes in the parameters of add firstname, surname and unity id of applicant
	 * to create an application and add application to a position
	 * 
	 * @param firstName of applicant
	 * @param surname of appliant
	 * @param unityId of applicant
	 */
	public void addApplicationToPosition(String firstName, String surname, String unityId) {
		if (activePosition != null) {
			activePosition.addApplication(firstName, surname, unityId);
		}
		
		
//		Application newAddedApplication = new Application(firstName, surname, unityId);
//		ArrayList<Application> applicationList = new ArrayList<Application>();
//		
//		applicationList = activePosition.getApplications();
//		
		
		
		
	}
	
	/**
	 * Method takes in the id of an application to be used to
	 * delete the application.
	 * 
	 * @param id of the application that is to be deleted
	 */
	public void deleteApplicationById(int id) {
		if(this.activePosition != null) {
			this.activePosition.deleteApplicationById(id);
		}
		
	}
	
	/**
	 * Method used to execute the command provided by parameter c
	 * on specific application provided by id
	 * 
	 * @param id of the application
	 * @param c command to be executed
	 */
	public void executeCommand(int id, Command c) {
		if(this.activePosition != null) {
		this.activePosition.executeCommand(id, c);
		}
	}
	/**
	 * Protected method resetManager
	 */
	protected void resetManager() {
		singleton = null;
	}
		
	
	
	
	
}
