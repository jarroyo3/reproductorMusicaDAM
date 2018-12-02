package com.example.jorge.reproductormultimedia.widget.cancion;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jorge.reproductormultimedia.R;

import java.util.ArrayList;

public class CancionAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Cancion> items;

    public CancionAdapter(Activity activity, ArrayList<Cancion> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Cancion> divisa) {
        for (Cancion cancionI: divisa) {
            this.items.add(cancionI);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == convertView) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_cancion, null);
        }

        Cancion c =  this.items.get(position);
        final String tituloCancion = c.getTitulo();

        TextView nombreDivisa = (TextView) v.findViewById(R.id.tvTitulo);
        nombreDivisa.setText(c.getTitulo());

        TextView valorDivisa = (TextView) v.findViewById(R.id.tvDuracion);
        valorDivisa.setText(c.getDuracion().toString());

        return v;
    }
}
