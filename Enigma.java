import java.util.ArrayList;
import java.util.List;

public class Enigma {

  public static void main(String[] args) {
    ArrayList<String> menu = new ArrayList<String>();
    menu.add("Affine");
    menu.add("Columnar Transposition Cipher");

    if (args[0].equals("l")) {
      print_menu(menu);
    }
  }

  public static void print_menu(ArrayList<String> menu) {
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






}
