package com.example.kyung.jsondatapractice.githubusers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kyung.jsondatapractice.R;
import com.example.kyung.jsondatapractice.Remote;
import com.example.kyung.jsondatapractice.githubusers.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-17.
 */

public class GitHubUserView extends FrameLayout {

    ListView listView;
    List<User> users;

    public GitHubUserView(Context context) {
        super(context);

        load();
        initView();
    }
    // View를 초기화
    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.githubuser,null);
        listView = (ListView) view.findViewById(R.id.listView);
        addView(view);
    }
    // 리스트뷰 아답터 연결
    private void initListView(){
        GitHubUserAdapter adapter = new GitHubUserAdapter(getContext(),users);
        listView.setAdapter(adapter);
    }
    // 데이터를 로드
    private void load(){
        // data 변수에 불러온 데이터를 입력
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... params) {
                return Remote.getData("https://api.github.com/users");
            }

            @Override
            protected void onPostExecute(String jsonString) {
                if("AccessError".equals(jsonString)){
                    Toast.makeText(getContext(), "githubuser Api 통신 오류", Toast.LENGTH_SHORT).show();
                } else {
                    // jsonString을 parsing
                    users = parse(jsonString);
                    // data = 결과 (쓰레드로 작동하기 때문에 작업이 끝난후 이어지게 하려면 이곳에 넣는다.)
                    initListView();
                }
            }
        }.execute();
    }

    // Gson 라이브러리 사용
    private List<User> parse(String string){
        List<User> result = new ArrayList<>();
        // 양 끝 문자 없애기 [, ]
        string = string.substring(2, string.length()-3);
        // 문자열 분리하기
        String array[] = string.split("\\},\\{");
        for(String item : array) {
            item = "{"+item+"}";
            Gson gson = new Gson();
            User user = gson.fromJson(item,User.class);
            result.add(user);
        }
        return result;
    }

//    private List<User> parse(String string){
//        List<User> result = new ArrayList<>();
//        // 앞의 문자 두개 없애기 [ {
//        string = string.substring(string.indexOf("{")+1);
//        // 뒤의 문자 두개 없애기 } ]
//        string = string.substring(0,string.lastIndexOf("}"));
//        // 문자열 분리하기
//        String array[] = string.split("\\},\\{");
//        for(String item : array){
//            User user = new User();
//            // item 문자열을 분리하여 user의 멤버변수에 담기
//            String member[] = item.split(",");
//            for(String unit : member){
//                String first = unit.substring(0,unit.indexOf(":"));
//                String second = unit.substring(unit.indexOf(":")+1);
//
//                if(first.equals("\"id\"")){
//                    user.setId(Integer.parseInt(second));
//                } else if(first.equals("\"login\"")){
//                    second = second.substring(second.indexOf("\"")+1,second.lastIndexOf("\""));
//                    user.setLogin(second);
//                } else if(first.equals("\"avatar_url\"")){
//                    second = second.substring(second.indexOf("\"")+1,second.lastIndexOf("\""));
//                    user.setAvatar_url(second);
//                }
//            }
//            result.add(user);
//        }
//        return result;
//    }
}
