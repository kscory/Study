package com.example.kyung.basiclist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-09-19.
 */
// 기본 아답터 클래스를 상속받아서 구현
public class CustomAdapter extends BaseAdapter {
    // 데이터 저장소를 아답터 내부에 두는것이 컨트롤하기 편하다.
    List<String> data;
    Context context;

    // 생성자
    public  CustomAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    //현재 데이터 총 개수
    @Override
    public int getCount() {
        return data.size();
    }

    // 현재 뿌려질 데이터를 리턴
    @Override
    public Object getItem(int position) { // <-호출되는 목록아이템의 위치가 position
        return data.get(position);
    }

    // 뷰의 아이디를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 목록에 나타나는 아이템 하나하나를 그려준다.
    // 화면에 1 픽셀이라도 나타나면 getView가 호출
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView ==null){ // 아이템 view를 재사용하기 위해서 null 체크를 해준다.
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            // 뷰안에 있는 텍스트뷰 위젯에 값을 입력한다.
            // (가) 아이템이 최초 호출될 경우는 Holder에 위젯들을 담고,
            holder = new Holder();
            holder.textView =(TextView) convertView.findViewById(R.id.textView);
            holder.init();
            // (나) 홀더를 View에 붙여놓는다.
            convertView.setTag(holder);
        } else{
            // View에 붙어 있는 홀더를 가져온다.
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(data.get(position));
        return convertView;
    }

    class Holder{
        TextView textView;
        public void init(){
            textView.setOnClickListener(new View.OnClickListener() {
                // 화면에 보여지는 View는 기본적으로 자신이 속한 Component의 컨텍스트를 그대로 가지고 있다.
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("valueKey", textView.getText());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

// 테스트할때 꼭 봐야하는 데이터는 log가 아니라 System.out으로 찍는다.