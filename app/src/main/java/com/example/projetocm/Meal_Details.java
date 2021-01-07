package com.example.projetocm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;

public class Meal_Details extends Fragment {

    private static final String mealName = "param1";
    private static final String mealImage = "param2";
    private static final String mealMeas = "param3";
    private static final String mealInst = "param4";
    private static final String mealArea = "param5";
    private static final String mealCat = "param6";
    private static final String mealIng = "param7";
    DetailFragmentListener mListener;

    private String name;
    private String imageLink;
    private String instructions;
    private String measurements;
    private String area;
    private String category;
    private String ingredients;

    Meal_Details(){}

    public static Meal_Details newInstance(Meal meal) {
        Meal_Details fragment = new Meal_Details();
        Bundle args = new Bundle();
        args.putString(mealName, meal.name);
        args.putString(mealImage, meal.image);
        args.putString(mealMeas, meal.measurements);
        args.putString(mealInst, meal.instructions);
        args.putString(mealArea, meal.area);
        args.putString(mealCat, meal.category);
        args.putString(mealIng, meal.ingredients);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(mealName);
            imageLink = getArguments().getString(mealImage);
            instructions = getArguments().getString(mealInst);
            measurements = getArguments().getString(mealMeas);
            area = "Origin: "+ getArguments().getString(mealArea);
            category = "Category: " + getArguments().getString(mealCat);
            ingredients = getArguments().getString(mealIng);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.meal_details, container, false);

        String ingMeas = combine(ingredients, measurements);

        ImageView image = view.findViewById(R.id.foodpic);
        TextView title = view.findViewById(R.id.mealname);
        title.setText(name);
        TextView tv_ing = view.findViewById(R.id.ingredients_text);
        tv_ing.setText(ingMeas);
        TextView tv_prep = view.findViewById(R.id.preparation_text);
        tv_prep.setText(instructions);
        new DownloadImage(image).execute(imageLink);

        ImageButton back = view.findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ImageButton shop = view.findViewById(R.id.shopping_button);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.DetailFragmentInteraction();
            }
        });


        return view;
    }

    public static String combine(String ingredients, String measurements) {
        String i[] = ingredients.split(",");
        String m[] = measurements.split(",");
        List<String> ing = new ArrayList<String>();
        ing = Arrays.asList(i);
        List<String> meas = new ArrayList<String>();
        meas = Arrays.asList(m);
        int c1 = 0, c2 = 0;
        ArrayList<String> res = new ArrayList<String>();

        while(c1 < ing.size() || c2 < meas.size()) {
            if(c1 < ing.size())
                res.add((ing.get(c1++)));
            if(c2 < meas.size())
                res.add((meas.get(c2++)));
            res.add("\n");
        }
        String ing_meas = res.toString();
        ing_meas
                = ing_meas.replace("[", "")
                .replace("]", "")
                .replace(",", "");
        System.out.println(ing_meas);
        return ing_meas;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Meal_Details.DetailFragmentListener) {
            // This will initialize the variable. It will return an exception if it is not
            //  implemented in the java code of the variable context (in our case the
            //  context is the MainActivity.
            mListener = (Meal_Details.DetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DetailFragmentListener {
        void DetailFragmentInteraction();
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
