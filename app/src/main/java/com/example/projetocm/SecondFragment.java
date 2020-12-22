package com.example.projetocm;

import android.content.Context;
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
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String mealName = "param1";
    private static final String mealImage = "param2";
    private static final Meal meal_access = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SecondFragment.SecondFragmentInteractionListener mListener;

    public SecondFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(Meal meal) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(mealName, meal.name);
        args.putString(mealImage, meal.image);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(mealName);
            mParam2 = getArguments().getString(mealImage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        ImageView image = view.findViewById(R.id.foodpic);
        ImageButton web = view.findViewById(R.id.webButton);
        ImageButton video = view.findViewById(R.id.videoButton);

        TextView title = view.findViewById(R.id.mealname);
        title.setText(mParam1);
        new DownloadImageTask(image).execute(mParam2);


        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        image.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                mListener.SecondFragmentInteraction();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SecondFragment.SecondFragmentInteractionListener) {
            // This will initialize the variable. It will return an exception if it is not
            //  implemented in the java code of the variable context (in our case the
            //  context is the MainActivity.
            mListener = (SecondFragment.SecondFragmentInteractionListener) context;
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


