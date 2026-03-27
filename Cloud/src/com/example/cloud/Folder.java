package com.example.cloud;

import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс папки в облачном хранилище
 * @author Developer
 * @version 1.0
 */
public class Folder implements Serializable {
    
    private static final String TAG = "CloudStorage_Folder";
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String path;
    private Date createdAt;
    private String ownerId;
    private List<FileItem> files;
    private List<Folder> subFolders;
    
    /**
     * Конструктор по умолчанию
     */
    public Folder() {
        Log.v(TAG, "Folder: Создание нового объекта папки");
        this.files = new ArrayList<>();
        this.subFolders = new ArrayList<>();
        this.createdAt = new Date();
    }
    
    /**
     * Конструктор с именем папки
     * @param name Имя папки
     */
    public Folder(String name) {
        this();
        this.name = name;
        Log.d(TAG, "Folder: Создана папка: " + name);
    }
    
    // Геттеры и сеттеры
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
        Log.v(TAG, "setId: Установлен ID папки: " + id);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        Log.d(TAG, "setName: Установлено имя папки: " + name);
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
        Log.v(TAG, "setPath: Установлен путь: " + path);
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        Log.v(TAG, "setCreatedAt: Установлена дата создания: " + createdAt);
    }
    
    public String getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        Log.v(TAG, "setOwnerId: Установлен ID владельца: " + ownerId);
    }
    
    public List<FileItem> getFiles() {
        return files;
    }
    
    public void setFiles(List<FileItem> files) {
        this.files = files;
        Log.d(TAG, "setFiles: Установлен список файлов, количество: " + files.size());
    }
    
    public List<Folder> getSubFolders() {
        return subFolders;
    }
    
    public void setSubFolders(List<Folder> subFolders) {
        this.subFolders = subFolders;
        Log.d(TAG, "setSubFolders: Установлен список подпапок, количество: " + subFolders.size());
    }
    
    /**
     * Добавление файла в папку
     * @param file Файл для добавления
     * @return true если файл добавлен, false если уже существует
     */
    public boolean addFile(FileItem file) {
        if (files.contains(file)) {
            Log.w(TAG, "addFile: Файл уже существует в папке: " + file.getName());
            return false;
        }
        
        files.add(file);
        Log.d(TAG, "addFile: Файл добавлен в папку: " + file.getName());
        return true;
    }
    
    /**
     * Удаление файла из папки
     * @param fileId ID файла для удаления
     * @return true если файл удален, false если не найден
     */
    public boolean removeFile(String fileId) {
        for (FileItem file : files) {
            if (file.getId().equals(fileId)) {
                files.remove(file);
                Log.d(TAG, "removeFile: Файл удален из папки: " + file.getName());
                return true;
            }
        }
        
        Log.w(TAG, "removeFile: Файл с ID " + fileId + " не найден");
        return false;
    }
    
    /**
     * Добавление подпапки
     * @param folder Подпапка для добавления
     * @return true если папка добавлена, false если уже существует
     */
    public boolean addSubFolder(Folder folder) {
        if (subFolders.contains(folder)) {
            Log.w(TAG, "addSubFolder: Подпапка уже существует: " + folder.getName());
            return false;
        }
        
        subFolders.add(folder);
        Log.d(TAG, "addSubFolder: Подпапка добавлена: " + folder.getName());
        return true;
    }
    
    /**
     * Получение общего размера папки
     * @return Размер папки в байтах
     */
    public long getTotalSize() {
        long totalSize = 0;
        
        for (FileItem file : files) {
            totalSize += file.getSize();
        }
        
        for (Folder folder : subFolders) {
            totalSize += folder.getTotalSize();
        }
        
        Log.d(TAG, "getTotalSize: Общий размер папки " + name + ": " + totalSize + " байт");
        return totalSize;
    }
    
    @Override
    public String toString() {
        return "Folder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", filesCount=" + files.size() +
                ", subFoldersCount=" + subFolders.size() +
                '}';
    }
}