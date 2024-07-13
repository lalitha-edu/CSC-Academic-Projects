/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.tickets;

/**
 * ActiveTicketList that holds a list of the activeTickets
 * @author Lalitha Edupuganti
 *
 */
public class ActiveTicketList extends AbstractCategory {
	
	/**
	 * Constant holding the name of the “Active Tickets” list.
	 */
	public static final String ACTIVE_TASKS_NAME = "Active Tickets";
	
	
	/**
	 * Constructor constructs the ActiveTicketList with the expected
	 * name and no completed tickets
	 */
	public ActiveTicketList() {
		super(ACTIVE_TASKS_NAME, 0);
		
	}

	/**
	 *  Overrides the method to check that the Ticket is active 
	 *  before adding to the end of the ISwapList. If the Ticket is not active,
	 *  an IAE is thrown with the message “Cannot add ticket to Active Tickets.”
	 *  
	 *  @param t the ticket that is trying to be added to the active tickets list
	 *  @throws IllegalArgumentException if the ticket is not active
	 */
	@Override
	public void addTicket(Ticket t) {
		if (!t.isActive()) {
			throw new IllegalArgumentException("Cannot add ticket to Active Tickets.");
		}
		getTickets().add(t);
		t.addCategory(this);
	}

	/**
	 *  Overrides the method to ensure that the paramter value
	 *  matches the expected name of “Active Tickets”. If so, the name is set
	 *  
	 *  @param categoryName name of the category that is to be checked for expected name 
	 *  @throws IllegalArgumentException if the parameter categoryName does not matched the
	 *  expected name of "Active Tickets"
	 */
	@Override
	public void setCategoryName(String categoryName) {
		if(categoryName.equals(ACTIVE_TASKS_NAME)) {
			super.setCategoryName(categoryName);
		} else {
			throw new IllegalArgumentException("The Active Tickets list may not be edited.");
		}
	}
	
	/**
	 *  Returns a 2D String array where the first column 
	 *  is the name of the Category that the Ticket belongs 
	 *  to (or at least the Category at index 0) and the name of the Ticket.
	 *  
	 *  @return 2D array of tickets with the name of the category in the first column
	 */
	@Override
	public String[][] getTicketsAsArray() {
		String [][] ticketArray = new String [getTickets().size()][2];
		
		for(int i = 0; i < getTickets().size(); i++) {
			ticketArray[i][0] = getTickets().get(i).getCategoryName();
			//getTickets().get(i).getCategoryName();
			ticketArray[i][1] = getTickets().get(i).getTicketName();
			
		}
		return ticketArray;
	}
	
	/**
	 * Clears the ActiveTicketList of all Tickets. 
	 */
	public void clearTickets() {
		int sizeArray = this.getTicketsAsArray().length;
		for (int i = sizeArray - 1; i >= 0; i--) {
			this.removeTicket(i);
		}
	}
	
}
