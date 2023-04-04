package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Task_Widget extends AppWidgetProvider{

    private static final String ACTION_SIMPLE_APP_WIDGET = "ACTION_BROADCASTWIDGETSAMPLE";


    private static final String ACTION1 = "0";
    private static final String ACTION2 = "1";
    private static final String ACTION3 = "2";
    private static final String ACTION4 = "3";
    private static final String ACTION5 = "4";
    private static final String ACTION6 = "5";
    private static final String ACTION7 = "6";

    private static final String MyOnClick = "myOnClickTag";
    MyDatabaseHelper myDB;

    @SuppressLint("ResourceType")
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        @SuppressLint("RemoteViewLayout") RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.task__widget);
        views.removeAllViews(R.id.linearLayout1);

        views.setTextViewText(R.id.CurrentTask,"Список завдань");
        //Add button
        Intent AddButtonIntent = new Intent(context, AddTaskActivityWidget.class);
        PendingIntent AddButtonPendingIntent = PendingIntent.getActivity(context, 0, AddButtonIntent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.add_button_widget, AddButtonPendingIntent);



        ArrayList<String> task_id = new ArrayList<>();
        ArrayList<String> task_value = new ArrayList<>();

        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        Cursor cursor = myDB.readAllData();
        while (cursor.moveToNext()) {
            task_id.add(cursor.getString(0));
            task_value.add(cursor.getString(1));
        }
        if(task_value.size()>0){
            views.setTextViewText(R.id.EmptyDatabase,"");
        }
        else{
            views.setTextViewText(R.id.EmptyDatabase,"Завдання відсутні!");
        }



        try{
            @SuppressLint("RemoteViewLayout") RemoteViews checkBox = new RemoteViews(context.getPackageName(), R.layout.text_view_layout);
            checkBox.setTextViewText(R.id.checkBox, task_value.get(0));
            views.addView(R.id.linearLayout1, checkBox);

            Intent intentf = new Intent(context, Task_Widget.class);
            intentf.setAction(ACTION1);
            PendingIntent test = PendingIntent.getBroadcast(context, 0, intentf,
                    PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            checkBox.setOnClickPendingIntent(R.id.checkBox, test);
        }catch (Exception e){}

        try{
            @SuppressLint("RemoteViewLayout") RemoteViews checkBox1 = new RemoteViews(context.getPackageName(), R.layout.test1);
            checkBox1.setTextViewText(R.id.checkBox1, task_value.get(1));
            views.addView(R.id.linearLayout1, checkBox1);

            Intent intent1 = new Intent(context, Task_Widget.class);
            intent1.setAction(ACTION2);
            PendingIntent test = PendingIntent.getBroadcast(context, 0, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            checkBox1.setOnClickPendingIntent(R.id.checkBox1, test);
        }catch (Exception e){}
        try{
            @SuppressLint("RemoteViewLayout") RemoteViews checkBox2 = new RemoteViews(context.getPackageName(), R.layout.checkbox2);
            checkBox2.setTextViewText(R.id.checkBox2, task_value.get(2));
            views.addView(R.id.linearLayout1, checkBox2);

            Intent intent2 = new Intent(context, Task_Widget.class);
            intent2.setAction(ACTION3);
            PendingIntent test = PendingIntent.getBroadcast(context, 0, intent2,
                    PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            checkBox2.setOnClickPendingIntent(R.id.checkBox2, test);
        }catch (Exception e){}
        try{
            @SuppressLint("RemoteViewLayout") RemoteViews checkBox3 = new RemoteViews(context.getPackageName(), R.layout.checkbox3);
            checkBox3.setTextViewText(R.id.checkBox3, task_value.get(3));
            views.addView(R.id.linearLayout1, checkBox3);

            Intent intent3 = new Intent(context, Task_Widget.class);
            intent3.setAction(ACTION4);
            PendingIntent test = PendingIntent.getBroadcast(context, 0, intent3,
                    PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            checkBox3.setOnClickPendingIntent(R.id.checkBox3, test);
        }catch (Exception e){}
        try{
            @SuppressLint("RemoteViewLayout") RemoteViews checkBox4 = new RemoteViews(context.getPackageName(), R.layout.checkbox4);
            checkBox4.setTextViewText(R.id.checkBox4, task_value.get(4));
            views.addView(R.id.linearLayout1, checkBox4);

            Intent intent4 = new Intent(context, Task_Widget.class);
            intent4.setAction(ACTION5);
            PendingIntent test = PendingIntent.getBroadcast(context, 0, intent4,
                    PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            checkBox4.setOnClickPendingIntent(R.id.checkBox4, test);
        }catch (Exception e){}
        try{
            @SuppressLint("RemoteViewLayout") RemoteViews checkBox5 = new RemoteViews(context.getPackageName(), R.layout.checkbox5);
            checkBox5.setTextViewText(R.id.checkBox5, task_value.get(5));
            views.addView(R.id.linearLayout1, checkBox5);

            Intent intent5 = new Intent(context, Task_Widget.class);
            intent5.setAction(ACTION6);
            PendingIntent test = PendingIntent.getBroadcast(context, 0, intent5,
                    PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            checkBox5.setOnClickPendingIntent(R.id.checkBox5, test);
        }catch (Exception e){}
        try{
            @SuppressLint("RemoteViewLayout") RemoteViews checkBox6 = new RemoteViews(context.getPackageName(), R.layout.checkbox6);
            checkBox6.setTextViewText(R.id.checkBox6, task_value.get(6));
            views.addView(R.id.linearLayout1, checkBox6);

            Intent intent6 = new Intent(context, Task_Widget.class);
            intent6.setAction(ACTION7);
            PendingIntent test = PendingIntent.getBroadcast(context, 0, intent6,
                    PendingIntent.FLAG_UPDATE_CURRENT |PendingIntent.FLAG_IMMUTABLE);
            checkBox6.setOnClickPendingIntent(R.id.checkBox2, test);
        }catch (Exception e){}








        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent ) {
        super.onReceive(context, intent);

        try{

            ArrayList<String> task_id = new ArrayList<>();
            ArrayList<String> task_value = new ArrayList<>();
            MyDatabaseHelper myDB = new MyDatabaseHelper(context);
            Cursor cursor = myDB.readAllData();
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_value.add(cursor.getString(1));
            }

            if (ACTION1.equals(intent.getAction())){
                myDB.deleteTask(task_id.get(0));


            }
            if (ACTION2.equals(intent.getAction())){
                myDB.deleteTask(task_id.get(1));
            }
            if (ACTION3.equals(intent.getAction())){
                myDB.deleteTask(task_id.get(2));
            }
            if (ACTION4.equals(intent.getAction())){
                myDB.deleteTask(task_id.get(3));
            }
            if (ACTION5.equals(intent.getAction())){
                myDB.deleteTask(task_id.get(4));
            }
            if (ACTION6.equals(intent.getAction())){
                myDB.deleteTask(task_id.get(5));
            }
            if (ACTION7.equals(intent.getAction())){
                myDB.deleteTask(task_id.get(6));
            }

        }
        catch (Exception e){}



    }
}