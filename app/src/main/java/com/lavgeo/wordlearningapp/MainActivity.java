package com.lavgeo.wordlearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    String myTag = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Test test = new Test();
        test.printPath(this);
        test.createDirs(this);
        Log.d("myTag", test.readFile(this,"base1.txt"));

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        addRadioButtons();
    }

    // функция для создания Radio Button исходя из количества файло с расширением txt в папке программы
    public void addRadioButtons(){
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        File root = this.getExternalFilesDir(null);
        ArrayList<String> arrayList = new Test().getNamesOfFilesTXT(root);
        Log.d(myTag, "files in folder " + arrayList.size());
        for (int i = 0; i < arrayList.size(); i++){
            //create radio button
            RadioButton rb = new RadioButton(this);
            String text = arrayList.get(i);
            // delete extention
            //text = text.replace(".txt","");
            rb.setText(text);
            //assign an automatically generated id to teh radio button
            rb.setId(View.generateViewId());
            rb.setGravity(Gravity.CENTER);
            radioGroup.addView(rb);
        }


    }

    // при нажатии на кнопку выбрать файл запускаем следующую активити ListLearningModesActivity
    public void  onClickChooseFile(View view) {
        Intent intent = new Intent(MainActivity.this, ListLearningModesActivity.class);
        RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
        // проверяем выбран ли файл
        if(rb != null){
            String fileBaseName = rb.getText().toString();
            intent.putExtra("choosenFileName", fileBaseName);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "You need to choose file", Toast.LENGTH_SHORT).show();
        }
    }



}
