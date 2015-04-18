package lab5a;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;

import java.lang.SecurityException;
import java.lang.IllegalStateException;

/**
 * The class Vigenere will encode/decode a file using the Vigenere Cipher
*/
public class Vigenere {
    
    private String source;
    private String destination;
    private Scanner input;
    private Formatter output;
    private static int pos = 0;
    private char[][] vigenere = new char[26][26];
    
    /**
     * The Constructor Method will Build the Vigenere character table array
     */
    public Vigenere(){
        pos = 0;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] data;
        data = alphabet.toCharArray();
            for(int i = 0; i < 26; i++){
                System.arraycopy(data, 0, vigenere[i], data.length - i, i);
                System.arraycopy(data, i, vigenere[i], 0, data.length - i);
            }
    }
    
    /**
     * The method setSource will set the source file's path
     * @param in is a String that represents the source files path
     */
    public void setSource(String in){
        source = in;
    }
    
    /**
     * The method setDestination will set the destination file's path
     * @param out is a String that represents the source files path
     */    
    public void setDestination(String out){
        destination = out;
    }
    
    /**
     * The method openFiles() will open both the source and destination file
     */
    private void openFiles(){
        
        //Open Source
        try{
           input = new Scanner(new File(source));
        }
        catch(FileNotFoundException fileNotFoundException){
            System.err.println("Error opening file.");
            System.exit(1);
        }
        
        //Open Destination
        try{
           output = new Formatter(destination);
        }
        catch(SecurityException securityException){
            System.err.println("You do not have write access to this file.");
            System.exit(1);
        }
        catch(FileNotFoundException fileNotFoundException){
            System.err.println("Error creating file.");
            System.exit(1);
        }
        
    }
    
    /**
     * The method closeFiles() will close both the source and destination file.
     */
    private void closeFiles(){
        
        //Close Source
        if(input != null){
            input.close();
        }
        
        //Close Destination
        if(output != null){
            output.close();
        }
        
    }
    
    /**
     * The method encode() will encode a selected file using the Vigenere Cipher.
     * the output will be written to the source file
     * @param cipherKey is a String that represents the cipher of the encryption  
     */
    public void encode(String cipherKey){
        cipherKey = cipherKey.toUpperCase();
        String encode = "";
        String line;
        int row; 
        int col; 
        
        //open source and destination files
        this.openFiles();
        try{
            
            //read line by line of the source file
            while(input.hasNextLine()){
                
                //read character by character of the line
                line = input.nextLine();
                for(int i = 0; i < line.length(); i++){
                    
                    //when cipher position reaches the last character, reset
                    if(pos == cipherKey.length()){
                        pos = 0;
                    }
                    
                    //get ascii value of the char then minus 65 to get vigenere row index
                    col = (int)cipherKey.charAt(pos) - 65;

                    /**
                     * Uppercase alphabet ASCII range
                     * append encoded character to encode string
                     * increment cipher character position
                     */
                    if((int)line.charAt(i) > 64 && (int)line.charAt(i) <= 90){
                        row = (int)(line.charAt(i)) - 65;
                        encode += this.vigenere[row][col];
                        pos++;
                    }
                    
                    /**
                     * Lowercase alphabet ASCII range
                     * append encoded character to encode string
                     * increment cipher character position
                     */
                    else if((int)line.charAt(i) > 96 && (int)line.charAt(i) <= 122){
                        row = (int)(line.charAt(i)) - 97;
                        encode += Character.toLowerCase(this.vigenere[row][col]);
                        pos++;
                    }
                    
                    //Any character that is not a letter will be added to the encode string
                    else{
                        encode += line.charAt(i);
                    }
                }
                
                //maintain linebreaks in decode string
                encode += "\n";
            }
            
        //save to the source file    
        output.format("%s", encode);
        }
        
        //Throw exception if the file cannot be read
        catch(IllegalStateException stateException){
            System.err.println("File could not be read.");
            System.exit(1);
        }
        
        //close both source and destination files
        this.closeFiles();
    }
    
    /**
     * The method decode() will decode a file that was previously encoded with the Vigenere Cipher.
     * the output will be written to the destination file
     * @param cipherKey is a String that represents the cipher of the encryption  
     */
    public void decode(String cipherKey){
        cipherKey = cipherKey.toUpperCase();
        String decode = "";
        String line;
        int row;
        int col = 0;
        
        //open source and destination files
        this.openFiles();
        try{
            //read line by line of the destination file
            while(input.hasNextLine()){
                
                //read character by character of the line
                line = input.nextLine();
                for(int i = 0; i < line.length(); i++){
                    
                    //when cipher position reaches the last character, reset
                    if(pos == cipherKey.length()){
                        pos = 0;
                    }
                    
                    //get ASCII value of the char then minus 65 to get vigenere row index
                    row = (int)cipherKey.charAt(pos) - 65;
                    
                    
                    //use row index to get col index of the matched letter
                    for(int x = 0; x < 26; x++){
                        if(this.vigenere[row][x] == Character.toUpperCase(line.charAt(i))){
                            col = x;
                        }             
                    }
                    
                    /**
                     * Uppercase alphabet ASCII range
                     * append decoded character to decode string
                     * increment cipher character position
                     */
                    if((int)line.charAt(i) > 64 && (int)line.charAt(i) <= 90){
                        decode += (char)(col + 65);
                        pos++;
                    }
                    
                    /**
                     * Lowercase alphabet ASCII range
                     * append decoded character to decode string
                     * increment cipher character position
                     */
                    else if((int)line.charAt(i) > 96 && (int)line.charAt(i) <= 122){
                        decode += (char)(col + 97);
                        pos++;
                    }
                    
                    //Any character that is not a letter will be added to the decode string
                    else{
                        decode += line.charAt(i);
                    }
                }
                
                //maintain linebreaks in decode string
                decode += "\n";
            }
            
            //save to the destination file
            output.format("%s", decode);
        }
        
        //Throw exception if the file cannot be read
        catch(IllegalStateException stateException){
            System.err.println("File could not be read.");
            System.exit(1);
        }
        
        //close both source and destination files
        this.closeFiles();
    }
    
}
