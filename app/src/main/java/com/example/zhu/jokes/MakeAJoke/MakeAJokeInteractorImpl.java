package com.example.zhu.jokes.MakeAJoke;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhu.jokes.app.AppController;

/**
 * Created by zhu on 16/4/13.
 */
public class MakeAJokeInteractorImpl implements MakeAJokeInteractor {
    private final String JOKE_URL = "http://112.74.194.217:8080/jokes/show";
    private String TAG = MakeAJokeInteractorImpl.class.getSimpleName();
    private String tag_joke_req = "joke_req";
    @Override
    public void getAJoke(final OnGetAJokeFinishedListener listener){
        StringRequest jokeReq = new StringRequest(Request.Method.GET, JOKE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, s.toString());
                listener.onFinished(s.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(TAG, "Error: " + volleyError.getMessage());
                listener.onError(volleyError.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jokeReq, tag_joke_req);
    }
}
