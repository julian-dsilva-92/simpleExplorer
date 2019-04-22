package com.example.fileexplorer;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    public static String filename;
     boolean clicked = false;
    public static ArrayList<String> al= new ArrayList<String>();

    String  openFilepath = Environment.getExternalStorageDirectory().toString() + "/" + String.join("/", al );
    String intoDirpath = Environment.getExternalStorageDirectory().toString() + "/";
    String Listernerpath = intoDirpath;
    public static String basedir = Environment.getExternalStorageDirectory().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        basedir = Environment.getExternalStorageDirectory().toString();

        if (getIntent().hasExtra("filePath")) {
            basedir = getIntent().getStringExtra("filePath");

            if (basedir.startsWith(Environment.getExternalStorageDirectory().toString() + "/") == false) {
                basedir= Environment.getExternalStorageDirectory().toString() + "/" + basedir;
            }

        }

       File path = new File(basedir);

        List<String> files = new ArrayList<String>();
        File dir = new File (path.toString());
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                    files.add(file);
            }
        }



        String[] stringArray = files.toArray(new String[files.size()]);

        //using my own custom adapter to show icons
        setListAdapter(new myVieweradapter(this, stringArray));

        }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        // checks if file is a directory.

        filename = (String) getListAdapter().getItem(position);

        String path =  Listernerpath + String.join("/", al );

        File dirCheck = new File( path + "/" + filename + "/");

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


        // if back button was clicked, do not append the base directory
        Button button=  findViewById(R.id.backButton);


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clicked = true;
                if (clicked){
                    openFilepath = String.join("/", al );
                    intoDirpath = "";
                    Listernerpath = "";
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

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("filePath", intoDirpath + String.join("/", al ));
            startActivity(intent);

    }

    public void goBack(){

        String path =  String.join("/", al );

        try {
            int index = path.lastIndexOf("/");
            path = (path.substring(0, index));

        } catch (Exception e){


        }

        al.clear();
        al.add(0,"/");
        al.add(path);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filePath", path);
        startActivity(intent);

    }


    public void openFile(String Filename, String path ) throws IOException {

        File filepath = new File(path + "/" + Filename);

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

