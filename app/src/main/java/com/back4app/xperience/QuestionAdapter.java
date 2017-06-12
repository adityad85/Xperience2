package com.back4app.xperience;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Aditya on 10/25/2016.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
     List<Questions>questions;
    private Context context;
    public QuestionAdapter(List<Questions> questions, Context context){
        super();
        this.questions= questions;
        this.context=context;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewdate,textViewTitle,textViewQues,textViewUser;
        public ViewHolder(View itemView){
            super(itemView);
            textViewdate=(TextView)itemView.findViewById(R.id.date);
            textViewQues=(TextView)itemView.findViewById(R.id.quest);
            textViewTitle=(TextView)itemView.findViewById(R.id.title);
            textViewUser=(TextView)itemView.findViewById(R.id.user);
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context)
                .inflate(R.layout.card_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        Log.i("ch11","yeah");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
           Questions questions1 =questions.get(position);
        holder.textViewTitle.setText(questions1.getQuestion());
        holder.textViewQues.setText(questions1.getDescription());
        holder.textViewdate.setText(questions1.getUpdatedAt().toString());
        holder.textViewUser.setText(questions1.getUser());
        Log.i("ch11",questions1.getDescription());

    }

    @Override
    public int getItemCount() {
        Log.i("yo", String.valueOf(questions.size()));
        return questions.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
