package com.example.zhu.jokes.MakeAJoke;

import android.content.SharedPreferences;

/**
 * Created by zhu on 16/4/12.
 */
public interface MakeAJokePresenter {
    void onRefreshBtnClick();
    void onResume();
    void onDestroy();
    void saveData(SharedPreferences data);
    void restoreData(SharedPreferences data);
}
