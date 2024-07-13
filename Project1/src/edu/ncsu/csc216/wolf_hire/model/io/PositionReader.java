package edu.ncsu.csc216.wolf_hire.model.io;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.manager.Position;


/**
 * The IO class that reads through a position file to load positions
 * and applications
 * @author Lalitha Edupuganti
 *comment
 */
public class PositionReader {
	
	/**
     * Reads position records from a file and generates a list of valid Positions.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * an IllegalArgumentException is thrown. 
     * 
     * @param fileName file to read Position records from
     * @return a list of valid Positions
     * @throws IllegalArgumentException if the file cannot be found or read 
     */
	public static ArrayList<Position> readPositionFile(String fileName) {
		ArrayList<Position> positionList = new ArrayList<Position>();
//		boolean isValid = false;
		String fileFullText = "";
		
		
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName));
			
			fileFullText += fileReader.nextLine();
					
			while(fileReader.hasNextLine()) {
				fileFullText += "\n" + fileReader.nextLine();
			}
			
			if(fileFullText.charAt(0) != '#') {
				return positionList;
			}
			
			Scanner readLines = new Scanner(fileFullText);
			 
			readLines.useDelimiter("\\r?\\n?[#]");
			
				while(readLines.hasNextLine()) { 
					try {	 
					positionList.add(processPosition(readLines.next()));	
					} catch (IllegalArgumentException e) {
						//empty should do nothing to skip invalid applications
					}
				}
				fileReader.close();
				readLines.close();
				
	 
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		 
		return positionList;
	}
	
	 private static Position processPosition(String positionText) {
		 Position newPosition;
		 Scanner lineScanner = new Scanner(positionText);
		 
		 lineScanner.useDelimiter("\\r?\\n?[*]");
		 
//		 try {
		 newPosition = processPositionLine(lineScanner.nextLine());

		 while(lineScanner.hasNextLine()) {
			 Application newApplication;
			 try {
				 newApplication = processApplication(lineScanner.next());
				 
				 newPosition.addApplication(newApplication);
			 } catch (IllegalArgumentException e) {
				 //empty should do nothing to skip invalid applications
			 }
		 }
		 lineScanner.close();
		 
		 if (newPosition.getApplications().size() == 0) {
			 throw new IllegalArgumentException("Position cannot be created");
		 }
		 return newPosition;
//		 } catch (IllegalArgumentException e) {
//			 //empty should do nothing to skip invalid positions
//		 }
	 }
	 
	 private static Position processPositionLine(String positionLine) {
		 Position newPosition;
		 String positionName = "";
		 int hoursPerWeek = 0;
		 int payRate = 0;
		 
		 Scanner lineScanner = new Scanner(positionLine.trim());
				lineScanner.useDelimiter(",");
				
		if(lineScanner.hasNext()) {
				positionName = lineScanner.next();
				if(lineScanner.hasNextInt()) {
					hoursPerWeek = lineScanner.nextInt();

					if(lineScanner.hasNextInt()) {
						payRate = lineScanner.nextInt();
					} else {
						throw new IllegalArgumentException("Position cannot be created.");
					}
				} else {
					throw new IllegalArgumentException("Position cannot be created.");
				}
				
		}
		
		lineScanner.close();
		
		newPosition = new Position(positionName, hoursPerWeek, payRate);
		return newPosition;
		 
	 }
	 
	 private static Application processApplication(String positionLine) {
		 Application newApplication;
		 int id = 0;
		 String state = "";
		 String firstName = "";
		 String surname = "";
		 String unityId = "";
		 String reviewer = null;
		 String note = null;

		 Scanner lineScanner = new Scanner(positionLine.trim());
			 
		lineScanner.useDelimiter(",");
		 	
		 if(lineScanner.hasNextInt()) {
				id = lineScanner.nextInt();
				if(lineScanner.hasNext()) {
					state = lineScanner.next();
					if(lineScanner.hasNext()) {
						firstName = lineScanner.next();
						if(lineScanner.hasNext()) {
							surname = lineScanner.next();
							if(lineScanner.hasNext()) {
								unityId = lineScanner.next();
								if(lineScanner.hasNext()) {
									reviewer = lineScanner.next();
									if(lineScanner.hasNext()) {
										note = lineScanner.next();
									}
								}
							}
						}
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid application.");
			}
		  lineScanner.close();
		  newApplication = new Application(id, state, firstName, surname, unityId, reviewer, note);
		 return newApplication;
	 }
	
}

