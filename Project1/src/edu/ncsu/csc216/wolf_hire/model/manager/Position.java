/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.manager;



import java.util.ArrayList;


import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.command.Command;

/**
 * The class that creates a position object which has the functionality to
 * also store applications, add and remove applications from a list
 * @author Lalitha Edupuganti
 *
 */
public class Position {
	
	/** 
	 *  The string of the position name
	 */
	private String positionName = null;
	/** 
	 *  The hours per week for the position
	 */
	private int hoursPerWeek = 0;
	
	/** 
	 *  The payrate for the position
	 */
	private int payRate = 0;
	
	/** 
	 *  The arraylist of applications for a position
	 */
	private ArrayList<Application> applications;
	
	/** 
	 *  The constructor that constructs a position with given name, hoursperweek
	 *  and payrate
	 *  @param positionName the name of the poistion
	 *  @param hoursPerWeek the hours per week of the position
	 *  @param payRate the pay rate of the position
	 */
	public Position(String positionName, int hoursPerWeek, int payRate) {
		setPositionName(positionName);
		setHoursPerWeek(hoursPerWeek);
		setPayRate(payRate);
		
		
		applications = new ArrayList<Application>();
	}
	
	/** 
	 * sets the counter for the Application instances to the value 
	 * of the maximum id in the list of Applications for the position + 1.
	 */
	public void setApplicationId() {
		int maxid = 0;
		for (int i = 0; i < applications.size(); i++) {
			if(applications.get(i).getId() > maxid) {
				maxid = applications.get(i).getId();
			}
			
		}
		maxid += 1;
		
		Application.setCounter(maxid);
	}
	
	/** 
	 * sets the name of a position
	 * @param positionName name of the position
	 * @throws IllegalArgument if position name is null or empty.
	 */
	private void setPositionName(String positionName) {
		if (positionName == null || "".equals(positionName)) {
			throw new IllegalArgumentException("Position cannot be created.");
		} else {
			this.positionName = positionName;
		}
	}
	
	/** 
	 * Gets the name of a position
	 * @return null the position name
	 */
	public String getPositionName() {
		return this.positionName;
	}
	
	/** 
	 * sets the hours per week of a position
	 * @param hoursPerWeek the hours per week of the position
	 * @throws IllegalArgumentException if the hoursPerweek are out of the given range
	 */
	private void setHoursPerWeek(int hoursPerWeek) {
		if (hoursPerWeek < 5 || hoursPerWeek > 20) {
			throw new IllegalArgumentException("Position cannot be created.");
		} else {
			this.hoursPerWeek = hoursPerWeek;
		}
	}
	
	/** 
	 * Gets the hours per week of a position
	 * @return 0 the hours per week of a position
	 */
	public int getHoursPerWeek() {
		return this.hoursPerWeek;
	}
	
	/** 
	 * sets the pay rate of a position
	 * @param payRate the payrate of the position
	 * @throws IlegalArgumentException if payRate is out of given range
	 */
	private void setPayRate(int payRate) {
		if (payRate < 7 || payRate > 35) {
			throw new IllegalArgumentException("Position cannot be created.");
		} else {
			this.payRate = payRate;
		}
	}
	
	/** 
	 * Gets the pay rate of a position
	 * @return 0 the pay rate of a position
	 */
	public int getPayRate() {
		return this.payRate;
	}
	
	/** 
	 * creates a new Application in the submitted state, 
	 * adds it to the list in sorted order, and returns the id.
	 * @param firstName the first name of applicant
	 * @param surname the surname of the applicant
	 * @param unityId the unityid of the applicant
	 * @return 0 the id of the application
	 */
	public int addApplication(String firstName, String surname, String unityId) {

		setApplicationId(); //
		Application newApplication = new Application(firstName, surname, unityId);
		
		return addApplication(newApplication);
	}
	
	/** 
	 * Adds the application to the list in sorted order by id.
	 * @param application the application to the list in sorted order
	 * @return 0 the id of the application
	 * @throws IllegalArgumentException if an application already exists
	 * with the given id
	 */
	public int addApplication(Application application) {
		setApplicationId(); //
		for (int i = 0; i < applications.size(); i++) {
			if(application.getId() == applications.get(i).getId()) {
				throw new IllegalArgumentException("Application cannot be created.");
			} else if (application.getId() < applications.get(i).getId()) {
				applications.add(i, application);
				return application.getId();
			}
		}
		applications.add(application);
		return application.getId();
	}
	
	/** 
	 * Returns the List of Applications.
	 * @return the list of applications
	 */
	public ArrayList<Application> getApplications() {
		return this.applications;
	}
	
	/** 
	 * returns the Application in the list with the given id. 
	 * If there is no Application with that id, the method returns null.
	 * @param id of the application that is to be returned from the list
	 * @return null there is no application with the id
	 * and returns the application found in the list using the id
	 */
	public Application getApplicationById(int id) {
		for(int i = 0; i < applications.size(); i++) {
			if(id == applications.get(i).getId()) {
				return applications.get(i);
			}
		}
		
		return null;
	}
	
	/** 
	 * returns the Application in the list with the given id. 
	 * If there is no Application with that id, the method returns null.
	 * @param id of the application that is to be returned from the list
	 * @param c the command to be executed
	 * 
	 */
	public void executeCommand(int id, Command c) {
		for (int i = 0; i < applications.size(); i++) {
			if (id == applications.get(i).getId()) {
				applications.get(i).update(c);
			}
		}
	}
	
	/** 
	 * Removes the Application with the given id from the list. 
	 * If there is no Application to remove, the list doesn’t change.
	 * 
	 * @param id of the application that is to be removed from the list
	 */
	public void deleteApplicationById(int id) {
			for (int i = 0; i < applications.size(); i++) {
				if (applications.get(i).getId() == id) {
					applications.remove(i);
				}
			}
	}
	
	/** 
	 * Returns the string representation of the Position that is printed
	 * during file save operations
	 * 
	 * @return the string representation of postion
	 */
	public String toString() {
		
		return "# " + getPositionName() + "," + getHoursPerWeek() + "," + getPayRate();
		
	}
}
