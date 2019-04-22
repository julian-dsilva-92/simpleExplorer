package com.example.fileexplorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import static com.example.fileexplorer.MainActivity.basedir;

public class myVieweradapter extends ArrayAdapter<String> {

private final Context context;
private final String[] values;

public myVieweradapter(Context context, String[] values) {
        super(context, R.layout.customview, values);
        this.context = context;
        this.values = values;
        }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.customview, parent, false);
        TextView textView =  rowView.findViewById(R.id.label);
        ImageView imageView1 = rowView.findViewById(R.id.logo);
        textView.setText(values[position]);

        String s = values[position];


        File file_S =  new File(basedir + "/" + s);
        System.out.println("basedir is " + file_S.toString());
        if (!file_S.isDirectory()){
            imageView1.setImageResource(R.drawable.file);
            System.out.println(file_S.toString() + "is a directory");

        }
        else {
            imageView1.setImageResource(R.drawable.directory);
        }

        return rowView;
    }




}
