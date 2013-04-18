package forShell;


/** Ching Yu
 * 	March 10, 2013
 *  Creating a Shell Interface Using Java
 *  Modifying a Java program so that it serves as
 *  a shell interface that accepts user commands
 *  and then executes each command in a separate
 *  process external to the Java virtual machine.
 *  
 *  Changing Directories
 *  	Use cmd.exe /c echo hello world for testing
	 	Use cmd.exe /c cd .. 
 */
import java.io.*;
import java.util.*;

public class SimpleShellPart2 {

	public static String separator = "\\";

	public static void main(String [] args) throws java.io.IOException {

		String commandLine;
		BufferedReader console = 
				new BufferedReader(new InputStreamReader(System.in));
		ProcessBuilder pb = new ProcessBuilder();

		// We break out with <Control><C>
		while(true) {
			// Reader user input
			System.out.print("jsh>");
			commandLine = console.readLine();

			// If they entered a return, just loop again
			if(commandLine.equals("")) {
				continue;
			}

			// Now parse the input to obtain the commands and params
			// Create a StringTokenizer object and ArrayList object
			StringTokenizer st = new StringTokenizer(commandLine);
			ArrayList<String> myCommands = new ArrayList<String>();

			// Store commands 
			while (st.hasMoreTokens()){
				myCommands.add(st.nextToken());
			}//while
			
			if (myCommands.contains("cd")){
				if (myCommands.get(myCommands.size()-1).equals("cd")){
					File home = new File(System.getProperty("user.home"));
					System.out.println("The home directory is " + home);
					pb.directory(home);
					continue;
				}else{
					String argument = myCommands.get(myCommands.size()-1);
					System.out.println("The argument passed is " + argument);
					String createPath =  pb.directory() + separator + argument;
					System.out.println("The path created  is " + createPath);
					File newPath = new File(createPath);
					pb.directory(newPath);
					continue;
				}//else
			}//check

			// Create the Process builder object
			pb.command(myCommands);

			// Now start the process
			Process process = pb.start();

			// Obtain the output stream
			InputStream is = process.getInputStream();
			// Read what the process returned
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null){
				// Content returned by the command
				System.out.println(line);
			}//while
			br.close();
		}//while still in jsh
		
	}// main method
}// SimpleShellPart2 class
