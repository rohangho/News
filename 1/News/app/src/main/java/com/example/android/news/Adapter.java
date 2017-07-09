package com.example.android.news;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.resource;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by ROHAN on 08-07-2017.
 */

public class Adapter extends ArrayAdapter<custom> {

    public Adapter(@NonNull Context context, ArrayList<custom> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        custom current=getItem(position);
        TextView title=(TextView) listItemView.findViewById(R.id.heading);
        title.setText(current.getTitle());
        ImageView img=(ImageView)listItemView.findViewById(R.id.icon);
        Picasso.with(getContext()).load(current.getResource()).into(img);
        return listItemView;

    }


}

