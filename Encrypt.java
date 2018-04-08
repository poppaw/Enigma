
public class Encrypt {
    
    public static String incorrectParamethers(){
        return "Be aware of type key: "+
                "alpha with no repetitions-key for CTC, "+
                "number-key for CC, CM and RF";
    }

    public static String choose (String cipher, String key, String text) throws NumberFormatException{
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
        else if (cipher.equals("ctc"))
            result = CtcEncipher.runCtcEncipher(text, key);
        else if (cipher.equals("rf"))
            result = RailFence.railEncrypt(text, Integer.parseInt(key));
        else
            throw new NumberFormatException("Something wrong whith your cipher identfiers");
    
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