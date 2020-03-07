package com.ivanilov.techcard.Presenter.Entity;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PercentagesConverter {

    @TypeConverter
    public String fromIngredients(List<Double> percentages) {

        if (percentages.size() == 0) return null;

        String result = "";

        for (Double ingredient : percentages) {
            result = result + ingredient + (",");
        }
        result.substring(0, result.length() - 1);
        return result;
    }

    @TypeConverter
    public ArrayList<Double> toIngredients(String data) {

        if (data == null) return null;

        List<String> strings = Arrays.asList(data.split(","));
        ArrayList<Double> result = new ArrayList<>();

        for (String s : strings) {
            result.add(Double.valueOf(s));
        }

        return result;
    }
}
