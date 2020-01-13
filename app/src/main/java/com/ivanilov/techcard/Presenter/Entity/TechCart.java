package com.ivanilov.techcard.Presenter.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

@Entity(tableName = "TechCart")
public class TechCart {

    @PrimaryKey
    @NonNull
    String name;
    @TypeConverters({IngedientsConverter.class})
    ArrayList<String> ingredients;
    @TypeConverters({PercentagesConverter.class})
    ArrayList<Double> percentages;


    public TechCart(String name, ArrayList<String> ingredients, ArrayList<Double> percentages) {

        this.name = name;
        this.ingredients = ingredients;
        this.percentages = percentages;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Double> getPercentages() {
        return percentages;
    }

    public void setPercentages(ArrayList<Double> percentages) {
        this.percentages = percentages;
    }


}
