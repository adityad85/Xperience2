package com.back4app.xperience;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Aditya on 10/24/2016.
 */
public class MainParse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("SFHyklZn7mI4pvXIB2pdWYCwAtDkDTo3IX56wbx6")
                .clientKey("vlMFPcBM42BATabbE8XJq7MVPXPC2hne9T9brbzv")
                .server("https://parseapi.back4app.com/") // The trailing slash is important
                .build()
        );
    }
}
