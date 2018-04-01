import java.util.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.out; // in order not to write System.out. all the time


// write to console: modus(-e or -d), cipher name (shortcuts), key (if required), "<", fileName for input, [">", filName for output]
// ex: -e cc 3 <messages > ciphers        /the result will be saved into file
//ex: -d cm 4 <ciphers                   /the result will be printed to console.

public class Enigma {

  public static void main(String[] args) {
    List<String> toCipher = new ArrayList<String>();
    Scanner inputStream = new Scanner(System.in);
    while(inputStream.hasNextLine()){
      String line = inputStream.nextLine();
      String messageLine = line.replaceAll("\\n","");
      toCipher.add(messageLine);
      //inputStream.close();
    }
    //out.println("args.length: " + args.length); // for tests
    //String message = "ABCDWKLMNXYZ abcdklmnwxyz !@#$%^&*()_+<>?/.,|\\ aa";
    for(String message: toCipher ){
      try {
        int args_length = args.length;
        String modus = args[0].toLowerCase();

        if (args_length == 1) {
          if (modus.equals("-l")) {
            print_menu();
          }
        } 
        else if (args_length == 2) {
          String cipher_name = args[1].toLowerCase();
          if (cipher_name.equals("ac")) {
            out.println(AtbashCipher(message, modus));
          }
        } 
        else if (args_length == 3) {
          String cipher_name = args[1].toLowerCase();
          String key = args[2].toLowerCase();
          if (cipher_name.equals("ctc")) {
            out.println(ColumnarTransposition(message, modus, key));
          } 
          else if (cipher_name.equals("cc")){
            if (modus.equals("-e")){
              out.println(CesarClassic.encrypt(message, Integer.parseInt(key)));
            }
            else if (modus.equals("-d")){
              out.println(CesarClassic.decrypt(message,Integer.parseInt(key)));
              }
          }
          else if (cipher_name.equals("cm")){
            if (modus.equals("-e")){
              out.println(CesarModern.encrypt(message, Integer.parseInt(key)));
            }
            else if (modus.equals("-d")){
              out.println(CesarModern.decrypt(message,Integer.parseInt(key)));
            }
          }
          else if (cipher_name.equals("rf")){
            if (modus.equals("-e")){
              out.println(RailFence.railEncrypt(message, Integer.parseInt(key)));
            }
            else if (modus.equals("-d")){
            out.println("Wait for me, Honey. I'm comming soon!");
            }
          }
        }
      }
      catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
        out.println("Wrong arguments! or leak of command line arguments");
        e.printStackTrace();
      }
    }
  }

  public static void print_menu() {
    ArrayList<String> menu = new ArrayList<String>();
    menu.add(0,"AtbashCipher (AC)");
    menu.add(1,"ColumnarTranspositionCipher (CTC key)");
    menu.add(2, "Caesar classic (CC <number key>)");
    menu.add(3, "Caesar modern (CM <number key>)");
    menu.add(4, "Rail Fence (RF <number key>)");
    
    for (String cipher : menu) {
      Integer index = menu.indexOf(cipher) +1;

      out.println(index + ") " + cipher);
    }
  }


  public static String AtbashCipher(String input, String modus) {
      String text = input.toLowerCase();
      String cipher = "";
      String alphabet = "abcdefghijklmnopqrstuvwxyz";
      String alphabet_reversed = "zyxwvutsrqponmlkjihgfedcba";

      if (modus.equals("-e") | modus.equals("-d")) {
        for(int i=0; i< text.length(); i++){
          if (!Character.isLetter(text.charAt(i))) {  //Paweł s
            cipher += text.charAt(i);
          } 
          else {
            int index = alphabet.indexOf(text.charAt(i));
            char character = alphabet_reversed.charAt(index);
            cipher += character;
          }
        }
      }
      return cipher;
    }

  public static String ColumnarTransposition(String input, String modus, String key) {
    //key - the word is to be shorter than the encrypted text, without repeating characters
    String text = input.toLowerCase();
    String result = null;
    if (modus.equals("-e")) {
      result =  CT_Encipher(text, key, "x");
    } else if (modus.equals("-d")) {
      result =  CT_Decipher(text, key);
    }
    return result;
  }


  public static String CT_Encipher(String text, String key, String pad) {
    text = text.replaceAll("\\s+",""); //remove whitespaces
    Integer text_len = text.length();
    Integer key_len = key.length();

    Integer pad_number = key_len - (text_len%key_len);//get the quantity of pads to use
    for (int i=0; i<pad_number; i++) {
      text += pad;//add pad do text
    }
    Integer len_padtext = text.length();//get the text length after adding pads
    Integer rows_number = len_padtext/key_len;//get the number of rows to split the text
    Integer start = 0;
    Integer end = key_len;
    //split text to rows
    List<String> rows = new ArrayList<String>();
    for (int i=0; i<rows_number; i++) {
      String row = text.substring(start,end);
      rows.add(row);
      start += key_len;
      end += key_len;
    }

    char[] sorted_key = key.toCharArray();
    Arrays.sort(sorted_key);
    Integer index;

    //use <key> and <sorted key> to find new indexes for row characters
    List<Integer> list_of_index = new ArrayList<Integer>();
    for (char character: sorted_key) {
      index = key.indexOf(character);
      list_of_index.add(index);
    }
    //use list_of_index to sort every row
    ArrayList<String> sorted_rows = new ArrayList<String>();
    for (String row : rows) {
      String sorted_row = "";
      for (int element : list_of_index) {
        sorted_row += row.charAt(element);
      }
      sorted_rows.add(sorted_row);
    }

    //concatenate every row to get encrypted text
    //encrypted text takes indexed character from every row
    String encryption = "";
    index = 0;
    while (index < key_len) {
      for (String row : sorted_rows) {
        encryption += row.charAt(index);
      }
      index += 1;
    }
    return encryption;
  }

  public static String CT_Decipher(String text, String key) {
    Integer text_len = text.length();
    Integer key_len = key.length();
    Integer row_len = text_len/key_len;

    //get a list of rows
    ArrayList<String> rows = new ArrayList<String>();
    Integer start = 0;
    Integer end = row_len;

    for (int i=0; i<key_len; i++) {
      rows.add(text.substring(start,end));
      start += row_len;
      end += row_len;
    }

    //reorder every row
    //take character at specific index of every row, concatenate them to string
    Integer index;
    ArrayList<String> reordered_rows = new ArrayList<String>();
    index = 0;
    while (index < row_len) {
      String reordered_row = "";
      for (String row : rows) {
        reordered_row += row.charAt(index);
      }
      index += 1;
      //add string to list
      reordered_rows.add(reordered_row);
    }

    char[] sorted_key = key.toCharArray();
    Arrays.sort(sorted_key);
    //use <key> and <sorted key> to find new indexes for row characters
    List<Integer> list_of_index = new ArrayList<Integer>();
    for (char character: key.toCharArray()) {
      index = new String(sorted_key).indexOf(character);
      list_of_index.add(index);
    }

    //decrypt every reordered_row
    //take character at specific index of every row, concatenate them to string, add string to list
    ArrayList<String> decrypted_rows = new ArrayList<String>();
      for (String row : reordered_rows) {
        String decrypted_row = "";
        for (int element : list_of_index) {
          decrypted_row += row.charAt(element);
        }
        decrypted_rows.add(decrypted_row);
      }

    //concatenate every row to get encrypted text
    //encrypted text takes indexed character from every row
    String decryption = "";
    for (String row : decrypted_rows) {
      decryption += row;
    }
    return decryption;
  }
}
