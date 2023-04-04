package com.example.myapplication;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.CountDownTimer;
import android.widget.RemoteViews;
import android.widget.Toast;




import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "TaskLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_VALUE = "task_value";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_VALUE+ " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addTask(String returnString){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_VALUE, returnString);
        long result = db.insert(TABLE_NAME, null,cv);
        if (result == -1){
            Toast.makeText(context, "Помилка збереження!", Toast.LENGTH_SHORT).show();
        }
        else{
            new CountDownTimer(1000, 1000) {

                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {

                    Toast.makeText(context, "Завдання збережено!", Toast.LENGTH_SHORT).show();
                }
            }.start();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void deleteTask(String id_task){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?", new String[]{id_task});
        if (result == -1){
            Toast.makeText(context, "Помилка завершення завдання!", Toast.LENGTH_SHORT).show();
        }else{
            new CountDownTimer(100, 100) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    Toast.makeText(context, "Завдання завершено!", Toast.LENGTH_SHORT).show();
                }
            }.start();
        }
    }
}