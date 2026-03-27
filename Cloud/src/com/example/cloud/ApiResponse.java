package com.example.cloud;

import android.util.Log;

/**
 * Класс ответа API
 * @author Developer
 * @version 1.0
 * @param <T> Тип данных ответа
 */
public class ApiResponse<T> {
    
    private static final String TAG = "CloudStorage_ApiResponse";
    
    private boolean success;
    private String message;
    private T data;
    
    /**
     * Конструктор ответа API
     * @param success Статус успешности
     * @param message Сообщение
     * @param data Данные ответа
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        
        Log.v(TAG, "ApiResponse: Создан ответ API. Успех: " + success + 
                  ", Сообщение: " + message);
    }
    
    /**
     * Проверка успешности выполнения
     * @return true если успешно, false если ошибка
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * Получение сообщения
     * @return Сообщение ответа
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Получение данных ответа
     * @return Данные ответа
     */
    public T getData() {
        return data;
    }
    
    /**
     * Установка статуса успешности
     * @param success Статус успешности
     */
    public void setSuccess(boolean success) {
        this.success = success;
        Log.v(TAG, "setSuccess: Установлен статус успешности: " + success);
    }
    
    /**
     * Установка сообщения
     * @param message Сообщение
     */
    public void setMessage(String message) {
        this.message = message;
        Log.v(TAG, "setMessage: Установлено сообщение: " + message);
    }
    
    /**
     * Установка данных ответа
     * @param data Данные ответа
     */
    public void setData(T data) {
        this.data = data;
        Log.v(TAG, "setData: Установлены данные ответа");
    }
    
    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}