public class ColumnarTransposition {
  public static void main(String[] args) {
    runCtcEncipher(String text, String key);
    runCtcDecipher(String text, String key);
  }


  public static String runCtcEncipher(String text, String key) {
    text = text.replaceAll("\\s+",""); //removes all whitespaces and non-visible characters
    Integer text_len = text.length();
    Integer key_len = key.length();
    Integer pad_number = key_len - (text_len%key_len);//get the quantity of pads to use

    text = addPadsToText(text, pad_number);
    Integer len_padtext = text.length();//get the text length after adding pads
    Integer rows_number = len_padtext/key_len;//get the number of rows to split the text
    ArrayList<String> rows = splitTextToRows(text, rows_number, key_len);
    char[] sorted_key = sortKey(key);
    ArrayList<Integer> list_of_index = getIndexes(key, sorted_key);
    ArrayList<String> sorted_rows = sortRows(rows, list_of_index);
    String encryption = getEncryptedText(key_len, sorted_rows);
    return encryption;
  }


  public static String addPadsToText(String text, Integer pad_number) {
    for (int i=0; i<pad_number; i++) {
      text += "x";
    }
    return text;
  }


  public static ArrayList<String> splitTextToRows(String text, Integer rows_number, Integer key_len) {
    Integer start = 0;
    Integer end = key_len;
    ArrayList<String> rows = new ArrayList<String>();
    for (int i=0; i<rows_number; i++) {
      String row = text.substring(start,end);
      rows.add(row);
      start += key_len;
      end += key_len;
    }
    return rows;
  }


  public static char[] sortKey(String key) {
    char[] sorted_key = key.toCharArray();
    Arrays.sort(sorted_key);
    return sorted_key;
  }


  public static ArrayList<Integer> getIndexes(String string, char[] characters) {
    //use <key> and <sorted key> to find new indexes for row characters
    ArrayList<Integer> list_of_index = new ArrayList<Integer>();
    Integer index;
    for (char character: characters) {
      index = string.indexOf(character);
      list_of_index.add(index);
    }
    return list_of_index;
  }


  public static ArrayList<String> sortRows(ArrayList<String> rows, ArrayList<Integer> list_of_index) {
    //use list_of_index to sort every row
    ArrayList<String> sorted_rows = new ArrayList<String>();
    for (String row : rows) {
      String sorted_row = "";
      for (int element : list_of_index) {
        sorted_row += row.charAt(element);
      }
      sorted_rows.add(sorted_row);
    }
    return sorted_rows;
  }


  public static String getEncryptedText(Integer key_len, ArrayList<String> sorted_rows) {
    //concatenate every row to get encrypted text
    //encrypted text takes indexed character from every row
    String encryption = "";
    Integer index = 0;
    while (index < key_len) {
      for (String row : sorted_rows) {
        encryption += row.charAt(index);
      }
      index += 1;
    }
    return encryption;
  }


  public static String runCtcDecipher(String text, String key) {
    Integer text_len = text.length();
    Integer key_len = key.length();
    Integer row_len = text_len/key_len;
    ArrayList<String> rows = getListOfRows(text, row_len, key_len);
    ArrayList<String> reordered_rows = reorderEveryRow(row_len, rows);
    char[] sorted_key = sortKey(key);
    ArrayList<Integer> list_of_index = getIndexes(new String(sorted_key), key.toCharArray());
    ArrayList<String> decrypted_rows = decryptEveryRow(reordered_rows, list_of_index);
    String decryption = getDecryptedText(decrypted_rows);
    return decryption;
  }


  public static ArrayList<String> getListOfRows(String text, Integer row_len, Integer key_len) {
    ArrayList<String> rows = new ArrayList<String>();
    Integer start = 0;
    Integer end = row_len;
    for (int i=0; i<key_len; i++) {
      rows.add(text.substring(start,end));
      start += row_len;
      end += row_len;
    }
    return rows;
  }


  public static ArrayList<String> reorderEveryRow(Integer row_len, ArrayList<String> rows) {
    //reorder every row
    //take character at specific index of every row, concatenate them to string
    ArrayList<String> reordered_rows = new ArrayList<String>();
    Integer index = 0;
    while (index < row_len) {
      String reordered_row = "";
      for (String row : rows) {
        reordered_row += row.charAt(index);
      }
      index += 1;
      //add string to list
      reordered_rows.add(reordered_row);
    }
    return reordered_rows;
  }


  public static ArrayList<String> decryptEveryRow(ArrayList<String> reordered_rows, ArrayList<Integer> list_of_index) {
    //take character at specific index of every row, concatenate them to string, add string to list
    ArrayList<String> decrypted_rows = new ArrayList<String>();
      for (String row : reordered_rows) {
        String decrypted_row = "";
        for (int element : list_of_index) {
          decrypted_row += row.charAt(element);
        }
        decrypted_rows.add(decrypted_row);
      }
      return decrypted_rows;
    }


  public static String getDecryptedText(ArrayList<String> decrypted_rows) {
    //concatenate every row to get encrypted text
    //encrypted text takes indexed character from every row
    String decryption = "";
    for (String row : decrypted_rows) {
      decryption += row;
    }
    return decryption;
    }
}
