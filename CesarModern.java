public class CesarModern{

    static String encrypt(String text, int shift) {
        char sign;
        char newSign;
        String encrypted = "";

        for(int i=0; i< text.length(); i++){
            sign = text.charAt(i);
            newSign = (char)(sign + shift);
            encrypted += newSign;
        }
        return encrypted;     
    }

    static String decrypt(String text, int shift) {
        char sign;
        char newSign;
        String decrypted = ""; 

        for(int i=0; i< text.length(); i++){
            sign = text.charAt(i);
            newSign = (char)(sign - shift); 
            decrypted += newSign;
        }
        return decrypted;
    }

    public static void main(String[] args) {
        String text = "Bociany na Ekrany!";   
        int shift = 8;
        String crypt = "Jwkqiv(vi(Msziv)";
        System.out.println(encrypt(text, shift));
        System.out.println(decrypt(crypt, shift));
    
    }
}