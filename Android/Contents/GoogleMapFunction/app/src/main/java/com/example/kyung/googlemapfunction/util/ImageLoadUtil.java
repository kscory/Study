package com.example.kyung.googlemapfunction.util;

import android.content.Context;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.example.kyung.googlemapfunction.R;
import com.squareup.picasso.Picasso;

/**
 * Picasso, AQuery 를 한번 써보기 위해 사용
 */

public class ImageLoadUtil {
    public static void loadImageByPicasso(Context context, String imageUrl, ImageView imageView){
        Picasso.with(context)
                .load(imageUrl)
                .fit() // imageView의 크기에 맞게 화질을 바꾼다.
                .into(imageView);
    }

    public static void loadImageByGlide(){

    }

    public static void loadImageByAQuery(Context context, String imageUrl, ImageView imageView){
        new AQuery(context).id(imageView).image(imageUrl);
    }
}
