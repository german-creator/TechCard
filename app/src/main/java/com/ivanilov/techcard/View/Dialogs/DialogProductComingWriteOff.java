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

import com.ivanilov.techcard.Presenter.ComingWriteOff;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;
import com.ivanilov.techcard.View.Fragments.ProductFragment;

public class DialogProductComingWriteOff extends DialogFragment {

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
        final android.widget.EditText editTextReason = view.findViewById(R.id.Dialog_Coming_Reason);
        editTextValue.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        final DialogProductComingWriteOff viewFragment = this;
        textViewBalance.setVisibility(View.INVISIBLE);
        textViewDeleteIngredient.setVisibility(View.INVISIBLE);


        final String nameIngredient = getArguments().getString("Name");

        builder.setView(view);

        final Boolean[] typeOperation = {true};

        textViewName.setText(getArguments().getString("Name"));


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (editTextValue.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Введите количество", Toast.LENGTH_LONG).show();
                else {
                    if (editTextReason.getText().toString().equals(""))
                        Toast.makeText(getContext(), "Введите причину", Toast.LENGTH_LONG).show();

                    else {

                        ComingWriteOff comingWriteOff = new ComingWriteOff();

                        if (typeOperation[0]) {
                            comingWriteOff.comingTechCart(viewFragment, nameIngredient, Integer.valueOf(editTextValue.getText().toString()), editTextReason.getText().toString());
                        } else {
                            comingWriteOff.writeOffTechCart(viewFragment, nameIngredient, Integer.valueOf(editTextValue.getText().toString()), editTextReason.getText().toString());
                        }
                        dismiss();

                        ProductFragment productFragment = ((ProductFragment) getActivity().getSupportFragmentManager().getFragments().get(0));
                        productFragment.updateAdapter();

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
