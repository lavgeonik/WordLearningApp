package com.lavgeo.wordlearningapp;
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

public class ReadAndWrite {

    public String read_TXT_UTF_8(String filePath){
        String str = "";
        String strOut = "";
        try {

            File fileDir = new File(filePath);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            while ((str = in.readLine()) != null) {
                //  System.out.println(str);
                strOut = strOut + str + "\n";
            }

            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }

        return strOut;
    }


    public String read_TXT_UTF_8(File file){
        String str = "";
        String strOut = "";
        try {

            File fileDir = file;

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            while ((str = in.readLine()) != null) {
                //  System.out.println(str);
                strOut = strOut + str + "\n";
            }

            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }

        return strOut;
    }

    public ArrayList<String> read_TXT_UTF_8_ReturnArrayList(File file){
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
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            str = e.toString();
        }

        return arrayList;
    }


    public void write_TXT_UTF_8(String filePath, String strOut){
        try {
            File fileDir = new File(filePath);

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));

            out.append(strOut);

            out.flush();
            out.close();

        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void write_TXT_UTF_8(File file, String strOut){
        try {
            File fileDir = file;

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));

            out.append(strOut);

            out.flush();
            out.close();

        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
