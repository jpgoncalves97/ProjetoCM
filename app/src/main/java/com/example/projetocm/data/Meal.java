package com.example.projetocm.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Meal {

    public String id, name, category, area, instructions, image, tag, youtube, source, ingredients, measurements;

    public Meal(JSONObject json){
        try {
            id = json.getString("idMeal");
            name = json.getString("strMeal");
            category = json.getString("strCategory");
            area = json.getString("strArea");
            instructions = json.getString("strInstructions");
            image = json.getString("strMealThumb");
            tag = json.getString("strTags");
            youtube = json.getString("strYoutube");
            source = json.getString("strSource");

            ArrayList<String> ingredients_list = new ArrayList<>();
            ArrayList<String> measurements_list = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                String ingredient = json.getString("strIngredient" + i);
                String measurement = json.getString("strMeasure" + i);
                if (ingredient == null || ingredient.equals(""))
                    break;
                ingredients_list.add(ingredient);
                measurements_list.add(measurement);
            }
            String[] ingredients_array = ingredients_list.toArray(new String[0]);
            String[] measurements_array = measurements_list.toArray(new String[0]);

            ingredients = join(",", ingredients_array);
            measurements = join(",", measurements_array);
        } catch (JSONException e){
            System.out.println(e);
        }
    }

    public String join(String delimiter, String[] arr){
        if (arr.length == 0)
            return "";
        if (arr.length == 1)
            return arr[0];
        String str = arr[0];
        for (int i = 1; i < arr.length-1; i++){
            str += delimiter + arr[i];
        }
        return str + delimiter + arr[arr.length-1];
    }

    public void print(){
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("category: " + category);
        System.out.println("area: " + area);
        System.out.println("instructions: " + instructions);
        System.out.println("image: " + image);
        System.out.println("tag: " + tag);
        System.out.println("youtube: " + youtube);
        System.out.println("source: " + source);
        System.out.println("ingredients: " + ingredients);
        System.out.println("measurements: " + measurements);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getSource() {
        return source;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }
}
