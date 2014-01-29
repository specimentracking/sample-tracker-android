package org.galaxyproject.sampletracker.logic.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Methods for doing cryptography operations.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class Crypto {

    private static final String AES = "AES";
    private static final String CHARSET = "UTF-8";
    private static final String AES_INSTANCE = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00};

    public enum HashMethod {
        SHA_1 ("SHA-1"),
        SHA_256 ("SHA-256"),
        SHA_512 ("SHA-512");

        private final String mAlgorithm;

        private HashMethod(String algorithm) {
            mAlgorithm = algorithm;
        }

        public String getAlgorithm() {
            return mAlgorithm;
        }
    }

    public static byte[] hash(HashMethod method, byte[] bytes) {
        MessageDigest sha;
        try {
            sha = MessageDigest.getInstance(method.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        sha.update(bytes);
        return sha.digest();
    }

    public static byte[] hashSha1(byte[] bytes) {
        return hash(HashMethod.SHA_1, bytes);
    }

    public static String hashSha1ToString(byte[] bytes) {
        return toHexString(hash(HashMethod.SHA_1, bytes));
    }

    public static byte[] hashSha256(byte[] bytes) {
        return hash(HashMethod.SHA_256, bytes);
    }

    public static String hashSha256ToString(byte[] bytes) {
        return toHexString(hash(HashMethod.SHA_256, bytes));
    }

    public static byte[] hashSha512(byte[] bytes) {
        return hash(HashMethod.SHA_512, bytes);
    }

    public static String hashSha512ToString(byte[] bytes) {
        return toHexString(hash(HashMethod.SHA_512, bytes));
    }

    public static String encryptAes(String key, String plain) throws CryptoException {
        try {
            SecretKeySpec keySpec = getKeySpec(key);
            byte[] result = doAes(Cipher.ENCRYPT_MODE, keySpec, plain.getBytes(CHARSET));
            return toHexString(result);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptAes(String key, String encrypted) throws CryptoException {
        SecretKeySpec keySpec = getKeySpec(key);
        byte[] enc = toByte(encrypted);
        byte[] result = doAes(Cipher.DECRYPT_MODE, keySpec, enc);
        return new String(result);
    }

    private static SecretKeySpec getKeySpec(String key) {
        try {
            // Make 256 bit has from key
            byte[] key256 = hashSha256(key.getBytes(CHARSET));
            // Use key for creating key spec
            return new SecretKeySpec(key256, AES);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] doAes(int mode, SecretKeySpec keySpec, byte[] bytes) throws CryptoException {
        try {
            Cipher cipher = Cipher.getInstance(AES_INSTANCE);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(IV);
            cipher.init(mode, keySpec, ivSpec);
            return cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        } catch (NoSuchPaddingException e) {
            throw new CryptoException(e);
        } catch (InvalidKeyException e) {
            throw new CryptoException(e);
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException(e);
        } catch (BadPaddingException e) {
            throw new CryptoException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new CryptoException(e);
        }
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    public static String toHexString(byte[] digest) {
        StringBuilder hexString = new StringBuilder();

        for (byte charByte : digest) {
            if ((charByte & 0xF0) == 0) {
                // insert zero, because Integer.toHexString will produce one char only
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(charByte & 0xFF));
        }
        return hexString.toString();
    }
}
