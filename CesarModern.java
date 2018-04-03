public class CesarModern{

    static String runCesarEncrypt(String text, int shift) {
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

    static String runCesarDecrypt(String text, int shift) {
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
        System.out.println(runCesarEncrypt(text, shift));
        System.out.println(runCesarDecrypt(crypt, shift));

    }
}
