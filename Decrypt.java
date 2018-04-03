public class Decrypt{
    public static void main(String[] args) {
        System.out.println(choose("ctc", "piesek", "mxmxaxzxaxmx"));       
    }

    public static String choose (String cipher, String key, String text){
        String result = new String();
        if (cipher.equals("cc")){
           result = CesarClassic.decrypt(text, Integer.parseInt(key));
        }
        else if (cipher.equals("cm"))
            result = CesarModern.decrypt(text, Integer.parseInt(key));
        else if (cipher.equals("ctc"))
            result = CtcDecipher.runCtcDecipher(text, key);
        else if (cipher.equals("rf"))
            result = RailFence.railDecrypt(text, Integer.parseInt(key));
        else result = "Something wrong whith your cipher identfiers"; // or throw exc.
        return result;       
    }
}