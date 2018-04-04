import java.util.List;
import java.util.ArrayList;
import static java.lang.System.out;

public class RailFence{
    
    static String textAdapter(String text){
        if (text.length() <= 2){     // if text is to short
            StringBuilder sb = new StringBuilder();
            String redundant = "Xxx";
            sb.append(redundant);
            sb.append(text);
            sb.append(redundant);
            text = sb.toString();
        }
        return text;
    }

    static int keyModifier(String text, int key){ 
        if (key < 2 || key > text.length()/2)
            key = 3;
        return key;
    }
     
    public static String railEncrypt(String text, int key){
        text = textAdapter(text);
        key = keyModifier(text, key);
        
        List <char[]> rail = new ArrayList<char[]>();
        //create  empty arrays nested in list
        for(int i=0; i <key;i++) rail.add(new char[text.length()]);

        int col; // position of element in array: depending on position of letter in text
        int raw = 0; // oscilating (rising and falling) position of array in list within the list's length: from 0 to lenth of list(excluding).
        int operand = 1; //changes raw value
        for (int j=0; j<text.length();j++){
            char sign = text.charAt(j);
            col = j;
            if(raw+operand<0 || raw+operand==key){// reaching list borders
                operand = -operand; //changes oscylation direction
            }
            rail.get(raw)[col] = sign;
            raw += operand;
        }
        String cipher = "";
        for(char[]array:rail) {
            for(char letter:array){
                cipher += letter;
            }
        }
        return cipher;
    }

    public static String railDecrypt(String text, int key){
        String message = "Wymyśl mnie, lub szukaj na Google'u";
        return (message);
    }

    public static void main(String[] args) {
        String text = new String("Ha");
        int key = 1;
        System.out.println(railEncrypt(text, key));
        //railEncrypt(text, key);
    }
}               