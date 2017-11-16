package com.example.android.assignment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ConfigItems {
    @SerializedName("items")
    public ArrayList<Config> names;

    public ArrayList<Config> getPayoutCharges() {
        return names;
    }

    public void setPayoutCharges(ArrayList<Config> payoutCharges) {
        names = payoutCharges;
    }
    @SerializedName("owner")
    public ArrayList<Config> owner;

    public ArrayList<Config> getOwnerDetails() {
        return owner;
    }

    public void setOwnerDetails(ArrayList<Config> ownerDetails) {
        owner = ownerDetails;
    }
}
