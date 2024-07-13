/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


import edu.ncsu.csc216.wolf_tickets.model.group.Group;
import edu.ncsu.csc216.wolf_tickets.model.tickets.AbstractCategory;

import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Ticket;


/**
 * The GroupReader class is used to read an external file and process its contents as a Group,
 * Category and Ticket objects. It is able to process the whole file, as well as the text in it
 * in order to correctly generate the new objects. 
 * @author Carolina Adri Lima
 * @author Lalitha Edupuganti
 *
 */
public class GroupReader {

	/**
	 * Method used to read an external file that contains the information/data
	 * to be read and stored as Group, Category, and Ticket objects. Throws an exception
	 * if file cannot be loaded.
	 * @param groupFile name of the File to be read
	 * @return a Group object containing the File's information
	 */
	public static Group readGroupFile(File groupFile) {
		Group newGroup;
	
		String fileFullText = "";
		
		try {
			Scanner fileReader = new Scanner(new FileInputStream(groupFile));
			
			fileFullText += fileReader.nextLine();
					
			while(fileReader.hasNextLine()) {
				fileFullText += "\n" + fileReader.nextLine();
			}
			
			if(fileFullText.charAt(0) != '!') {
				throw new IllegalArgumentException("Unable to load file.");
			}
			
			
			Scanner readLines = new Scanner(fileFullText);
			 
			String groupName = readLines.nextLine().substring(1).trim();
			
			newGroup = new Group(groupName);
			
			readLines.useDelimiter("\\r?\\n?[#]");
			
//			readLines.next(); //added extra
			
				while(readLines.hasNextLine()) { 
					try {	 
					Category newCategory = processCategory(readLines.next());
						
					newGroup.addCategory(newCategory);	
					
					for(int i = 0; i < newCategory.getTickets().size(); i++) {
						if(newCategory.getTicket(i).isActive()) {
							newGroup.setCurrentCategory("Active Tickets");
							newGroup.addTicket(newCategory.getTicket(i));
						}
					}
	
					} catch (IllegalArgumentException e) {
						//empty should do nothing to skip invalid applications
					}
				}
				fileReader.close();
				readLines.close();
				
	 
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		
		
		newGroup.setCurrentCategory("Active Tickets"); 
		newGroup.setChanged(false);
		
		return newGroup;
	
	}
	
	/**
	 * A helper method to read the file data and transform it into a 
	 * Category object
	 * @param categoryText a string containing the information about the Category object
	 * @return a Category object that was read from the file
	 */
	private static Category processCategory(String categoryText) {
		 Category newCategory = null;
		 
//		 ActiveTicketList newActiveList = new ActiveTicketList(); //attempt active ticket list
		 
		 Scanner lineScanner = new Scanner(categoryText);
		 
		 
		 
		 lineScanner.useDelimiter("\\r?\\n?[*]");
		 
//		 try {
		 newCategory = processCategoryLine(lineScanner.nextLine());

		 while(lineScanner.hasNextLine()) {
			 Ticket newTicket;
			 try {
				 newTicket = processTicket(newCategory, lineScanner.next());
				 
				 newCategory.addTicket(newTicket);
				 
//				 newActiveList.addTicket(newTicket); //attempt to fix active ticket list ticket count
				 
			 } catch (NullPointerException | IllegalArgumentException e) {
				 //empty should do nothing to skip invalid applications
			 }
		 }
		 lineScanner.close();
		 
		 return newCategory;
//		 } catch (IllegalArgumentException e) {
//			 //empty should do nothing to skip invalid positions
//		 }
	}
	
	 private static Category processCategoryLine(String positionLine) {
		 Category newCategoryLine;
		 String categoryName = "";
		 int completedCount = 0;
		 
		 Scanner lineScanner = new Scanner(positionLine.trim());
				lineScanner.useDelimiter(",");
				
		if(lineScanner.hasNext()) {
				categoryName = lineScanner.next();
				if(lineScanner.hasNextInt()) {
					completedCount = lineScanner.nextInt();
				} else {
					throw new IllegalArgumentException("Position cannot be created.");
				}
				
		}
		
		lineScanner.close();
		
		newCategoryLine = new Category(categoryName, completedCount);
		return newCategoryLine;
		 
	 }
	 
	/**
	 * A helper method to read the file data and transform it into a 
	 * Ticket object
	 * @param category the category for the ticket being processed
	 * @param ticketText the text for a ticket being processed
	 * @return a Ticket object that was read from the file
	 */
	private static Ticket processTicket(AbstractCategory category, String ticketText) {
		Ticket newTicket = null;
		
		String ticketName = "";
		String ticketDescription = "";
		Boolean isActive = false;
		
		Scanner lineScanner = new Scanner(ticketText.trim());
		
		//Note: Use of split was referenced from a suggestion in a piazza post
		//found during debugging
		String [] firstLine = lineScanner.nextLine().trim().split(",");
		
		
		if(!firstLine[0].equals("")) {
			ticketName = firstLine[0].trim();
			if(firstLine.length > 1 && firstLine[1].equalsIgnoreCase("active")) {
					isActive = true;
			}
			
			if(lineScanner.hasNextLine()) {
				ticketDescription = lineScanner.nextLine();
			}
			newTicket = new Ticket(ticketName, ticketDescription, isActive);
		}
		
		return newTicket;
		
	}
}