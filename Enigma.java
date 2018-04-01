import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Enigma {

  public static void main(String[] args) {

    try {
      runAplication(text, args);
    }
    catch (ArrayIndexOutOfBoundsException ex) {
      System.err.println("You have missed required arguments!");
      printInstruction();
    }
  }


  public static void runAplication(String text, String[] args) {
    String method = args[0].toLowerCase();

    if (method.equals("-l")) {
      printMenu();
    }

    String text = getInput();
    if (args[1].toLowerCase().equals("ac")) {
        System.out.println(AtbashCipher.runAtbashCipher(text));
    }
    else if (args[1].toLowerCase().equals("ctc") && method.equals("-e") && isLetter(args[2])) {
        System.out.println(CtcEncipher.runCtcEncipher(text, args[2]));
    }
    else if (args[1].toLowerCase().equals("ctc") && method.equals("-d") && isLetter(args[2])) {
        System.out.println(CtcDecipher.runCtcDecipher(text, args[2]));
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
}
