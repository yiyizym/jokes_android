package com.example.zhu.jokes.MakeAJoke;

import android.content.SharedPreferences;

/**
 * Created by zhu on 16/4/12.
 */
public interface MakeAJokeInteractor {
    interface OnGetAJokeFinishedListener {
        void onFinished(String joke);
        void onError(String errorMsg);
    }
    void getAJoke(OnGetAJokeFinishedListener listener);
    void saveData(SharedPreferences data);
    void restoreData(SharedPreferences data);
}
