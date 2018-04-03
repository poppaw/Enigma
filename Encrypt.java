
public class Encrypt {
    public static void main(String[] args) {
        try{
            System.out.println("ctc "+ choose("ctc", "3", "AaMmZz"));
        }
        catch(IllegalArgumentException e){
            System.out.println(incorrectParamethers()); // testing of calling method
        }      
    }

    public static String incorrectParamethers(){
        return "Be aware of type key: alpha with no repetitions-key for CTC, number-key for CC, CM and RF";
    }

    public static String choose (String cipher, String key, String text){
        String result = new String();
        if (cipher.equals("cc")){
            result = CesarClassic.encrypt(text, Integer.parseInt(key));
        }
        else if (cipher.equals("cm"))
            result = CesarModern.encrypt(text, Integer.parseInt(key));
        else if (cipher.equals("ctc"))
            result = CtcEncipher.runCtcEncipher(text, key);
        else if (cipher.equals("rf"))
            result = RailFence.railEncrypt(text, Integer.parseInt(key));
        else
            result = "Something wrong whith your cipher identfiers"; // or throw IllegalArgument exc.
        return result;       
    }
}