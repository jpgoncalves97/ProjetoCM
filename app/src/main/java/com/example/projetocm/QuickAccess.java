package com.example.projetocm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
 * Use the {@link QuickAccess#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickAccess extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String mealName = "param1";
    private static final String mealImage = "param2";
    private static final String mealWeb = "param3";
    private static final String mealVideo = "param4";
    private static final String mealArea = "param5";
    private static final String mealCat = "param6";
    private static final String mealIng = "param7";
    private static final Meal meal_access = null;

    // TODO: Rename and change types of parameters
    private String name;
    private String imageLink;
    private String webLink;
    private String videoLink;
    private String area;
    private String category;
    private String ingredients;

    private QuickAccess.SecondFragmentInteractionListener mListener;

    public QuickAccess() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QuickAccess newInstance(Meal meal) {
        QuickAccess fragment = new QuickAccess();
        Bundle args = new Bundle();
        args.putString(mealName, meal.name);
        args.putString(mealImage, meal.image);
        args.putString(mealWeb, meal.source);
        args.putString(mealVideo, meal.youtube);
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
            webLink = getArguments().getString(mealWeb);
            videoLink = getArguments().getString(mealVideo);
            area = "Origin: "+ getArguments().getString(mealArea);
            category = "Category: " + getArguments().getString(mealCat);
            ingredients = "Ingredients: " +"\n\n"+ getArguments().getString(mealIng);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.quick_access, container, false);

        ImageView image = view.findViewById(R.id.foodpic);
        ImageButton web = view.findViewById(R.id.webButton);
        ImageButton video = view.findViewById(R.id.videoButton);

        TextView title = view.findViewById(R.id.mealname);
        title.setText(name);
        TextView tv_area = view.findViewById(R.id.tv_area);
        tv_area.setText(area);
        TextView tv_cat = view.findViewById(R.id.tv_cat);
        tv_cat.setText(category);
        TextView tv_ing = view.findViewById(R.id.tv_ing);
        tv_ing.setText(ingredients);

        new DownloadImageTask(image).execute(imageLink);


        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(webLink); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(videoLink); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        image.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                mListener.SecondFragmentInteraction();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuickAccess.SecondFragmentInteractionListener) {
            // This will initialize the variable. It will return an exception if it is not
            //  implemented in the java code of the variable context (in our case the
            //  context is the MainActivity.
            mListener = (QuickAccess.SecondFragmentInteractionListener) context;
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


    public interface SecondFragmentInteractionListener {
        void SecondFragmentInteraction();
    }


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


