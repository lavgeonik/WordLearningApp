package com.lavgeo.wordlearningapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestModeAudioActivity extends AppCompatActivity {


    ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    TextView textViewWordNumberInBase;

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
    int correctInt;
    int wordInBaseNumber;


    //

    // on creation base method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_test_mode_audio);

        imageView1 = findViewById(R.id.imageView1TestModeAudio);
        imageView2 = findViewById(R.id.imageView2TestModeAudio);
        imageView3 = findViewById(R.id.imageView3TestModeAudio);
        imageView4 = findViewById(R.id.imageView4TestModeAudio);
        imageView5 = findViewById(R.id.imageView5TestModeAudio);

        textViewWordNumberInBase = findViewById(R.id.textViewNumberTextModeAudio);

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

        print("before creating mp3");
        createMP3(flashCard.getMp3FileName());
        playMP3();

        insertImagesINTOViews();
    }

    public void btnPlay(View view){
        playMP3();
    }

    private void insertImagesINTOViews(){
        Random r = new Random();

        int randomIntShowOnScreen = r.nextInt(5) + 1;
        correctInt = randomIntShowOnScreen;
        print("inserText correct word is " + correctInt);
        print("random int show on screen is " + randomIntShowOnScreen);

        String imageFileName1, imageFileName2, imageFileName3, imageFileName4, imageFileName5;

        String[] variablesForLmages = new String[5];
        ArrayList<Integer> prevInteger = new ArrayList<Integer>();
        print("one step before cycle. base size is " + wordBase.getSizeDataBase());
        int correctWordNumberInBase = wordInBaseNumber;
        prevInteger.add(correctWordNumberInBase);

        for (int i = 0; i < variablesForLmages.length; i++) {
            print("i is " + i);
            int randomInt = r.nextInt(wordBase.getSizeDataBase()); // + 1

            // цикл пока массив не будет содержать повторяющихся значений
            while(prevInteger.contains(randomInt) == true) {
                randomInt = r.nextInt(wordBase.getSizeDataBase());
            }

            print("" + randomInt);

            variablesForLmages[i] = wordBase.getFromBase(randomInt).getImageFileName();
            print("word in variable is " + variablesForLmages[i]);
            prevInteger.add(randomInt);
        }

        variablesForLmages[randomIntShowOnScreen-1] = flashCard.getImageFileName();

        imageFileName1 = variablesForLmages[0];
        imageFileName2 = variablesForLmages[1];
        imageFileName3 = variablesForLmages[2];
        imageFileName4 = variablesForLmages[3];
        imageFileName5 = variablesForLmages[4];

        print("correct images is " + variablesForLmages[randomIntShowOnScreen-1] );
        drawImage(imageView1, imageFileName1);
        drawImage(imageView2, imageFileName2);
        drawImage(imageView3, imageFileName3);
        drawImage(imageView4, imageFileName4);
        drawImage(imageView5, imageFileName5);

    }

    private void testAnswer(int i) {
        int intForCompare = i;
        // if chosed correct word
        print("string For comparison is " + intForCompare);
        print("correct word is " + correctInt);
        if (correctInt == intForCompare) {
            playMP3();
            cdt.start();
        }
        // if uncorrected
        else{
            addToWrongAnswersCollections(flashCard.getForeignWord());
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
        } else {
            Toast.makeText(this, "done all words", Toast.LENGTH_SHORT).show();
            learnedWordNumbers.clear();
            numberOfLearningWord = 0;
            textViewWordNumberInBase.setText("" + (numberOfLearningWord + 1));
        }
        mainCycle();
    }

    public void print(String str){
        Log.d("myTag", str);
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

    public void drawImage(ImageView pictureImageView, String imgFileName){

        File root = this.getExternalFilesDir(null);
        String imgFilePath = root.getPath() + "/media/img/" + imgFileName;
        File imgFile = new File(imgFilePath);
        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFilePath);
            pictureImageView.setImageBitmap(myBitmap);
        }
    }

    public void addMouseListener() {
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 1;
                testAnswer(i);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 2;
                testAnswer(i);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 3;
                testAnswer(i);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 4;
                testAnswer(i);
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 5;
                testAnswer(i);
            }
        });

    }

}
