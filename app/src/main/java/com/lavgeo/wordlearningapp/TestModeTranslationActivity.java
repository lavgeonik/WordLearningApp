package com.lavgeo.wordlearningapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TestModeTranslationActivity extends AppCompatActivity {


    ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    TextView textViewWordNumberInBase, textViewForegnWord;
    EditText editTextTranslation;

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
        setContentView(R.layout.activity_test_mode_tranlation);

        imageView1 = findViewById(R.id.imageView1TestModeTranslation);
        imageView2 = findViewById(R.id.imageView2TestModeTranslation);
        imageView3 = findViewById(R.id.imageView3TestModeTranslation);
        imageView4 = findViewById(R.id.imageView4TestModeTranslation);
        imageView5 = findViewById(R.id.imageView5TestModeTranslation);

        textViewWordNumberInBase = findViewById(R.id.textViewNumberTextModeTranslation);
        textViewForegnWord = findViewById(R.id.textViewForeanWordTestModeTranslation);
        editTextTranslation = findViewById(R.id.editTextTestModeTranslation);

        wordBase = WordBase.getInstance(this, "");
        flashCard = new FlashCard();
        textViewWordNumberInBase.setText("" + numberOfLearningWord +1);
        wrongAnswers = new ArrayList<String>();
        learnedWordNumbers = new ArrayList<Integer>();

        createTimer();

        mainCycle();

        addKeyListeners();

    }

    public void mainCycle() {

        Random r = new Random();

        do
        {
            wordInBaseNumber = r.nextInt(wordBase.getSizeDataBase());
        }
        while(learnedWordNumbers.contains(wordInBaseNumber));

        learnedWordNumbers.add(wordInBaseNumber);
        flashCard = wordBase.getFromBase(wordInBaseNumber);  //numberOfLearningWord

        // String nativeWord, foreignWord, imageFileName, mp3FileName;

        createMP3(flashCard.getMp3FileName());
        textViewForegnWord.setText(flashCard.getForeignWord());
        playMP3();

        editTextTranslation.setText("");
        insertImagesINTOViews();
        setImagesINVisible();
    }

    private void testAnswer() {
        print("Enter clicked");
        String strInTextArea = editTextTranslation.getText().toString().toLowerCase();
        print("text in text area is " + strInTextArea);
        String correctWord = flashCard.getImageFileName().replace(".jpg","").toLowerCase(); // + "\n";
        print("correct word is " + correctWord);
        print(strInTextArea + "\t" + correctWord);
        if (strInTextArea.equals(correctWord)) {
            playMP3();
            cdt.start();
        }
        else{
            addToWrongAnswersCollections(flashCard.getForeignWord());
        }
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



    public void setImagesVisible(View view){
        imageView1.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.VISIBLE);
        imageView3.setVisibility(View.VISIBLE);
        imageView4.setVisibility(View.VISIBLE);
        imageView5.setVisibility(View.VISIBLE);
    }
    public void setImagesINVisible(){
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
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
        else{
            Toast.makeText(this, "not found jpg file " + imgFileName, Toast.LENGTH_LONG).show();
        }
    }

    private void addKeyListeners() {

        editTextTranslation.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //       Toast.makeText(ImageLearningMode_Activity.this, editText.getText(), Toast.LENGTH_SHORT).show();
                    testAnswer();
                    print("code after testAnswer");
                    //InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //in.hideSoftInputFromWindow(editTextTranslation.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }

}
