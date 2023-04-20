/**
 * @author Max Day
 * Created At: 2021/09/07
 */
package API.Credentials;

import API.Tools.FileManager;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Author: Max Day
 * This was adapted from a marked project PAT that was done when I was in highs-cool. It was meant for SQL Server logins as a backend type server. I have adapted it to work with this.
 * The concept was derived from the Oracle Documentation and I have a full explanation on how it works. I have now decided to rewrite it slightly.
 */

public class DeepEncrypt {

    public static void registerUser(String uName, String uPassword, String fName, String sName) throws Exception {
        String entry = String.format("%s|%s|%s|%s|%s", uName, generateStorngPasswordHash(uPassword), fName, sName);
        new FileManager().addEntry(entry);
        new FileManager().createTBLManager(uName);
        System.out.println("Wrote password");
    }

    /**
     * Boolean to check the validity of a username and password when checked in the database
     * @param inUser
     * @param inPass
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static boolean valid(String inUser, String inPass) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        FileManager fileManager = new FileManager();
        Scanner scanner = new Scanner(fileManager.readFile());
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split("\\|");
            if (inUser.equals(parts[0])) { // username found
                if (validatePassword(inPass, parts[1])) {
                    System.out.printf("Welcome %s, %s it is great to see you again.%n", parts[2], parts[3]);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Runs generate hash to create a hashed password as a string
     *
     * @param password
     * @return hashed password as a String
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = getSalt();
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return "1000:" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Generates a salt using the secure random class - algo sharping 1
     * @return byte array a random salt
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    /**
     * converts the hex to byte array then encrypts the entered password and then checks the difference between the encrypted and the stored encrypted password
     * @param originalPassword
     * @param storedPassword
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        byte[] hash = fromHex(parts[2]);
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), fromHex(parts[1]), Integer.parseInt(parts[0]), hash.length * 8);
        byte[] testHash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec).getEncoded();
        return Arrays.equals(hash, testHash);
    }
    /**
     * converts the Byte array to a string
     * @param array
     * @return byte array of hex characters
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        return String.format("%0" + (array.length << 1) + "x", bi);
    }
    /**
     * Converts hex to byte array
     *
     * @param hex input String
     * @return byte array of characters
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0, j = 0; i < hex.length(); i += 2, j++)
            bytes[j] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        return bytes;
    }
}



