package com.lavgeo.wordlearningapp;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class LearningModeImageFirstActivity extends Activity {
    EditText wordNumberInBaseTextView;
    EditText editText;
    TextView TranslationTextView;
    ImageView PictureImageView;
    Button btnClickPause;

    // objects
    WordBase wordBase;
    FlashCard flashCard;

    // interger
    int numberOfLearningWord = 0;

    // Media Player
    MediaPlayer mPlayer;

    // Timers
    CountDownTimer timeStep_1;

    private int TIMER_SECONDS = 15;
    boolean isStopped = false;

    // on creation base method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_learning_mode);

        PictureImageView = findViewById(R.id.imageViewLearningMode);
        TranslationTextView = findViewById(R.id.textLearningModeViewTranslation);
        wordNumberInBaseTextView = findViewById(R.id.editTextNumberAutoImageLearn);
        editText = findViewById(R.id.editTextAreaLearningModeForeanWord);
        btnClickPause = findViewById((R.id.btnLearningModePause));

        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/images");
        Log.d("myTag", root.toString() + "\t dir to srt " + dir.toString());

        wordBase = WordBase.getInstance(this, "");
        flashCard = new FlashCard();
        wordNumberInBaseTextView.setText("" + numberOfLearningWord +1);

        // turn off edit text with foreign word
        editText.setVisibility(View.INVISIBLE);
        addKeyListener();
        createTimer();
        mainCycle();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        stopMP3();
        timeStep_1.cancel();
        this.finish();

    }

    public void  onClickPause(View view) {
        if(!isStopped) {
            timeStep_1.cancel();
            isStopped = true;
            btnClickPause.setText("resume");

        } else {
            mainCycle();
            isStopped = false;
            btnClickPause.setText("pause");
        }
    }

    public void  onClickNextWord(View view) {
        goToNextWord();
    }

    public void loadFlashCard() {
        System.out.println("word base size is :" + wordBase.getSizeDataBase() + " Number of learning word  is "
                + numberOfLearningWord);
        flashCard = wordBase.getFromBase(numberOfLearningWord);
        // String nativeWord, foreignWord, imageFileName, mp3FileName;
        editText.setText(flashCard.getForeignWord());
        TranslationTextView.setText(flashCard.getNativeWord());
        drawImage(flashCard.getImageFileName());
        createMP3(flashCard.getMp3FileName());
    }

    public void createTimer(){

        // 1 period after 5 sec turn off image + play
        // 2 period after 5+2 seconds turn on Label

        timeStep_1 = new CountDownTimer(TIMER_SECONDS * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int secondsToFinish = (int) (millisUntilFinished / 1000);
                int hours = secondsToFinish / (60 * 60);
                int tempMint = (secondsToFinish - (hours * 60 * 60));
                int minutes = tempMint / 60;

                secondsToFinish = tempMint - (minutes * 60);

                if(secondsToFinish ==  TIMER_SECONDS){
                    setViewsViseable();
                }
                // play sound
                if(secondsToFinish == (TIMER_SECONDS - 1)){
                    playMP3();
                }

                // turn off image and play sound
                if(secondsToFinish == (TIMER_SECONDS / 2)){
                    PictureImageView.setVisibility(View.INVISIBLE);
                    playMP3();
                }

                if (secondsToFinish == 2) {
                    setAllInviseble();
                }

                Log.d("myTag", ("TIME : " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", secondsToFinish)));
            }

            public void onFinish() {
                Log.d("myTag", "timer finished");
                goToNextWord();
            }
        };

    }

    private void setAllInviseble(){
        PictureImageView.setVisibility(View.INVISIBLE);
        TranslationTextView.setVisibility(View.INVISIBLE);
    }
    private void setViewsViseable(){
        PictureImageView.setVisibility(View.VISIBLE);
        TranslationTextView.setVisibility(View.VISIBLE);
    }


    private void mainCycle() {

        loadFlashCard();
        editText.setText(flashCard.getForeignWord());
        //       editText.setInputType(InputType.TYPE_NULL);
        editText.requestFocus();
        editText.setCursorVisible(false);
        editText.setFocusable(false);
        wordNumberInBaseTextView.setCursorVisible(false);

        playMP3();

        try {
            timeStep_1.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        timeStep_1.start();

    }


    public void goToNextWord() {
        stopMP3();

        if (numberOfLearningWord < (wordBase.getSizeDataBase() - 1)) {
            numberOfLearningWord += 1;
            wordNumberInBaseTextView.setText("" + (numberOfLearningWord + 1));
            mainCycle();
        } else {
            numberOfLearningWord = 0;
            wordNumberInBaseTextView.setText("" + (numberOfLearningWord + 1));
            setAllInviseble();
        }
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

    private void addKeyListener(){
        wordNumberInBaseTextView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    int input = Integer.valueOf(wordNumberInBaseTextView.getText().toString());
                    if (input > 0 && input < (wordBase.getSizeDataBase()))
                        numberOfLearningWord = input-1;
                    mainCycle();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(editText.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }


}
