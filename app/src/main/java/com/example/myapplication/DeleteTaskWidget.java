package com.example.myapplication;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteTaskWidget extends AppCompatActivity {
    MyDatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            ArrayList<String> task_id = new ArrayList<>();
            ArrayList<String> task_value = new ArrayList<>();

            MyDatabaseHelper myDB = new MyDatabaseHelper(this);
            Cursor cursor = myDB.readAllData();
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_value.add(cursor.getString(1));
            }
            myDB.deleteTask(task_id.get(0));
        }catch (Exception e){

        }

        updateWidget();
        finish();
    }
    void updateWidget(){
        Intent intent = new Intent(this, Task_Widget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Task_Widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }

}
