package com.back4app.xperience;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aditya on 10/19/2016.
 */
public class LoginFirst extends Fragment implements View.OnClickListener,View.OnKeyListener{
    private EditText username ,password,eMail;
    private TextView loginTextView1;
    private Button signUpbutton;
    private Boolean activeMode;
    private RelativeLayout relativeLayout;
    private void attemptRegister() {
        // Reset errors.
        //eMail.setError(null);
        //password.setError(null);

        // Store values at the time of the login attempt.
        String email = eMail.getText().toString();
        String password1 = password.getText().toString();

        boolean cancel = false;
        View focusView = null;
        Log.i("Password0",password1);
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password1)||password1.length()<6) {
            password.setError("Please Set A Strong Password");
            focusView =password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            eMail.setError("Can't Be Empty");
            focusView = eMail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            eMail.setError("Not Valid");
            focusView = eMail;
            cancel = true;
        }
        Log.i("email",String.valueOf(isEmailValid(email)));
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            //TextUtils.StringSplitter here = new TextUtils.SimpleStringSplitter(delimiter);
            ParseUser user = new ParseUser();
            user.setUsername(email.split("@")[0]);
            user.setPassword(password1);
            user.setEmail(email);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(com.parse.ParseException e) {

                    if (e == null) {
                        // showProgress(false);
                        final String title = "Account Created Successfully!";
                        final String message = "Please verify your email before Login";
                        Toast.makeText(getActivity().getApplicationContext(),title + message,Toast.LENGTH_LONG).show();
                    } else {
                        //showProgress(false);
                        final String title = "Error Account Creation failed";
                        final String message = "Account could not be created";
                        // alertDisplayer(title, message + " :" + e.getMessage());
                        Toast.makeText(getActivity().getApplicationContext(),title +message +": "+ e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

            });
        }
    }
    private boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        // final String emailk = "^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$";
        pattern = Pattern.compile("^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*+@kiet.edu$");
        matcher = pattern.matcher(email);
        if(matcher.matches())
            return true;
        else
            return false;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN )
        {
            signUpOrLogin(v);
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginTextView) {
            if (activeMode == true) {
                activeMode = false;
                loginTextView1.setText("Sign Up");
                signUpbutton.setText("Log In");
                username.setVisibility(View.VISIBLE);
                eMail.setVisibility(View.INVISIBLE);

            } else
            {
                activeMode = true;
                loginTextView1.setText("Log In");
                signUpbutton.setText("Sign Up");
                username.setVisibility(View.INVISIBLE);
                eMail.setVisibility(View.VISIBLE);


            }
        }
        else if(v.getId()==R.id.login_layout)
        {
            InputMethodManager im= (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
        }
    }
    public void signUpOrLogin(View view){
        if(activeMode== true){
            attemptRegister();
        }

        else {
            View focusView = null;
            if (TextUtils.isEmpty(username.getText()))
            {username.setError
                    ("C'mon, Don't Be Shy");
                focusView = username;
                focusView.requestFocus();}
            else
            if (TextUtils.isEmpty(password.getText()))
            {password.setError("Hey, You Can't Enter Without Those Magic Words");
                focusView = password;
                focusView.requestFocus();}

            Log.i("username",username.getText().toString());
            ParseUser.logInInBackground(String.valueOf(username.getText()), String.valueOf(password.getText()), new LogInCallback() {
                @Override
                public void done(ParseUser user, com.parse.ParseException e) {
                    if(user!=null)
                    {
                        Intent i = new Intent(getActivity(),Ask.class);
                        startActivity(i);

                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),e.getMessage().substring(e.getMessage().indexOf(" ")),Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

  /*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(new Parse.Configuration.Builder(getActivity().getApplicationContext())
                .applicationId("SFHyklZn7mI4pvXIB2pdWYCwAtDkDTo3IX56wbx6")
                .clientKey("vlMFPcBM42BATabbE8XJq7MVPXPC2hne9T9brbzv")
                .server("https://parseapi.back4app.com/") // The trailing slash is important
                .build()
        );
        activeMode = true;

        //setContentView(R.layout.login_layout);
        username = (EditText) getView().findViewById(R.id.username);
        /*password = (EditText) getView().findViewById(R.id.password);
        signUpbutton = (Button)getView().findViewById(R.id.button);
        loginTextView1 = (TextView) getView().findViewById(R.id.loginTextView);
        eMail = (EditText)getView().findViewById(R.id.eMail);

        //loginTextView1.setOnClickListener(this);
        //password.setOnKeyListener(this);
        //eMail.setOnKeyListener(this);
//        relativeLayout.setOnClickListener(this);

    }
*/

View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.login_layout, container, false);
        /*Parse.initialize(new Parse.Configuration.Builder(getActivity().getApplicationContext())
                .applicationId("SFHyklZn7mI4pvXIB2pdWYCwAtDkDTo3IX56wbx6")
                .clientKey("vlMFPcBM42BATabbE8XJq7MVPXPC2hne9T9brbzv")
                .server("https://parseapi.back4app.com/") // The trailing slash is important
                .build()
        );*/
        activeMode = true;

        //setContentView(R.layout.login_layout);
        username = (EditText) myview.findViewById(R.id.username);
        password = (EditText) myview.findViewById(R.id.password);
        signUpbutton = (Button)myview.findViewById(R.id.button);
        loginTextView1 = (TextView) myview.findViewById(R.id.loginTextView);
        eMail = (EditText)myview.findViewById(R.id.eMail);

       loginTextView1.setOnClickListener(this);

        signUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activeMode== true){
                    attemptRegister();
                }

                else {
                    View focusView = null;
                    if (TextUtils.isEmpty(username.getText()))
                    {username.setError("C'mon, Don't Be Shy");
                        focusView = username;
                        focusView.requestFocus();}
                    else
                    if (TextUtils.isEmpty(password.getText()))
                    {password.setError("Hey, You Can't Enter Without Those Magic Words");
                        focusView = password;
                        focusView.requestFocus();}

                    Log.i("username",username.getText().toString());
                    ParseUser.logInInBackground(String.valueOf(username.getText()), String.valueOf(password.getText()), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, com.parse.ParseException e) {
                            if(user!=null)
                            {
                                Intent i = new Intent(getActivity(),MainActivity.class);
                                startActivity(i);

                            }
                            else
                                Toast.makeText(getActivity().getApplicationContext(),e.getMessage().substring(e.getMessage().indexOf(" ")),Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });
       /* password.setOnKeyListener(this);
        eMail.setOnKeyListener(this);
        relativeLayout.setOnClickListener(this);
*/
        return myview;
    }

}
