package uk.co.afe.utils;

import org.apache.commons.lang.RandomStringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilities of security
 *
 * @author Sergey Teryoshin
 * 09.03.2017 21:53
 */
public final class SecurityUtils {

    private static final int SALT_SIZE = 64;
    private static final String SEQUENCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * Salt generation.
     */
    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return RandomStringUtils.random(SALT_SIZE, SEQUENCE.toCharArray());
    }

    /**
     * Generate hash code of string using specified salt
     */
    public static String getHash(String value, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((value + salt).getBytes());
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private SecurityUtils() {
    }

}
