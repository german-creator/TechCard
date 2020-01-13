package com.ivanilov.techcard.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;
import com.ivanilov.techcard.View.MainActivity;

public class DialogCraateIngredient extends DialogFragment {

    EditText editTextName;
    Spinner dropdown;
    EditText editTextAmount;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_create_ingredints, null);

        builder.setView(view);

        dropdown = view.findViewById(R.id.Dialog_Create_Spinner);
        editTextName = view.findViewById(R.id.Dialog_Create_Name_Ingredient);
        editTextAmount = view.findViewById(R.id.Dialog_Create_Amount);
        editTextAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        Button buttonSave = view.findViewById(R.id.Dialog_Create_Ingredient_Button_Save);
        Button buttonDel = view.findViewById(R.id.Dialog_Create_Ingredient_Button_Del);


        String[] items = new String[]{"Граммы", "Миллилитры", "Штуки"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (editTextName.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Введите названия ингридиента", Toast.LENGTH_LONG).show();
                else {
                    if (editTextAmount.getText().toString().equals(""))
                        Toast.makeText(getContext(), "Введите количество в наличии", Toast.LENGTH_LONG).show();

                    else {
                        saveIngredient();
                        IngredientsFragment ingredientsFragment = ((IngredientsFragment) getActivity().getSupportFragmentManager().getFragments().get(0));
                        ingredientsFragment.updateAdapter();
                        dismiss();
                    }
                }
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return builder.create();
    }

    public void saveIngredient() {

        IngerdientTable ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();
        Ingredient ingredient = new Ingredient(
                editTextName.getText().toString(),
                dropdown.getSelectedItemPosition() + 1,
                Double.valueOf(editTextAmount.getText().toString())
        );
        ingerdientTable.ingerdientDAO().insertAll(ingredient);

    }
}
