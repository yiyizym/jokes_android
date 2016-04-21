package com.example.zhu.jokes.MakeAJoke;

import android.content.SharedPreferences;

/**
 * Created by zhu on 16/4/12.
 */
public class MakeAJokePresenterImpl implements MakeAJokePresenter, MakeAJokeInteractor.OnGetAJokeFinishedListener {
    private MakeAJokeView makeAJokeView;
    private MakeAJokeInteractor makeAJokeInteractor;

    public MakeAJokePresenterImpl(MakeAJokeView makeAJokeView){
        this.makeAJokeView = makeAJokeView;
        makeAJokeInteractor = new MakeAJokeInteractorImpl();
    }
    @Override
    public void onResume(){
        if (makeAJokeView != null){
            makeAJokeView.showProgress();
        }
        makeAJokeInteractor.getAJoke(this);
    }
    @Override
    public void onDestroy(){
        makeAJokeView = null;
    }
    @Override
    public void onFinished(String joke){
        if (makeAJokeView != null){
            makeAJokeView.setJoke(joke);
            makeAJokeView.hideProgress();
        }
    }
    @Override
    public void onError(String msg){
        if (makeAJokeView != null){
            makeAJokeView.hideProgress();
            makeAJokeView.showErrorMsg(msg);
        }
    }
    @Override
    public void onRefreshBtnClick(){
        makeAJokeView.showProgress();
        makeAJokeInteractor.getAJoke(this);
    }


    @Override
    public void saveData(SharedPreferences data){
        SharedPreferences.Editor editor = data.edit();
        editor.putString("jokeText", makeAJokeView.getJoke());
        editor.commit();
        makeAJokeInteractor.saveData(data);
    }

    @Override
    public void restoreData(SharedPreferences data){
        makeAJokeView.setJoke(data.getString("jokeText",""));
        makeAJokeInteractor.restoreData(data);
    }
}
