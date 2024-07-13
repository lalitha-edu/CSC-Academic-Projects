/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.group;

import java.io.File;

import edu.ncsu.csc216.wolf_tickets.model.io.GroupWriter;
import edu.ncsu.csc216.wolf_tickets.model.tickets.AbstractCategory;
import edu.ncsu.csc216.wolf_tickets.model.tickets.ActiveTicketList;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Ticket;
import edu.ncsu.csc216.wolf_tickets.model.util.ISortedList;
import edu.ncsu.csc216.wolf_tickets.model.util.SortedList;

/**
 * The Group class knows its name, if there was a change, its categories, the current category,
 * as well as the active ticket list. Group is able to add and edit both tickets and categories,
 * as well as remove Category and save and export to a file
 * @author Carolina Adri Lima
 * @author Lalitha Edupuganti
 *
 */
public class Group {

	/** Field that contains the name of the Group */
	private String groupName;
	/** Field that keeps track if the Group has changed or not */
	private boolean isChanged;
	/** Field that contains the list of categories in alphabetica order */
	private ISortedList<Category> categories;
	/** Field that contains the list of active tickets */
	private ActiveTicketList activeTicketList;
	/** Field that keeps track of the current category being displayed */
	private AbstractCategory currentCategory;
	/** Name of the Active Tickets Category */
	private static final String ACTIVE_TASKS_NAME = "Active Tickets";
	
	/**
	 * Constructor method for the Group object which receives the name to be attributed
	 * to the group
	 * @param groupName a string that represents the name to be given to the Group object
	 */
	public Group(String groupName) {
		setGroupName(groupName);
		setChanged(true);
		categories = new SortedList<Category>();
		activeTicketList = new ActiveTicketList();
		currentCategory = activeTicketList;
	}
	
	/**
	 * Method used to save and export the group into  afile
	 * @param groupFile the name of the file to be exported to
	 */
	public void saveGroup(File groupFile) {
		GroupWriter.writeGroupFile(groupFile, groupName, categories);
		setChanged(false);
		
	}
	
	/**
	 * Method used to get the name of the Group object
	 * @return a String containing the name of the Group object
	 */
	public String getGroupName() {
		return groupName;
	}
	
	/**
	 * Method used to set the name of the Group as the groupName field
	 * @param groupName the name to be attributed to the Group object
	 */
	private void setGroupName(String groupName) {
		if (groupName == null || "".equals(groupName) || ACTIVE_TASKS_NAME.equals(groupName) ) {
			throw new IllegalArgumentException("Invalid name.");
		}
		this.groupName = groupName;
	}
	
	/**
	 * Method that is used to get the isChanged field which determines
	 * whether the Group object has been changed or not
	 * @return false if the Group is saved, true otherwise
	 */
	public boolean isChanged() {
		return isChanged;
	}
	
	/**
	 * Method that is used to update and set the value of the isChanged field
	 * @param changed a boolean that determines whether or not the Groupd has changed since the last save
	 */
	public void setChanged(boolean changed) {
		isChanged = changed;
	}
	
	/**
	 * Method used to add a new Category to the list of categories as long as
	 * the cateogry name is not a duplicate or Active, which will cause an Exception
	 * to be thrown
	 * @param category the Categry to be added to the list of categories
	 */
	public void addCategory(Category category) {
		if (ACTIVE_TASKS_NAME.equalsIgnoreCase(category.getCategoryName())) {
			throw new IllegalArgumentException("Invalid name.");
		}
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getCategoryName().equalsIgnoreCase(category.getCategoryName())) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}
		categories.add(category);
		currentCategory = category;
		setChanged(true);
	}
	
	/**
	 * Method used to return the list of the name of the Categories in the Group
	 * @return a String array containing the name of the Categories in the categories list
	 */
	public String[] getCategoriesNames() {
		String [] categoriesName = new String [categories.size() + 1];
		
		categoriesName[0] = activeTicketList.getCategoryName();
		
		for(int i = 0; i < categories.size(); i++) {
			categoriesName[i + 1] = categories.get(i).getCategoryName();
		}
		
		return categoriesName;		
	}
	
	/**
	 * A helper method to work with the ActiveTicketList allowing the user to build
	 * the ActiveTicketList each time there is a change in by iterating through all
	 * of the Categories and active Tickets.
	 */
	private void getActiveTicketList() {
		
		activeTicketList.clearTickets();
		for(int i = 0; i < categories.size(); i++) {
			Category cat = categories.get(i);
			for (int j = 0; j < cat.getTickets().size(); j++) {
				Ticket t = cat.getTicket(j);
				if (t.isActive()) {
					activeTicketList.addTicket(t);
				}
			}
		}
	}
	
	/**
	 * Sets the currentCategory field to the AbstractCategory with the given name
	 * in the parameter.
	 * @param categoryName the name of the AbstractCategory to be set
	 */
	public void setCurrentCategory(String categoryName) {
		if (categoryName == ACTIVE_TASKS_NAME) {
			getActiveTicketList();
			currentCategory = activeTicketList;
		} else {
			for (int i = 0; i < categories.size(); i++) {
				if (categoryName.equals(categories.get(i).getCategoryName())) {
					currentCategory = categories.get(i);
				}
			}
		}
	}
	
	/**
	 * Method used to return the current Category as an AbstractCategory
	 * @return the AbstractCategory that is the current Category
	 */
	public AbstractCategory getCurrentCategory() {
		return currentCategory;
	}
	
	/**
	 * Method used to edit the name of the currentCategory, throws an IAE
	 * if the name is a duplicate or matches the ActiveTicketList name
	 * @param categoryName the new name to be attributed to the currentCategory
	 */
	public void editCategory(String categoryName) {
		if(ACTIVE_TASKS_NAME.compareToIgnoreCase(categoryName) == 0 || currentCategory.getCategoryName().equals(categoryName)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		if (currentCategory.getCategoryName() == ACTIVE_TASKS_NAME) {
			throw new IllegalArgumentException("The Active Tickets list may not be edited.");
		}
		
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getCategoryName().equalsIgnoreCase(categoryName)) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}
		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getCategoryName().equals(currentCategory.getCategoryName())) {
				Category catToChange = categories.remove(i);
				
				catToChange.setCategoryName(categoryName);
				categories.add(catToChange);
				
				isChanged = true;
			}
		}
	}
	
	/**
	 * Method used to remove the currentCategory form the categories list. IAE is thrown
	 * if the currentCategory is an ActiveTicketList
	 */
	public void removeCategory() {
		if (currentCategory.getCategoryName() == ACTIVE_TASKS_NAME) {
			throw new IllegalArgumentException("The Active Tickets list may not be deleted.");
		}
		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getCategoryName().equals(currentCategory.getCategoryName())) {
				categories.remove(i);
				currentCategory = activeTicketList;
			}
			
		}
	}
	
	/**
	 * Method used to add a new ticket to the currentCategory 
	 * @param ticket the ticket to be added
	 */
	public void addTicket(Ticket ticket) {

			if (currentCategory instanceof Category) {
				
				currentCategory.addTicket(ticket);
				
				if (ticket.isActive()) {
					
					getActiveTicketList();
				}
				isChanged = true;
				
			}	
	}
	
	/**
	 * Mehtod used to edit a ticket's field(s) in the currentCategory
	 * @param idx the index of teh Ticket on the list
	 * @param ticketName the name of the Ticket
	 * @param ticketDescription the description of the Ticket
	 * @param active whether the Ticket is active or not
	 */
	public void editTicket(int idx, String ticketName, String ticketDescription, boolean active) {
		
		if (currentCategory instanceof Category) {
			currentCategory.getTicket(idx).setTicketName(ticketName);
			currentCategory.getTicket(idx).setTicketDescription(ticketDescription);
			currentCategory.getTicket(idx).setActive(active);
			
			
			getActiveTicketList();
			
			
			isChanged = true;
		}
		
		

	}
}
