package com.example.zhu.jokes.MakeAJoke;

/**
 * Created by zhu on 16/5/7.
 */
public class MakeAJokeInteractorFactoryImpl implements MakeAJokeInteractorFactory {
    @Override
    public MakeAJokeInteractor createMakeAJokeInteractor(){
        return new MakeAJokeInteractorImpl();
    };
}
