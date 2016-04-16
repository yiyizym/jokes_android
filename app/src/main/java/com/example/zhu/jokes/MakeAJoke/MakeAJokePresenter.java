package com.example.zhu.jokes.MakeAJoke;

import android.os.Bundle;

/**
 * Created by zhu on 16/4/12.
 */
public interface MakeAJokePresenter {
    void onRefreshBtnClick();
    void onResume();
    void onDestroy();
    void saveState(Bundle outState);
    void restoreState(Bundle savedState);
}
