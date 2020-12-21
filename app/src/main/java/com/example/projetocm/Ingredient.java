package com.example.projetocm;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient {

    String id;
    String ingredient;
    boolean included;

    Ingredient(JSONObject json){
        try {
            this.id = json.getString("idIngredient");
            this.ingredient = json.getString("strIngredient");
            this.included = true;
        } catch (JSONException e){
            System.out.println(e);
        }
    }

    public void print(){
        System.out.println("Ingredient: " + this.ingredient);
        System.out.println("Included: " + this.included);
    }

}
