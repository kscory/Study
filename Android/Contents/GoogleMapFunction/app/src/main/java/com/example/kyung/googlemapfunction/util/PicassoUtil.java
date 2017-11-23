package com.example.kyung.googlemapfunction.util;

import android.content.Context;
import android.widget.ImageView;

import com.example.kyung.googlemapfunction.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Kyung on 2017-11-23.
 */

public class PicassoUtil {
    public static void loadImage(Context context, String imageUrl, ImageView imageView){
        Picasso.with(context)
                .load(imageUrl)
                .into(imageView);
    }
}
