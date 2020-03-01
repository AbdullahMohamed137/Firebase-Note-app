package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class BaseAdapter extends android.widget.BaseAdapter {
    ArrayList<Note> list = new ArrayList<>();
    Context context;

    public BaseAdapter(Context context, ArrayList<Note> Notelist) {
        list = Notelist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_single_row, null);
        TextView Date = view.findViewById(R.id.time_text_view);
        TextView t = view.findViewById(R.id.title_text_view);
        t.setText(list.get(position).getTitle());
        Date.setText(list.get(position).getDate());
        return view;
    }
}
