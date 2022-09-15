package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        ParseUser.registerSubclass(User.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Pb21RuN31CzKYcyDdHtz4armgfhzCtH1VZzTQuxL")
                .clientKey("Cz51IcN3N0I5gE0NrAoqjF44FoGDigiPnRdaOEOx")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
