import java.util.List;
import java.util.ArrayList;
import static java.lang.System.out;

public class RailFence{
    
    static String textAdapter(String text){
        /* adapts a text to the requirements of the cipher. 
        Too short text (shorter than 3 characters) is not suitable for encryption.
        */
        if (text.length() < 3){     // if text is too short;
            StringBuilder sb = new StringBuilder();
            String filler = "Xxx"; // fills too short text
            sb.append(filler);
            sb.append(text);
            sb.append(filler);
            text = sb.toString();
        }
        return text;
    }

    static int keyModifier(String text, int key){ 
        /* the key value for this cipher can not be less than 2, 
        nor should be greater than the half-length value of the text.
        This modifies given key to an acceptable value.
        */
        if (key < 2 || key > text.length()/2) // if key value is too low or too great;
            key = 3; //emergency key;
        return key;
    }


	public static List<char[]> createMatrix(String text, int key){
        
        /*Creates  empty matrix as arrays nested in list:
        The key value determines the number of nested arrays("rails" - raws), 
        the text length determines the size of the arrays ("posts" - columns). 
        */
        List <char[]> rail = new ArrayList<char[]>();
        for(int i=0; i <key;i++) rail.add(new char[text.length()]);

        int col; // position of element in array: depending on position of letter in text (variable j)
        int raw = 0; // oscilating (rising and falling) position of array in list within the list's length: from 0 to length of list(excluding).
        int operand = 1; //changes raw value;

        for (int j=0; j<text.length();j++){
            char sign = text.charAt(j);
            col = j;
            if(raw+operand<0 || raw+operand==key){//if value reaches list borders(max index in matrix)...
                operand = -operand; //...operand changes oscylation direction(increments/decrements index in matrix);
            }
            rail.get(raw)[col] = sign; // assignment position of matrix to next sign of the text;
            raw += operand;
        }
        return rail;
    }

     
    public static String railEncrypt(String text, int key){
        text = textAdapter(text);
        key = keyModifier(text, key);
        
        List <char[]> rail = createMatrix(text, key);
        String cipher = "";
        for(char[]array:rail) {
            for(char sign:array){
                if (sign != '\0') //if sign is not an empty sign (in Unicode '\0') (differs from space '\72'!)
                    cipher += sign;
            }
        }
        return cipher;
    }


    public static String railDecrypt(String cipheredText, int key) {
		
		cipheredText = textAdapter(cipheredText);
        key = keyModifier(cipheredText, key);
       
        // reconstructing shape and dimensions of supposed enciphering rail fence matrix:
        List<Integer> arraysDimentions = new ArrayList<>(); //this will collect lengths (netto:without empty chars) of each arrays of supposed encrpytion matrix
        List <char[]> railMatrix = createMatrix(cipheredText, key);
        for (char[]array: railMatrix) { //reconstruction of places of letters in supposed matrix;
            String charsFromArray = new String(""); //temporary variable;
            for(char sign: array){      
                if (sign != '\0') //if sign is not an empty sign (in Unicode: '\0'. Note:differs from space: '\72'!);
                    charsFromArray +=sign;
            }
            arraysDimentions.add(charsFromArray.length()); //
        }
        
        // splitting ciphered text into parts lenght-of-reconstructed-arrays long (netto: including spaces, excluding empty chars);
        ArrayList<String> raws = new ArrayList<>(); //list of splitted parts of enciphered text; 
        int start = 0;
        int end;
        for (int value:arraysDimentions) {
            end = start + value;
            String reconstrRaw = cipheredText.substring(start,end);
            raws.add(reconstrRaw);
            start += value;
        }
        //replaces signs of cipher text in matrix with signs which probably were during enciphering:
        for (int n=0; n<railMatrix.size(); n++){ //"n" is for array index of railMatrix and for index of array raw (with string parts of enciphered text);
            int numOfLetter = 0; //number of sign of splitted part of ciphered text (index of char element of string element of splitted text list);
              
            for (int m=0; m<railMatrix.get(n).length; m++){ //"m" is for index of matrix's array element (including empty chars); 
                if (railMatrix.get(n)[m] != '\0') { //empty chars are omitted;
                    railMatrix.get(n)[m] = raws.get(n).charAt(numOfLetter); //replacing chars in matrix;
                    numOfLetter ++;
                }
            }
        }
        // deciphering:
        String message = "";
        int col; // position of element in array: depending on position of letter in text
        int raw = 0; // oscilating (rising and falling) position of array in list within the list's length: from 0 to lenth of list(excluding).
        int operand = 1; //changes raw value

        for (int j=0; j<cipheredText.length();j++){
            col = j;
            if(raw+operand<0 || raw+operand==key){// reaching list borders
                operand = -operand; //changes oscylation direction
            }
            message +=railMatrix.get(raw)[col]; // extracting signs from matrix and concatenate them into message
            raw +=operand;
        }
        return message.replace("Xxx","").replace("Xxx",""); //removes redundant fillers;
    }

    
    public static void main(String[] args) {
        String text = new String("Halina zgina pingwina");
        int key = 3;
        String cipheredText = railEncrypt(text, key);
		String message = railDecrypt(cipheredText, key);
        out.println(text);
        out.println(cipheredText);
        out.println(message);       
    }    
}           
