/**
 * @author Max Day
 * Created At: 2023/04/15
 */

import java.util.HashMap;
import java.util.regex.Pattern;

public class testThing {
    private static HashMap<String, Object> cache = new HashMap<>();

    public static Object getCache(String key) {
        Object value = cache.get(key);
        if (value == null) {
            value = retrieveValueFromSource(key);
            cache.put(key, value);
        }
        return value;
    }
               /* u can actuallly write it as this but im too lazy for it this is so basic honeslty
    public static Object value(String key) {
    return cache.computeIfAbsent(key, k -> retrieveValueFromSource(k));
}

     */
    private static Object retrieveValueFromSource(String key) {
        return key.toUpperCase();
    }




        public static void main(String[] args) {
            String input = "The quick brown fox jumps over the lazy dog";
            Pattern pRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");//123456M$       https://regex101.com amazingsite
                       if (pRegex.matcher("this&hM1").matches()) {
                System.out.println("Pattern matches input");
            } else {
                System.out.println("Pattern does not match input");
            }
        }



}


