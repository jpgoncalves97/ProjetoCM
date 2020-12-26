package com.example.projetocm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResults extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirstFragmentInteractionListener mListener;
    private LayoutInflater objLayoutInflater;
    private Meal[] meal;

    // TODO: Rename and change types and number of parameters
    public static SearchResults newInstance(){
        SearchResults fragment = new SearchResults();
        Bundle args = new Bundle();
        //#args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.meal_search, container, false);

        ImageView image = view.findViewById(R.id.foodpic);
        ImageButton reject = view.findViewById(R.id.rejectButton);
        ImageButton accept = view.findViewById(R.id.acceptButton);

        TextView title = view.findViewById(R.id.mealname);


        new randomMeal(meal, image, title).execute();


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new randomMeal(meal, image, title).execute();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new randomMeal(meal, image, title).execute();
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


        public randomMeal(Meal[] meal, ImageView image, TextView title) {
            this.image = image;
            this.title = title;
        }

        @Override
        protected Meal[] doInBackground(String... args) {
            try {
                Meal[] meals = API.randomMeal();
                return meals;
            } catch (NullPointerException e){
                System.out.println("Cant find in API");
            }
            return null;
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
        }
    }

}