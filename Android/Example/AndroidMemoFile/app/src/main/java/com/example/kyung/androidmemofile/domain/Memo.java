package com.example.kyung.androidmemofile.domain;

import android.util.Log;

/**
 * Created by Kyung on 2017-09-20.
 */

public class Memo {

    private static final String DELIMETER ="//";

    private int no;
    private String title;
    private String author;
    private long datetime;
    private String content;

    public void parse(String text){
        // 1. 문자열을 줄단위로 분해
        String lines[] = text.split("\n");
        // 2. 문자열을 행(DELIMETER) 단위로 분해
        for(String item : lines){
            String columns[] = item.split(DELIMETER);
            String key="";
            String value="";
            if(columns.length==2){
                key=columns[0];
                value=columns[1];
            } else{
                key="";
                value=columns[0];
            }

            switch (key){
                case "no":
                    setNo(Integer.parseInt(value));
                    break;
                case "title":
                    setTitle(value);
                    break;
                case "author":
                    setAuthor(value);
                    break;
                case "datetime":
                    setDatetime(Long.parseLong(value));
                    break;
                case "content":
                    setContent(value);
                    break;
                default:
                    appendContent(value);
            }
        }
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 남은 content들을 대입
    public void appendContent(String value){
        this.content += "\n"+value;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("no").append(DELIMETER).append(getNo()).append("\n");
        result.append("title").append(DELIMETER).append(getTitle()).append("\n");
        result.append("author").append(DELIMETER).append(getAuthor()).append("\n");
        result.append("datetime").append(DELIMETER).append(getDatetime()).append("\n");
        result.append("content").append(DELIMETER).append(getContent()).append("\n");

        return result.toString();
    }

    public byte[] toByte(){
        return toString().getBytes();
    }
}
