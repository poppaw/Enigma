import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class AtbashCipher {

  public static void main(String[] args) {
    System.out.println(runAtbashCipher("Ala ma kota"));
  }


  public static String runAtbashCipher(String text) {
      text = text.toLowerCase();
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
}
