package com.example.projetocm;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {

    private static JSONArray request(String url_str){
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(url_str);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);
            return new JSONObject(response).getJSONArray("meals");
        } catch (JSONException | IOException e){
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private static String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static Meal[] searchMealByName(String name){
        JSONArray response = request("https://www.themealdb.com/api/json/v1/1/search.php?s=" + name);
        if (response != null){
            return mealArray(response);
        }
        return null;
    }

    public static Meal searchMealById(String id){
        JSONArray response = request("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + id);
        if (response != null){
            try {
                Meal obj = new Meal(response.getJSONObject(0));
                return obj;
            } catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Category[] listCategories(){
        JSONArray response = request("https://www.themealdb.com/api/json/v1/1/list.php?c=list");
        if (response != null){
            return categoryArray(response);
        }
        return null;
    }

    public static Area[] listAreas(){
        JSONArray response = request("https://www.themealdb.com/api/json/v1/1/list.php?a=list");
        if (response != null){
            return areaArray(response);
        }
        return null;
    }

    public static Ingredient[] listIngredients(){
        JSONArray response = request("https://www.themealdb.com/api/json/v1/1/list.php?i=list");
        if (response != null){
            return ingredientArray(response);
        }
        return null;
    }

    public static Meal[] filterByIngredient(String text){
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i=";
        JSONArray response = request(url + text);
        if (response != null){
            return mealArray(response);
        }
        return null;
    }

    public static Meal[] filterByArea(String text){
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?a=";
        JSONArray response = request(url + text);
        if (response != null){
            return mealArray(response);
        }
        return null;
    }

    public static Meal[] filterByCategory(String text){
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";
        JSONArray response = request(url + text);
        if (response != null){
            return mealArray(response);
        }
        return null;
    }

    private static Meal[] mealArray(JSONArray array) {
        try {
            Meal[] meals = new Meal[array.length()];
            for (int i = 0; i < array.length(); i++) {
                meals[i] = new Meal(array.getJSONObject(i));
            }
            return meals;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static Category[] categoryArray(JSONArray array) {
        try {
            Category[] categories = new Category[array.length()];
            for (int i = 0; i < array.length(); i++) {
                categories[i] = new Category(array.getJSONObject(i));
            }
            return categories;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static Area[] areaArray(JSONArray array) {
        try {
            Area[] areas = new Area[array.length()];
            for (int i = 0; i < array.length(); i++) {
                areas[i] = new Area(array.getJSONObject(i));
            }
            return areas;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static Ingredient[] ingredientArray(JSONArray array) {
        try {
            Ingredient[] ingredients = new Ingredient[array.length()];
            for (int i = 0; i < array.length(); i++) {
                ingredients[i] = new Ingredient(array.getJSONObject(i));
            }
            return ingredients;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

}
/*
class Task extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... urls) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);
            //JSONObject json = new JSONObject(response);
            //JSONObject meal = json.getJSONArray("meals").getJSONObject(0);
            JSONObject json = new JSONObject(response).getJSONArray("meals").getJSONObject(0);
            switch (urls[1]){
                case "meal":
                    Meal obj = new Meal(json);
                    obj.print();
                    break;
                case "area":
                    Area obj2 = new Area(json);
                    obj2.print();
                    break;
                case "category":
                    Category obj3 = new Category(json);
                    obj3.print();
                    break;
                case "ingredient":
                    Ingredient obj4 = new Ingredient(json);
                    obj4.print();
                    break;
            }
            //System.out.println(json.getJSONArray("meals").getJSONObject(0).getString("strYoutube"));
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }
}
*/