package com.example.projetocm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SearchMeal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SEARCH_TYPE = "param1";

    // TODO: Rename and change types of parameters
    private String mSearchType;
    private LayoutInflater objLayoutInflater;
    private Meal[] meals;
    private ArrayList<String> searchItems = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private SearchMeal.SearchMealFragmentInteractionListener mListener;

    public static SearchMeal newInstance(String searchType){
        SearchMeal fragment = new SearchMeal();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_TYPE, searchType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (getArguments() != null) {
            mSearchType = getArguments().getString(ARG_SEARCH_TYPE);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.meal_search, container, false);

        ImageButton searchButton = view.findViewById(R.id.searchButton);
        EditText searchText = view.findViewById(R.id.searchMealText);
        ListView searchList = view.findViewById(R.id.searchList);

        arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, searchItems);
        searchList.setAdapter(arrayAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchText.getText().toString().replaceAll("[ ]+", "_");
                new Task().execute(mSearchType, text);
                ((MainActivity) getActivity()).hideKeyboard();
                searchText.clearFocus();
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.SearchMealFragmentInteraction(meals[position]);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PastMeals.HistoryFragmentInteractionListener) {
            // This will initialize the variable. It will return an exception if it is not
            //  implemented in the java code of the variable context (in our case the
            //  context is the MainActivity.
            mListener = (SearchMeal.SearchMealFragmentInteractionListener) context;
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

    public interface SearchMealFragmentInteractionListener{
        void SearchMealFragmentInteraction(Meal meal);
    }

    class Task extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... args) {

            switch (mSearchType){
                case "name":
                    meals = API.searchMealByName(args[1]);
                    break;
                case "category":
                    meals = API.filterByCategory(args[1]);
                    break;
                case "ingredient":
                    meals = API.filterByIngredient(args[1]);
                    break;
                case "area":
                    meals = API.filterByArea(args[1]);
                    break;
            }
             if (!mSearchType.equals("name") && meals != null) {
                for (int i = 0; i < meals.length; i++) {
                    meals[i] = API.searchMealById(meals[i].id);
                }
                for (Meal meal: meals){
                    meal.print();
                }
                searchItems.clear();
                for (Meal meal: meals){
                    searchItems.add(meal.name);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
