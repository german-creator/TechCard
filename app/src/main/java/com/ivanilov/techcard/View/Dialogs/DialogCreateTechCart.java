package com.ivanilov.techcard.View.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.ivanilov.techcard.Presenter.Adapters.CreateTechCartAdapter;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.Model.TestDatabase;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.TechCartFragment;
import com.ivanilov.techcard.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import ru.evotor.framework.inventory.ProductItem;

public class DialogCreateTechCart extends DialogFragment {

    TechCartTable techCartTable;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_create_tech_cart, null);

        builder.setView(view);
        final Spinner dropdown = view.findViewById(R.id.Dialog_Create_TechCart_Spinner);
        ListView listView = view.findViewById(R.id.Dialog_Create_TechCart_ListView);
        Button buttonSave = view.findViewById(R.id.Dialog_Create_TechCart_Button_Save);
        Button buttonDel = view.findViewById(R.id.Dialog_Create_TechCart_Button_Del);
        TextView textViewDel = view.findViewById(R.id.Dialog_Create_TechCart_Del);

        techCartTable = ((MainActivity) getActivity()).getTechCartTable();

        List<TechCart> techCartList = techCartTable.techCartDAO().getAllTechCart();
        View footerView = ((LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_plus, null, false);
        List<String> nameOfProduct = getAllProductName();


        String nameTechCart = "";
        final CreateTechCartAdapter createTechCartAdapter;
        int position = 100000000;

        try {
            nameTechCart = getArguments().getString("nameTechCart");
        } catch (Exception e) {

        }

        if (nameTechCart.equals("")) {
            createTechCartAdapter = new CreateTechCartAdapter(this, footerView, getIngredientList(), null, null);

        } else {
            int positionInTechCartList = 0;
            for (int i = 0; i < techCartList.size(); i++) {
                if (techCartList.get(i).getName().equals(nameTechCart)) positionInTechCartList = i;
            }

            position = getTechCartPositionProductItem(nameOfProduct, nameTechCart);

            createTechCartAdapter = new CreateTechCartAdapter(this, footerView, getIngredientList(), techCartList.get(positionInTechCartList).getIngredients(), techCartList.get(positionInTechCartList).getPercentages());

        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, nameOfProduct);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        if (!nameTechCart.equals("")) dropdown.setSelection(position);


        listView.setAdapter(createTechCartAdapter);
        listView.addFooterView(footerView);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.nav_default_enter_anim);
        textViewDel.startAnimation(animation);

        textViewDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogConfirm dialogConfirm = new DialogConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("Type", "Техкарта");
                bundle.putString("Name", dropdown.getSelectedItem().toString());
                dialogConfirm.setArguments(bundle);
                dialogConfirm.show(getFragmentManager(), "Тэг?");
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (createTechCartAdapter.cheсkItemValue() == false){
                    Toast.makeText(getActivity(), "Заполните все строки", Toast.LENGTH_SHORT).show();

                }
                else {
                    saveTechCart(createTechCartAdapter, techCartTable, dropdown);
                    updateFragment();
                    dismiss();
                }

            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return builder.create();
    }

    public List<Ingredient> getIngredientList() {
        IngerdientTable ingerdientTable = ((MainActivity) getActivity()).getDbIngerdient();
        List<Ingredient> ingredients = ingerdientTable.ingerdientDAO().getAllIngredient();
        return ingredients;

    }

    public List<String> getAllProductName() {
        TestDatabase testDatabase = new TestDatabase();
        List<ProductItem> productItems = testDatabase.getAllProduct(getContext());

        List<String> result = new ArrayList<>();
        for (ProductItem i : productItems) {
            result.add(i.getName());
        }

        return result;
    }

    public int getTechCartPositionProductItem(List<String> allProductname, String nameTechCart) {
        int result = 0;

        for (int i = 0; i < allProductname.size(); i++) {
            if (allProductname.get(i).equals(nameTechCart)) result = i;
        }
        return result;
    }

    public void deleteTechCart(TechCart techCart) {
        techCartTable.techCartDAO().delete(techCart);
        dismiss();
    }

    public void saveTechCart(CreateTechCartAdapter adapter, TechCartTable techCartTable, Spinner dropdown) {
        TechCart techCart = adapter.createTechCartNoName();
        techCart.setName(dropdown.getSelectedItem().toString());
        techCartTable.techCartDAO().insertAll(techCart);

    }

    public void updateFragment() {
        TechCartFragment techCartFragment = ((TechCartFragment) getActivity().getSupportFragmentManager().getFragments().get(0));
        techCartFragment.updateAdapter();

    }
}
