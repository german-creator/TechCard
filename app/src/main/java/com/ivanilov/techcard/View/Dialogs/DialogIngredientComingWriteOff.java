package com.ivanilov.techcard.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Presenter.ComingWriteOff;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;
import com.ivanilov.techcard.View.MainActivity;

import java.text.DecimalFormat;

public class DialogIngredientComingWriteOff extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_coming_write_off, null);

        TextView textViewDeleteIngredient = view.findViewById(R.id.Dialog_Coming_Delete_Ingredient);
        TextView textViewName = view.findViewById(R.id.Dialog_Coming_Name);
        TextView textViewBalance = view.findViewById(R.id.Dialog_Coming_Units);
        Button buttonSave = view.findViewById(R.id.Dialog_Coming_Button_Save);
        Button buttonCancel = view.findViewById(R.id.Dialog_Coming_Button_Cancel);
        final Button buttonComing = view.findViewById(R.id.Dialog_Coming_Button_Coming);
        final Button buttonWriteOff = view.findViewById(R.id.Dialog_Coming_Button_Write_off);
        final EditText editTextValue = view.findViewById(R.id.Dialog_Coming_Value);
        final EditText editTextReason = view.findViewById(R.id.Dialog_Coming_Reason);
        editTextValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        final DialogIngredientComingWriteOff viewFragment = this;


        builder.setView(view);

        final Boolean[] typeOperation = {true};
        final String nameIngredient = getArguments().getString("Name");

        textViewName.setText(nameIngredient);

        IngerdientTable ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();
        final Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(nameIngredient);

        String unit = "";

        if (ingredient.getUnit() == 1) unit = "гр";
        if (ingredient.getUnit() == 2) unit = "мл";
        if (ingredient.getUnit() == 3) unit = "шт";


        DecimalFormat decimalFormat = new DecimalFormat("###,###.#");

        String amountText = decimalFormat.format(ingredient.getAmount());

        textViewBalance.setText("Остаток " + amountText + " " + unit);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (editTextValue.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Введите количество", Toast.LENGTH_LONG).show();
                else {
                    if (editTextReason.getText().toString().equals(""))
                        Toast.makeText(getContext(), "Введите причину", Toast.LENGTH_LONG).show();

                    else {
                        double a = Double.valueOf(editTextValue.getText().toString()) - ingredient.getAmount();
                        if (!typeOperation[0] && a > 0)
                            Toast.makeText(getContext(), "Списание не может превышать остаток", Toast.LENGTH_LONG).show();

                        else {

                            ComingWriteOff comingWriteOff = new ComingWriteOff();

                            if (typeOperation[0]) {
                                comingWriteOff.comingIngredient(viewFragment, nameIngredient, Double.valueOf(editTextValue.getText().toString()), editTextReason.getText().toString(), 1);
                            } else {
                                comingWriteOff.writeOffIngredient(viewFragment, nameIngredient, Double.valueOf(editTextValue.getText().toString()), editTextReason.getText().toString(), 1);
                            }
                            dismiss();

                            IngredientsFragment ingredientsFragment = ((IngredientsFragment) getActivity().getSupportFragmentManager().getFragments().get(0));
                            ingredientsFragment.updateAdapter();

                        }
                    }


                }

            }
        });

        textViewDeleteIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogConfirm dialogConfirm = new DialogConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("Name", nameIngredient);
                bundle.putString("Type", "Ингредиент");
                dialogConfirm.setArguments(bundle);
                dialogConfirm.show(getFragmentManager(), "Тэг?");

            }
        });


        buttonComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOperation[0] = true;
                buttonComing.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
                buttonWriteOff.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.background), PorterDuff.Mode.MULTIPLY);


            }
        });

        buttonWriteOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOperation[0] = false;

                buttonWriteOff.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
                buttonComing.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.background), PorterDuff.Mode.MULTIPLY);

            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return builder.create();
    }

}
