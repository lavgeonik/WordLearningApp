package com.lavgeo.wordlearningapp;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.LocaleDisplayNames;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestModeImageFirstActivity extends Activity {

    ImageView PictureImageView;
    TextView textViewWordNumberInBase;
    TextView editText1, editText2, editText3,editText4, editText5;

    // objects
    WordBase wordBase;
    FlashCard flashCard;

    // interger
    int numberOfLearningWord = 0;

    // Media Player
    MediaPlayer mPlayer;

    CountDownTimer cdt;

    private ArrayList<String> wrongAnswers;
    private ArrayList<Integer> learnedWordNumbers;
    // String
    String correctWord;
    int wordInBaseNumber;


    // on creation base method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_test_mode_image_first);

        PictureImageView = findViewById(R.id.imageViewTestModeImageFirst);
        textViewWordNumberInBase = findViewById(R.id.textViewNumberTestModeImageFirst);
        editText1 = findViewById(R.id.editText1TestModeImageFirst);
        editText2   = findViewById(R.id.editText2TestModeImageFirst);
        editText3 = findViewById(R.id.editText3TestModeImageFirst);
        editText4 = findViewById(R.id.editText4TestModeImageFirst);
        editText5 = findViewById(R.id.editText5TestModeImageFirst);

        wordBase = WordBase.getInstance(this, "");
        flashCard = new FlashCard();
        textViewWordNumberInBase.setText("" + numberOfLearningWord +1);
        wrongAnswers = new ArrayList<String>();
        learnedWordNumbers = new ArrayList<Integer>();
        addMouseListener();

        createTimer();

        mainCycle();

    }

    public void mainCycle() {

        Random r = new Random();

        do
        {
            wordInBaseNumber = r.nextInt(wordBase.getSizeDataBase());
        }
        while(learnedWordNumbers.contains(wordInBaseNumber));

        learnedWordNumbers.add(wordInBaseNumber);

        System.out.println("word base size is :" + wordBase.getSizeDataBase() + " Number of learning word  is "
                + wordInBaseNumber);

        flashCard = wordBase.getFromBase(wordInBaseNumber);  //numberOfLearningWord

        // String nativeWord, foreignWord, imageFileName, mp3FileName;

        drawImage(flashCard.getImageFileName());
        print("before creating mp3");
        createMP3(flashCard.getMp3FileName());

        // english image name flashCard.getImageFileName().split(".")[0];
        correctWord = flashCard.getForeignWord();

        print("before insert text into labes");
        insertTextINTOLabes();
    }


    private void testAnswer(String strForComparison) {
        String comparedString = strForComparison;
        // if chosed correct word
        print("string For comparison is " + strForComparison);
        print("correct word is " + correctWord);
        if (comparedString.equals(correctWord)) {
            playMP3();
            cdt.start();
        }
        // if uncorrected
        else{
            addToWrongAnswersCollections(correctWord);
        }

    }
    public void createTimer(){
        int TIMER_SECONDS = 2;
        cdt = new CountDownTimer(TIMER_SECONDS * 1000 + 1000, 1000) {
            @Override
            public void onTick(long l) {
                // do nothing
            }

            @Override
            public void onFinish() {
                goToNextWord();
            }
        };
    }


    public void goToNextWord() {
        stopMP3();

        if (numberOfLearningWord < (wordBase.getSizeDataBase() - 1)) {
            numberOfLearningWord += 1;
            textViewWordNumberInBase.setText("" + (numberOfLearningWord + 1));
            mainCycle();
        } else {
            Toast.makeText(this, "done all words", Toast.LENGTH_SHORT).show();
            learnedWordNumbers.clear();
            numberOfLearningWord = 0;
            textViewWordNumberInBase.setText("" + (numberOfLearningWord + 1));
            mainCycle();
        }
    }

    public void insertTextINTOLabes() {
        Random r = new Random();

        int randomIntShowOnScreen = r.nextInt(5) + 1;
        print("inserText correct word is " + correctWord);
        print("random int show on screen is " + randomIntShowOnScreen);

        String strLabel1, strLabel2, strLabel3, strLabel4, strLabel5;

        String[] variablesForLabels = new String[5];

        ArrayList<Integer> prevInteger = new ArrayList<Integer>();

        print("one step before cycle. base size is " + wordBase.getSizeDataBase());

        int correctWordNumberInBase = wordInBaseNumber;;
        prevInteger.add(correctWordNumberInBase);

        for (int i = 0; i < variablesForLabels.length; i++) {
            print("i is " + i);
            int randomInt = r.nextInt(wordBase.getSizeDataBase()); // + 1

            // цикл пока массив не будет содержать повторяющихся значений
            while(prevInteger.contains(randomInt) == true) {
                randomInt = r.nextInt(wordBase.getSizeDataBase());
            }

            print("" + randomInt);

            variablesForLabels[i] = wordBase.getFromBase(randomInt).getForeignWord();
            print("word in variable is " + variablesForLabels[i]);
            prevInteger.add(randomInt);
        }

        strLabel1 = variablesForLabels[0];
        strLabel2 = variablesForLabels[1];
        strLabel3 = variablesForLabels[2];
        strLabel4 = variablesForLabels[3];
        strLabel5 = variablesForLabels[4];

        switch (randomIntShowOnScreen) {
            case 1:
                editText1.setText(correctWord);
                editText2.setText(strLabel2);
                editText3.setText(strLabel3);
                editText4.setText(strLabel4);
                editText5.setText(strLabel5);
                break;
            case 2:
                editText2.setText(correctWord);
                editText1.setText(strLabel1);
                editText3.setText(strLabel3);
                editText4.setText(strLabel4);
                editText5.setText(strLabel5);

            case 3:
                editText3.setText(correctWord);
                editText1.setText(strLabel1);
                editText2.setText(strLabel2);
                editText4.setText(strLabel4);
                editText5.setText(strLabel5);
                break;

            case 4:
                editText4.setText(correctWord);
                editText1.setText(strLabel1);
                editText2.setText(strLabel2);
                editText3.setText(strLabel3);
                editText5.setText(strLabel5);
                break;
            case 5:
                editText5.setText(correctWord);
                editText1.setText(strLabel1);
                editText2.setText(strLabel2);
                editText3.setText(strLabel3);
                editText4.setText(strLabel4);
                break;

        }

    }



    public void addToWrongAnswersCollections(String strWrongAnswer) {

        if(wrongAnswers.contains(strWrongAnswer) == false)
            wrongAnswers.add(strWrongAnswer);

    }


    private void createMP3(String mp3FileName){
        File root = this.getExternalFilesDir(null);
        String mp3File = root.getPath() + "/media/audio/" + mp3FileName;
//        File mp3File = new File(root, mp3FileName);
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mp3File);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
            Toast.makeText(this, "not found mp3 file " + mp3FileName, Toast.LENGTH_LONG).show();
        }

        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("error", e.toString());
            Toast.makeText(this, "not found mp3 file " + mp3FileName, Toast.LENGTH_LONG).show();
        } catch(IllegalStateException e){
            e.printStackTrace();
            Log.d("error", e.toString());
            Toast.makeText(this, "not found mp3 file " + mp3FileName, Toast.LENGTH_LONG).show();
        }

        // create from raw mPlayer = MediaPlayer.create(LearningModeImageFirstActivity.this, mp3FileName);
    }



    private void playMP3(){
        try {
            if(!mPlayer.isPlaying()) {
                mPlayer.start();
            }
        } catch (IllegalStateException e) {
            Log.e("myTag", e.toString());
            e.printStackTrace();
        }
    }

    private void stopMP3(){
        mPlayer = null;
    }


    public void drawImage(String imgFileName){

        File root = this.getExternalFilesDir(null);
        String imgFilePath = root.getPath() + "/media/img/" + imgFileName;
        File imgFile = new File(imgFilePath);
        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath);
            PictureImageView.setImageBitmap(myBitmap);
        }
        else{
            Toast.makeText(this, "not found jpg file " + imgFileName, Toast.LENGTH_LONG).show();
        }
    }

    public void addMouseListener(){
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAnswer(editText1.getText().toString());
            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAnswer(editText2.getText().toString());
            }
        });
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAnswer(editText3.getText().toString());
            }
        });
        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAnswer(editText4.getText().toString());
            }
        });
        editText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAnswer(editText5.getText().toString());
            }
        });

    }

    public void print(String str){
        Log.d("myTag", str);
    }


}
