package com.example.kyung.jsondatapractice.githubusers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kyung.jsondatapractice.R;
import com.example.kyung.jsondatapractice.githubusers.model.User;

import java.util.List;

/**
 * Created by Kyung on 2017-10-17.
 */

public class GitHubUserAdapter extends BaseAdapter {

    Context context;
    List<User> data;

    public GitHubUserAdapter(Context context,List<User> data){
        this.context = context;
        this.data = data;
    }

    // 전체 사이즈
    @Override
    public int getCount() {
        return data.size();
    }
    // 현재 뿌려질 데이터를 리턴
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    // 뷰의 아이디를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
    // 목록에 나타나는 아이템 하나하나를 그린다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        // 아이템View 재사용
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.githubuser_list,parent,false);
            // 홀더에 담기
            holder = new Holder();
            holder.textId = (TextView)convertView.findViewById(R.id.textId);
            holder.textLogin = (TextView)convertView.findViewById(R.id.textLogin);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            // 태그 달기
            convertView.setTag(holder);
        } else{
            // 태그 추출
            holder = (Holder) convertView.getTag();
        }
        // 값 입력
        holder.textId.setText(data.get(position).getId()+"");
        holder.textLogin.setText(data.get(position).getLogin());
        // 이미지 불러오기 (그래들에 추가)
        Glide.with(context)                                 // 글라이드 초기화
                .load(data.get(position).getAvatar_url())   // 주소에서 이미지 가져오기 (폰에있는 uri 넘기면 그것을 넘길수도 있음)
                .into(holder.imageView);                    // 실제 대상에 꽂는다.

        return convertView;
    }
}
class Holder{
    TextView textId;
    TextView textLogin;
    ImageView imageView;
}
