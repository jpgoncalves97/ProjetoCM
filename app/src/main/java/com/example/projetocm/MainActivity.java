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
import com.example.projetocm.fragments.Meal_Details;
import com.example.projetocm.fragments.QuickAccess;
import com.example.projetocm.fragments.RandomMeal;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements
        RandomMeal.FirstFragmentInteractionListener,
        QuickAccess.SecondFragmentInteractionListener,
        Meal_Details.DetailFragmentListener{

    private DrawerLayout drawer;
    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new Database(getApplicationContext());

        drawer = findViewById(R.id.main_activity);

        RandomMeal randomMeal = RandomMeal.newInstance(dbHelper, MainActivity.this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.main_activity, randomMeal, "fragOne");
        fragmentTransaction.commit();
    }


    @Override
    public void FirstFragmentInteraction(Meal meal,int status) {
        if(status == 0) {
            QuickAccess fragmentTwo = QuickAccess.newInstance(meal);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.main_activity, fragmentTwo, "fragTwo");
            fragmentTransaction.addToBackStack("Top");
            fragmentTransaction.commit();
        }else if(status == 1){
            Meal_Details fragment = Meal_Details.newInstance(meal);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_activity, fragment, "fragdetails");
            fragmentTransaction.addToBackStack("Top");
            fragmentTransaction.commit();
        }
    }

    public void SecondFragmentInteraction() {
        RandomMeal fragmentOne = (RandomMeal) getSupportFragmentManager().findFragmentByTag("fragOne");
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void DetailFragmentInteraction(int function) {

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
