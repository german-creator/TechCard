package com.ivanilov.techcard.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.ivanilov.techcard.R;

public class DialogComingWriteOff extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_coming_write_off, null);

        TextView textViewDeleteIngredient = view.findViewById(R.id.Dialog_Coming_Delete_Ingredient);
        Button buttonSave = view.findViewById(R.id.Dialog_Coming_Button_Save);
        Button buttonCancel = view.findViewById(R.id.Dialog_Coming_Button_Cancel);

        builder.setView(view);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final String nameIngredient = getArguments().getString("Name");
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

        return builder.create();
    }

}
