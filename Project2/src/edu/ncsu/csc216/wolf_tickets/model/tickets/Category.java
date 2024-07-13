/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.tickets;

/**
 * The Category object extends the AbstractCategory class. It is able to create an object
 * using its name and an integer representing the number of completed tickets under the category.
 * It is also able to get an array of the tickets under the Category and compare two Category names
 * to avoid duplicates.
 * @author Lalitha Edupuganti
 *
 */
public class Category extends AbstractCategory implements Comparable<Category> {

	/**
	 * Constructor used to construct a category object with the parametes
	 * of categoryName and completedCount passed into the AbstractCategory
	 * parent constructor
	 * @param categoryName is the name of the category object
	 * @param completedCount the completedCount for the category type tickets
	 */
	public Category(String categoryName, int completedCount) {
		super(categoryName, completedCount);
		
	}

	/**
	 * Returns a 2D String array where the first column is the priority
	 * of the Ticket, which is the index of the ticket in the list of tickets,
	 * and the name of the Ticket.
	 * @return 2D array of tickts with the name of the ticket with the list of tickets
	 */
	@Override
	public String[][] getTicketsAsArray() {
		String [][] ticketArray = new String [getTickets().size()][2];
		for(int i = 0; i < getTickets().size(); i++) {
			ticketArray[i][0] = i + "";
			ticketArray[i][1] = getTicket(i).getTicketName();
			
		}
		return ticketArray;
	}

	/**
	 * Compares the names of the Categories
	 * @param otherCategory the category to be compared
	 * @return int the value between the two category names
	 * that are compared
	 */
	@Override
	public int compareTo(Category otherCategory) {
		if (getCategoryName().compareToIgnoreCase(otherCategory.getCategoryName()) < 0) {
            return -1;
        } else if (getCategoryName().compareToIgnoreCase(otherCategory.getCategoryName()) > 0) {
            return 1;
        } else {
        	return 0;
        }
		
	}
	
	

}
