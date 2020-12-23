package com.example.projetocm.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetocm.API;
import com.example.projetocm.OnSwipeTouchListener;
import com.example.projetocm.R;
import com.example.projetocm.data.Database;
import com.example.projetocm.data.Meal;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RandomMeal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomMeal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirstFragmentInteractionListener mListener;
    private LayoutInflater objLayoutInflater;
    private Meal[] meal;

    private Database dbhelper;
    private Context context;

    public RandomMeal(Database dbhelper, Context context) {
        this.dbhelper = dbhelper;
        this.context = context;
    }


    // TODO: Rename and change types and number of parameters
    public static RandomMeal newInstance(Database dbhelper, Context context){
        RandomMeal fragment = new RandomMeal(dbhelper,context);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.random_meal, container, false);

        ImageView image = view.findViewById(R.id.foodpic);
        ImageButton reject = view.findViewById(R.id.rejectButton);
        ImageButton accept = view.findViewById(R.id.acceptButton);

        TextView title = view.findViewById(R.id.mealname);


        new randomMeal(image, title,0).execute();


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new randomMeal(image, title,-1).execute();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new randomMeal(image, title,1).execute();
            }
        });

        image.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeLeft() {
                Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                System.out.println(meal[0].name);
                mListener.FirstFragmentInteraction(meal[0]);
            }
        });


        return view;
    }

    public interface OnTaskCompleted{
        void onTaskCompleted(Meal[] from_async);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FirstFragmentInteractionListener) {
            // This will initialize the variable. It will return an exception if it is not
            //  implemented in the java code of the variable context (in our case the
            //  context is the MainActivity.
            mListener = (FirstFragmentInteractionListener) context;
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


    public interface FirstFragmentInteractionListener {
        void FirstFragmentInteraction(Meal meal);
    }

    class randomMeal extends AsyncTask<String, Void, Meal[]> {
        ImageView image;
        TextView title;
        int status;


        public randomMeal(ImageView image, TextView title, int status) {
            this.image = image;
            this.title = title;
            this.status = status;
        }

        @Override
        protected Meal[] doInBackground(String... args) {
            Meal[] meals = API.randomMeal();
            return meals;
        }

        @Override
        protected void onPostExecute(Meal[] result) {
            //do stuff
            //how to return a value to the calling method
            meal = result;
            title.setText(meal[0].name);
            new DownloadImageTask(image)
                    .execute(meal[0].image);
            System.out.println(meal[0].id);

            dbhelper.add_meal(meal[0],status);

            dbhelper.check_meal_status(meal[0].id);

            dbhelper.get_meals();
        }
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
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



