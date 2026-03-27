package com.example.cloud;

import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Утилиты для шифрования данных
 * @author Developer
 * @version 1.0
 */
public class EncryptionUtils {
    
    private static final String TAG = "CloudStorage_Encrypt";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    /**
     * Генерация ключа шифрования
     * @return Секретный ключ
     */
    public static SecretKey generateKey() {
        Log.d(TAG, "generateKey: Генерация ключа шифрования");
        
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(256);
            SecretKey key = keyGen.generateKey();
            
            Log.i(TAG, "generateKey: Ключ успешно сгенерирован");
            return key;
            
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "generateKey: Ошибка генерации ключа", e);
            return null;
        }
    }
    
    /**
     * Шифрование данных
     * @param data Данные для шифрования
     * @param key Ключ шифрования
     * @return Зашифрованные данные
     */
    public static byte[] encrypt(byte[] data, SecretKey key) {
        Log.d(TAG, "encrypt: Шифрование данных, размер: " + data.length);
        
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data);
            
            Log.i(TAG, "encrypt: Данные зашифрованы, размер: " + encryptedData.length);
            return encryptedData;
            
        } catch (Exception e) {
            Log.e(TAG, "encrypt: Ошибка шифрования", e);
            return null;
        }
    }
    
    /**
     * Дешифрование данных
     * @param encryptedData Зашифрованные данные
     * @param key Ключ шифрования
     * @return Расшифрованные данные
     */
    public static byte[] decrypt(byte[] encryptedData, SecretKey key) {
        Log.d(TAG, "decrypt: Дешифрование данных, размер: " + encryptedData.length);
        
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedData = cipher.doFinal(encryptedData);
            
            Log.i(TAG, "decrypt: Данные расшифрованы, размер: " + decryptedData.length);
            return decryptedData;
            
        } catch (Exception e) {
            Log.e(TAG, "decrypt: Ошибка дешифрования", e);
            return null;
        }
    }
    
    /**
     * Получение MD5 хэша данных
     * @param data Данные
     * @return MD5 хэш в виде строки
     */
    public static String getMD5Hash(byte[] data) {
        Log.v(TAG, "getMD5Hash: Вычисление MD5 хэша");
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(data);
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            String result = hexString.toString();
            Log.v(TAG, "getMD5Hash: Хэш вычислен: " + result);
            return result;
            
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "getMD5Hash: Ошибка вычисления хэша", e);
            return null;
        }
    }
    
    /**
     * Получение SHA-256 хэша данных
     * @param data Данные
     * @return SHA-256 хэш в виде строки
     */
    public static String getSHA256Hash(byte[] data) {
        Log.v(TAG, "getSHA256Hash: Вычисление SHA-256 хэша");
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data);
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            String result = hexString.toString();
            Log.v(TAG, "getSHA256Hash: Хэш вычислен");
            return result;
            
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "getSHA256Hash: Ошибка вычисления хэша", e);
            return null;
        }
    }
    
    /**
     * Преобразование строки в ключ
     * @param keyString Строка ключа
     * @return Секретный ключ
     */
    public static SecretKey stringToKey(String keyString) {
        Log.v(TAG, "stringToKey: Преобразование строки в ключ");
        
        try {
            byte[] keyBytes = keyString.getBytes("UTF-8");
            SecretKey key = new SecretKeySpec(keyBytes, ALGORITHM);
            
            Log.d(TAG, "stringToKey: Ключ создан");
            return key;
            
        } catch (Exception e) {
            Log.e(TAG, "stringToKey: Ошибка преобразования", e);
            return null;
        }
    }
}