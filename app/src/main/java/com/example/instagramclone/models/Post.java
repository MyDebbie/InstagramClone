package com.example.instagramclone.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;


@ParseClassName("Post")
@Parcel(analyze = Post.class)

public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_Image = "image";
    public static final String KEY_User = "user";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_Like = "like";
    public static final String KEY_Comment = "comment";
    public static final String KEY_Num_Like = "num_like";



    public Post (){}


    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }


    public ParseFile getImage() { return getParseFile(KEY_Image); }
    public void setImage(ParseFile parseFile) { put(KEY_Image, parseFile); }


    public ParseUser getUser() { return getParseUser(KEY_User); }
    public void setUser(ParseUser user) { put(KEY_User, user); }


    public JSONArray getLikes() { return getJSONArray(KEY_Like); }
    public void setLikes(ParseUser liker) { add(KEY_Like, liker); }
    public void removeLikes(List<String> likers) { remove(KEY_Like); put(KEY_Like, likers); }



    public int getNumLike() {
        return getInt(KEY_Num_Like);
    }
    public void setNumLike(int num_like) {
        put(KEY_Num_Like, num_like);
    }


    public JSONArray getComment() { return getJSONArray(KEY_Comment); }
    public void setComment(Comment comment) { add(KEY_Comment, comment); }




    public static List<String> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> likers = new ArrayList<String>();
        try {
            for(int i = 0; i < jsonArray.length(); i++) {
                likers.add(jsonArray.getJSONObject(i).getString("objectId"));
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return likers;
    }

}
