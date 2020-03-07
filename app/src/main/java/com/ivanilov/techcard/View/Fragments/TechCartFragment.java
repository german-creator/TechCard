package com.ivanilov.techcard.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TestDatabase;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Dialogs.DialogCreateTechCart;
import com.ivanilov.techcard.View.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.evotor.framework.inventory.ProductItem;


public class TechCartFragment extends Fragment {

    ImageButton imageButton;
    ArrayAdapter<String> adapter;
    ArrayList<String> nameTechCartList;
    GridView gridView;
    DialogCreateTechCart dialogCreateTechCart;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tech_cart, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        imageButton = getActivity().findViewById(R.id.toolbar_imagebutton);

        imageButton.setVisibility(View.VISIBLE);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IngerdientTable ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();

                List<Ingredient> ingredients = ingerdientTable.ingerdientDAO().getAllIngredient();
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.nav_default_enter_anim);
                imageButton.startAnimation(animation);

                List<String> duplicate = testDuplicate();
                if (duplicate.size()!=0) {
                    Toast.makeText(getContext(), "Для работы приложения, необходимо изменить названия ваших товаров, они не должны повторяться!"+ duplicate.toString(), Toast.LENGTH_LONG).show();
                }else {
                    if (ingredients.isEmpty()) {
                        Toast.makeText(getContext(), "Сначала добавьте продукты", Toast.LENGTH_LONG).show();
                    } else {
                        dialogCreateTechCart = new DialogCreateTechCart();
                        dialogCreateTechCart.show(getFragmentManager(), "DialogCreateTechCart");
                    }
                }
            }
        });


        nameTechCartList = getAllTechCartName();

        gridView = getView().findViewById(R.id.techcart_gridview);

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_tech_cart2, nameTechCartList);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IngerdientTable ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();

                List<Ingredient> ingredients = ingerdientTable.ingerdientDAO().getAllIngredient();

                if (ingredients.isEmpty()) {
                    Toast.makeText(getContext(), "Сначала добавьте продукты", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("nameTechCart", nameTechCartList.get(position));
                    dialogCreateTechCart = new DialogCreateTechCart();
                    dialogCreateTechCart.setArguments(bundle);
                    dialogCreateTechCart.show(getFragmentManager(), "DialogCreateTechCart");
                }

            }
        });
        gridView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
    }

    public ArrayList<String> getAllTechCartName() {

        TechCartTable techCartTable = ((MainActivity) getActivity()).getTechCartTable();

        List<TechCart> allTechCart = techCartTable.techCartDAO().getAllTechCart();

        ArrayList<String> nameTechCart = new ArrayList<>();

        for (TechCart i : allTechCart) {
            nameTechCart.add(i.getName());
        }

        return nameTechCart;

    }

    public void updateAdapter() {

        nameTechCartList = getAllTechCartName();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_tech_cart, nameTechCartList);
        gridView.setAdapter(adapter);

    }

    public void deleteTechCart(TechCart techCart) {
        dialogCreateTechCart.deleteTechCart(techCart);
        onResume();
    }

    public List<String> testDuplicate (){
        TestDatabase testDatabase = new TestDatabase();
        List<ProductItem> productItems = testDatabase.getAllProduct(getContext());

        List<String> result = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (ProductItem i : productItems) {
            names.add(i.getName());
        }

        Collections.sort(names);
        for (int i=1; i<names.size(); i++){
            if (names.get(i).equals(names.get(i-1))){
                result.add(names.get(i));
            }
        }

        Collections.sort(result);

        return result;

    }


}
