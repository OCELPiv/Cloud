package com.example.cloud;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Утилиты для работы с файлами
 * @author Developer
 * @version 1.0
 */
public class FileUtils {
    
    private static final String TAG = "CloudStorage_FileUtils";
    
    /**
     * Копирование файла
     * @param source Исходный файл
     * @param dest Файл назначения
     * @return true если копирование успешно
     */
    public static boolean copyFile(File source, File dest) {
        Log.d(TAG, "copyFile: Копирование файла из " + source.getPath() + " в " + dest.getPath());
        
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            
            Log.i(TAG, "copyFile: Файл скопирован успешно");
            return true;
            
        } catch (IOException e) {
            Log.e(TAG, "copyFile: Ошибка копирования файла", e);
            return false;
        }
    }
    
    /**
     * Получение расширения файла
     * @param fileName Имя файла
     * @return Расширение файла
     */
    public static String getFileExtension(String fileName) {
        Log.v(TAG, "getFileExtension: Получение расширения файла: " + fileName);
        
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        
        int lastDot = fileName.lastIndexOf('.');
        String extension = lastDot > 0 ? fileName.substring(lastDot + 1).toLowerCase() : "";
        
        Log.v(TAG, "getFileExtension: Расширение: " + extension);
        return extension;
    }
    
    /**
     * Получение MIME типа файла
     * @param fileName Имя файла
     * @return MIME тип
     */
    public static String getMimeType(String fileName) {
        Log.v(TAG, "getMimeType: Определение MIME типа для: " + fileName);
        
        String extension = getFileExtension(fileName);
        String mimeType;
        
        switch (extension) {
            case "txt":
                mimeType = "text/plain";
                break;
            case "jpg":
            case "jpeg":
                mimeType = "image/jpeg";
                break;
            case "png":
                mimeType = "image/png";
                break;
            case "pdf":
                mimeType = "application/pdf";
                break;
            case "mp3":
                mimeType = "audio/mpeg";
                break;
            case "mp4":
                mimeType = "video/mp4";
                break;
            default:
                mimeType = "application/octet-stream";
                break;
        }
        
        Log.d(TAG, "getMimeType: MIME тип: " + mimeType);
        return mimeType;
    }
    
    /**
     * Форматирование размера файла в читаемый вид
     * @param size Размер в байтах
     * @return Отформатированная строка
     */
    public static String formatFileSize(long size) {
        Log.v(TAG, "formatFileSize: Форматирование размера: " + size);
        
        if (size <= 0) {
            return "0 B";
        }
        
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        
        String formatted = String.format("%.1f %s", 
                size / Math.pow(1024, digitGroups), units[digitGroups]);
        
        Log.v(TAG, "formatFileSize: Результат: " + formatted);
        return formatted;
    }
    
    /**
     * Проверка доступного места на диске
     * @param context Контекст
     * @return Доступное место в байтах
     */
    public static long getAvailableStorageSpace(Context context) {
        Log.d(TAG, "getAvailableStorageSpace: Проверка доступного места");
        
        File path = context.getFilesDir();
        long availableSpace = path.getUsableSpace();
        
        Log.i(TAG, "getAvailableStorageSpace: Доступно места: " + formatFileSize(availableSpace));
        return availableSpace;
    }
    
    /**
     * Создание временного файла
     * @param context Контекст
     * @param prefix Префикс имени файла
     * @param suffix Суффикс имени файла
     * @return Временный файл
     */
    public static File createTempFile(Context context, String prefix, String suffix) {
        Log.d(TAG, "createTempFile: Создание временного файла: " + prefix + "*" + suffix);
        
        try {
            File tempFile = File.createTempFile(prefix, suffix, context.getCacheDir());
            Log.i(TAG, "createTempFile: Временный файл создан: " + tempFile.getPath());
            return tempFile;
            
        } catch (IOException e) {
            Log.e(TAG, "createTempFile: Ошибка создания временного файла", e);
            return null;
        }
    }
    
    /**
     * Удаление временных файлов
     * @param context Контекст
     * @return Количество удаленных файлов
     */
    public static int cleanTempFiles(Context context) {
        Log.d(TAG, "cleanTempFiles: Очистка временных файлов");
        
        File cacheDir = context.getCacheDir();
        File[] files = cacheDir.listFiles();
        
        int deletedCount = 0;
        if (files != null) {
            for (File file : files) {
                if (file.delete()) {
                    deletedCount++;
                }
            }
        }
        
        Log.i(TAG, "cleanTempFiles: Удалено временных файлов: " + deletedCount);
        return deletedCount;
    }
}