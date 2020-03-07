package com.ivanilov.techcard.Presenter;


import android.content.Context;

import androidx.fragment.app.Fragment;

import com.ivanilov.techcard.Model.HistoryTable;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.Presenter.Entity.History;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.View.MainActivity;

import java.util.Calendar;
import java.util.Random;


public class ComingWriteOff {

    public void comingIngredient(Fragment view, String name, Double value, String reason, int sum) {

        IngerdientTable ingerdientTable = ((MainActivity) view.getActivity()).getDbIngerdient();

        Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(name);
        ingredient.setAmount(ingredient.getAmount() + value * sum);

        ingerdientTable.ingerdientDAO().insertAll(ingredient);

        if (reason != null) {
            HistoryTable historyTable = ((MainActivity) view.getActivity()).getHistoryTable();

            Random r = new Random();
            int id = r.nextInt(100000 + 1);

            Calendar date = Calendar.getInstance();
            long d = date.getTimeInMillis();


            History history = new History(
                    id,
                    name,
                    true,
                    value,
                    d,
                    reason);

            historyTable.historyDAO().insert(history);
        }


    }

    public void writeOffIngredient(Fragment view, String name, Double value, String reason, int sum) {

        IngerdientTable ingerdientTable = ((MainActivity) view.getActivity()).getDbIngerdient();

        Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(name);
        ingredient.setAmount(ingredient.getAmount() - value * sum);

        ingerdientTable.ingerdientDAO().insertAll(ingredient);

        if (reason != null) {
            HistoryTable historyTable = ((MainActivity) view.getActivity()).getHistoryTable();

            Random r = new Random();
            int id = r.nextInt(100000 + 1);

            Calendar date = Calendar.getInstance();
            long d = date.getTimeInMillis();

            History history = new History(
                    id,
                    name,
                    false,
                    value,
                    d,
                    reason);

            historyTable.historyDAO().insert(history);
        }

    }

    public void comingTechCart(Fragment view, String name, int sum, String reason) {

        IngerdientTable ingerdientTable = ((MainActivity) view.getActivity()).getDbIngerdient();
        TechCartTable techCartTable = ((MainActivity) view.getActivity()).getTechCartTable();

        TechCart techCart = techCartTable.techCartDAO().getTechCartByName(name);

        for (int i = 0; i < techCart.getIngredients().size(); i++) {
            Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(techCart.getIngredients().get(i));
            comingIngredient(view, ingredient.getName(), techCart.getPercentages().get(i), null, sum);

        }


        HistoryTable historyTable = ((MainActivity) view.getActivity()).getHistoryTable();

        Random r = new Random();
        int id = r.nextInt(100000 + 1);

        Calendar date = Calendar.getInstance();
        long d = date.getTimeInMillis();

        History history = new History(
                id,
                name,
                true,
                sum,
                d,
                reason);

        historyTable.historyDAO().insert(history);

    }

    public void writeOffTechCart(Fragment view, String name, int sum, String reason) {

        IngerdientTable ingerdientTable = ((MainActivity) view.getActivity()).getDbIngerdient();
        TechCartTable techCartTable = ((MainActivity) view.getActivity()).getTechCartTable();

        TechCart techCart = techCartTable.techCartDAO().getTechCartByName(name);

        for (int i = 0; i < techCart.getIngredients().size(); i++) {
            Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(techCart.getIngredients().get(i));
            writeOffIngredient(view, ingredient.getName(), techCart.getPercentages().get(i), null, sum);

        }


        HistoryTable historyTable = ((MainActivity) view.getActivity()).getHistoryTable();

        Random r = new Random();
        int id = r.nextInt(100000 + 1);

        Calendar date = Calendar.getInstance();
        long d = date.getTimeInMillis();

        History history = new History(
                id,
                name,
                false,
                sum,
                d,
                reason);

        historyTable.historyDAO().insert(history);
    }


    public void comingIngredientReceiver(String name, Double value, int sum, IngerdientTable ingerdientTable) {

        Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(name);
        ingredient.setAmount(ingredient.getAmount() + value * sum);

        ingerdientTable.ingerdientDAO().insertAll(ingredient);


    }

    public void writeOffIngredientReceiver(String name, Double value, int sum, IngerdientTable ingerdientTable) {

        Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(name);
        ingredient.setAmount(ingredient.getAmount() - value * sum);

        ingerdientTable.ingerdientDAO().insertAll(ingredient);


    }

    public void comingTechCartReceiver(Context context, String name, int sum, IngerdientTable ingerdientTable, TechCartTable techCartTable) {

        TechCart techCart = techCartTable.techCartDAO().getTechCartByName(name);

        for (int i = 0; i < techCart.getIngredients().size(); i++) {
            Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(techCart.getIngredients().get(i));
            comingIngredientReceiver(ingredient.getName(), techCart.getPercentages().get(i), sum, ingerdientTable);

        }


    }

    public void writeOffTechCartReceiver(Context context, String name, int sum, IngerdientTable ingerdientTable, TechCartTable techCartTable) {

        TechCart techCart = techCartTable.techCartDAO().getTechCartByName(name);

        for (int i = 0; i < techCart.getIngredients().size(); i++) {
            Ingredient ingredient = ingerdientTable.ingerdientDAO().getIngredientByName(techCart.getIngredients().get(i));
            writeOffIngredientReceiver(ingredient.getName(), techCart.getPercentages().get(i), sum, ingerdientTable);

        }


    }

}
