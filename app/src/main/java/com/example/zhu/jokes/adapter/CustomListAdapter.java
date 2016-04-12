package com.example.zhu.jokes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhu.jokes.R;
import com.example.zhu.jokes.model.Joke;

import java.util.List;

/**
 * Created by zhu on 16/4/9.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Joke> jokes;

    public CustomListAdapter(Activity activity, List<Joke> jokes){
        this.activity = activity;
        this.jokes = jokes;
    }
    @Override
    public int getCount() {
        return jokes.size();
    }

    @Override
    public Object getItem(int location) {
        return jokes.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView content = (TextView) convertView.findViewById(R.id.content);

        Joke m = jokes.get(position);

        content.setText(m.getContent());

        return convertView;
    }
}
