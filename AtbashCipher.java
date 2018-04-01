import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class AtbashCipher {

  public static void main(String[] args) {
    text = text.toLowerCase();
    if (Enigma.isLetter(text)) {
      runAtbashCipher(String text);
    }
    else {
      throw new InputMismatchException("Wrong input! Input must be a string consisting of letters!");
    }
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
}
