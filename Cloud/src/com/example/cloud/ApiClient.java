package com.example.cloud;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Клиент для работы с HTTP API
 * @author Developer
 * @version 1.0
 */
public class ApiClient {
    
    private static final String TAG = "CloudStorage_ApiClient";
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;
    
    private Map<String, String> headers;
    
    /**
     * Конструктор клиента
     */
    public ApiClient() {
        this.headers = new HashMap<>();
        Log.v(TAG, "ApiClient: Создание HTTP клиента");
    }
    
    /**
     * Выполнение GET запроса
     * @param url URL запроса
     * @return Ответ сервера
     */
    public String get(String url) {
        Log.d(TAG, "get: Выполнение GET запроса к: " + url);
        return executeRequest(url, "GET", null);
    }
    
    /**
     * Выполнение POST запроса
     * @param url URL запроса
     * @param body Тело запроса
     * @return Ответ сервера
     */
    public String post(String url, String body) {
        Log.d(TAG, "post: Выполнение POST запроса к: " + url);
        return executeRequest(url, "POST", body);
    }
    
    /**
     * Выполнение PUT запроса
     * @param url URL запроса
     * @param body Тело запроса
     * @return Ответ сервера
     */
    public String put(String url, String body) {
        Log.d(TAG, "put: Выполнение PUT запроса к: " + url);
        return executeRequest(url, "PUT", body);
    }
    
    /**
     * Выполнение DELETE запроса
     * @param url URL запроса
     * @return Ответ сервера
     */
    public String delete(String url) {
        Log.d(TAG, "delete: Выполнение DELETE запроса к: " + url);
        return executeRequest(url, "DELETE", null);
    }
    
    /**
     * Выполнение HTTP запроса
     * @param urlStr URL запроса
     * @param method Метод запроса
     * @param body Тело запроса
     * @return Ответ сервера
     */
    private String executeRequest(String urlStr, String method, String body) {
        HttpURLConnection connection = null;
        
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            
            // Установка заголовков
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
            
            // Установка тела запроса для POST/PUT
            if (body != null && ("POST".equals(method) || "PUT".equals(method))) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(body.getBytes("UTF-8"));
                    os.flush();
                    Log.v(TAG, "executeRequest: Отправлено тело запроса, размер: " + body.length());
                }
            }
            
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "executeRequest: Код ответа: " + responseCode);
            
            // Чтение ответа
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            Log.d(TAG, "executeRequest: Получен ответ, размер: " + response.length());
            return response.toString();
            
        } catch (Exception e) {
            Log.e(TAG, "executeRequest: Ошибка выполнения запроса", e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /**
     * Добавление заголовка
     * @param key Ключ заголовка
     * @param value Значение заголовка
     */
    public void addHeader(String key, String value) {
        headers.put(key, value);
        Log.v(TAG, "addHeader: Добавлен заголовок: " + key + " = " + value);
    }
    
    /**
     * Удаление заголовка
     * @param key Ключ заголовка
     */
    public void removeHeader(String key) {
        headers.remove(key);
        Log.v(TAG, "removeHeader: Удален заголовок: " + key);
    }
    
    /**
     * Очистка всех заголовков
     */
    public void clearHeaders() {
        headers.clear();
        Log.d(TAG, "clearHeaders: Все заголовки очищены");
    }
}