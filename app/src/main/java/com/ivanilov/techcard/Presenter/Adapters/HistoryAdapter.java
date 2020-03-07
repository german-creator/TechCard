package com.ivanilov.techcard.Presenter.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ivanilov.techcard.Model.HistoryTable;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Presenter.Entity.History;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.HistoryFragment;
import com.ivanilov.techcard.View.MainActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private HistoryFragment view;
    private List<History> item;
    private IngerdientTable ingerdientTable;
    private List<Boolean> color = new ArrayList<>();


    public HistoryAdapter(HistoryFragment view, List<History> item) {
        this.view = view;
        this.item = item;
        ingerdientTable = ((MainActivity) view.getActivity()).getDbIngerdient();
        for (History history : item) {
            color.add(true);
        }


    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public History getItem(int position) {
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
            convertView = LayoutInflater.from(view.getContext()).inflate(R.layout.item_history, parent, false);
        }
        final TextView textViewName = convertView.findViewById(R.id.item_history_name);
        final TextView textViewType = convertView.findViewById(R.id.item_history_type);
        final TextView textViewDate = convertView.findViewById(R.id.item_history_date);
        final TextView textViewAmount = convertView.findViewById(R.id.item_history_amount);
        final TextView textViewUnits = convertView.findViewById(R.id.item_history_units);
        final TextView textViewReason = convertView.findViewById(R.id.item_history_reason);


        History history = item.get(position);

        textViewName.setText(history.getIngredient());
        if (history.getType()) textViewType.setText("Приход");
        else textViewType.setText("Списание");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(history.getDate());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM");

        textViewDate.setText(format.format(calendar.getTime()));

        DecimalFormat decimalFormat = new DecimalFormat("###,###.#");

        String amountText = decimalFormat.format(history.getAmount());

        textViewAmount.setText(amountText);

        try {
            int i = ingerdientTable.ingerdientDAO().getIngredientByName(history.getIngredient()).getUnit();
            if (i == 1) textViewUnits.setText("гр");
            if (i == 2) textViewUnits.setText("мл");
            if (i == 3) textViewUnits.setText("шт");
        } catch (Exception e) {
            textViewUnits.setText("шт");
            color.set(position, false);
        }

        if (!color.get(position)) {
            convertView.setBackgroundColor(view.getResources().getColor(R.color.colorPrimaryVeryLight));
        } else
            convertView.setBackgroundColor(view.getResources().getColor(R.color.background));


        textViewReason.setText(history.getReason());


        return convertView;
    }
}