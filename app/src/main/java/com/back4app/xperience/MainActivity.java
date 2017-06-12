package com.back4app.xperience;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        setSupportActionBar(toolbar);
       // ConnectivityManager cm= (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        FragmentManager fragmentManager = getFragmentManager();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                Intent i = new Intent(getApplicationContext(),QuestionCard.class);
                startActivity(i);
            }
        });
       // NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
       // boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
       // TextView textView = (TextView)findViewById(R.id.textView);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        textView=(TextView)header.findViewById(R.id.textView);
  //     if(ParseUser.getCurrentUser()!=null)
//        textView.setText(ParseUser.getCurrentUser().toString()+"kiet.edu");


            fragmentManager.beginTransaction()
                .replace(R.id.content_frame,
                        new Ask())
                .commit();
        if(NetworkChecker.getInstance(this).isOnline())
        {Log.i("check ", String.valueOf( NetworkChecker.getInstance(this).isOnline()));
            Snackbar snackbar = Snackbar.make(relativeLayout,"No Internet",Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
        Intent i = getIntent();
        if(i.getExtras() != null)
        if(i.getExtras().getInt("frag")==R.id.nav_login)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new LoginFirst())
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else
        if(id==R.id.action_help){
            Intent i= new Intent(getApplicationContext(),Help.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
    public void profileCall(View view){
        Intent i = new Intent(getApplicationContext(),ProfileView.class);
        startActivity(i);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_ask) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new Ask())
                    .commit();
            // Handle the camera action
        } else if (id == R.id.nav_project) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new Project())
                    .commit();

        } else if (id == R.id.nav_login) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                             new LoginFirst())
                    .commit();

        } else if (id == R.id.nav_contact) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            new Contact())
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
