package com.example.zhu.jokes.MakeAJoke;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhu.jokes.R;

/**
 * Created by zhu on 16/4/12.
 */
public class MakeAJokeActivity extends Activity implements MakeAJokeView, View.OnClickListener {
    private ProgressBar progressBar;
    private TextView jokeText;
    private MakeAJokePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_a_joke);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        jokeText = (TextView) findViewById(R.id.joke_text);
        findViewById(R.id.refresh_btn).setOnClickListener(this);

        presenter = new MakeAJokePresenterImpl(this);
    }

    @Override
    public void setJoke(String joke){
        jokeText.setText(joke);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v){
        presenter.onRefreshBtnClick();
    }
}