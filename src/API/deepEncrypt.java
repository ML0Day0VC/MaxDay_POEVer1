/**
 * @author Max Day
 * Created At: 2023/04/04
 */
package API;

import com.sun.jdi.PathSearchingVirtualMachine;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

/**
 * TODO: make sure to only allow certain characters in usernames and passwords
 * Author: Max Day
 * Adapted form oracle docs
 */


public class deepEncrypt {
    private static String[] uInfo = null;

    public static void genNewUser(String uName, String uPassword, String fName, String sName, String dOB) throws Exception {
        System.out.println("Wrote password");
        new FileManager().addEntry(String.format("%s|%s|%s|%s|%s", uName, generateStorngPasswordHash(uPassword), fName, sName, dOB));
        new FileManager().createTBLManager(uName);
    }

    /**
     * Boolean to check the validity of a username and password when checked in the database
     *
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
            String s = scanner.next();
            if (s.contains(inUser)) { //username found
                uInfo = s.split("\\|");
                if (validatePassword(inPass, uInfo[1])) {
                    System.out.println("Password correct. Logged into account.");
                    return true;
                } else {
                    System.err.println("Incorrect login or password ");
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
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Generates a salt using the secure random class
     *
     * @return byte array a random salt
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * converts the Byte array to a string
     *
     * @param array
     * @return byte array of hex characters
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
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
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length; //credit to some reddit feed for this bullshit and how to validate the different hash lenghts
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    /**
     * Converts hex to byte array
     *
     * @param hex input String
     * @return byte array of characters
     */
    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;

    }



}



