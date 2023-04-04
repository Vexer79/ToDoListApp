package com.example.myapplication;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.rgb;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemeUtils;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity implements OnClickListener{
    Button add_button;


    public static int COUNT_TASK = 0;
    MyDatabaseHelper myDB;
    ArrayList<String> task_id, task_value;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Список завдань");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        add_button = (Button)findViewById(R.id.button);
        add_button.setOnClickListener(this);


        updateData("ALL");
        updateWidget();


    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                Intent intent = new Intent(getApplicationContext(),Add_Task_Activity_test.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                Add_Task_Activity_test_ResultLauncher.launch(intent);
                break;


        }
    }
    @Override
    protected void onStop() {
        updateData("ALL");
        updateWidget();
        super.onStop();

    }
    @Override
    protected void onPause(){
        updateData("ALL");
        updateWidget();
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        updateData("ALL");
        updateWidget();
        super.onDestroy();
    }

    @SuppressLint("ResourceAsColor")
    public void create_task(String returnString, int current_task_id){
        LinearLayout linearLayout =  (LinearLayout) findViewById(R.id.linearlayout);
        CheckBox checkbox = new CheckBox(getApplicationContext());
        checkbox.setText(returnString);


        float radius = 50f;
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build();

        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        ViewCompat.setBackground(checkbox,shapeDrawable);
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                shapeDrawable.setFillColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.darker_gray));
                checkbox.setTextColor(WHITE);

                break;
            case Configuration.UI_MODE_NIGHT_NO:
                shapeDrawable.setFillColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.light_gray));
                checkbox.setTextColor(BLACK);
                break;
        }

        if (returnString.length()>0 && returnString.length()<70){
            checkbox.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
        else{
            checkbox.setGravity(Gravity.LEFT | Gravity.START);
        }
        checkbox.setPadding(10,7,35,20);
        checkbox.setTextSize(16);
        TextView margin = new TextView(getApplicationContext());


        final float scale = getResources().getDisplayMetrics().density;
        margin.setHeight((int) (10 * scale + 0.5f));

        if ((returnString.length()<=34)&&!(returnString.contains("\n"))){
            checkbox.setHeight((int) (45 * scale + 0.5f));

            checkbox.setPadding(10,7,35,15);
        }

        COUNT_TASK += 1;

        checkbox.setOnClickListener (new OnClickListener () {
            @Override
            public void onClick (View v)
            {
                if(checkbox.isChecked()) {
                    checkbox.setPaintFlags(checkbox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    /*
                    checkbox.setVisibility(View.GONE);//use for disable textview
                    margin.setVisibility(View.GONE);
                    COUNT_CHECKBOX-=1;
                    */
                    new CountDownTimer(1000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            checkbox.setVisibility(View.GONE);//use for disable textview
                            margin.setVisibility(View.GONE);
                            COUNT_TASK -= 1;
                            //delete task
                            myDB.deleteTask(String.valueOf(current_task_id));
                            storeDataInArrays();
                            updateWidget();



                        }
                    }.start();
                }
                else {
                    checkbox.setPaintFlags(checkbox.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }

            }

        });

        linearLayout.addView(checkbox);
        linearLayout.addView(margin);
    }
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            TextView empty_text = findViewById(R.id.empty_text);
            empty_text.setText("Завдання відсутні!");
            //Заміна textview
        }else{
            TextView empty_text = findViewById(R.id.empty_text);
            empty_text.setText("");
            while(cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_value.add(cursor.getString(1));

            }
        }
    }
    void updateData(String key){
        myDB = new MyDatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_value = new ArrayList<>();

        if (key == "ALL"){
            storeDataInArrays();
            LinearLayout linearLayout =  (LinearLayout) findViewById(R.id.linearlayout);
            linearLayout.removeAllViews();
            if (!task_id.isEmpty()){
                for(int i = 0; i<task_id.size();i++){
                    TextView empty_text = findViewById(R.id.empty_text);
                    empty_text.setText("");
                    create_task(task_value.get(i), Integer.parseInt(task_id.get(i)));
                }
            }
            else{
                TextView empty_text = findViewById(R.id.empty_text);
                empty_text.setText("Завдання відсутні!");
            }
        } else if (key == "LAST") {
            storeDataInArrays();
            if (!task_id.isEmpty()){
                TextView empty_text = findViewById(R.id.empty_text);
                empty_text.setText("");
                create_task(task_value.get(task_value.size()-1), Integer.parseInt(task_id.get(task_id.size()-1)));
            }
            else{
                TextView empty_text = findViewById(R.id.empty_text);
                empty_text.setText("Завдання відсутні!");
            }
        }
        else{
            System.out.println("KEY ERROR");
        }

    }
    void updateWidget(){
        Intent intent = new Intent(this, Task_Widget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), Task_Widget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }


    ActivityResultLauncher<Intent> Add_Task_Activity_test_ResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Add_Task_Activity_test.RESULT_OK) {
                        // There are no request codes

                        Intent data = result.getData();
                        String returnString = data.getStringExtra("message_key");

                        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                        myDB.addTask(returnString);
                        updateData("ALL");
                        updateWidget();



                    }
                }
            });



}
