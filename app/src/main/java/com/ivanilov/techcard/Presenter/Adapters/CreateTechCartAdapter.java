package com.ivanilov.techcard.Presenter.Adapters;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Dialogs.DialogCreateTechCart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateTechCartAdapter extends BaseAdapter {

    private DialogCreateTechCart view;
    private View footerView;
    private List<Ingredient> ingredients;
    private List<String> ingredientsAllName = new ArrayList<>();
    private List<Integer> positionList = new ArrayList<>();
    private ArrayList<String> itemName;
    private List<String> itemUnit = new ArrayList<>();
    private ArrayList<Double> percentage;


    public CreateTechCartAdapter(DialogCreateTechCart view, View footerView, List<Ingredient> ingredients, ArrayList<String> itemName, ArrayList<Double> percentage) {
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
        } else {
            this.itemName = itemName;
            this.percentage = percentage;
        }

        for (int i = 0; i < this.itemName.size(); i++) {
            for (int j = 0; j < ingredients.size(); j++) {
                if (this.itemName.get(i).equals(ingredients.get(j).getName())) {
                    positionList.add(j);
                    if (ingredients.get(j).getUnit() == 1) itemUnit.add("грамм");
                    if (ingredients.get(j).getUnit() == 2) itemUnit.add("мл.");
                    if (ingredients.get(j).getUnit() == 3) itemUnit.add("шт.");

                }
            }

        }

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

    public TechCart createTechCartNoName() {
        TechCart techCart = new TechCart(
                "",
                itemName,
                percentage);
        return techCart;
    }


    @Override

    public View getView(final int position, View convertView, final ViewGroup parent) {
        View viewitem;
        if (convertView == null) {
            viewitem = LayoutInflater.from(view.getContext()).inflate(R.layout.item_create_tech_cart, parent, false);
        } else {
            viewitem = convertView;
        }

        final Spinner spinner = viewitem.findViewById(R.id.item_create_TechCart_Spinner);
        final TextView textViewUnit = viewitem.findViewById(R.id.item_create_TechCart_TextView);
        final EditText editTextPercentage = viewitem.findViewById(R.id.item_create_TechCart_EditText);
        editTextPercentage.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        final ImageView imageViewClose = viewitem.findViewById(R.id.item_create_TechCart_Image);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, ingredientsAllName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (positionList.size() != 0) {
            spinner.setSelection(positionList.get(position));

        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {

                if (p == positionList.get(position)) {

                } else {
                    String newName = ingredientsAllName.get(p);
                    for (Ingredient i : ingredients) {

                        positionList.set(position, p);
                        if (i.getName().equals(newName)) {
                            if (i.getUnit() == 1) itemUnit.set(position, "грамм");
                            if (i.getUnit() == 2) itemUnit.set(position, "мл.");
                            if (i.getUnit() == 3) itemUnit.set(position, "шт.");
                            notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        textViewUnit.setText(itemUnit.get(position));

        Double d = percentage.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        editTextPercentage.setText(decimalFormat.format(d));


        editTextPercentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (percentage.size() > position) {
                    percentage.set(position, Double.valueOf(String.valueOf(editTextPercentage.getText())));

                }
            }
        });


        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemName.remove(position);
                itemUnit.remove(position);
                percentage.remove(position);
                positionList.remove(position);
                notifyDataSetChanged();

            }
        });

        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemName.add("");

                if (ingredients.get(0).getUnit() == 1) itemUnit.add("грамм");
                if (ingredients.get(0).getUnit() == 2) itemUnit.add("мл.");
                if (ingredients.get(0).getUnit() == 3) itemUnit.add("шт.");

                percentage.add(0.0);
                positionList.add(0);
                notifyDataSetChanged();
            }
        });

        return viewitem;
    }


}

