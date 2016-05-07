package com.example.zhu.jokes.MakeAJoke;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zhu.jokes.Configuration;
import com.example.zhu.jokes.app.AppController;
import com.example.zhu.jokes.model.Joke;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
    private Integer maxPageNum = 10;
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
    public void saveData(SharedPreferences data){
        Gson gson = new Gson();
        String jsonText = gson.toJson(jokes, getJokeType());
        SharedPreferences.Editor editor = data.edit();
        editor.putInt("pageNum", pageNum);
        editor.putInt("maxPageNum", maxPageNum);
        editor.putString("jokes", jsonText);
        editor.commit();
        Log.d(DEBUG, "saveData");
    }

    @Override
    public void restoreData(SharedPreferences data){
        Gson gson = new Gson();
        pageNum = data.getInt("pageNum", 0);
        maxPageNum = data.getInt("maxPageNum", 10);
        String jsonText = data.getString("jokes", "");
        if (!jsonText.isEmpty()){
            jokes = gson.fromJson(jsonText, getJokeType());
        } else {
            jokes = new ArrayList<>();
        }
        Log.d(DEBUG, "restoreData");
    }

    //see https://github.com/google/gson/blob/master/UserGuide.md#serializing-and-deserializing-generic-types
    private Type getJokeType(){
        Type jokeType = new TypeToken<ArrayList<Joke>>() {}.getType();
        return jokeType;
    }

    private void getAJokeFromBackend(final OnGetAJokeFinishedListener listener){
        String uri = String.format("http://" + Configuration.HOST_URL+ "/jokes/get_paginate_joke.json?page_num=%1$s&items_per_page=%2$s",
                pageNum,
                itemsPerPage);
//        Log.d(DEBUG, "uri :" + uri);
        JsonObjectRequest jokesReq = new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("return_code") == 0) {

                        Integer totalCount = jsonObject.getInt("total_count");
//                        Log.d("DEBUG", "totalCount :" + totalCount);
                        maxPageNum = (int) Math.ceil((double)totalCount / itemsPerPage);
//                        Log.d(DEBUG, "maxPageNum :" + maxPageNum);

                        if (pageNum == 0){
                            pageNum = jsonObject.getInt("page_num");
                        }
                        pageNum++;
                        pageNum = pageNum % (maxPageNum + 1);
                        if (pageNum == 0){
                            pageNum++;
                        }
//                        Log.d(DEBUG, "pageNum :" + pageNum);

                        JSONArray jsonArray = jsonObject.getJSONArray("jokes");
//                        Log.d(DEBUG, "jsonArray: " + jsonArray.toString());
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
//                Log.d(TAG, volleyError.getMessage());
                listener.onError(volleyError.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jokesReq, tag_joke_req);
    }

    private void getAJokeFromLocal(final OnGetAJokeFinishedListener listener){
        Joke jokeToReturn;
        jokeToReturn = jokes.remove(jokes.size()-1);
//        Log.d(TAG, jokeToReturn.getContent());
        listener.onFinished(jokeToReturn.getContent());
    }
}
