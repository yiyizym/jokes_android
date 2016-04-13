package com.example.zhu.jokes.MakeAJoke;

/**
 * Created by zhu on 16/4/12.
 */
public interface MakeAJokeInteractor {
    interface OnGetAJokeFinishedListener {
        void onFinished(String joke);
    }
    void getAJoke(OnGetAJokeFinishedListener listener);
}