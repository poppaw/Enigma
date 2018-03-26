import java.util.ArrayList;
import java.util.List;

public class Enigma {

  List<String> menu = new ArrayList<String>();
  menu.add("Affine");
  menu.add("Columnar Transposition Cipher");


  public static void main(String[] args) {
    if (args[0].equals("-l")){
      print_menu(menu);
    }
  }

  public static void print_menu(ArrayList<String> menu) {
    Integer index = 1;
    for (String cipher : menu) {
      System.out.println(index + ")" + cipher);
    }
}









}
