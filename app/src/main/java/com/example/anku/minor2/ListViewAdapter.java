package com.example.anku.minor2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Trojan Detected on 14-03-2017.
 */

public class ListViewAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] content;
    private final Integer[] imgid;

    public ListViewAdapter(Activity context, String[] content, Integer[] imgid)
    {
        super(context,R.layout.custom_list,content);
        this.context = context;
        this.content = content;
        this.imgid = imgid;


    }
    // updates the Listview's images and text
    public View getView(int position, View view, ViewGroup parent )
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list, null, true);

        TextView tvContent = (TextView) rowView.findViewById(R.id.textView);
        ImageView ivImage = (ImageView) rowView.findViewById(R.id.imageView3);

        tvContent.setText(content[position]);
        ivImage.setImageResource(imgid[position]);
        return rowView;
    }
}
