package com.example.fileexplorer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.fileexplorer.MainActivity.al;

public class fileViewer extends AppCompatActivity {
    String file_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_viewer);

        if (getIntent().hasExtra("filetxt")) {
             file_text =  getIntent().getStringExtra("filetxt");
        }

        TextView textView = findViewById(R.id.editText);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(file_text);

    }

    public void save (View v) throws IOException {


        String path = Environment.getExternalStorageDirectory().toString() + "/" + String.join("/", al );
        File filepath = new File(path + "/" + MainActivity.filename);
        TextView textView = findViewById(R.id.editText);
        String text =textView.getText().toString();

        FileOutputStream fos = new FileOutputStream (filepath);
        fos.write(text.getBytes());
        fos.close();


    }

}
