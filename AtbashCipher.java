public class AtbashCipher {


  public static void main(String[] args) {
    runAtbashCipher(String text);
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
