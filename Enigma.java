import java.util.*;
import java.io.*;

public class Enigma {

  public static void main(String[] args) {


    if (args[0].equals("l")) {
      print_menu();
    }
  }

  public static void print_menu() {
    ArrayList<String> menu = new ArrayList<String>();
    menu.add("Affine");
    menu.add("Columnar Transposition Cipher");
    Integer index = 1;
    for (String cipher : menu) {
      System.out.println(index + ") " + cipher);
    }
  }

  public static void choose_cipher() {
    System.out.println("Choose the cipher: ");

  }

  public static void affine(String text, String mode, ArrayList<Integer>key) {
    text = text.replaceAll("\\s+","");
    List<Character> cipher_result = new ArrayList<Character>();
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    Integer a = key.get(0);
    Integer b = key.get(1);
    for (Character ch: text.toCharArray()) {
      if (mode.equals("e")) {
        Integer x = alphabet.indexOf(ch);
        Integer c = a*x + b;
        Character encipher_char = alphabet.charAt(c%26);
        cipher_result.add(encipher_char);
      } else if (mode.equals("d")) {
        Integer x = alphabet.indexOf(ch);
        Integer c = 15*(x - 5);
        Character decipher_char = alphabet.charAt(c%26);
        cipher_result.add(decipher_char);
      }
    }
    for (Character element : cipher_result) {
      System.out.print(element);
    }
  }

  public static void ColumnarTransposition(String text, String key, String method){
    if (method.equals("e")) {
      System.out.println(CT_Encipher(text, key, "x"));
    } else if (method.equals("d")) {
      System.out.println(CT_Decipher(text, key));
    }
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

    //use <key> and <sorted key> to find new indexes for row characters
    List<Integer> list_of_index = new ArrayList<Integer>();
    for (char character: sorted_key) {
      Integer index = key.indexOf(character);
      list_of_index.add(index);
    }
    //use list_of_index to sort every row
    ArrayList<String> sorted_rows = new ArrayList<String>();
    for (String row : rows) {
      String sorted_row = "";
      for (int index : list_of_index) {
        sorted_row += row.charAt(index);
      }
      sorted_rows.add(sorted_row);
    }

    //concatenate every row to get encrypted text
    //encrypted text takes indexed character from every row
    String encryption = "";
    Integer index = 0;
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
    Integer index;

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
