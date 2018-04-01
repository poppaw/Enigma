import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Enigma {

  public static void main(String[] args) {
    String text = getInput();

    if (isLetter(text)) {
      try {
        runAplication(text, args);
      }
      catch (ArrayIndexOutOfBoundsException ex) {
        System.err.println("You have missed required arguments!");
        printInstruction();
      }
    }
    else {
      throw new InputMismatchException("Wrong input! Input must be a string consisting of letters!");
    }
  }


  public static void runAplication(String text, String[] args) {
    text = text.toLowerCase();
    String method = args[0].toLowerCase();

    if (method.equals("-l")) {
      printMenu();
    }
    else if (args[1].toLowerCase().equals("ac")) {
        System.out.println(runAtbashCipher(text));
    }
    else if (args[1].toLowerCase().equals("ctc") && method.equals("-e") && isLetter(args[2])) {
        System.out.println(runCtcEncipher(text, args[2]));
    }
    else if (args[1].toLowerCase().equals("ctc") && method.equals("-d") && isLetter(args[2])) {
        System.out.println(runCtcDecipher(text, args[2]));
    }
    else {
      throw new IllegalArgumentException ("Wrong arguments! Use -l option to see all available ciphers with instruction!");
    }
  }


  public static boolean isLetter(String input) {
    input = input.replaceAll("\\s+","");
    char[] chars = input.toCharArray();
    for (char character : chars) {
        if(!Character.isLetter(character)) {
            return false;
        }
    }
    return true;
  }


  public static void printInstruction() {
    System.err.println("INSTRUCTION:");
    System.err.println("Atbash Cipher                  (write: -e|-d AC)");
    System.err.println("Columnar Transposition Cipher  (write: -e|-d CTC key)");
    System.err.println("The key for CTC must be a string with no repetitive characters");
  }


  public static String getInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    ArrayList<String> lines = new ArrayList<String>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      lines.add(line + " ");
    }
    for (String element : lines) {
      input += element;
    }
    return input;
  }


  public static void printMenu() {
    ArrayList<String> menu = new ArrayList<String>();
    menu.add("Atbash Cipher (write: -e|-d AC)");
    menu.add("Columnar Transposition Cipher (write: -e|-d CTC key)");
    Integer index = 1;
    for (String cipher : menu) {
      System.out.println(index + ") " + cipher);
    }
    System.out.println("The key for CTC must be a string with no repetitive characters");
  }


  public static String runAtbashCipher(String text) {
      String cipher = "";
      String alphabet = "abcdefghijklmnopqrstuvwxyz";
      String alphabet_reversed = "zyxwvutsrqponmlkjihgfedcba";

      for (char character : text.toCharArray()) {
        if (character == ' ') {
          cipher += ' ';
        } else {
          int index = alphabet.indexOf(character);
          character = alphabet_reversed.charAt(index);
          cipher += character;
        }
      }
      return cipher;
    }

    public static String ColumnarTransposition(String input, String method, String key) {
    //key - the word is to be shorter than the encrypted text, without repeating characters
    String text = input.toLowerCase();
    String result = null;
    if (method.equals("-e")) {
      result =  CT_Encipher(text, key, "x");
    } else if (method.equals("-d")) {
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
