package com.example.zhu.jokes.MakeAJoke;

/**
 * Created by zhu on 16/4/12.
 */
public interface MakeAJokeView {
    void setJoke(String joke);
    void showProgress();
    void hideProgress();
    void showErrorMsg(String msg);
}
