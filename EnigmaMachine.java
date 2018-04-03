import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.out;

public class EnigmaMachine{

    public static void print_menu() {
        ArrayList<String> menu = new ArrayList<String>();
        menu.add(0,"Atbash: " + "-e/-d  ac  <stdInput>");
        menu.add(1,"Columnar transposition: "+ "-e/-d  ctc  <letter key> <stdInput>");
        menu.add(2, "Caesar classic: "+ "-e/-d  cc  <number key>  <stdInput>");
        menu.add(3, "Caesar modern: "+ "-e/-d  cm  <number key>  <stdInput>");
        menu.add(4, "Rail Fence: "+ "-e/-d  rf  <number key>  <stdInput>");
        
        out.println("\nTo choose modus and cipher use proper format of input:\n");
        for (String cipher : menu) {
          Integer index = menu.indexOf(cipher) +1;
          out.println("\t" + index + ") " + cipher);
        }
        out.println("\n\t-e means \"encipher\", -d means \"decipher\",-l is for displaying menu");
    
    }
    
    public static void incorrectParamethers(){
        out.println("\nBe aware of number or type of your input arguments: 1 modus, 2 cipher, [optional] 3 key:\n \t\t - alpha-with-no-repetitions-key for CTC, number-key for CC, CM and RF, no key for AC");
        print_menu();
    }
    static List<String> getInput(){
        List<String> toCipher = new ArrayList<String>();
        Scanner inputStream = new Scanner(System.in);
        while(inputStream.hasNextLine()){
            String line = inputStream.nextLine();
            String messageLine = line.replaceAll("\\n","");
            toCipher.add(messageLine); // list of texts for encipher/decipher
        }
        return toCipher;                                 
    }

    public static void main(String[] args) {
        try{
            String modus = args[0].toLowerCase(); // "-l" or "-e" or "-d"

            if (modus.equals("-l")) //no matter else "-l"
                print_menu();
            else if(args.length >=2) {
                String cipher = args[1].toLowerCase(); // kind of crypting e.i. "cc" = CeasarClassic,

                if (cipher.equals("ac")){  //no matter else "ac" which is the same for both -e and -d modus
                    for(String message: getInput())
                        out.println(AtbashCipher.runAtbashCipher(message));
                }
                else if(args.length >2){
                    String key = args[2].toLowerCase(); //establish args[2] as key
                    if (modus.equals("-e")){
                        for(String message: getInput()) 
                            out.println(Encrypt.choose(cipher, key, message));
                    }
                    else if (modus.equals("-d")){
                        for(String message: getInput()) 
                            out.println(Decrypt.choose(cipher, key, message));
                    }
                    else incorrectParamethers();
                }
                else incorrectParamethers();
            }
            else incorrectParamethers();
        }
        catch(ArrayIndexOutOfBoundsException | IllegalArgumentException e){
            out.println("number of your arguments: " + args.length); //ogarnąć format
            out.println("Except: " + e.getMessage()); // prints only message without StackTrace
            //e.printStackTrace();
            incorrectParamethers();
        }           
    }
}