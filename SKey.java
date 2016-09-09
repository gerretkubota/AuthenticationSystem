/********************************************************************************
*
*
*  Name: Gerret Kubota
*  Class: CS 460
*  Assignment: Final Project
*  Description: Creating a authentication system for users to log into a server.
*               The user can create/login into the server. If the user is creating
*               an account for the first time, it will ask to come up with a 
*               user name and password and entering the number of authentications
*               the person would want for each time they log in 
*               (the number they enter will be the number of times the password will
*               be hashed by using SHA-1). If the user is trying
*               to log into the server, the server will ask the user to input the
*               provided password that was stated in the previous logging in session.
*               As the passwords are used, it will be deleted.
*
*********************************************************************************/


import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.util.Stack;
import java.util.InputMismatchException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class SKey 
{
	// global static variables that will be used throughout the program.
	//private static String input = "";
	private static String userName = "";
	private static String password = "";
	private static String text = "";
	private static String tempUser = "";
  private static String hashedPW = "";
	private static int numOfAuth;
	private static int num;
  private static boolean status = false;
  private static boolean status2 = false;
	private final static String buff = "jl23l4kjvlk;j9230@#$@#kzvkl;1][p123]i13i90';/?`kja$#9";

	// This is the main method, where the actual program runs.
  public static void main(String[] args) throws IOException
  {
    // Scanner object will be used to get the user's inputs
  	Scanner kb = new Scanner(System.in);

  	// This is the while-loop for the menu to be displayed, keep displaying until the the user
  	// enters a value of 4 to exit the program.
      while(num != 4)
      {
      	try{
        // Prints the menu on the screen
        System.out.println("****************************************\n" +
                           "* Welcome to the Authentication System *\n" +
                           "****************************************");
    	  System.out.println("Choose an option: \n");
    	  System.out.println("[1] Create an account.");
    	  System.out.println("[2] Log in.");
    	  System.out.println("[3] Credits.");
    	  System.out.println("[4] Exit program.\n");
    	  // Obtain the user's input and store it into the variable num
    	  System.out.println("input> ");
    	  num = kb.nextInt();
        // Switch cases for particular values being inputed by the user.	
        switch(num)
        {
          case 1:
        	  kb.nextLine();
        	  System.out.println("****************************************\n" + 
                               "*           Create a UserID            *\n" +
                               "****************************************");
             // In this 1st case, the user will be prompted to create a user name, but if the user name that they
             // try to create already exists, it will keep asking the user to create a new one because
             // that user name already exists in the system.
             while(!status)
             {
        	     System.out.println("Enter a user name to create> ");
               userName = kb.nextLine().toLowerCase();
               // Opens the text file and checks to see if the user name already exists in the system.
               FileReader file = new FileReader("passwords.txt");
               BufferedReader br = new BufferedReader(file);
               String temp = "";
               while((temp = br.readLine()) != null)
               {
            	   String[] tempArray = temp.split(" ");
            	   tempUser = tempArray[0];
            	   if(userName.equals(tempUser))
            	   {
            		   System.out.println("That user name exists already! Try a different name");
                   status = false;
            		   break;
            	   }
                 else
                 {
                   status = true;
                 }
               }
             }
             status = false;
             // If the user name is successfully created, prompt the user to create a password.
             System.out.println("Enter a password for this user name> ");
             password = kb.nextLine();
             // Prompt the user to enter the number of authentications this account will hold.
             while(!status)
             {
               try{
                 System.out.println("Enter the number of authentications you would like for this account> ");
                 numOfAuth = kb.nextInt();
                 if(numOfAuth > 0)
                 {
                   status = true;
                 }
               }catch(InputMismatchException e){
                 System.out.println("Not a valid input.");
                 System.out.println();
                 kb.next();
                 continue;
                 }
             }
             status = false;           

             // This opens the passwords text file and writes the user name created by the user into the text file.
             try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("passwords.txt", true)))) {
               out.println("\n");
               out.println(userName);
               // close the text file.
               out.close();
             }catch (IOException e) {
               e.printStackTrace();
             }

             // If the user only wants one authentication password for this account,
             // then hash the password and store it into the variable hashedPW 
             // and set the password variable password as an empty string to
             // delete the password.
             if(numOfAuth == 1)
             {
               hashedPW = superSHA1Hash(password);
               password = "";
               // Open the text file and append the hashed password to the text file.
               try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("passwords.txt", true)))) {
               out.println("1. " + hashedPW);
               out.println();
               // close the text file.
               out.close();
               }catch (IOException e) {
                e.printStackTrace();
               }
             }
             // else statement with any values besides 1 is entered from the user
             else
             {
           	   // Create a stack object to store all the hashed passwords.
           	   // The purpose of storing the hashed passwords into the stack is to
           	   // obtain the passwords in a reverse matter; i.e. 20th password, 19th, ...n-1
               Stack<String> s = new Stack<>();
               // hash the password first
               hashedPW = superSHA1Hash(password);
               // then delete the password by setting the password variable as an empty string.
               password = "";
               // push the first hashed password into the stack.
               s.push(hashedPW);
               // Within this for-loop, hash the hashed password numOfAuth times (obtained from user),
               // then push that hashed password into the stack.
               for(int i = 0; i < numOfAuth-1; i++)
               {
                 hashedPW = superSHA1Hash(hashedPW);
                 s.push(hashedPW);
               }
               // Open the password text file, and store each of the passwords that poppped from the stack object.
               // Do this numOfAuth times in a for-loop. 
               try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("passwords.txt", true)))){
                 for(int i = numOfAuth; i > 0; i--)
                 {
                   out.println(i + ". " + s.pop());
                 }
                 out.println();
                 out.println();
                 // close the text file.
                 out.close();
                 }catch(IOException e){
               	  e.printStackTrace();
                 }
             }
             // Open the passwords text file.
             FileReader file = new FileReader("passwords.txt");
             BufferedReader br3 = new BufferedReader(file);
             text = "";
             // Read each of the line in the text file until the line contains the user name.
             while((text = br3.readLine()) != null)
             {
           	  // If the line being read equals to the user name that was created,
           	  // read the next line and split that line by splitting any text with a period and space
           	  // by using the regular expression. tempArray[0] should hold the nth number of the password and
           	  // tempArray[1] should hold the password. Print those 2 values.
           	  if(text.equals(userName))
           	  {
           	    text = br3.readLine();
           	    String[] tempArray = text.split("[\\.\\s]+");
           	    System.out.println("Your next password " + tempArray[0] + " is " + tempArray[1]);
           	    System.out.println();
           	  }
             } 
             // close the text file.
             file.close();
        	  break;
          case 2:
            kb.nextLine();
        	  // This 2nd case will allow existing users to log in to the server.
            // It will prompt the user to enter their existing user name.
        	  System.out.println("Enter user name> ");
            userName = kb.nextLine().toLowerCase();
            // Open the passwords text file.
            FileReader read = new FileReader("passwords.txt");
            BufferedReader br = new BufferedReader(read);
            // While-loop until the end of the text file.
            while((text = br.readLine()) != null)
            {
              // If the line being read matches the name matches by the entered user name,
              // go into the if statement.
              if(text.equals(userName))
              {
                // Read the next line
                text = br.readLine();
                // should split into 2 elements [0] = #, [1] = hash value
                String[] tempArray = text.split("[\\.\\s]+");
                // ask the user for the nth password
                System.out.println("Enter password for " + tempArray[0] + "> ");
                password = kb.nextLine();
                // if the password matches then print that they have successfully logged in
                // then provide the next password to log in next time
                if(password.equals(tempArray[1]))
                {
                  System.out.println("Successfully logged in!"); 
                  // Creates 2 files, one for the original and the other will be a temp.
                  // The purpose of this is to copy everything over and edit any changes
                  // to the temp file and then delete the original file and rename the
                  // temp file as the original file name.
                  File inputFile = new File("passwords.txt");
                  File tempFile = new File("myTempFile.txt");

                  BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                  BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                  // The variable text has the line of string that has the numerical value of which password
                  // and the actual password. This was already obtained in the earlier statements, this will
                  // be the line that will be removed.
                  String lineToRemove = text.trim();
                  String currentLine;

                  while((currentLine = reader.readLine()) != null) 
                  {
                    // trim newline when comparing with lineToRemove
                    String trimmedLine = currentLine.trim();
                    // if the line bing read equals to the line that needs to be deleted, it skips
                    if(trimmedLine.equals(lineToRemove)) 
      	              continue;
      	            // writes each of the line into the temp text file
                    writer.write(currentLine + System.getProperty("line.separator"));
                  }
                  writer.close();
                  reader.close(); 
                  // delete the original file
                  // then rename the temp file to the original text file name.
                  inputFile.delete();
                  tempFile.renameTo(inputFile);
                  status2 = true;
                }
                else
                {
                  System.out.println("The password you have entered is invalid. Please try again by " +
                                     "logging in again.");
                  System.out.println();
                  status2 = false;
                }
              }
            }
            read.close();
            if(text == null && status2 == false)
            {
          	  System.out.println("The user name you have entered does not exist.");
          	  System.out.println();
            }
            // reopen the files to obtain the next password and giving it to
            // the user so they can log in the next time..
            if(status2 == true)
            {
              FileReader readAgain = new FileReader("passwords.txt");
              BufferedReader br2 = new BufferedReader(readAgain);
              while((text = br2.readLine()) != null)
              {
                // If the line being read matches the user name
                // go to the if statement.
                if(text.equals(userName))
                {	
                  // Read the next line.
                  text = br2.readLine();
                  if(!(text.equals("")))
                  {
                  // split the line being read.
                  String[] tempArray = text.split("[\\.\\s]+");
                  System.out.println("Please write this down for the next log in!");
                  System.out.println("Your next password " + tempArray[0] + " is " + tempArray[1]);
                  System.out.println();
                  }
                  // If the next line being read is empty, then 
                  // delete the account because there are no more authentication passwords left.
                  else
                  {
                  	File inputFile = new File("passwords.txt");
                    File tempFile = new File("myTempFile.txt");

                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                    // The variable text has the line of string that has the numerical value of which password
                    // and the actual password. This was already obtained in the earlier statements, this will
                    // be the line that will be removed.
                    String lineToRemove = userName.trim();
                    String currentLine;

                    while((currentLine = reader.readLine()) != null) 
                    {
                      // trim newline when comparing with lineToRemove
                      String trimmedLine = currentLine.trim();
                      // if the line bing read equals to the line that needs to be deleted, it skips
                      if(trimmedLine.equals(lineToRemove)) 
        	              continue;
        	            // writes each of the line into the temp text file
                      writer.write(currentLine + System.getProperty("line.separator"));
                    }
                    writer.close();
                    reader.close(); 
                    // delete the original file
                    // then rename the temp file to the original text file name.
                    inputFile.delete();
                    tempFile.renameTo(inputFile);
                    System.out.println("This account has no more authentications." +
                                       "\nThe account is deleted.\n");
                  }
                }
              }
              status2 = false;
              readAgain.close();
            }
        	  break;
          case 3:
        	  // This will open a message dialog that states the copy rights
        	  JOptionPane.showMessageDialog(null, "Copyright © 2015 [Gerret Kubota] All rights reserved.",
        		                              "Copyrights", JOptionPane.INFORMATION_MESSAGE);
        	  break;
          case 4:
            // This will exit the program.
        	  System.out.println("Thank you for using our service!");
        	  System.exit(0);
        	  break;
          default: 
        	  System.out.println("Error. Please enter the correct numerical value displayed on the menu!");
        	  System.out.println();
        	  break;
        }
      }catch(InputMismatchException e){
  		  System.out.println("Invalid input.");
  		  System.out.println();
  		  kb.next();
  		  continue;
  	  }
    }
  }
   
  // This method will hash the entered string through the parameter and return hashed string.
  public static String superSHA1Hash(String pw)
  {
  	// If the string entered is a null, it will return null.
    if(pw == null)
    	return null;
    // Add "salt" to the entered string; in another words, the "salt", in this case, the buff
    // will be combined to the string being passed through the parameter and stored into 
    // the variable hashedPW. The purpose of this is to make the decrypting harder.
    hashedPW = pw + buff;

    try{
      // Create a MessageDigest objeect and obtain the kind of hashing the program should use,
      // in this case, the SHA-1 hash.
      MessageDigest msg = MessageDigest.getInstance("SHA-1");
      // Update the MessageDigest by getting the obtaining the array of bytes of the variable hashedPW
      // then offset of where to start the array, and get the length of the hashedPW string;
      msg.update(hashedPW.getBytes(), 0, hashedPW.length());
      // Create a big integer with the num of bits as 1, use the hashed value produced by msg.digest() to genereate a random value.
      // then convert that into a string of 16 (hexadeicmal).
      hashedPW = new BigInteger(1, msg.digest()).toString(16);
    }catch(NoSuchAlgorithmException e){
    	e.printStackTrace();
    }
    // return the result
    return hashedPW;
  }
}
