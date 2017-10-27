package com.example.kyung.remotebbs.model;

/**
 * Created by Kyung on 2017-10-27.
 */

public class Data {
    private String _id;
    private String content;
    private String title;
    private String user_id;
    private String date;

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String get_id () {
        return _id;
    }

    public void set_id (String _id) {
        this._id = _id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", title = "+title+", _id = "+_id+", user_id = "+user_id+", date = "+date+"]";
    }
}
