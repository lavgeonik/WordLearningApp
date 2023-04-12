package com.lavgeo.wordlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ListLearningModesActivity extends AppCompatActivity {
    String fileBaseName;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_learning_modes);
        Intent intent = getIntent();
        fileBaseName = intent.getStringExtra("choosenFileName");
        textView = findViewById(R.id.textView);
        String textInTextView = textView.getText().toString();
        textView.setText(textInTextView + " " + fileBaseName);

        Log.d("myTag", "file choosen is " + fileBaseName );

        WordBase wb = WordBase.getInstance(this, fileBaseName);

        Log.d("myTag", wb.getFromBase(0).getNativeWord());
    }


    public void  onClickImageFirstLearningMode(View view) {
        Intent intent = new Intent(ListLearningModesActivity.this, LearningModeImageFirstActivity.class);
        startActivity(intent);
    }

    public void  onClickTextFirstLearningMode(View view) {
        Intent intent = new Intent(ListLearningModesActivity.this, LearningModeTextFirstActivity.class);
        startActivity(intent);
    }

    public void  onClickImageFirstTestMode(View view) {
        Intent intent = new Intent(ListLearningModesActivity.this, TestModeImageFirstActivity.class);
        startActivity(intent);
    }

    public void  onClickTextFirstTestMode(View view) {
        Intent intent = new Intent(ListLearningModesActivity.this, TestModeTextFirstActivity.class);
        startActivity(intent);
    }

    public void  onClickAudioTestMode(View view) {
        Intent intent = new Intent(ListLearningModesActivity.this, TestModeAudioActivity.class);
        startActivity(intent);
    }

    public void  onClickTranlationTestMode(View view) {
        Intent intent = new Intent(ListLearningModesActivity.this, TestModeTranslationActivity.class);
        startActivity(intent);
    }


}
