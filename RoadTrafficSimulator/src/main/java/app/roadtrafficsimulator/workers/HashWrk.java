package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.exceptions.UnexpectedException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashWrk {
    public static String hash(String in) {
        try {
            // Digest the password
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(in.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md5.digest();
            // Encode digest to base64
            return Base64.getEncoder().encodeToString(digest);

        } catch (NoSuchAlgorithmException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }
}
