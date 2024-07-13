/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.tickets;

import edu.ncsu.csc216.wolf_tickets.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tickets.model.util.SwapList;

/**
 * AbstractCategory abstract class controls a list of tickets
 * a category name along with a number of completed tickets.
 * 
 * @author Lalitha Edupuganti
 *
 */
public abstract class AbstractCategory {
	
	/**
	 * CategoryName string field for the AbstractCategory
	 */
	private String categoryName = "";
	
	/**
	 * The count field for to count the number of completed tickets
	 */
	private int completedCount = 0;
	
	/**
	 * The field for a list of tickets that can be assigned to a specific category
	 */
	private ISwapList<Ticket> tickets;
	
	/**
	 * Constructor for the classa and sets the using the parameters
	 * for categoryName and completedCount
	 * Constructs a SwapList for the Tickets
	 * @param categoryName the name of the Category
	 * @param completedCount the count of the number of completed tickets
	 * @throws IllegalArgumentException for if the categoryName is empty or null
	 * and if the completed count is less than zero
	 */
	public AbstractCategory(String categoryName, int completedCount) {
		setCategoryName(categoryName);
		if (completedCount < 0) {
			throw new IllegalArgumentException("Invalid completed count.");
		}
		this.completedCount = completedCount;
		tickets = new SwapList<Ticket>();
	}
	
	/**
	 * Constructor for the classa and sets the using the parameters
	 * for categoryName and completedCount
	 * Constructs a ISwapList for the Tickets
	 * @return string the CategoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	
	/**
	 * Setter method to set the categoryName field
	 * @param categoryName the name that will be set to the field value
	 * @throws IllegalArgumentException for if the categoryName is empty or null
	 */
	public void setCategoryName(String categoryName) {
		if (categoryName == null || "".equals(categoryName)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.categoryName = categoryName;
	}
	
	/**
	 * Getter method that gets the SwapList of tickets
	 * @return the SwapList of tickets for the field
	 */
	public ISwapList<Ticket> getTickets() {
		return tickets;
	}
	
	/**
	 * Getter method that gets the CompletedCount variable
	 * @return int the completed count
	 */
	public int getCompletedCount() {
		return completedCount;
	}
	
	/**
	 * Adds the Ticket to the end of the list. The current instance
	 * of the Category adds itself to the Ticket
	 * @param t the ticket that is to be added
	 */
	public void addTicket(Ticket t) {
		tickets.add(t);
		t.addCategory(this);
	}
	
	/**
	 * Removes the Ticket from the list of tickets and returns the removed ticket.
	 * This can be delegated to the ISwapList
	 * @param idx index at which the ticket must be removed
	 * @return ticket the ticket removed from the list of tickets
	 */
	public Ticket removeTicket(int idx) {
		return tickets.remove(idx);
	}
	
	/**
	 * Returns the Ticket at the given index. This can be delegated to the ISwapList.
	 * @param idx the index at which a ticket is to be gotten
	 * @return ticket at the given index
	 */
	public Ticket getTicket(int idx) {
		return tickets.get(idx);
	}
	
	/**
	 * Finds the given Ticket in the list and removes it. 
	 * The completedCount is incremented.
	 * @param t the ticket which is removed from the list
	 */
	public void completeTicket(Ticket t) {
		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.get(i) == t) {
				tickets.remove(i);
				this.completedCount++;
			}
		}
	}
	
	/**
	 * An abstract method that returns a 2D String array. 
	 * The contents of the array are left for the child classes to define.
	 * @return 2D array of tickets as an array
	 */

	public abstract String[][] getTicketsAsArray();

}
