/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.Scanner;

/**
 * @Class LogIn - check client login
 */
public class LogIn {

      String username;
      String password;

   /**
    * CONSTRUCT to initialise serial fields
    * @param username - initials username
    * @param password - initialise password
    */
   public LogIn(String username, String password) {

         this.username = username;
         this.password = password;
      }

      /**
       * Check user logins
       * Uses data file Data.txt
       * @return true if client login matched  data else false
       */
      public boolean isCorrect() {
         boolean check = false;
         Scanner myReader =null;
         try {
            File currentDir = new File("ChatA");
            File parentDir = currentDir.getParentFile();
            File myObj = new File(parentDir,"Data.txt");
            myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
               String data = myReader.nextLine();
               if(data.charAt(0) != '#') {
                  String[] details = data.split("#");
                  check = details[0].equalsIgnoreCase(username) && details[1].equalsIgnoreCase(password);
                  if (check) {
                     break;
                  }
               }
            }

         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
         finally {
            assert myReader != null;
            myReader.close();
         }
         return check;
      }

   }

