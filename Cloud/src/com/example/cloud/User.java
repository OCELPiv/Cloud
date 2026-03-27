package com.example.cloud;

import android.util.Log;
import java.io.Serializable;

/**
 * Класс пользователя облачного хранилища
 * @author Developer
 * @version 1.0
 */
public class User implements Serializable {
    
    private static final String TAG = "CloudStorage_User";
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private long storageLimit;
    private long storageUsed;
    private String authToken;
    
    /**
     * Конструктор по умолчанию
     */
    public User() {
        Log.v(TAG, "User: Создание нового объекта пользователя");
        this.storageLimit = 5 * 1024 * 1024 * 1024L; // 5 GB
        this.storageUsed = 0;
    }
    
    /**
     * Конструктор с параметрами
     * @param username Имя пользователя
     * @param email Email пользователя
     */
    public User(String username, String email) {
        this();
        this.username = username;
        this.email = email;
        Log.d(TAG, "User: Создан пользователь: " + username);
    }
    
    // Геттеры и сеттеры
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
        Log.v(TAG, "setId: Установлен ID пользователя: " + id);
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
        Log.d(TAG, "setUsername: Установлено имя пользователя: " + username);
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
        Log.d(TAG, "setEmail: Установлен email: " + email);
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        Log.v(TAG, "setPasswordHash: Установлен хэш пароля");
    }
    
    public long getStorageLimit() {
        return storageLimit;
    }
    
    public void setStorageLimit(long storageLimit) {
        this.storageLimit = storageLimit;
        Log.d(TAG, "setStorageLimit: Установлен лимит хранилища: " + storageLimit + " байт");
    }
    
    public long getStorageUsed() {
        return storageUsed;
    }
    
    public void setStorageUsed(long storageUsed) {
        this.storageUsed = storageUsed;
        Log.d(TAG, "setStorageUsed: Использовано хранилища: " + storageUsed + " байт");
    }
    
    public String getAuthToken() {
        return authToken;
    }
    
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
        Log.v(TAG, "setAuthToken: Установлен токен авторизации");
    }
    
    /**
     * Проверка наличия свободного места
     * @param fileSize Размер файла
     * @return true если есть место, false если нет
     */
    public boolean hasEnoughSpace(long fileSize) {
        boolean hasSpace = (storageUsed + fileSize) <= storageLimit;
        Log.d(TAG, "hasEnoughSpace: Проверка свободного места. Размер: " + fileSize + 
                   ", Результат: " + hasSpace);
        return hasSpace;
    }
    
    /**
     * Расчет процента использованного места
     * @return Процент использованного места
     */
    public float getStorageUsagePercent() {
        float percent = (float) storageUsed / storageLimit * 100;
        Log.v(TAG, "getStorageUsagePercent: Использовано " + percent + "% места");
        return percent;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", storageLimit=" + storageLimit +
                ", storageUsed=" + storageUsed +
                '}';
    }
}