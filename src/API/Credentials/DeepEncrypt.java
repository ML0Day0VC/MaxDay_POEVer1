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

    /**
     * creates and entry for a new user to be added to the database. It creates a new user and takes in there credentials and stores it with the password encrypted
     * @param uName
     * @param uPassword
     * @param fName
     * @param sName
     * @throws Exception
     */
    public static void registerUser(String uName, String uPassword, String fName, String sName) throws Exception {
        String entry = String.format("%s|%s|%s|%s|%s", uName, generateStrongPasswordHash(uPassword), fName, sName);
        new FileManager().addEntry(entry);
        new FileManager().createTBLManager(uName);
        System.out.println("Wrote password");
    }

    /**
     * Checks wether the username and password of a user are valid. It is done by taking the store encrypted password and comparing it to the password that is just been entered. The password is encrypted so that the stored and just entered
     * passwords are both encrypted and thus they are-compared meaning the actual inputted password is never actually stored for security measures
     * This is mainly done in ValidatePassword method but this method manages the interaction
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
     * Runs generate hash to create a hashed password as a string strong
     *
     * @param password
     * @return hashed password as a String
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
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
     * Converts the hex to byte array then encrypts the entered password and then checks the difference between the encrypted and the stored encrypted password
     * NOTE the password is never actually saved only the encrypted form of it
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
     * Converts the Byte array to a string
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



