package com.example.projetocm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastMeals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastMeals extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView mealView;
    private Database dbhelper;
    private PastMeals.HistoryFragmentInteractionListener mListener;

    public PastMeals(Database dbhelper) {
        this.dbhelper = dbhelper;
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PastMeals newInstance(Database dbhelper) {
        PastMeals fragment = new PastMeals(dbhelper);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_past_meals, container, false);

        dbhelper.get_meals();

        mealView = (ListView) view.findViewById(R.id.meal_list);
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.meal_list_item, dbhelper.populateListView());
        mealView.setAdapter(itemsAdapter);

        mealView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id_) {
                String title = (String) parent.getAdapter().getItem(position);
                new searchMeal().execute(title);
            }
        });
        return view;
    }

    public interface HistoryFragmentInteractionListener {
        void HistoryFragmentInteraction(Meal meal);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PastMeals.HistoryFragmentInteractionListener) {
            // This will initialize the variable. It will return an exception if it is not
            //  implemented in the java code of the variable context (in our case the
            //  context is the MainActivity.
            mListener = (HistoryFragmentInteractionListener) context;
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

    class searchMeal extends AsyncTask<String, Void, Meal> {
        int liked;

        public searchMeal() {
        }


        @Override
        protected Meal doInBackground(String... args) {
            String title = args[0];
            title = title.replace(" ", "_");
            Meal[] meals = API.searchMealByName(title);
            return meals[0];
        }

        @Override
        protected void onPostExecute(Meal result) {
            //do stuff
            //how to return a value to the calling method
            mListener.HistoryFragmentInteraction(result);
        }
    }
}