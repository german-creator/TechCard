package com.ivanilov.techcard.View.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ivanilov.techcard.Model.HistoryTable;
import com.ivanilov.techcard.Presenter.Adapters.HistoryAdapter;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Dialogs.DialogHistoryDescription;
import com.ivanilov.techcard.View.MainActivity;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    ImageButton imageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        imageButton = getActivity().findViewById(R.id.toolbar_imagebutton);

        imageButton.setVisibility(View.INVISIBLE);

        ArrayList<String> list = new ArrayList<>();
        list.add("aaaaa");
        list.add("bbbbb");
        list.add("ccccc");

        HistoryTable historyTable = ((MainActivity) getActivity()).getHistoryTable();


        HistoryAdapter historyAdapter = new HistoryAdapter(this, historyTable.historyDAO().getAllHistory());

        ListView listView = getView().findViewById(R.id.history_listview);


        listView.setAdapter(historyAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}