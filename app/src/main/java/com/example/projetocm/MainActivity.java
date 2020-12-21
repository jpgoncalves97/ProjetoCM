package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void randomMeal(View view2) {
        View view = findViewById(android.R.id.content).getRootView();
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        String cmd = spinner.getSelectedItem().toString();
        String text = ((EditText)findViewById(R.id.editText)).getText().toString();
        /* exemplos:
        name: Arrabiata
        id = 52772
        filter ingredient = chicken_breast
        filter category = vegetarian
        filter area = italian
        */
        new Task().execute(cmd, text);
    }
}

class Task extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... args) {
        try {
            switch (args[0]) {
                case "meal name":
                    Meal[] meals = API.searchMealByName(args[1]);
                    for (Meal m : meals) {
                        m.print();
                    }
                    break;
                case "meal id":
                    Meal m = API.searchMealById(args[1]);
                    m.print();
                    break;
                case "list categories":
                    Category[] categories = API.listCategories();
                    for (Category c : categories) {
                        c.print();
                    }
                    break;
                case "list area":
                    Area[] areas = API.listAreas();
                    for (Area a : areas) {
                        a.print();
                    }
                    break;
                case "list ingredients":
                    Ingredient[] ingredients = API.listIngredients();
                    for (Ingredient i : ingredients) {
                        i.print();
                    }
                    break;
                case "filter ingredient":
                    Meal[] meals2 = API.filterByIngredient(args[1]);
                    for (Meal m2 : meals2) {
                        m2.print();
                    }
                    break;
                case "filter category":
                    Meal[] meals3 = API.filterByCategory(args[1]);
                    for (Meal m3 : meals3) {
                        m3.print();
                    }
                    break;
                case "filter area":
                    Meal[] meals4 = API.filterByArea(args[1]);
                    for (Meal m4 : meals4) {
                        m4.print();
                    }
                    break;
                default:
                    System.out.println("no match");
            }
        } catch (NullPointerException e){
            System.out.println("Cant find in API");
        }
        return null;
    }
}