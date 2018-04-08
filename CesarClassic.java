public class CesarClassic{
    
    public static String encrypt(String text, int shift) throws NumberFormatException{
        char sign;
        char newSign;
        String encrypted = "";
        for(int i=0; i< text.length(); i++){
            sign = text.charAt(i);
            if (!Character.isLetter(sign)) newSign = sign;
            else if (Character.isUpperCase(sign) && (sign + shift > 'Z')) newSign = (char) (sign+shift - 26);
            else if (Character.isUpperCase(sign) && (sign + shift < 'A')) newSign = (char) (sign + shift + 26);
            else if (Character.isLowerCase(sign) && (sign + shift > 'z')) newSign = (char) (sign+shift - 26);
            else if (Character.isLowerCase(sign) && (sign + shift < 'a')) newSign = (char) (sign + shift + 26);
            else newSign = (char)(sign + shift);
            encrypted += newSign;
        }
        return encrypted;
    }
    public static String decrypt(String text, int shift)throws NumberFormatException{
        char sign;
        char newSign;
        String decrypted = "";
        for(int i=0; i< text.length(); i++){
            sign = text.charAt(i);
            if (!Character.isLetter(sign)) newSign = sign;
            else if (Character.isUpperCase(sign) && (sign - shift > 'Z')) newSign = (char) (sign - shift + 26);
            else if (Character.isUpperCase(sign) && (sign - shift < 'A')) newSign = (char) (sign - shift + 26);
            else if (Character.isLowerCase(sign) && (sign - shift > 'z')) newSign = (char) (sign - shift + 26);
            else if (Character.isLowerCase(sign) && (sign - shift < 'a')) newSign = (char) (sign - shift + 26); 
            else newSign = (char)(sign - shift);
            decrypted += newSign;
        }
        return decrypted;
    }
        

    public static void main(String[] args) {
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz !@#$%^&";   
        int shift = 8;
        String encrypted = encrypt(text,shift);
        System.out.println(encrypted);
        String decrypted = decrypt(encrypted, shift);
        System.out.println(decrypted);
        boolean accurate = decrypted.equals(text);
        System.out.println("decrypted = text before encryption: " + accurate);
    }
}
