/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.io;
import edu.ncsu.csc216.wolf_tickets.model.group.Group;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Category;
import edu.ncsu.csc216.wolf_tickets.model.tickets.Ticket;
import edu.ncsu.csc216.wolf_tickets.model.util.ISortedList;
import edu.ncsu.csc216.wolf_tickets.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tickets.model.util.SwapList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


/**
 * The writer method that writes a Group to a file
 * @author Carolina Adri Lima
 * @author Lalitha Edupuganti
 *
 */
public class GroupWriter {

	/**
	 * Method that receives the name of a file, name of a Group and a list of Categories to
	 * be saved and exported into the given file name with the desired data format. Throws an
	 * Exception if unable to save the file
	 * @param groupFile name of the file to receive the information
	 * @param groupName name of the group being exported
	 * @param categories the list of Categories being exported
	 */
	public static void writeGroupFile(File groupFile, String groupName, ISortedList<Category> categories) {
		PrintStream fileWriter;
		
		Group groupToWrite = new Group(groupName);
		
		ISwapList<Ticket> ticketsList = new SwapList<Ticket>();
		
		
		try {
			fileWriter = new PrintStream(new FileOutputStream(groupFile));
				
				fileWriter.println("! " + groupToWrite.getGroupName());
			
				for (int i = 0; i < categories.size(); i++) {
				
					ticketsList = categories.get(i).getTickets();
					
					fileWriter.println("# " + categories.get(i).getCategoryName().toString() + "," + categories.get(i).getCompletedCount());
					
					for(int j = 0; j < ticketsList.size(); j++) {
						fileWriter.println(ticketsList.get(j).toString());
					}
				}
			
				fileWriter.close();

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
		
	}
}

