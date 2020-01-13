package com.ivanilov.techcard.Presenter.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;

import java.util.ArrayList;

public class TechCartAdapter extends BaseAdapter {

    private IngredientsFragment view;
    private ArrayList<String> item;


    public TechCartAdapter(IngredientsFragment view, ArrayList<String> item){
        this.view = view;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public String getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(view.getContext()).inflate(R.layout.item_ingredient, parent, false);
        }
        final TextView textViewName = convertView.findViewById(R.id.item_ingredient_name);

        final TextView textViewAmount = convertView.findViewById(R.id.item_ingredient_amount);

        final TextView textViewUnits = convertView.findViewById(R.id.item_ingredient_units);


        textViewName.setText(item.get(position));
        textViewAmount.setText(item.get(position));
        textViewUnits.setText(item.get(position));

        return convertView;
    }
}