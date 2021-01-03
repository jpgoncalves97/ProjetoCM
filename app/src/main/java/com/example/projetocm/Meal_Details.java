package com.example.projetocm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Meal_Details extends Fragment {

    DetailFragmentListener mListener;

    Meal_Details(){}

    public static Meal_Details newInstance(Meal meal) {
        Meal_Details fragment = new Meal_Details();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.meal_details, container, false);


        return view;
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
        void DetailFragmentInteraction(int function);
    }
}
