package com.example.cloud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.cloud.FileItem;
import com.example.cloud.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Помощник для работы с базой данных
 * @author Developer
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String TAG = "CloudStorage_DBHelper";
    private static final String DATABASE_NAME = "cloud_storage.db";
    private static final int DATABASE_VERSION = 1;
    
    // Таблица пользователей
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD_HASH = "password_hash";
    private static final String COLUMN_STORAGE_LIMIT = "storage_limit";
    private static final String COLUMN_STORAGE_USED = "storage_used";
    
    // Таблица файлов
    private static final String TABLE_FILES = "files";
    private static final String COLUMN_FILE_ID = "id";
    private static final String COLUMN_FILE_NAME = "name";
    private static final String COLUMN_FILE_PATH = "path";
    private static final String COLUMN_FILE_SIZE = "size";
    private static final String COLUMN_MIME_TYPE = "mime_type";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_MODIFIED_AT = "modified_at";
    private static final String COLUMN_OWNER_ID = "owner_id";
    private static final String COLUMN_IS_PUBLIC = "is_public";
    private static final String COLUMN_CHECKSUM = "checksum";
    
    /**
     * Конструктор помощника БД
     * @param context Контекст приложения
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "DatabaseHelper: Инициализация базы данных");
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: Создание таблиц базы данных");
        
        // Создание таблицы пользователей
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL,"
                + COLUMN_EMAIL + " TEXT UNIQUE NOT NULL,"
                + COLUMN_PASSWORD_HASH + " TEXT NOT NULL,"
                + COLUMN_STORAGE_LIMIT + " INTEGER,"
                + COLUMN_STORAGE_USED + " INTEGER"
                + ")";
        db.execSQL(createUsersTable);
        
        // Создание таблицы файлов
        String createFilesTable = "CREATE TABLE " + TABLE_FILES + "("
                + COLUMN_FILE_ID + " TEXT PRIMARY KEY,"
                + COLUMN_FILE_NAME + " TEXT NOT NULL,"
                + COLUMN_FILE_PATH + " TEXT,"
                + COLUMN_FILE_SIZE + " INTEGER,"
                + COLUMN_MIME_TYPE + " TEXT,"
                + COLUMN_CREATED_AT + " INTEGER,"
                + COLUMN_MODIFIED_AT + " INTEGER,"
                + COLUMN_OWNER_ID + " INTEGER,"
                + COLUMN_IS_PUBLIC + " INTEGER,"
                + COLUMN_CHECKSUM + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_OWNER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        db.execSQL(createFilesTable);
        
        Log.i(TAG, "onCreate: Таблицы успешно созданы");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: Обновление базы данных с версии " + oldVersion + " до " + newVersion);
        
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
        
        Log.i(TAG, "onUpgrade: База данных обновлена");
    }
    
    /**
     * Добавление пользователя
     * @param user Пользователь
     * @return ID пользователя или -1 при ошибке
     */
    public long addUser(User user) {
        Log.d(TAG, "addUser: Добавление пользователя: " + user.getUsername());
        
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD_HASH, user.getPasswordHash());
        values.put(COLUMN_STORAGE_LIMIT, user.getStorageLimit());
        values.put(COLUMN_STORAGE_USED, user.getStorageUsed());
        
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        
        if (id != -1) {
            Log.i(TAG, "addUser: Пользователь добавлен с ID: " + id);
        } else {
            Log.e(TAG, "addUser: Ошибка добавления пользователя");
        }
        
        return id;
    }
    
    /**
     * Получение пользователя по имени
     * @param username Имя пользователя
     * @return Пользователь или null
     */
    public User getUser(String username) {
        Log.d(TAG, "getUser: Поиск пользователя: " + username);
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USERNAME + "=?",
                new String[]{username}, null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            user.setPasswordHash(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_HASH)));
            user.setStorageLimit(cursor.getLong(cursor.getColumnIndex(COLUMN_STORAGE_LIMIT)));
            user.setStorageUsed(cursor.getLong(cursor.getColumnIndex(COLUMN_STORAGE_USED)));
            
            Log.i(TAG, "getUser: Пользователь найден: " + username);
        } else {
            Log.w(TAG, "getUser: Пользователь не найден: " + username);
        }
        
        cursor.close();
        db.close();
        return user;
    }
    
    /**
     * Добавление файла
     * @param file Файл
     * @return true если добавление успешно
     */
    public boolean addFile(FileItem file) {
        Log.d(TAG, "addFile: Добавление файла: " + file.getName());
        
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_FILE_ID, file.getId());
        values.put(COLUMN_FILE_NAME, file.getName());
        values.put(COLUMN_FILE_PATH, file.getPath());
        values.put(COLUMN_FILE_SIZE, file.getSize());
        values.put(COLUMN_MIME_TYPE, file.getMimeType());
        values.put(COLUMN_CREATED_AT, file.getCreatedAt().getTime());
        values.put(COLUMN_MODIFIED_AT, file.getModifiedAt().getTime());
        values.put(COLUMN_OWNER_ID, file.getOwnerId());
        values.put(COLUMN_IS_PUBLIC, file.isPublic() ? 1 : 0);
        values.put(COLUMN_CHECKSUM, file.getChecksum());
        
        long result = db.insert(TABLE_FILES, null, values);
        db.close();
        
        boolean success = result != -1;
        if (success) {
            Log.i(TAG, "addFile: Файл добавлен: " + file.getName());
        } else {
            Log.e(TAG, "addFile: Ошибка добавления файла: " + file.getName());
        }
        
        return success;
    }
    
    /**
     * Получение всех файлов пользователя
     * @param userId ID пользователя
     * @return Список файлов
     */
    public List<FileItem> getUserFiles(int userId) {
        Log.d(TAG, "getUserFiles: Получение файлов пользователя с ID: " + userId);
        
        List<FileItem> files = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_FILES, null, COLUMN_OWNER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null);
        
        while (cursor.moveToNext()) {
            FileItem file = new FileItem();
            file.setId(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_ID)));
            file.setName(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_NAME)));
            file.setPath(cursor.getString(cursor.getColumnIndex(COLUMN_FILE_PATH)));
            file.setSize(cursor.getLong(cursor.getColumnIndex(COLUMN_FILE_SIZE)));
            file.setMimeType(cursor.getString(cursor.getColumnIndex(COLUMN_MIME_TYPE)));
            file.setCreatedAt(new java.util.Date(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_AT))));
            file.setModifiedAt(new java.util.Date(cursor.getLong(cursor.getColumnIndex(COLUMN_MODIFIED_AT))));
            file.setOwnerId(String.valueOf(userId));
            file.setPublic(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_PUBLIC)) == 1);
            file.setChecksum(cursor.getString(cursor.getColumnIndex(COLUMN_CHECKSUM)));
            
            files.add(file);
        }
        
        cursor.close();
        db.close();
        
        Log.i(TAG, "getUserFiles: Найдено файлов: " + files.size());
        return files;
    }
    
    /**
     * Удаление файла
     * @param fileId ID файла
     * @return true если удаление успешно
     */
    public boolean deleteFile(String fileId) {
        Log.d(TAG, "deleteFile: Удаление файла с ID: " + fileId);
        
        SQLiteDatabase db = this.getWritableDatabase();
        int deleted = db.delete(TABLE_FILES, COLUMN_FILE_ID + "=?", new String[]{fileId});
        db.close();
        
        boolean success = deleted > 0;
        if (success) {
            Log.i(TAG, "deleteFile: Файл удален: " + fileId);
        } else {
            Log.w(TAG, "deleteFile: Файл не найден: " + fileId);
        }
        
        return success;
    }
}