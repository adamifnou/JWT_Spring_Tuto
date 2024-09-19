package com.example.JwtTuto;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        try {
            int securityLevel = 512; // Change this value to 256, 512, etc.
            String base64SecretKey = generateSecretKey(securityLevel);
            System.out.println("Base64 Encoded Secret Key: " + base64SecretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateSecretKey(int securityLevel) throws Exception {
        KeyGenerator keyGen;
        switch (securityLevel) {
            case 256:
                keyGen = KeyGenerator.getInstance("HmacSHA256");
                break;
            case 512:
                keyGen = KeyGenerator.getInstance("HmacSHA512");
                break;
            default:
                throw new IllegalArgumentException("Unsupported security level: " + securityLevel);
        }
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}