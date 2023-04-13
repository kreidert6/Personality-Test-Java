//Tyler Kreider
//TA: Jeremy Chen
//CSE 142
//Assignment #7
//05/29/20
//
//This program will read a file that has people's names and their 
//responses to a personality test. This program will determine that 
//person's personality type in 4 different categories based on their 
//answers.

import java.io.*;
import java.util.*;

public class Personality {
   public static final int CATEGORIES = 4; //4 different personality categories

   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      intro();
      System.out.print("input file name? ");
      String inputName = console.nextLine(); //determines what file to read
      System.out.print("output file name? ");
      String outputName = console.nextLine();//determines what file to print on
      Scanner input = new Scanner(new File(inputName));
      
      int count = getLength(input); 
       
      String[] names = new String [count / 2]; 
      String[] responses = new String [count / 2];
      Scanner input2 = new Scanner(new File(inputName));
      makeArrays(input2, names, responses);
      personalityType(outputName, names, responses);        
   }
   //this method explains the program to the reader
   public static void intro() {
      System.out.println("This program processes a file of answers to the"); 
      System.out.println("Keirsey Temperament Sorter. It converts the"); 
      System.out.println("various A and B answers for each person into"); 
      System.out.println("a sequence of B-percentages and then into a"); 
      System.out.println("four-letter personality type."); 
      System.out.println(); 
   }
   //this method will determine the amount of people in the file
   //Scanner input is the file the program is reading            
   public static int getLength(Scanner input) {
      int count = 0;
      while(input.hasNextLine()) {
         String line = input.nextLine();        
         count++;        
      } 
      return count;
   }
   
  //this method will fill the names array and responses array
  //Scanner input is the file the program is reading
  //String[] names is the array that holds each person's name
  //String[] responses is the array that holds each person's responses      
   public static void makeArrays(Scanner input, String[] names, String[] responses) {    
      int i = 0;
      while(input.hasNextLine()) {        
         names[i] = input.nextLine();
         responses[i] = input.nextLine();        
         i++;       
      }
   }
   
   //This method will create an output file to write on 
   //and will create arrays for aCount, bCount, and totalCount of responses per person,
   //then will find percentage of b answers.
   //String outputName is the name of the file created
   //String[] names is the array that holds each person's name
   //String[] responses is the array that holds each person's responses
   public static void personalityType(String outputName, String[] names, String[] responses) throws FileNotFoundException { 
      PrintStream output = new PrintStream(new File(outputName));     
      for(int i = 0; i < names.length; i++) {        
         int [] aCount = findA(responses[i]);         
         int [] bCount = findB(responses[i]);         
         int [] totalCount = findCount(aCount, bCount);                
         int [] bPercentages = calcPercent(aCount, bCount, totalCount);               
         String personality = findPersonality(bPercentages);         
         output.println(names[i] + ": " + Arrays.toString(bPercentages) + " =" + personality);              
      }          
   } 
   
   //This will fill the aCount array for how many A answers the person had
   //String response is the answers the person gave for each question
   public static int[] findA(String response) {
      int[] aCount = new int [CATEGORIES];  
      for(int i = 0; i < 70; i++) {
         String upper = response.toUpperCase();
         char letter = upper.charAt(i);     
         int rem = i % 7;
         if(letter == 'A') {
            if(rem == 0) {
               aCount[0]++;
            } 
            else if(rem == 1 || rem == 2) {
               aCount[1]++;
            }
            else if(rem == 3 || rem == 4) {
               aCount[2]++;
            }
            else if(rem == 5 || rem == 6) {         
               aCount[3]++;
            }        
         }
      }          
      return aCount;
   }
   
   //This will fill the bCount array for how many B answers the person had
   //String response is the answers the person gave for each question
   public static int[] findB(String response) {
      int[] bCount = new int [CATEGORIES];   
      for(int i = 0; i < 70; i++) {
         String upper = response.toUpperCase();
         char letter = upper.charAt(i);
            
         int rem = i % 7;
         if(letter == 'B') {
            if(rem == 0) {
               bCount[0]++;
            } 
            else if(rem == 1 || rem == 2) {
               bCount[1]++;
            }
            else if(rem == 3 || rem == 4) {
               bCount[2]++;
            }
            else if(rem == 5 || rem == 6) {         
               bCount[3]++;
            }        
         }
      } 
                 
      return bCount;
   }
   
   //This method wil find the total amount of A and B answers without dashes
   //int[] aCount is amount of A answers and int[] bCount is amount of B answers
   public static int[] findCount(int[] aCount, int[] bCount) {
      int[] totalCount = new int[CATEGORIES];
      for(int i = 0; i < CATEGORIES; i++) {
         totalCount[i] = aCount[i] + bCount[i];
      }
      return totalCount;
   
   }
  
   //This method will find the percentage of B answers from the total answer count
   //int[] aCount is amount of A answers and int[] bCount is amount of B answers
   //int[] totalCount are the number of total answers without dashes
   public static int[] calcPercent(int[] aCount, int[] bCount, int[] totalCount) {
      int[] bPercentages = new int [CATEGORIES];     
      for(int i = 0; i < CATEGORIES; i++) {        
         bPercentages[i] =  (int) Math.round((double) bCount[i] / (double) totalCount[i] * 100);        
      }      
      return bPercentages;
   }
         
   //This method will determine what personality the person is for each category
   //based on bPercentages. Int[] bPercentages is percent of B answers.
   public static String findPersonality(int[] bPercentages) {
      String personality = " ";
      String[] aTypes = new String[]{"E", "S", "T", "J"};
      String[] bTypes = new String[]{"I", "N", "F", "P"};
      for(int i = 0; i < CATEGORIES; i++) {
         if(bPercentages[i] > 50) {
            personality = personality + bTypes[i];
         }
         else if(bPercentages[i] < 50) {
            personality = personality + aTypes[i];
         }
         else if(bPercentages[i] == 50) {
            personality = personality + "X";
         }
      }
      return personality;
   }
}
