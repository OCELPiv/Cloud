package com.example.cloud;

import android.util.Log;
import java.io.Serializable;
import java.util.Date;

/**
 * Класс файла в облачном хранилище
 * @author Developer
 * @version 1.0
 */
public class FileItem implements Serializable {
    
    private static final String TAG = "CloudStorage_FileItem";
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String path;
    private long size;
    private String mimeType;
    private Date createdAt;
    private Date modifiedAt;
    private String ownerId;
    private boolean isPublic;
    private String checksum;
    
    /**
     * Конструктор по умолчанию
     */
    public FileItem() {
        Log.v(TAG, "FileItem: Создание нового объекта файла");
        this.createdAt = new Date();
        this.modifiedAt = new Date();
        this.isPublic = false;
    }
    
    /**
     * Конструктор с основными параметрами
     * @param name Имя файла
     * @param size Размер файла
     */
    public FileItem(String name, long size) {
        this();
        this.name = name;
        this.size = size;
        Log.d(TAG, "FileItem: Создан файл: " + name + ", размер: " + size);
    }
    
    // Геттеры и сеттеры
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
        Log.v(TAG, "setId: Установлен ID файла: " + id);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        Log.d(TAG, "setName: Установлено имя файла: " + name);
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
        Log.v(TAG, "setPath: Установлен путь: " + path);
    }
    
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
        Log.d(TAG, "setSize: Установлен размер файла: " + size + " байт");
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
        Log.v(TAG, "setMimeType: Установлен MIME тип: " + mimeType);
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        Log.v(TAG, "setCreatedAt: Установлена дата создания: " + createdAt);
    }
    
    public Date getModifiedAt() {
        return modifiedAt;
    }
    
    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
        Log.v(TAG, "setModifiedAt: Установлена дата изменения: " + modifiedAt);
    }
    
    public String getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        Log.v(TAG, "setOwnerId: Установлен ID владельца: " + ownerId);
    }
    
    public boolean isPublic() {
        return isPublic;
    }
    
    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
        Log.d(TAG, "setPublic: Установлен статус публичности: " + aPublic);
    }
    
    public String getChecksum() {
        return checksum;
    }
    
    public void setChecksum(String checksum) {
        this.checksum = checksum;
        Log.v(TAG, "setChecksum: Установлена контрольная сумма: " + checksum);
    }
    
    /**
     * Обновление даты изменения
     */
    public void updateModifiedDate() {
        this.modifiedAt = new Date();
        Log.d(TAG, "updateModifiedDate: Обновлена дата изменения файла: " + name);
    }
    
    @Override
    public String toString() {
        return "FileItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", mimeType='" + mimeType + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}