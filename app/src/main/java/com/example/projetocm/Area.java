package com.example.projetocm;

import org.json.JSONException;
import org.json.JSONObject;

public class Area {

    String area;
    boolean included;

    Area(JSONObject json){
        try {
            this.area = json.getString("strArea");
            this.included = true;
        } catch (JSONException e){
            System.out.println(e);
        }
    }

    public void print(){
        System.out.println("Area: " + this.area);
        System.out.println("Included: " + this.included);
    }

}
