package com.example.android.news;

import android.media.Image;

import static android.R.attr.resource;

/**
 * Created by ROHAN on 08-07-2017.
 */

public class custom {
    //First one for image
    private String resource;
    private String title;
    private String url;

    public custom(String a,String b,String c){
        this.resource=a;
        this.title=b;
        this.url=c;
    }

    public String getResource() {
        return resource;
    }
    public String getTitle(){
        return title;
    }
    public String getUrl(){
        return url;
    }
}
