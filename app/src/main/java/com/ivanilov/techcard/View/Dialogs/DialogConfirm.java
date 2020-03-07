package com.ivanilov.techcard.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ivanilov.techcard.Model.IngerdientDAO;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;
import com.ivanilov.techcard.View.Fragments.TechCartFragment;
import com.ivanilov.techcard.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DialogConfirm extends DialogFragment {

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_confirm, null);

        final String name = getArguments().getString("Name");
        TextView textViewTitle = view.findViewById(R.id.Dialog_Confim_Title);
        Button buttonOk = view.findViewById(R.id.Dialog_Confirm_Button_Ok);
        Button buttonCancel = view.findViewById(R.id.Dialog_Confirm_Button_Cancel);

        if (getArguments().getString("Type").equals("Техкарта")) {
            textViewTitle.setText("Удалить техкарту " + name + "?");
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TechCartFragment techCartFragment = ((TechCartFragment) getActivity().getSupportFragmentManager().getFragments().get(0));
                    techCartFragment.deleteTechCart(new TechCart(name, null, null));

                    dismiss();
                    techCartFragment.onResume();
                }
            });

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
        if (getArguments().getString("Type").equals("Ингредиент")) {

            textViewTitle.setText("Удалить  ингридиент " + name + "?");
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IngredientsFragment ingredientsFragment = ((IngredientsFragment) getActivity().getSupportFragmentManager().getFragments().get(0));
                    ingredientsFragment.deleteIngredient(new Ingredient(name, 0, null));

                }
            });


            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        if (getArguments().getString("Type").equals("CheckIngredientFree")) {

            final ArrayList<String> nameList = getArguments().getStringArrayList("NameTechCartToDelete");
            String nameListString = "";
            for (String i : nameList) {
                nameListString = nameListString + i + ", ";
            }

            textViewTitle.setText("Удаление ингридиента приведет к его удалению из техкарт: " + nameListString + " вы действительно хотите его удалить?");

            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    IngerdientTable ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();
                    ingerdientTable.ingerdientDAO().delete(new Ingredient(name, 1, null));

                    TechCartTable techCartTable = ((MainActivity) getActivity()).getTechCartTable();
                    List<TechCart> techCartList = techCartTable.techCartDAO().getAllTechCart();

                    for (String i : nameList) {
                        for (int j = 0; j < techCartList.size(); j++) {
                            if (techCartList.get(j).getName().equals(i)) {
                                for (int a = 0; a < techCartList.get(j).getIngredients().size(); a++) {
                                    if (techCartList.get(j).getIngredients().get(a).equals(name)) {
                                        techCartList.get(j).getIngredients().remove(a);
                                        techCartList.get(j).getPercentages().remove(a);

                                    }
                                }
                            }
                        }
                    }

                    techCartTable.techCartDAO().insertList(techCartList);

                    IngredientsFragment ingredientsFragment = ((IngredientsFragment) getActivity().getSupportFragmentManager().getFragments().get(0));
                    ingredientsFragment.updateAdapter();
                    ingredientsFragment.dissmisAllDialog();

                }
            });

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        builder.setView(view);
        return builder.create();
    }

}
