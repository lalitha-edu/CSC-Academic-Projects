/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.tickets;

import edu.ncsu.csc216.wolf_tickets.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tickets.model.util.SwapList;

/**
 * The ticket class knows its name, description, if it is active, as well as the list of categories.
 * It is able to add a Category, complete a ticket and set the ticket as active.
 * @author Carolina Adri Lima
 *
 */
public class Ticket {

	/** A string that holds the name of the ticket */
	private String ticketName;
	/** A string that holds the description of the ticket */
	private String ticketDescription;
	/** A boolean that stores if the ticket is active or not */
	private boolean active;
	/** The category that the ticket belongs to */
	private ISwapList<AbstractCategory> categories;
	
	/**
	 * Constructor method of the Ticket object which receives a name and a description for the ticket
	 * as well as whether the ticket is active or not.
	 * @param ticketName the name of the ticket
	 * @param ticketDescription the description of the ticket
	 * @param active whether the ticket is active or not
	 */
	public Ticket(String ticketName, String ticketDescription, boolean active) {
		setTicketName(ticketName);
		setTicketDescription(ticketDescription);
		setActive(active);
		categories = new SwapList<AbstractCategory>();
	}
	
	/**
	 * A method that returns the name of the ticket when called
	 * @return a string containing the name of the ticket
	 */
	public String getTicketName() {
		return ticketName;
	}
	
	/**
	 * A method that sets the ticketName field to the String parameter received
	 * @param ticketName the name to be attributed to the ticket
	 * @throws IllegalArgumentException if parameter is empty or null
	 */
	public void setTicketName(String ticketName) {
		if (ticketName == null || "".equals(ticketName)) {
			throw new IllegalArgumentException("Incomplete ticket information.");
		}
		this.ticketName = ticketName;
	}
	
	/**
	 * Method that returns the description of the ticket when called
	 * @return a string containing the description of the ticket
	 */
	public String getTicketDescription() {
		return ticketDescription;
	}
	
	/**
	 * A method that sets the ticketDescription field to the String parameter received
	 * @param description the description to be attributed to the ticket
	 * @throws IllegalArgumentException if parameter is empty or null
	 */
	public void setTicketDescription(String description) {
		if (description == null) {
			throw new IllegalArgumentException("Incomplete ticket information.");
		}
		ticketDescription = description;
	}
	
	/**
	 * Boolean that returns if the ticket is active or not
	 * @return true if ticket is active, false otherwise
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Method that sets the active field to true if ticket is active, false otherwise
	 * @param active boolean which determines whether or not ticket is active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Method used to get the name of the Category that the ticket belongs to
	 * @return a String containing the name of the Category that the ticket belongs to
	 * @throws IllegalArgumentException if parameter is empty or null
	 */
	public String getCategoryName() {
		if (categories == null || categories.size() == 0) {
			return "";
		} else {
			return categories.get(0).getCategoryName();
		}
	}
	
	/**
	 * Method that allows the Category to add itself to the ticket once the ticket is assigned a Category
	 * @param category the Category that the ticket belongs to
	 * @throws IllegalArgumentException if parameter is null
	 */
	public void addCategory(AbstractCategory category) {
		if (category == null) {
			throw new IllegalArgumentException("Incomplete ticket information");
		}
		boolean isDuplicate = false;
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).equals(category)) {
				isDuplicate = true;
			}
		}
		if (!isDuplicate) {
			categories.add(category);
		}
	}
	
	/**
	 * Method used to set the ticket as complete and notify its Categories
	 */
	public void completeTicket() {
		for(int i = 0; i < categories.size(); i++) {
			categories.get(i).completeTicket(this);
		}
	}
	
	/**
	 * Method used to return a String representation of teh Ticket for a printing file.
	 * @return string that is returned based on the format chosen in the toString method
	 */
	public String toString() {
		if (active) {
			return "* " + getTicketName() + ",active\n" + getTicketDescription();
		} else {
			return "* " + getTicketName() + "\n" + getTicketDescription();
		}
	}

 }
