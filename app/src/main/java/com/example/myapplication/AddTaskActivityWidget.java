package com.example.myapplication;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

public class AddTaskActivityWidget extends AppCompatActivity implements View.OnClickListener{
    Button add_button;
    Button cancel;

    EditText input;

    String notation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Створення завдання");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_test);
        add_button = (Button)findViewById(R.id.add_button2);
        cancel = (Button)findViewById(R.id.cancel2);

        add_button.setEnabled(false);

        input = (EditText) findViewById(R.id.editTextTextMultiLine);

        add_button.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //corner radius
        float radius = 50f;
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build();

        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        ViewCompat.setBackground(input,shapeDrawable);
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                shapeDrawable.setFillColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.darker_gray));
                input.setTextColor(WHITE);

                break;
            case Configuration.UI_MODE_NIGHT_NO:
                shapeDrawable.setFillColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.light_gray));
                input.setTextColor(BLACK);
                break;
        }
        input.setPadding(40,40, 0,0);

        //focus keyboard
        input.requestFocus();
        input.postDelayed(new Runnable(){
                              @Override public void run(){
                                  InputMethodManager keyboard=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                  keyboard.showSoftInput(input,0);
                              }
                          }
                ,200);

        //output
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                add_button.setEnabled(true);
                if (input.getText().toString().length() >= 1){
                    add_button.setEnabled(true);
                }
                else{
                    add_button.setEnabled(false);
                }
            }
        });

        //if button on keyboard is pressed

    }

    //if xml-buttons get pressed
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.add_button2:
                notation = input.getText().toString();
                if (!notation.matches("")){
                    input.setText("");
                    System.out.println(notation);
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddTaskActivityWidget.this);
                    myDB.addTask(notation);
                    updateWidget();
                    Intent refresh = new Intent(this, MainActivity.class);
                    startActivity(refresh);
                    this.finish();
                    new CountDownTimer(1000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {

                            Toast.makeText(getApplicationContext(), "Завдання збережено!", Toast.LENGTH_SHORT).show();
                        }
                    }.start();

                }else{
                    Toast.makeText(getApplicationContext(), "Поле пусте!", Toast.LENGTH_SHORT).show();
                    input.setText("");
                    return;
                }

                finish();
                overridePendingTransition(0, 0);
                break;

            case R.id.cancel2:
                input.setText("");
                finish();
                overridePendingTransition(0, 0);
                break;
        }
    }
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    void updateWidget(){
        Intent intent = new Intent(this, Task_Widget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Task_Widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }

}

