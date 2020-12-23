package com.example.projetocm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.projetocm.data.Database;
import com.example.projetocm.data.Meal;
import com.example.projetocm.fragments.QuickAccess;
import com.example.projetocm.fragments.RandomMeal;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements RandomMeal.FirstFragmentInteractionListener, QuickAccess.SecondFragmentInteractionListener {

    private DrawerLayout drawer;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new Database(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.main_activity);

        RandomMeal randomMeal = RandomMeal.newInstance(dbHelper, MainActivity.this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, randomMeal, "fragOne");
        fragmentTransaction.commit();
    }


    @Override
    public void FirstFragmentInteraction(Meal meal) {
        QuickAccess fragmentTwo = QuickAccess.newInstance(meal);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_activity , fragmentTwo, "fragTwo");
        fragmentTransaction.addToBackStack("Top");
        fragmentTransaction.commit();
    }

    public void SecondFragmentInteraction() {
        RandomMeal fragmentOne = (RandomMeal) getSupportFragmentManager().findFragmentByTag("fragOne");
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper = new Database(getApplicationContext());
    }
}
