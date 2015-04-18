package lab5a;

import java.util.Scanner;

/**
 * @author William Taylor-Holubeshen
 * @studentId 000305063
 * @date Apr 10 2013
 * @statement I, William Taylor-Holubeshen, 000305063 certify that this material is my original work. 
 * No other person's work has been used without due acknowledgement.
 * @purpose The Lab5a class will take in 3 arguments when launched. A source file path, 
 * destination file path and a command. The class will then prompt the user for a 
 * cipher key. It will then read the source file to encode or decode the file using
 * the Vigenere Cipher. The output will be saved to the destination file.
 */
public class Lab5a {

    public static void main(String[] args) {
        
        if(args.length != 3){
            System.err.println("Not enough arguements");
            System.exit(1);
        }
        
        //get cipher key from user
        System.out.print("Enter your encoding key: ");
        Scanner input = new Scanner(System.in);
        String cipherKey = input.nextLine().replaceAll("[^a-zA-Z]","");
     
        Vigenere vigenere = new Vigenere();
        
        //set source and destination file paths
        vigenere.setSource(args[0]);
        vigenere.setDestination(args[1]);
        
        //encode file
        if(args[2].equalsIgnoreCase("/e")){
            System.out.println("Original file: "+ args[0]);
            System.out.println("Encoded file: " + args[1]);
            System.out.println("Encryption key: "+ cipherKey);
            vigenere.encode(cipherKey);
            System.out.println("Encryption complete!");
        }
        
        //decode file
        else if(args[2].equalsIgnoreCase("/d")){
            System.out.println("Original file: "+ args[0]);
            System.out.println("Decoded file: " + args[1]);
            System.out.println("Encryption key: "+ cipherKey);
            vigenere.decode(cipherKey);
            System.out.println("Decryption complete!");
        }
        
        //Throw error if the user does not input a proper command
        else{
            System.out.println("An error has occured. Please check the command and try again");
        }
        
    }
    
}
