
public class Encrypt {
    
    public static String incorrectParamethers(){
        return "Be aware of type key: "+
                "alpha with no repetitions-key for CTC, "+
                "number-key for CC, CM and RF";
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

    public static String choose (String cipher, String key, String text) throws IllegalArgumentException{
        String result = new String();
        if (cipher.equals("cc")){
            try{
            result = CesarClassic.encrypt(text, Integer.parseInt(key));
            }
            catch (NumberFormatException e){
                return "Except: Key for CC, CM and RF must be a number (int)";
            }
        }
        else if (cipher.equals("cm"))
            result = CesarModern.encrypt(text, Integer.parseInt(key));
        else if (cipher.equals("ctc")&& isLetter(key))
            result = CtcEncipher.runCtcEncipher(text, key);
        else if (cipher.equals("rf"))
            result = RailFence.railEncrypt(text, Integer.parseInt(key));
        else
            throw new IllegalArgumentException("Something wrong whith your cipher identfiers");
    
        return result;       
    }

    // for testing:
    public static void main(String[] args)  {
        try{
            System.out.println("cc "+ choose("cc", "p", "AaMmZz"));
        }
        catch (NumberFormatException e) {
            System.out.println(e);
        }
        catch(IllegalArgumentException e){
            System.out.println(incorrectParamethers()); // testing of calling method
        }      
    }
}