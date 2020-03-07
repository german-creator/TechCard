package com.ivanilov.techcard.Presenter.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Dialogs.DialogConfirm;
import com.ivanilov.techcard.View.Dialogs.DialogCreateSetIngredient;
import com.ivanilov.techcard.View.Dialogs.DialogCreateTechCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CreateTechCartAdapter extends BaseAdapter {

    private DialogCreateTechCart view;
    private View footerView;
    private List<Ingredient> ingredients;
    private ArrayList<String> ingredientsAllName = new ArrayList<>();
    private List<Integer> positionList = new ArrayList<>();
    private ArrayList<String> itemName;
    private List<String> itemUnit = new ArrayList<>();
    private ArrayList<Double> percentage;
    private String nameTechCart;


    public CreateTechCartAdapter(DialogCreateTechCart view, View footerView, List<Ingredient> ingredients, ArrayList<String> itemName, ArrayList<Double> percentage, String nameTechCart) {
        this.view = view;
        this.footerView = footerView;
        this.ingredients = ingredients;

        for (Ingredient i : ingredients) {
            ingredientsAllName.add(i.getName());
        }

        if (itemName == null) {
            this.itemName = new ArrayList<>();
            this.itemName.add(ingredientsAllName.get(0));
            this.percentage = new ArrayList<>();
            this.percentage.add(0.0);
            this.itemUnit.add("");
        } else {
            this.itemName = itemName;
            this.percentage = percentage;
        }

        for (int i = 0; i < this.itemName.size(); i++) {
            for (int j = 0; j < ingredients.size(); j++) {
                if (this.itemName.get(i).equals(ingredients.get(j).getName())) {
                    positionList.add(j);
                    if (ingredients.get(j).getUnit() == 1) itemUnit.add("гр");
                    if (ingredients.get(j).getUnit() == 2) itemUnit.add("мл");
                    if (ingredients.get(j).getUnit() == 3) itemUnit.add("шт");

                }
            }

        }

        this.nameTechCart  = nameTechCart;

    }

    @Override
    public int getCount() {
        return itemName.size();
    }

    @Override
    public Object getItem(int position) {
        return itemName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean cheсkItemValue() {
        for (Double i : percentage) {
            if (i == 0) return false;
        }

        return true;
    }

    public boolean cheсkItemCopy() {

        HashSet<Integer> a = new HashSet<>(positionList);

        if (a.size() < positionList.size()) return false;

        return true;
    }

    public TechCart createTechCartNoName() {
        TechCart techCart = new TechCart(
                "",
                itemName,
                percentage);
        return techCart;
    }


    @Override

    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View viewitem;
        if (convertView == null) {
            viewitem = LayoutInflater.from(view.getContext()).inflate(R.layout.item_create_tech_cart, parent, false);
        } else {
            viewitem = convertView;
        }

        final TextView textViewName = viewitem.findViewById(R.id.item_create_TechCart_Name);
        final TextView textViewUnit = viewitem.findViewById(R.id.item_create_TechCart_TextView);
        final EditText editTextPercentage = viewitem.findViewById(R.id.item_create_TechCart_EditText);


        textViewUnit.setText(itemUnit.get(position));

        Double d = percentage.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###.#");
        editTextPercentage.setText(decimalFormat.format(d));
        textViewName.setText(ingredientsAllName.get(positionList.get(position)));

        editTextPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCreateSet(position);
            }
        });


        viewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCreateSet(position);
            }
        });


        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogCreateNewSet();
            }
        });

        return viewitem;
    }

    public void openDialogCreateSet (int position){

        ArrayList<String> nameToDialog = new ArrayList<>();
        for (int i=0; i<ingredientsAllName.size(); i++){
            if (positionList.contains(i)) continue;
            nameToDialog.add(ingredientsAllName.get(i));

        }

        nameToDialog.add(itemName.get(position));

        double[] percentageToDialog = new double[percentage.size()];
        for (int i=0; i<percentage.size(); i++){
            percentageToDialog[i] = percentage.get(i);
        }

        DialogCreateSetIngredient dialogCreateSetIngredient = new DialogCreateSetIngredient();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("AllName", nameToDialog);
        bundle.putStringArrayList("NameList", itemName);
        bundle.putDoubleArray("PercentageList", percentageToDialog);
        bundle.putInt("NumberPosition", nameToDialog.size()-1);
        bundle.putInt("Position", position);
        bundle.putDouble("Percentage", percentage.get(position));
        bundle.putString("Unit", itemUnit.get(position));
        bundle.putString("NameTechCart", nameTechCart);

        dialogCreateSetIngredient.setArguments(bundle);
        dialogCreateSetIngredient.show(view.getFragmentManager(), "Тэг?");

    }

    public void openDialogCreateNewSet (){

        if (ingredientsAllName.size()==positionList.size()) return;

        ArrayList<String> nameToDialog = new ArrayList<>();
        for (int i=0; i<ingredientsAllName.size(); i++){
            if (positionList.contains(i)) continue;
            nameToDialog.add(ingredientsAllName.get(i));

        }


        double[] percentageToDialog = new double[percentage.size()];
        for (int i=0; i<percentage.size(); i++){
            percentageToDialog[i] = percentage.get(i);
        }

        DialogCreateSetIngredient dialogCreateSetIngredient = new DialogCreateSetIngredient();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("NameList", itemName);
        bundle.putDoubleArray("PercentageList", percentageToDialog);
        bundle.putStringArrayList("AllName", nameToDialog);
        bundle.putInt("NumberPosition", 0);
        bundle.putInt("Position", positionList.size()+1);
        bundle.putDouble("Percentage", 0.0);
        bundle.putString("Unit", itemUnit.get(0));
        bundle.putString("NameTechCart", nameTechCart);

        dialogCreateSetIngredient.setArguments(bundle);
        dialogCreateSetIngredient.show(view.getFragmentManager(), "Тэг?");

    }




}

