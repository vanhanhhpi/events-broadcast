package hpilib.coreu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hpilib.coreu.mapper.CustomObjectMapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

  public static ObjectMapper Utilsmapper = new CustomObjectMapper(false, false, true);
  public static String redisRefixKey = "HPI.OMS.";
  public static String DateFormatStringT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ";
  public static int quantityBitWisePermission(List<Integer> permissions) {
    int total = 0;
    if (permissions == null || permissions.isEmpty())
      return 0;
    for (Integer i : permissions) {
      /**
       * i là permission_id lưu trong DB, giá trị từ 1-n
       * bitwise hỗ trợ số lớn nhất là 64
       */
      if (i == null || i < 1 || i > 64)
        continue;
      total += Math.pow(2, i - 1);
    }
    return total;
  }

  public static Date AddHour(Date from, int hour) {
    Calendar c = Calendar.getInstance();
    c.setTime(from);
    c.add(Calendar.HOUR, hour);
    return c.getTime();
  }

  public static Date AddDay(Date from, int day) {
    Calendar c = Calendar.getInstance();
    c.setTime(from);
    c.add(Calendar.DATE, day);
    return c.getTime();
  }

  public static Date AddMonth(Date from, int month) {
    Calendar c = Calendar.getInstance();
    c.setTime(from);
    c.add(Calendar.MONTH, month);
    return c.getTime();
  }

  public static Date AddYear(Date from, int year) {
    Calendar c = Calendar.getInstance();
    c.setTime(from);
    c.add(Calendar.YEAR, year);
    return c.getTime();
  }

  public static boolean isNullOrEmpty(String vao) {
    return vao == null || vao.isBlank() || vao.isEmpty() || vao.trim().length() <= 0
            || vao.equalsIgnoreCase("null");
  }


  public static String DateToString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat(DateFormatStringT);
    String format = formatter.format(date);
    return format;
  }

  public static String DateToString(Date date, String format) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(date);
  }

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
          "^(?=.{3,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
          Pattern.CASE_INSENSITIVE);

  public static boolean validateEmail(String email) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return matcher.find();
  }

  public static String StackTraceToString(Throwable e) {
    if (e == null || e.getStackTrace() == null) {
      return "";
    }
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    String sStackTrace = sw.toString(); // stack trace as a string
    return sStackTrace;
  }

  public static <T> T[] pushArray(T[] arr, T item) {
    T[] tmp = Arrays.copyOf(arr, arr.length + 1);
    tmp[tmp.length - 1] = item;
    return tmp;
  }

  public static int[] pushArray(int[] arr, int item) {
    int[] tmp = Arrays.copyOf(arr, arr.length + 1);
    tmp[tmp.length - 1] = item;
    // con trỏ
    arr = tmp;
    return tmp;
  }

  public static <T> T[] pushArray(T[] arr, T[] item) {
    for (int i = 0; i < item.length; i++) {
      arr = pushArray(arr, item[i]);
    }
    return arr;
  }

  public static <T> T[] popArray(T[] arr) {
    T[] tmp = Arrays.copyOf(arr, arr.length - 1);
    return tmp;
  }



  static void validateJWT(String jwt) {
    final String[] jwtParts = jwt.split("\\.");
    if (jwtParts.length != 3) {
      throw new InvalidParameterException("not a JSON Web Token");
    }
  }



  public static String splitDotGetEndOfString(String str) {
    String[] temp = null;
    temp = str.split("\\.");
    return temp[temp.length - 1];
  }

  public static String deAccent(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
  }


  public static boolean checkRegex(String regex, String strInput) {
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(strInput);
    return matcher.find();
  }

  public static String objectToJson(Object object) {
    try {
      return Utilsmapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean isInteger(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }


  public static String toUrl(String text) {
    text = removeAccent(text.trim().toLowerCase().replace(" ", "-"));
    return text;
  }

  public static String removeSpecialCharacters(String text) {
    return text.trim().replaceAll("[^a-zA-Z0-9\\s+]", "");
  }

  public static boolean simplifyAndCompare(String a, String b) {
    if (a == null)
      return b == null;
    if (b == null)
      return a == null;
    a = removeAccent(a.toLowerCase());
    b = removeAccent(b.toLowerCase());
    return a.equals(b);
  }

  public static boolean lowerEquals(String a, String b) {
    if (!(a == null ? b == null : true))
      return false;
    if (!(b == null ? a == null : true))
      return false;
    String x = a.toLowerCase();
    String y = b.toLowerCase();
    return x.equals(y);
  }

  // Mang cac ky tu goc co dau
  private static final char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ',
          'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
          'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ',
          'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ',
          'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ',
          'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự',};
  // Mang cac ky tu thay the khong dau
  private static final char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E', 'E', 'E', 'I', 'I', 'O', 'O', 'O',
          'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A', 'a', 'D',
          'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a',
          'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
          'e', 'E', 'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
          'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',};

  /**
   * Bo dau 1 ky tu
   *
   * @param ch
   * @return
   */
  private static char removeAccent(char ch) {
    int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
    if (index >= 0) {
      ch = DESTINATION_CHARACTERS[index];
    }
    return ch;
  }

  /**
   * Bo dau 1 chuoi
   *
   * @param s
   * @return
   */
  public static String removeAccent(String s) {
    StringBuilder sb = new StringBuilder(s);
    for (int i = 0; i < sb.length(); i++) {
      sb.setCharAt(i, removeAccent(sb.charAt(i)));
    }
    return sb.toString();
  }

  /**
   * remove khoảng trắng đầu và cuối chuỗi
   *
   * @param str
   * @return
   */
  public static String trim(String str) {
    return str.replaceAll("^([ ]+)|([ ]+$)", "");
  }

  /**
   * remove ký tự truyền vào ở đầu và cuối chuỗi
   *
   * @param str
   * @param charTrim
   * @return
   */
  public static String trim(String str, String charTrim) {
    return str.replaceAll("^([" + charTrim + "]+)|([" + charTrim + "]+$)", "");
  }

  public static List<Double> divideEvenly(double total, int chia){

    boolean isNegative = false;
    if (total < 0) isNegative = true;
    total = Math.abs(total);
    int unit= unit(total);
    total = total/unit;

    Double numPer =  Math.floor(total/chia);
    double chiaDu = (total%chia);
    List<Double> lst = new ArrayList<>();
    for (int i = 1; i <= chia; i++){
      var number = numPer + (chiaDu > 0 ? chiaDu > 1 ? 1.0 : chiaDu : 0.0);
      number = number * unit;
      if (isNegative) {
        number = -number;
      }
      lst.add(Math.ceil(number));
      chiaDu--;
    }
    if (chia < 1 && total%chia < 1){
      var num0 =  lst.get(0);
      num0 += Math.ceil(total%chia * unit);
      lst.set(0, num0);
  }

    System.out.println(lst);
    return lst;
}

  public static int unit(double number){
    number = Math.abs(number);
    int unit = 1;
    if (number > 100){
      unit = 10;
    }
    if (number > 1000){
      unit = 100;
    }
    if (number > 10000){
      unit = 1000;
    }
    if (number > 100000){
      unit = 10000;
    }
    if (number > 1000000){
      unit = 100000;
    }
    if (number > 10000000){
      unit = 1000000;
    }
    return unit;
  }

  public static List<Double> divideProportions(double amount, List<Double> totalInSplitOrder) {
    var lst = new ArrayList<Double>();
    var total = totalInSplitOrder.stream().reduce(0.0, Double::sum) ;
    int unit = 1000;
    totalInSplitOrder.forEach(x -> {
      double a = (x * amount / (total * unit));
      var number = Math.round(a) * 1.0;
      if (lst.stream().reduce(0.0, Double::sum) / unit + number > amount / unit) {
        number = Math.floor(a);
      }
      lst.add(number * unit);
    });
    return lst;
  }
}
