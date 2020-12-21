package com.example.projetocm;

import org.json.JSONException;
import org.json.JSONObject;

public class Category {

    String category;
    boolean included;

    Category(JSONObject json){
        try {
            this.category = json.getString("strCategory");
            this.included = true;
        } catch (JSONException e){
            System.out.println(e);
        }
    }

    public void print(){
        System.out.println("Category: " + this.category);
        System.out.println("Included: " + this.included);
    }

}
