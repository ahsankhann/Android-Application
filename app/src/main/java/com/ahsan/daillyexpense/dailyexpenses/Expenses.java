package com.ahsan.daillyexpense.dailyexpenses;

import com.google.gson.annotations.SerializedName;

public class Expenses {
    @SerializedName("_id")
    public String ID;
    @SerializedName("food")
    public String Food;
    @SerializedName("petrol")
    public String Petrol;
    @SerializedName("ciggrette")
    public String Ciggrete;
    @SerializedName("others")
    public String Others;
}
