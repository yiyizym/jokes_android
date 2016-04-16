package com.example.zhu.jokes.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhu on 16/4/9.
 */
public class Joke implements Parcelable{

    private String content;

    public Joke(){}

    public Joke(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(content);
    }

    public static final Parcelable.Creator<Joke> CREATOR
            = new Parcelable.Creator<Joke>() {
        public Joke createFromParcel(Parcel in) {
            return new Joke(in);
        }

        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };

    private Joke(Parcel in) {
        content = in.readString();
    }
}
