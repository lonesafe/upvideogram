package com.roubsite.trans;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class GenerateEncryptionKey {
    public static void main(String[] args) {
        Key key = generateAESKey(128);
        System.out.println("Generated AES Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
    }

    public static Key generateAESKey(int keySize) {
        byte[] keyBytes = new byte[keySize / 8]; // 8 bits in a byte
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "AES");
    }
}