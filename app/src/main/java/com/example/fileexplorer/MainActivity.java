package com.example.fileexplorer;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class MainActivity extends ListActivity {

    private String filePath = "/storage/emulated/0/";
    public static String filename;
     boolean clicked = false;
    public static ArrayList<String> al= new ArrayList<String>();

    String  openFilepath = "/storage/emulated/0/" + String.join("/", al );
    String intoDirpath = "/storage/emulated/0/";
    String Listernerpath =  "/storage/emulated/0/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    ///storage/emulated/0/
        String basedir = Environment.getExternalStorageDirectory().toString();

        if (getIntent().hasExtra("filePath")) {
            basedir = getIntent().getStringExtra("filePath");


            if (basedir.startsWith("/storage/emulated/0/") == false) {
                basedir = "/storage/emulated/0/" + basedir;
            }

        }


       File path = new File(basedir);

        ArrayList files = new ArrayList();
        File dir = new File (path.toString());
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                    files.add(file);
            }
        }

        // Put the data into the list
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, files);

           setListAdapter(adapter);

        }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        filename = (String) getListAdapter().getItem(position);

        String path =  Listernerpath + String.join("/", al );

        File dirCheck = new File( path + "/" + filename + "/");
        System.out.println("Directory being checked is " + dirCheck.toString());

        if (dirCheck.isDirectory()){
            al.add(filename);
            intoDirectory();
        }
        else {
            try {

                openFile(filename, openFilepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void backButtonpressed( View v){
        backcheck();

    }


    public void backcheck(){

        Button button=  findViewById(R.id.backButton);


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                System.out.println("Back button was clicked and detected");
                clicked = true;

                if (clicked){
                    openFilepath = String.join("/", al );
                    intoDirpath = "";
                    Listernerpath = "";
                    System.out.println("openfilepath is " + openFilepath);
                    goBack();
                    clicked = false;
                }

                else {
                    goBack();
                }


            }
        });

    }



    public void intoDirectory() {

        System.out.println("intoDirectory path is" + intoDirpath + String.join("/", al ));

        System.out.println(Arrays.toString(al.toArray()));

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("filePath", intoDirpath + String.join("/", al ));
            startActivity(intent);

    }


    public void goBack(){

        System.out.println("Back button clicked");

        System.out.println("Array before back execution " + Arrays.toString(al.toArray()));

        String path =  String.join("/", al );

        try {
            int index = path.lastIndexOf(al.get(al.size()-1));
            path = (path.substring(0, index));
            int index2 = path.lastIndexOf("/");
            path = (path.substring(0, index));

        } catch (Exception e){

            path = "/storage/emulated/0/";
            al.add(path);
        }

        al.clear();
        al.add(path);
        System.out.println("Back path is now" + path);

        System.out.println("Array after back press " + Arrays.toString(al.toArray()));

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filePath", path);
        startActivity(intent);

    }


    public void openFile(String Filename, String path ) throws IOException {

        File filepath = new File(path + "/" + Filename);
        System.out.println("openFile path is " + filepath.toString());

        if (filepath.isDirectory()){
            intoDirectory();
        }
        else {
            FileInputStream in = new FileInputStream(filepath);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');

            }

            Intent intent = new Intent(this, fileViewer.class);
            intent.putExtra("filetxt", sb.toString());
            startActivity(intent);
            inputStreamReader.close();

        }

    }

    }

