package com.example.instagramclone;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_Image = "image";
    public static final String KEY_User = "user";
    public static final String KEY_CREATED_AT = "created_at";


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


    public String getFormattedTimestamp(){
        return TimeFormatter.getTimeDifference(KEY_CREATED_AT);
    }



}
