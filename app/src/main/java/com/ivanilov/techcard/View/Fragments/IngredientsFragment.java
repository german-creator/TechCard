package com.ivanilov.techcard.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ivanilov.techcard.Presenter.Adapters.IngredientAdapter;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Dialogs.DialogConfirm;
import com.ivanilov.techcard.View.Dialogs.DialogCraateIngredient;
import com.ivanilov.techcard.View.MainActivity;

import java.util.ArrayList;
import java.util.List;



public class IngredientsFragment extends Fragment {

    ImageButton imageButton;
    IngerdientTable ingerdientTable;
    ListView listView;
    IngredientAdapter ingredientAdapter;
    List<TechCart> techCartList;

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
        imageButton.setVisibility(View.VISIBLE);
        listView = getView().findViewById(R.id.ingredients_listveiew);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.nav_default_enter_anim);
                imageButton.startAnimation(animation);

                DialogCraateIngredient dialogCraateIngredient = new DialogCraateIngredient();
                dialogCraateIngredient.show(getFragmentManager(), "Тэг?");

            }
        });

        ingredientAdapter = new IngredientAdapter(this, this.getAllIngredients());


        listView.setAdapter(ingredientAdapter);


    }

    public interface OnFragmentInteractionListener {
    }


    public List<Ingredient> getAllIngredients() {

        ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();

        List<Ingredient> allIngredient = ingerdientTable.ingerdientDAO().getAllIngredient();

        return allIngredient;

    }

    public void updateAdapter() {

        ingredientAdapter = new IngredientAdapter(this, this.getAllIngredients());

        listView.setAdapter(ingredientAdapter);

    }

    public void deletIngredient(Ingredient ingredient) {
        ArrayList<String> techCartListIngredient = checkIngredientFree(ingredient);
        if (techCartListIngredient.size() == 0) {
            trueDeletIngredient(ingredient);
        } else {

            DialogConfirm dialogConfirm = new DialogConfirm();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("NameTechCartToDelete", techCartListIngredient);
            bundle.putString("Type", "CheckIngredientFree");
            bundle.putString("Name", ingredient.getName());

            dialogConfirm.setArguments(bundle);
            dialogConfirm.show(getFragmentManager(), "Тэг?");

        }


    }

    public ArrayList<String> checkIngredientFree(Ingredient ingredient) {
        String nameIngredient = ingredient.getName();
        TechCartTable techCartTable = ((MainActivity) getActivity()).getTechCartTable();
        techCartList = techCartTable.techCartDAO().getAllTechCart();
        ArrayList<String> techCartListIngredient = new ArrayList<>();
        for (TechCart i : techCartList) {
            boolean flag = false;
            for (String s : i.getIngredients()) {
                if (s.equals(nameIngredient)) {
                    flag = true;
                }
            }
            if (flag) techCartListIngredient.add(i.getName());


        }
        return techCartListIngredient;

    }

    public void trueDeletIngredient(Ingredient ingredient) {
        ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();
        TechCartTable techCartTable = ((MainActivity) getActivity()).getTechCartTable();
        
        ArrayList<String> ingredients;

        for (TechCart i: techCartList){
            ingredients = i.getIngredients();
            for (String j: ingredients){
                if (j.equals(ingredient.getName())){
                    ingredients.remove(j);
                    i.setIngredients(ingredients);
                    techCartTable.techCartDAO().insertAll(i);
                    break;
                }
            }
        }

        ingerdientTable.ingerdientDAO().delete(ingredient);
        ingredientAdapter.closeDialog();


    }
}