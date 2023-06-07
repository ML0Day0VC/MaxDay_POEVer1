/**
 * @author Max Day
 * Created At: 2023/04/15
 * <p>
 * IGNORE THIS THIS IS A TEST FILE IM GOING TO UNVERSION THIS FILE SOON
 * <p>
 * IGNORE THIS THIS IS A TEST FILE IM GOING TO UNVERSION THIS FILE SOON
 * <p>
 * IGNORE THIS THIS IS A TEST FILE IM GOING TO UNVERSION THIS FILE SOON
 * IGNORE THIS THIS IS A TEST FILE IM GOING TO UNVERSION THIS FILE SOON
 * IGNORE THIS THIS IS A TEST FILE IM GOING TO UNVERSION THIS FILE SOON
 */

/**
 * IGNORE THIS THIS IS A TEST FILE IM GOING TO UNVERSION THIS FILE SOON
 *
 */


import javax.swing.*;
import java.awt.*;
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

        String msg = "<html>This is how to get:<ul><li><i>italics</i> and <li><b>bold</b> and "
                     + "<li><u>underlined</u>...</ul></html>";
        JLabel label = new JLabel(msg);
        label.setFont(new Font("serif", Font.PLAIN, 14));
        JOptionPane.showConfirmDialog(null, label);



      int jop =   JOptionPane.showConfirmDialog(null, "Is the contact server up", "Please select", JOptionPane.YES_NO_OPTION);

        System.out.println(jop); // 0 = yes 1 = 0 ????????




        String taskName = "get supplies";
        int taskNumber = 24;
        String developerName = "James Smith";

        String taskID = taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + developerName.substring(developerName.length() - 3).toUpperCase();


        String ssss = "Create Blog Section\nDevelop a blog section for the website\nMichael Brown\n12\n3\nFix Bugs\nIdentify and fix any bugs in the website\nSophia Davis\n8\n1\nIntegrate Payment Gateway\nAdd a payment gateway to the website\nWilliam Garcia\n20\n2\nOptimize Website Speed\nImprove the website's loading speed\nOlivia Martinez\n16\n3\nDevelop Mobile App\nCreate a mobile app for the website\nEthan Wilson\n25\n1\nImplement Search Functionality\nAdd search functionality to the website\nAva Lee\n10\n2\nCreate User Dashboard\nDevelop a dashboard for users to manage their accounts\nBenjamin Jackson\n18\n3\nPerform Security Audit\nConduct a security audit and implement necessary measures\nMia Rodriguez\n14\n1\n";

        System.out.println(ssss);

        String s = "[max]";
        System.out.println(s.substring(1, s.length() - 1));


        String input = "The quick brown fox jumps over the lazy dog";
        Pattern pRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");//123456M$       https://regex101.com amazingsite
        if (pRegex.matcher("this&hM1").matches()) {
            System.out.println("Pattern matches input");
        } else {
            System.out.println("Pattern does not match input");
        }


        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}




