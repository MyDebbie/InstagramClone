package com.example.instagramclone.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;


@ParseClassName("Comment")
@Parcel(analyze = Comment.class)

public class Comment extends ParseObject{

    public static final String KEY_User = "user";
    public static final String KEY_Comments = "comments";


    public Comment (){}




    public ParseUser getUser() { return getParseUser(KEY_User); }

    public void setUser(ParseUser user) { put(KEY_User, user); }



    public String getComments() { return getString(KEY_Comments); }

    public void setComments(String comments) { put(KEY_Comments, comments); }


    public static List<String> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> comments = new ArrayList<String>();
        try {
            for(int i = 0; i < jsonArray.length(); i++) {
                comments.add(jsonArray.getJSONObject(i).getString("objectId"));
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return comments;
    }



}


