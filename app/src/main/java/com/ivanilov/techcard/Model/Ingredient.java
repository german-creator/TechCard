package com.ivanilov.techcard.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient")
public class Ingredient {

    @PrimaryKey
    @NonNull
    String name;

    String unit;
    Double amount;

    public Ingredient (String name, String unit, Double amount){
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    public String getName (){ return name; }
    public void setName (String name){
        this.name = name;
    }

    public String getUnit (){ return unit; }
    public void setUnit (String unit){
        this.unit = unit;
    }

    public Double getAmount  (){ return amount; }
    public void setAmount (Double amount){
        this.amount = amount;
    }


}
