package com.ivanilov.techcard.Presenter.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "History")
public class History {

    @PrimaryKey
    @NonNull
    int id;
    String ingredient;
    boolean type;
    double amount;
    Long date;
    String reason;

    public History(int id, String ingredient, boolean type, double amount, Long date, String reason) {
        this.id = id;
        this.ingredient = ingredient;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
