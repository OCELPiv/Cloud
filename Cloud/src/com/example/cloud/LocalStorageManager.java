package com.example.cloud;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Менеджер локального хранилища
 * @author Developer
 * @version 1.0
 */
public class LocalStorageManager {
    
    private static final String TAG = "CloudStorage_LocalStorage";
    private static final String STORAGE_DIR = "cloud_storage";
    
    private Context context;
    private File storageDir;
    
    /**
     * Конструктор менеджера
     * @param context Контекст приложения
     */
    public LocalStorageManager(Context context) {
        this.context = context;
        this.storageDir = new File(context.getFilesDir(), STORAGE_DIR);
        
        // Создание директории хранилища
        if (!storageDir.exists()) {
            boolean created = storageDir.mkdirs();
            Log.d(TAG, "LocalStorageManager: Создание директории хранилища: " + created);
        }
        
        Log.i(TAG, "LocalStorageManager: Инициализирован путь хранилища: " + storageDir.getAbsolutePath());
    }
    
    /**
     * Сохранение файла локально
     * @param fileName Имя файла
     * @param data Данные файла
     * @return true если сохранение успешно
     */
    public boolean saveFile(String fileName, byte[] data) {
        Log.d(TAG, "saveFile: Сохранение файла: " + fileName + ", размер: " + data.length);
        
        try {
            File file = new File(storageDir, fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
                fos.flush();
            }
            
            Log.i(TAG, "saveFile: Файл успешно сохранен: " + fileName);
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "saveFile: Ошибка сохранения файла", e);
            return false;
        }
    }
    
    /**
     * Загрузка файла из локального хранилища
     * @param fileName Имя файла
     * @return Данные файла или null при ошибке
     */
    public byte[] loadFile(String fileName) {
        Log.d(TAG, "loadFile: Загрузка файла: " + fileName);
        
        try {
            File file = new File(storageDir, fileName);
            if (!file.exists()) {
                Log.w(TAG, "loadFile: Файл не найден: " + fileName);
                return null;
            }
            
            byte[] data = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(data);
            }
            
            Log.i(TAG, "loadFile: Файл успешно загружен: " + fileName + ", размер: " + data.length);
            return data;
            
        } catch (IOException e) {
            Log.e(TAG, "loadFile: Ошибка загрузки файла", e);
            return null;
        }
    }
    
    /**
     * Удаление файла из локального хранилища
     * @param fileName Имя файла
     * @return true если удаление успешно
     */
    public boolean deleteFile(String fileName) {
        Log.d(TAG, "deleteFile: Удаление файла: " + fileName);
        
        try {
            File file = new File(storageDir, fileName);
            if (!file.exists()) {
                Log.w(TAG, "deleteFile: Файл не найден: " + fileName);
                return false;
            }
            
            boolean deleted = file.delete();
            if (deleted) {
                Log.i(TAG, "deleteFile: Файл успешно удален: " + fileName);
            } else {
                Log.w(TAG, "deleteFile: Не удалось удалить файл: " + fileName);
            }
            
            return deleted;
            
        } catch (Exception e) {
            Log.e(TAG, "deleteFile: Ошибка удаления файла", e);
            return false;
        }
    }
    
    /**
     * Проверка существования файла
     * @param fileName Имя файла
     * @return true если файл существует
     */
    public boolean fileExists(String fileName) {
        File file = new File(storageDir, fileName);
        boolean exists = file.exists();
        Log.v(TAG, "fileExists: Проверка существования файла: " + fileName + " - " + exists);
        return exists;
    }
    
    /**
     * Получение списка всех файлов
     * @return Массив имен файлов
     */
    public String[] listFiles() {
        Log.d(TAG, "listFiles: Получение списка файлов");
        
        String[] files = storageDir.list();
        int count = files != null ? files.length : 0;
        Log.i(TAG, "listFiles: Найдено файлов: " + count);
        
        return files;
    }
    
    /**
     * Получение размера файла
     * @param fileName Имя файла
     * @return Размер файла или -1 при ошибке
     */
    public long getFileSize(String fileName) {
        File file = new File(storageDir, fileName);
        
        if (file.exists()) {
            long size = file.length();
            Log.v(TAG, "getFileSize: Размер файла " + fileName + ": " + size);
            return size;
        }
        
        Log.w(TAG, "getFileSize: Файл не найден: " + fileName);
        return -1;
    }
    
    /**
     * Получение общего размера хранилища
     * @return Общий размер всех файлов
     */
    public long getTotalStorageSize() {
        long totalSize = 0;
        
        String[] files = storageDir.list();
        if (files != null) {
            for (String fileName : files) {
                totalSize += getFileSize(fileName);
            }
        }
        
        Log.d(TAG, "getTotalStorageSize: Общий размер хранилища: " + totalSize + " байт");
        return totalSize;
    }
    
    /**
     * Очистка локального хранилища
     * @return Количество удаленных файлов
     */
    public int clearStorage() {
        Log.d(TAG, "clearStorage: Очистка локального хранилища");
        
        int deletedCount = 0;
        String[] files = storageDir.list();
        
        if (files != null) {
            for (String fileName : files) {
                if (deleteFile(fileName)) {
                    deletedCount++;
                }
            }
        }
        
        Log.i(TAG, "clearStorage: Удалено файлов: " + deletedCount);
        return deletedCount;
    }
}