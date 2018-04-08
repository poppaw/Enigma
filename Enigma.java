import java.util.Arrays;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Enigma {

  public static void main(String[] args) {
    try {
      runApplication(args);
    }
    catch (ArrayIndexOutOfBoundsException ex) {
      System.err.println("You have missed required arguments!");
      printInstruction();
    }
    catch (Exception ex) {
      System.err.println("Incorrect arguments or input!");
      printInstruction();
    }
  }


  public static void runApplication(String[] args) {
    String method = args[0].toLowerCase();
    if (method.equals("-l")) {
      printMenu();
    }
    else {
      String cipher = args[1].toLowerCase();
      String text = getInput();
      if (cipher.toLowerCase().equals("a")) {
        System.out.println(AtbashCipher.runAtbashCipher(text));
      }
      else {
        String key = args[2].toLowerCase();
        if (cipher.equals("ct") && method.equals("-e") && isLetter(key)) {
          System.out.println(CtcEncipher.runCtcEncipher(text, key));
        }
        else if (cipher.equals("ct") && method.equals("-d") && isLetter(key)) {
          System.out.println(CtcDecipher.runCtcDecipher(text, key));
        }
        else if(cipher.equals("cc") && method.equals("-e")) {
          System.out.println(CesarClassic.encrypt(text, Integer.parseInt(key)));
        }
        else if(cipher.equals("cc") && method.equals("-d")) {
          System.out.println(CesarClassic.decrypt(text, Integer.parseInt(key)));
        }
        else if(cipher.equals("cm") && method.equals("-e")) {
          System.out.println(CesarModern.encrypt(text, Integer.parseInt(key)));
        }
        else if(cipher.equals("cm") && method.equals("-d")) {
          System.out.println(CesarModern.decrypt(text, Integer.parseInt(key)));
        }
        else if(cipher.equals("rf") && method.equals("-e")) {
          System.out.println(RailFence.railEncrypt(text, Integer.parseInt(key)));
        }
        else if(cipher.equals("rf") && method.equals("-d")) {
          System.out.println(RailFence.railDecrypt(text, Integer.parseInt(key)));
        }
        else {
          throw new IllegalArgumentException();
        }
      }
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
    System.err.println("Atbash                   (write: -e|-d A)");
    System.err.println("Columnar Transposition   (write: -e|-d CT key)");
    System.err.println("Cesar Classic            (write: -e|-d CC key)");
    System.err.println("Cesar Modern             (write: -e|-d CM key)");
    System.err.println("Rail Fence               (write: -e|-d RF key)");
    System.err.println("Input for A and CT must be a string consisting of letters");
    System.err.println("Key for CT must be a string with no repetitive characters");
    System.err.println("Key for CC, CM and RF must be a number");
  }


  public static String getInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    ArrayList<String> lines = new ArrayList<String>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      lines.add(line.replaceAll("\\n",""));
    }
    for (String element : lines) {
      input += element;
    }
    return input;
  }


  public static void printMenu() {
    ArrayList<String> menu = new ArrayList<String>();
    menu.add("Atbash                   (write: -e|-d A)");
    menu.add("Columnar Transposition   (write: -e|-d CT key)");
    menu.add("Cesar Classic            (write: -e|-d CC key)");
    menu.add("Cesar Modern             (write: -e|-d CM key)");
    int index = 1;
    for (String cipher : menu) {
      System.out.println(index + ") " + cipher);
      index += 1;
    }
  }
}
