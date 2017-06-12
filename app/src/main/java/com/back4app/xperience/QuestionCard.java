package com.back4app.xperience;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class QuestionCard extends AppCompatActivity {

    public EditText headQuestion,descQuestion;
    Switch switch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headQuestion = (EditText)findViewById(R.id.headQuestion);
        descQuestion = (EditText)findViewById(R.id.descQuestion);
        switch1 = (Switch)findViewById(R.id.switch1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("user",ParseUser.getCurrentUser().toString());
                if(ParseUser.getCurrentUser()!=null)
                {Log.i("user",ParseUser.getCurrentUser().getUsername());
                    ParseObject object = new ParseObject("Question");
                object.put("Question",headQuestion.getText().toString());
                object.put("Description",descQuestion.getText().toString());
                if(switch1.isChecked()) {
                    object.put("user", ParseUser.getCurrentUser().getUsername());
                }
                else
                object.put("user","Anonymous");
                object.put("username",ParseUser.getCurrentUser().getUsername());
                ParseACL acl=  new ParseACL();
                acl.setPublicReadAccess(true);
                object.setACL(acl);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e== null)
                        { Intent i= new Intent(getApplicationContext(),MainActivity.class);
                            Toast.makeText(getApplicationContext(),"Question Submitted",Toast.LENGTH_SHORT).show();
                        startActivity(i);}
                        else
                            Toast.makeText(getApplicationContext(),"There's a problem right now ,please remember your Question",Toast.LENGTH_SHORT).show();
                    }
                });}
                else
                {

                   new AlertDialog.Builder(QuestionCard.this)
                            .setTitle("Please Log In..")
                            .setMessage("Post in any way, Once you are logged In")
                            .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                    i.putExtra("frag",R.id.nav_login);
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("No Way", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                    //startActivity(i);
                                }
                            }).show();
                }
            }
        });
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
