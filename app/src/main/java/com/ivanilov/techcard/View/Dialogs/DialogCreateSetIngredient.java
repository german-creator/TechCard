package com.ivanilov.techcard.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;
import com.ivanilov.techcard.View.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DialogCreateSetIngredient extends DialogFragment {

    Spinner dropdown;
    EditText editTextAmount;
    TextView textViewUnits;
    Button buttonSave;
    Button buttonDel;


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_create_tech_set_ingredient, null);

        builder.setView(view);

        dropdown = view.findViewById(R.id.Dialog_Create_Set_Name);
        editTextAmount = view.findViewById(R.id.Dialog_Create_Set_Amount);
        editTextAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        textViewUnits = view.findViewById(R.id.Dialog_Create_Set_Units);
        buttonSave = view.findViewById(R.id.Dialog_Create_Set_Button_Save);
        buttonDel = view.findViewById(R.id.Dialog_Create_Set_Button_Del);


        final ArrayList<String> nameList = getArguments().getStringArrayList("AllName");
        final int position = getArguments().getInt("NumberPosition");
        final double[] amount = {getArguments().getDouble("Percentage")};
        final String nameTechCart = getArguments().getString("NameTechCart");
        final String[] unit = {getArguments().getString("Unit")};
        final List<Ingredient> ingredients = getIngredientList();
        final int[] unitInt = {0};
        final int p = getArguments().getInt("Position");
        final double[] percentageToDialog = getArguments().getDoubleArray("PercentageList");
        final ArrayList<String> nameToDialog = getArguments().getStringArrayList("NameList");


        try {
            if (unit[0].equals("гр")) unitInt[0] = 1;
            if (unit[0].equals("мл")) unitInt[0] = 2;
            if (unit[0].equals("шт")) unitInt[0] = 3;

        } catch (Exception e) {

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, nameList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(position);


        DecimalFormat decimalFormat = new DecimalFormat("###,###.#");
        editTextAmount.setText(decimalFormat.format(amount[0]));
        textViewUnits.setText(unit[0]);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newName = nameList.get(position);
                for (Ingredient i : ingredients) {
                    if (i.getName().equals(newName)) {
                        if (i.getUnit() == 1) unit[0] = "гр";
                        unitInt[0] = 1;
                        if (i.getUnit() == 2) unit[0] = "мл";
                        unitInt[0] = 2;
                        if (i.getUnit() == 3) unit[0] = "шт";
                        unitInt[0] = 3;
                    }
                }
                textViewUnits.setText(unit[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = editTextAmount.getText().toString();

                if (s.equals("0") || s.equals("0.") || s.equals("0.0")) {
                    Toast.makeText(view.getContext(), "Введите количество", Toast.LENGTH_LONG).show();
                } else {

                    ArrayList<Double> percentageResult = new ArrayList<>();

                    for (double d : percentageToDialog) {
                        percentageResult.add(d);
                    }

                    if (nameToDialog.size() <= p) {
                        nameToDialog.add(dropdown.getSelectedItem().toString());
                        percentageResult.add(Double.valueOf(editTextAmount.getText().toString()));
                    } else {
                        nameToDialog.set(p, dropdown.getSelectedItem().toString());
                        percentageResult.set(p, Double.valueOf(editTextAmount.getText().toString()));
                    }


                    FragmentManager fragmentManager = getFragmentManager();
                    DialogCreateTechCart dialogCreateTechCart = (DialogCreateTechCart) fragmentManager.findFragmentByTag("DialogCreateTechCart");

                    dialogCreateTechCart.updateAdapter(nameToDialog, percentageResult, nameTechCart);

                    dismiss();
                }


            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Double> percentageResult = new ArrayList<>();

                for (double d : percentageToDialog) {
                    percentageResult.add(d);
                }

                if (nameToDialog.size()<=p){

                }
                else {
                    nameToDialog.remove(p);
                    percentageResult.remove(p);

                }


                FragmentManager fragmentManager = getFragmentManager();
                DialogCreateTechCart dialogCreateTechCart = (DialogCreateTechCart) fragmentManager.findFragmentByTag("DialogCreateTechCart");

                dialogCreateTechCart.updateAdapter(nameToDialog, percentageResult, nameTechCart);

                dismiss();


            }
        });


        return builder.create();
    }

    public List<Ingredient> getIngredientList() {
        IngerdientTable ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();
        List<Ingredient> ingredients = ingerdientTable.ingerdientDAO().getAllIngredient();
        return ingredients;

    }

}
