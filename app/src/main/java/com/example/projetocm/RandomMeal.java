package com.example.projetocm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

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
    private Boolean mParam1;
    private String mParam2;
    private FirstFragmentInteractionListener mListener;
    private LayoutInflater objLayoutInflater;
    private Meal[] meal;
    private ImageView image;
    private TextView title;



    // TODO: Rename and change types and number of parameters
    public static RandomMeal newInstance(){
        RandomMeal fragment = new RandomMeal();
        Bundle args = new Bundle();
        //args.putBoolean(ARG_PARAM1, fetchNew);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objLayoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (getArguments() != null) {
            mParam1 = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.random_meal, container, false);

        image = view.findViewById(R.id.foodpic);
        ImageButton reject = view.findViewById(R.id.rejectButton);
        ImageButton accept = view.findViewById(R.id.acceptButton);

        title = view.findViewById(R.id.mealname);

        if (meal != null) {
            new DownloadImageTask(image)
                    .execute(meal[0].image);
            title.setText(meal[0].name);
        }
        else{
            new randomMeal(meal, image, title).execute();
        }



        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_reject_dialog();
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
    public void show_reject_dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a reason")

                .setSingleChoiceItems(R.array.choices, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }

                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        Toast.makeText(getContext(),"selectedPosition: " + selectedPosition,Toast.LENGTH_SHORT).show();
                        if (selectedPosition == 0){
                            //Not right now - simply fetch new random meal
                            new randomMeal(meal, image, title).execute();
                        }
                        else if(selectedPosition == 1){
                            //Don't like it - fetch new random meal and modify database for displayed meal
                            new randomMeal(meal, image, title).execute();
                        }
                        else if(selectedPosition == 2){
                            //Missing ingredients - Open schedule shopping screen

                        }

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the dialog from the screen

                    }
                })

                .show();

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