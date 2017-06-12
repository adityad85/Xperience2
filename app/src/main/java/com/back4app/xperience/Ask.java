package com.back4app.xperience;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 10/18/2016.
 */
public class Ask extends Fragment {

    //Creating a List of superheroes
    private ArrayList<Questions> questions;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private LinearLayout headerProgress ;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView= inflater.inflate(R.layout.ask,container,false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
        headerProgress = (LinearLayout)myView.findViewById(R.id.lHeaderProgress);
        recyclerView.setHasFixedSize(false);

       // swipeRefreshLayout =  (SwipeRefreshLayout)myView.findViewById(R.id.swipeRefresh);

        //layoutManager = new LinearLayoutManager();
        //recyclerView.setLayoutManager(layoutManager);
        //Initializing our superheroes list
        headerProgress.setVisibility(View.VISIBLE);
        questions = new ArrayList<>();
        //Calling method to get data
       getData();
      //  adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(),"yo"+questions.get(position).getDescription(),Toast.LENGTH_LONG).show();
                Intent i=new Intent(getActivity() ,QuestionElement.class);
                Questions q=new Questions();
                q=questions.get(position);
                i.putExtra("data", q);
                i.putExtra("id",position);
                startActivity(i);

            }
        }));
       // recyclerView.setAdapter(adapter);
       // swipeRefreshLayout.setOnRefreshListener(this);


        return myView;
    }
    public void getData()
    {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Question");
        query.addDescendingOrder("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    if(objects.size()>0)
                    {Log.i("Aditya","You would");
                        parseData(objects);}

                }

            }
        });
    }
    private void parseData(List<ParseObject> objects) {
        for(ParseObject user:objects){
            Questions quest = new Questions();
                try {
                    quest.setUser(user.getString("user"));
                    quest.setQuestion("Q."+user.getString("Question"));
                    //quest.setDescription(user.getString("Description"));
                    quest.setDescription(user.getString("Description"));
                    quest.setUpdatedAt(user.getUpdatedAt());
                    Log.i("res", quest.getUpdatedAt().toString());
                }
                catch (Exception e){
                    Log.i("not","reaq");
                    e.printStackTrace();
                }
            questions.add(quest);
        }
         adapter = new QuestionAdapter(questions,getActivity());
        recyclerView.setAdapter(adapter);
        Log.i("Sd","Aasd");
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        headerProgress.setVisibility(View.INVISIBLE);
    }

  /*@Override
    public void onRefresh() {
        getData();
    }*/
}
