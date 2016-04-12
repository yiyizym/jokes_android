package com.example.zhu.jokes.model;

/**
 * Created by zhu on 16/4/9.
 */
public class Joke {
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
}
