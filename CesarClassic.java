public class CesarClassic{
    
    public static String encrypt(String text, int shift){
        char sign;
        char newSign;
        String encrypted = "";
        for(int i=0; i< text.length(); i++){
            sign = text.charAt(i);
            if (!Character.isLetter(sign)) newSign = sign; //(sign == ' ')
            else if ((sign<='Z') && (sign + shift > 'Z')) newSign = (char) (sign+shift - 26);
            else if ((sign>='A') && ((sign + shift) < 'A')) newSign = (char) (sign + shift + 26);
            else if ((sign<='z') && (sign + shift > 'z')) newSign = (char) (sign+shift - 26);
            else if ((Character.isLowerCase(sign))&&(sign<='z') && (sign + shift < 'a')) newSign = (char) (sign + shift + 26);
            else newSign = (char)(sign + shift);
            
            encrypted += newSign;
        }
        return encrypted;
    }

    public static void main(String[] args) {
        String text = "Bociany na Ekrany!";   
        int shift = 8;
        
        System.out.println(encrypt(text,shift));

        
    }
}