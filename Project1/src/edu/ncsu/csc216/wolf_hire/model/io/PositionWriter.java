package edu.ncsu.csc216.wolf_hire.model.io;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.manager.Position;


/**
 * The IO class that writes a list of positions to a given file 
 * 
 * @author Lalitha Edupuganti
 *
 */
public class PositionWriter {
	
	/**
     * Receives a String with the file name to write to and a List of Positions to write to file. 
     * PositionWriter should use Position’s and Application’s toString() method to create 
     * the properly formatted output for Position and Application.
     * 
     * @param fileName file to write the Position list to
     * @param positions the arraylist of positions to be written to a file
     * @throws IllegalArgumentException if any errors or exceptions occur
     */
	public static void writePositionsToFile(String fileName, ArrayList<Position> positions) {
		
		PrintStream fileWriter;
		
		ArrayList<Application> applicationList = new ArrayList<Application>();
		
		try {
			fileWriter = new PrintStream(new FileOutputStream(fileName));
//			try {
				for (int i = 0; i < positions.size(); i++) {
				
					applicationList = positions.get(i).getApplications();
					
					 if (applicationList.isEmpty()) {
	//					 fileWriter.close();
//						 throw new IllegalArgumentException("Position cannot be written");
						 continue;
					 }
					
					fileWriter.println(positions.get(i).toString());
					for(int j = 0; j < applicationList.size(); j++) {
						fileWriter.println(applicationList.get(j).toString());
					}
				}
			
				fileWriter.close();
//			} catch (IllegalArgumentException e) {
//				//empty positions with no applications are ignored
//			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
		
	}
	
}