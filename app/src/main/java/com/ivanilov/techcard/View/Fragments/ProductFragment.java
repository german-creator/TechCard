package com.ivanilov.techcard.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.Model.TestDatabase;
import com.ivanilov.techcard.Presenter.Adapters.IngredientAdapter;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Dialogs.DialogProductComingWriteOff;
import com.ivanilov.techcard.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.inventory.ProductItem;


public class ProductFragment extends Fragment {

    ImageButton imageButton;
    ListView listView;
    ArrayAdapter<String> adapter;
    TestDatabase testDatabase;
    List<ProductItem> productItems;
    List<String> productName = new ArrayList<>();
    DialogProductComingWriteOff dialogProductComingWriteOff;
    View view;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        imageButton = getActivity().findViewById(R.id.toolbar_imagebutton);
        imageButton.setVisibility(View.INVISIBLE);
        listView = getView().findViewById(R.id.ingredients_listveiew);
        testDatabase = new TestDatabase();
        view = getView();



        TechCartTable techCartTable = ((MainActivity) getActivity()).getTechCartTable();

        List<TechCart> allTechCart = techCartTable.techCartDAO().getAllTechCart();


        for (TechCart i:allTechCart){
            productName.add(i.getName());
        }

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_tech_cart, productName);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("Name", productName.get(position));
                bundle.putBoolean("Product", true);
                dialogProductComingWriteOff = new DialogProductComingWriteOff();
                dialogProductComingWriteOff.setArguments(bundle);
                dialogProductComingWriteOff.show(getFragmentManager(), "Тэг?");
            }
        });


    }

    public interface OnFragmentInteractionListener {
    }

    public void updateAdapter() {

        listView.setAdapter(adapter);

    }

}