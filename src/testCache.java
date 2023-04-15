/**
 * @author Max Day
 * Created At: 2023/04/15
 */

import java.util.HashMap;

public class testCache {
    private static HashMap<String, Object> cache = new HashMap<>();
    public static Object getValue(String key) {
        Object value = cache.get(key);
        if (value == null) {
            value = retrieveValueFromSource(key);
            cache.put(key, value);
        }
        return value;
    }
    private static Object retrieveValueFromSource(String key) {
        System.out.println("Retrieving value for key: " + key);
        return key.toUpperCase();
    }
    public static void main(String[] args) {
        String key = "random cache lel";
        System.out.println(getValue(key));
        System.out.println(getValue(key));
    }
}