package com.erdk.ABotanJNI1;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class JavaCrypto {

    public static void main(String[] argv) {
        byte[] salt = new byte[8];
        String key;
        for (int i = 0; i < 5; i++) {
            key = JavaCrypto.pbkdf2Demo(Constants.password, salt, 4096, 32);
            System.out.print("RSA priv key ->" + key + "\n");
        }
    }

    public static String pbkdf2Demo(char[] password, byte[] salt, int iterations, int keyLength) {

        Key key = null;
        SecretKeyFactory secretKeyFactory;

        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password, salt, iterations, keyLength);
            key = secretKeyFactory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return bytesToHex(key.getEncoded());
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
