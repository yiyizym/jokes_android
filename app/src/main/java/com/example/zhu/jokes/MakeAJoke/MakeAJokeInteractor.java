package com.example.zhu.jokes.MakeAJoke;

import android.os.Bundle;

/**
 * Created by zhu on 16/4/12.
 */
public interface MakeAJokeInteractor {
    interface OnGetAJokeFinishedListener {
        void onFinished(String joke);
        void onError(String errorMsg);
    }
    void getAJoke(OnGetAJokeFinishedListener listener);
    void saveState(Bundle outState);
    void restoreState(Bundle savedState);
}
