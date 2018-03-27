import java.util.ArrayList;
import java.util.List;

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

  public static void ColumnarTranspositionEncipher(String text, String key, String pad_character) {
    text = text.replaceAll("\\s+",""); //remove whitespaces
    Integer text_len = text.length();
    Integer key_len = key.length();

    Integer pad = key_len - (text_len%key_len);//get the quantity of pads to use
    for (int i=0; i<pad; i++) {
      text += pad_character;//add pad do text
    }
    Integer len_text_pad = text.length();//get the text length after adding pads
    Integer rows_quantity = len_text_pad/key_len;//get the number of rows to split the text
    Integer start = 0;
    Integer end = key_len;
    //split text to rows
    List<String> rows = new ArrayList<String>();
    for (int i=0; i<rows_quantity; i++) {
      String row = text.substring(start,end);
      rows.add(row);
      start += key_len;
      end += key_len;
    }

    char[] sorted_key = key.toCharArray();
    Arrays.sort(sorted_key);

    //use <key> and <sorted key> to find new indexes for row characters
    List<Integer> list_of_index = new ArrayList<Integer>();
    for (char ch: sorted_key) {
      Integer index = key.indexOf(ch);
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

    //join every row to get encrypted text
    //encrypted text takes indexed character from every row
    String encryption = "";
    Integer index = 0;
    while (index < key_len) {
      for (String row : sorted_rows) {
        encryption += row.charAt(index);
      }
      index += 1;
    }
  }
}
