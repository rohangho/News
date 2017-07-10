package com.example.android.news;

import android.media.Image;

import static android.R.attr.resource;
import static com.example.android.news.R.drawable.a;

/**
 * Created by ROHAN on 08-07-2017.
 */

public class custom {
    //First one for image

    private String title;
    private String url;
    public String name;

    public custom(String b,String c,String a){

        this.title=b;
        this.url=c;
        this.name=a;
    }


    public String getTitle(){
        return title;
    }
    public String getUrl(){
        return url;
    }
    public String getName(){return name;}
}
