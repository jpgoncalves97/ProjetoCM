package com.example.projetocm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class shop_frag extends Fragment {
    private shopfraglistener mListener;
    private Database dbhelper;
    private Context context;
    private Meal meal;
    private TimePicker tp;
    private DatePicker dp;

    shop_frag(Meal meal, Database dbhelper, Context context){
        this.meal = meal;
        this.dbhelper = dbhelper;
        this.context = context;
    }

    public static shop_frag newInstance(Meal meal, Database dbhelper, Context context) {
        shop_frag fragment = new shop_frag(meal, dbhelper,context);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shop_frag, container, false);
        Button donebutton = view.findViewById(R.id.donebutton);
        dp = view.findViewById(R.id.dp);
        tp = view.findViewById(R.id.tp);

        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "notification created at "+ dp.getYear() + "/" +dp.getMonth() + "/" +dp.getDayOfMonth() + ":" + tp.getHour() + ":" +tp.getMinute() , Toast.LENGTH_LONG).show();
                alarm_receiver.setAlarm(context,dp.getYear(),dp.getMonth(),dp.getDayOfMonth(),tp.getHour(),tp.getMinute(),0);
                mListener.shopfraginteraction();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof shopfraglistener) {
            // This will initialize the variable. It will return an exception if it is not
            //  implemented in the java code of the variable context (in our case the
            //  context is the MainActivity.
            mListener = (shopfraglistener) context;
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
    public interface shopfraglistener {
        void shopfraginteraction();
    }


}
