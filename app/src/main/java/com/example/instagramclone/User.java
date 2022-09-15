package com.example.instagramclone;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;



@ParseClassName("_User")
public class User extends ParseUser {

    public static final String KEY_UserName = "username";
    public static final String KEY_Password = "password";
    public static final String KEY_Profile_Image = "profile_image";


    public ParseFile getImage() { return getParseFile(KEY_Profile_Image); }

    public void setImage(ParseFile parseFile) { put(KEY_Profile_Image, parseFile); }



    public String getUserName() {
        return getString(KEY_UserName);
    }

    public void setUserName(String username) {
        put(KEY_UserName, username);
    }


    public String getPassword() {
        return getString(KEY_Password);
    }

    public void setPassword(String password) {
        put(KEY_Password, password);
    }



}
