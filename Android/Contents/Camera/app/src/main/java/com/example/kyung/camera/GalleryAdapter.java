package com.example.kyung.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Kyung on 2017-09-29.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.Holder> {
    Context context;
    List<String> data;

    public GalleryAdapter(Context context){
        this.context = context;
    }

    public void setData(List<String> data){
        this.data = data;
        // 데이터가 변경되었다고 알려주어야 한다.
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String path = data.get(position);
        Uri uri = Uri.parse(path);
        holder.setImagePic(uri);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class Holder extends RecyclerView.ViewHolder{

        private ImageView imagePic;
        private TextView textDate;
        private Uri uri;

        public Holder(View itemView) {
            super(itemView);

            imagePic = (ImageView) itemView.findViewById(R.id.imagePic);
            textDate = (TextView) itemView.findViewById(R.id.textDate);

            imagePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("imagePath", uri.getPath());
                    // 이미지 경로는 Thumbnail 이 아닌 원본 이미지의 경로를 넘겨야 한다
                    Activity activity = (Activity) context;
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                }
            });

        }

        public void setImagePic(Uri uri){
            this.uri = uri;
            imagePic.setImageURI(uri);
        }

        public void setTextDate(String datetime){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            textDate.setText(sdf.format(datetime));
        }
    }
}
