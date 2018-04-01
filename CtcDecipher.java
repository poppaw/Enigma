import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class CtcDecipher {

  public static void main(String[] args) {
    text = text.toLowerCase();
    if (Enigma.isLetter(text)) {
      runCtcDecipher(String text, String key);
    }
    else {
      throw new InputMismatchException("Wrong input! Input must be a string consisting of letters!");
    }
  }


  public static String runCtcDecipher(String text, String key) {
    Integer text_len = text.length();
    Integer key_len = key.length();
    Integer row_len = text_len/key_len;
    ArrayList<String> rows = getListOfRows(text, row_len, key_len);
    ArrayList<String> reordered_rows = reorderEveryRow(row_len, rows);
    char[] sorted_key = CtcEncipher.sortKey(key);
    ArrayList<Integer> list_of_index = CtcEncipher.getIndexes(new String(sorted_key), key.toCharArray());
    ArrayList<String> decrypted_rows = decryptEveryRow(reordered_rows, list_of_index);
    String decryption = getDecryptedText(decrypted_rows);
    return decryption;
  }


  public static ArrayList<String> getListOfRows(String text, Integer row_len, Integer key_len) {
    ArrayList<String> rows = new ArrayList<String>();
    Integer start = 0;
    Integer end = row_len;
    for (int i=0; i<key_len; i++) {
      rows.add(text.substring(start,end));
      start += row_len;
      end += row_len;
    }
    return rows;
  }


  public static ArrayList<String> reorderEveryRow(Integer row_len, ArrayList<String> rows) {
    //reorder every row
    //take character at specific index of every row, concatenate them to string
    ArrayList<String> reordered_rows = new ArrayList<String>();
    Integer index = 0;
    while (index < row_len) {
      String reordered_row = "";
      for (String row : rows) {
        reordered_row += row.charAt(index);
      }
      index += 1;
      //add string to list
      reordered_rows.add(reordered_row);
    }
    return reordered_rows;
  }


  public static ArrayList<String> decryptEveryRow(ArrayList<String> reordered_rows, ArrayList<Integer> list_of_index) {
    //take character at specific index of every row, concatenate them to string, add string to list
    ArrayList<String> decrypted_rows = new ArrayList<String>();
      for (String row : reordered_rows) {
        String decrypted_row = "";
        for (int element : list_of_index) {
          decrypted_row += row.charAt(element);
        }
        decrypted_rows.add(decrypted_row);
      }
      return decrypted_rows;
    }


  public static String getDecryptedText(ArrayList<String> decrypted_rows) {
    //concatenate every row to get encrypted text
    //encrypted text takes indexed character from every row
    String decryption = "";
    for (String row : decrypted_rows) {
      decryption += row;
    }
    return decryption;
  }
}
