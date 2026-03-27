package com.example.cloud;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.cloud.User;
import com.example.cloud.CloudStorageAPI;
import com.example.cloud.LocalStorageManager;

/**
 * Главная активность приложения облачного хранилища
 * @author Developer
 * @version 1.0
 */
public class MainActivity extends Activity {
    
    private static final String TAG = "CloudStorage_Main";
    private CloudStorageAPI cloudAPI;
    private LocalStorageManager localStorage;
    private User currentUser;
    
    private Button btnUpload;
    private Button btnDownload;
    private Button btnSync;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Убедитесь, что это activity_main, а не main
        
        Log.d(TAG, "onCreate: Инициализация приложения облачного хранилища");
        
        initializeComponents();
        setupUI();
        
        Log.i(TAG, "onCreate: Приложение успешно инициализировано");
    }
    
    private void initializeComponents() {
        Log.v(TAG, "initializeComponents: Начало инициализации компонентов");
        
        try {
            cloudAPI = new CloudStorageAPI(this);
            localStorage = new LocalStorageManager(this);
            currentUser = new User();
            
            Log.d(TAG, "initializeComponents: Компоненты успешно инициализированы");
        } catch (Exception e) {
            Log.e(TAG, "initializeComponents: Ошибка инициализации", e);
            Toast.makeText(this, "Ошибка инициализации: " + e.getMessage(), 
                          Toast.LENGTH_LONG).show();
        }
    }
    
    private void setupUI() {
        Log.d(TAG, "setupUI: Настройка интерфейса пользователя");
        
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnDownload = (Button) findViewById(R.id.btn_download);
        btnSync = (Button) findViewById(R.id.btn_sync);
        
        if (btnUpload != null) {
            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Нажата кнопка загрузки файла");
                    uploadFile();
                }
            });
        } else {
            Log.e(TAG, "setupUI: Кнопка btn_upload не найдена");
        }
        
        if (btnDownload != null) {
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Нажата кнопка скачивания файла");
                    downloadFile();
                }
            });
        } else {
            Log.e(TAG, "setupUI: Кнопка btn_download не найдена");
        }
        
        if (btnSync != null) {
            btnSync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: Нажата кнопка синхронизации");
                    syncWithCloud();
                }
            });
        } else {
            Log.e(TAG, "setupUI: Кнопка btn_sync не найдена");
        }
    }
    
    private void uploadFile() {
        Log.d(TAG, "uploadFile: Подготовка к загрузке файла");
        Toast.makeText(this, "Загрузка файла...", Toast.LENGTH_SHORT).show();
        
        if (cloudAPI != null) {
            cloudAPI.uploadFile("test.txt", new byte[]{});
        } else {
            Log.e(TAG, "uploadFile: cloudAPI не инициализирован");
        }
    }
    
    private void downloadFile() {
        Log.d(TAG, "downloadFile: Подготовка к скачиванию файла");
        Toast.makeText(this, "Скачивание файла...", Toast.LENGTH_SHORT).show();
        
        if (cloudAPI != null) {
            cloudAPI.downloadFile("test.txt");
        } else {
            Log.e(TAG, "downloadFile: cloudAPI не инициализирован");
        }
    }
    
    private void syncWithCloud() {
        Log.d(TAG, "syncWithCloud: Начало синхронизации");
        Toast.makeText(this, "Синхронизация...", Toast.LENGTH_SHORT).show();
        
        if (cloudAPI != null) {
            cloudAPI.syncFiles();
        } else {
            Log.e(TAG, "syncWithCloud: cloudAPI не инициализирован");
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Завершение работы приложения");
    }
}