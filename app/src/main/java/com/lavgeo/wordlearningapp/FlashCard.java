package com.lavgeo.wordlearningapp;

import java.time.LocalDateTime;

public class FlashCard {
    private String nativeWord, foreignWord, imageFileName, mp3FileName;
    private LocalDateTime previousDateRepeate, nextDateRepeate;
    private int repetitions = 0, score = 0;


    public String getNativeWord() {
        return nativeWord;
    }

    public void setNativeWord(String nativeWord) {
        this.nativeWord = nativeWord;
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getMp3FileName() {
        return mp3FileName;
    }

    public void setMp3FileName(String mp3FileName) {
        this.mp3FileName = mp3FileName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }


    public LocalDateTime getPreviousDateRepeate() {
        return previousDateRepeate;
    }


    public void setPreviousDateRepeate(String previousDateRepeate) {
        try {
            this.previousDateRepeate = LocalDateTime.parse(previousDateRepeate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPreviousDateRepeate(LocalDateTime previousDateRepeate) {
        this.previousDateRepeate = previousDateRepeate;
    }



    public LocalDateTime getNextDateRepeate() {
        return nextDateRepeate;
    }

    public void setNextDateRepeate(String nextDateRepeate) {
        try {
            this.nextDateRepeate = LocalDateTime.parse(nextDateRepeate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNextDateRepeate(LocalDateTime nextDateRepeate) {
        this.nextDateRepeate = nextDateRepeate;
    }




}
