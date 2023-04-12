package com.lavgeo.wordlearningapp;

import android.os.Environment;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.ArrayList;

public class Test {


    public void writeStatistics(String sBody){
        // InputStream inputStream = mAppContext.getResources().openRawResource(R.raw.statistics);

        String sFileName = "stat.txt";
        String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        String strToday = dateFormat.format(today) + "\t";


        try{
            File root = new File(Environment.getExternalStorageDirectory(), "Experiment"); // getFilesDir
            File data = Environment.getDataDirectory();
            if(!root.exists()){
                root.mkdir();
            }
            File fileTXT = new File(root, sFileName);
            FileWriter writer = new FileWriter(fileTXT, true);
            writer.append(strToday + sBody);
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Calling:
/*
    Context context = getApplicationContext();
    String filename = "log.txt";
    String str = read_file(context, filename);
*/
    public String read_file(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    public void write(Context context){
        File data = Environment.getDataDirectory();
        Log.d("myTag", data.getPath());
        Log.d("myTag", Environment.getRootDirectory().toString());
        File root = context.getFilesDir();


        try{
            File baseFile = new File(root,"oz1.txt");
            Log.d("myTag", baseFile.getPath());
            if(!baseFile.exists()){
                baseFile.createNewFile();
            }
            FileWriter writer = new FileWriter(baseFile, true);
            writer.append("");
            writer.flush();
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    public void createDirs(Context context){
        try{
            File root = context.getExternalFilesDir(null);
            File baseFolder = new File(root,"media");
            File baseFileMP3 = new File(root,"media/audio");
            File baseFolderJPG = new File(root,"media/img");

            if(!baseFolder.exists()){
                baseFolder.mkdirs();
            }
            if(!baseFileMP3.exists()){
                baseFileMP3.mkdirs();
                Log.d("myTag", baseFileMP3.getPath());
            }
            if(!baseFolderJPG.exists()){
                baseFolderJPG.mkdirs();
                Log.d("myTag", baseFileMP3.getPath());
            }

        }
        catch (Exception e){
            Log.d("myTag", e.toString());
            e.printStackTrace();
        }


    }

    public void printPath(Context context){
        String dir = context.getFilesDir().toString();
        String extDir = context.getExternalFilesDir(null).toString();
        Log.d("myTag", dir);
        Log.d("myTag", extDir);
    }

    public String readFile(Context context, String fileName) {
        try {
            File root = context.getExternalFilesDir(null);
            File baseFile = new File(root, fileName);
            FileInputStream fis = new FileInputStream(baseFile); //context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return e.toString();
        } catch (UnsupportedEncodingException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        }

    }

    public ArrayList<String> getNamesOfFilesTXT(File folder) {

        File[] listOfFiles = folder.listFiles();
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                if (fileName.endsWith("txt"))
                    list.add(fileName);
            }
        }

        System.out.println("size of arr is " + list.size());

        return list;
    }

}
