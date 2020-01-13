package com.ivanilov.techcard.Presenter.Adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Dialogs.DialogComingWriteOff;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;

import java.text.DecimalFormat;
import java.util.List;

public class IngredientAdapter extends BaseAdapter {

    private IngredientsFragment view;
    private List<Ingredient> item;
    DialogComingWriteOff dialogComingWriteOff;


    public IngredientAdapter (IngredientsFragment view, List<Ingredient> item){
        this.view = view;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Ingredient getItem(int position) {
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


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Name", textViewName.getText().toString());
                dialogComingWriteOff = new DialogComingWriteOff();
                dialogComingWriteOff.setArguments(bundle);
                dialogComingWriteOff.show(view.getFragmentManager(), "Тэг?");
            }
        });

        textViewName.setText(item.get(position).getName());
        setUnitandAmount(textViewAmount, textViewUnits, item.get(position));

        return convertView;
    }

    public void setUnitandAmount (TextView textViewAmount, TextView textViewUnit, Ingredient ingredient){
        if (ingredient.getUnit() == 1) textViewUnit.setText("грамм");
        if (ingredient.getUnit() == 2) textViewUnit.setText("мл.");
        if (ingredient.getUnit() == 3) textViewUnit.setText("шт.");

        DecimalFormat decimalFormat = new DecimalFormat("###.#");

        textViewAmount.setText(decimalFormat.format(ingredient.getAmount()));
    }

    public void closeDialog (){
        dialogComingWriteOff.dismiss();
    }
}