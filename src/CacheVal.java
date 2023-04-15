/**
 * @author Max Day
 * Created At: 2023/04/15
 */

import java.util.HashMap;

public class CacheVal {
    private static HashMap<String, Object> cache = new HashMap<>();
    public static Object value(String key) {
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

}