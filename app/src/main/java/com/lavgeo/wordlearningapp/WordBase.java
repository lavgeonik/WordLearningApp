package com.lavgeo.wordlearningapp;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;



public class WordBase {
    private static WordBase wordBase;
    private static ArrayList<FlashCard> dataBase = new ArrayList();
    private static ArrayList<String> imagesFilesName = new ArrayList();
    private static ArrayList<String> foreinWords = new ArrayList();

    public synchronized static WordBase getInstance(Context context, String file){
        if(wordBase == null){
            wordBase = new WordBase();
            loadBase(context, file);
        }
        return wordBase;
    }

    private static void loadBase(Context context,String file) {
        String nativeWord, foreignWord, imageFileName, mp3FileName;

        File root = context.getExternalFilesDir(null);
        File baseFile = new File(root, file);

        // FILE PATH
        String filePath = "E:\\languages\\experiment\\prepare\\words\\Olly_2.1.Hard_words.txt"; //  Olly_2.1.txt
        // String content = new ReadAndWriteUTF_8().read_TXT_UTF_8(filePath);
        // String[] oneLines = content.split("\n");
        ArrayList<String> oneLines = read_TXT_UTF_8_ReturnArrayList(baseFile);

        // FlashCard flashCard;
        for (int i = 0; i < (oneLines.size()); i++) {
            // content += Kanji+ "|" + Translation + "|" + Hiragana + "|" + repetitions +
            // "|" + previousDateRepeate + "|" + nextDateRepeate + score + "\n";
            String[] splitTOWords = oneLines.get(i).split("\t");

            // вид файла know.jpg	conoce.mp3	known	conoce
            nativeWord = splitTOWords[2];
            foreignWord = splitTOWords[3];
            imageFileName = splitTOWords[0];
            mp3FileName = splitTOWords[1];

            FlashCard flashCard = new FlashCard();
            flashCard.setNativeWord(nativeWord);
            flashCard.setForeignWord(foreignWord);
            flashCard.setImageFileName(imageFileName);
            flashCard.setMp3FileName(mp3FileName);
            dataBase.add(flashCard);
            imagesFilesName.add(imageFileName);
            foreinWords.add(foreignWord);

            //print all base
//			System.out.println(oneLines[i]);

            // for testing base length of line
            //System.out.println(splitTOWords.length);
        }
    }

    public FlashCard getFromBase(int index) {
        return dataBase.get(index);
    }

    public int getSizeDataBase() {
        return dataBase.size();

    }

    public ArrayList<String> getImagesFilesName() {
        return imagesFilesName;
    }

    public ArrayList<String> getForeignWordsBase() {
        return foreinWords;
    }


    public static ArrayList<String> read_TXT_UTF_8_ReturnArrayList(File file){
        ArrayList<String> arrayList = new ArrayList<String>();
        String str = "";
        String strOut = "";
        try {

            File fileDir = file;

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            while ((str = in.readLine()) != null) {
                //  System.out.println(str);
                arrayList.add(str);
                strOut = strOut + str + "\n";
            }

            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
            str = e.toString();

            Log.d("error ", e.toString());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            Log.d("error ", e.toString());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.d("error ", e.toString());
        }

        return arrayList;
    }

}
