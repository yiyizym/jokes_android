package com.example.zhu.jokes.MakeAJoke;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zhu.jokes.Configuration;
import com.example.zhu.jokes.app.AppController;
import com.example.zhu.jokes.model.Joke;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhu on 16/4/13.
 */
public class MakeAJokeInteractorImpl implements MakeAJokeInteractor {
    private final String DEBUG = "DEBUG";
    private String TAG = MakeAJokeInteractorImpl.class.getSimpleName();
    private String tag_joke_req = "joke_req";
    private Integer pageNum = 0;
    private Integer itemsPerPage = 5;
    private Integer maxPageNum = 0;
    private ArrayList<Joke> jokes = new ArrayList<>();
    @Override
    public void getAJoke(final OnGetAJokeFinishedListener listener){

        if (jokes.isEmpty()){
            getAJokeFromBackend(listener);
        } else {
            getAJokeFromLocal(listener);
        }

    }

    @Override
    public void saveState(Bundle outState){
        Log.d(DEBUG, "saveState");
        outState.putInt("pageNum", pageNum);
        outState.putInt("maxPageNum", maxPageNum);
        outState.putParcelableArrayList("jokes", jokes);
    }

    @Override
    public void restoreState(Bundle savedState){
        pageNum = savedState.getInt("pageNum");
        maxPageNum = savedState.getInt("maxPageNum");
        jokes = savedState.getParcelableArrayList("jokes");
        Log.d(DEBUG, "restoreState, pageNum: " + pageNum.toString() + " maxPageNum: " + maxPageNum.toString());
    }

    private void getAJokeFromBackend(final OnGetAJokeFinishedListener listener){
        String uri = String.format("http://" + Configuration.HOST_URL+ "/jokes/get_paginate_joke.json?page_num=%1$s&items_per_page=%2$s",
                pageNum,
                itemsPerPage);
        Log.d(DEBUG, "uri :" + uri);
        JsonObjectRequest jokesReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("return_code") == 0) {

                        Integer totalCount = jsonObject.getInt("total_count");
                        Log.d("DEBUG", "totalCount :" + totalCount);
                        maxPageNum = (int) Math.ceil((double)totalCount / itemsPerPage);
                        Log.d(DEBUG, "maxPageNum :" + maxPageNum);

                        if (pageNum == 0){
                            pageNum = jsonObject.getInt("page_num");
                        }
                        pageNum++;
                        pageNum = pageNum % (maxPageNum + 1);
                        if (pageNum == 0){
                            pageNum++;
                        }
                        Log.d(DEBUG, "pageNum :" + pageNum);

                        JSONArray jsonArray = jsonObject.getJSONArray("jokes");
                        Log.d(DEBUG, "jsonArray: " + jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String content = obj.getString("content");
                            Joke joke = new Joke();
                            joke.setContent(content);
                            jokes.add(joke);
                        }
                        getAJokeFromLocal(listener);
                    } else {
                        listener.onError(jsonObject.getString("return_info"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, volleyError.getMessage());
                listener.onError(volleyError.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jokesReq, tag_joke_req);
    }

    private void getAJokeFromLocal(final OnGetAJokeFinishedListener listener){
        Joke jokeToReturn;
        jokeToReturn = jokes.remove(jokes.size()-1);
        Log.d(TAG, jokeToReturn.getContent());
        listener.onFinished(jokeToReturn.getContent());
    }
}
