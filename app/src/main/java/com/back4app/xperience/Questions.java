package com.back4app.xperience;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Aditya on 10/25/2016.
 */
public class Questions implements Parcelable{
    public String user,question,description;
    public Date updatedAt;

    protected Questions()
    {

    }

   protected Questions(Parcel in) {
        user = in.readString();
        question = in.readString();
        description = in.readString();

    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public Date getUpdatedAt()
    {
        return updatedAt;
    }
    public String getUser(){
        return user;
    }
    public String getQuestion()
    {
        return question;
    }
    public String getDescription(){
        return description;
    }
    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt=updatedAt;
    }
    public void setUser(String user)
    {
        this.user=user;
    }
    public void setQuestion(String question)
    {
        this.question=question;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }

   @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(question);
        dest.writeString(description);
        dest.writeString(String.valueOf(updatedAt));

    }
}
