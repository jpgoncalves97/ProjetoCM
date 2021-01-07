package com.example.projetocm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements
        RandomMeal.FirstFragmentInteractionListener,
        QuickAccess.SecondFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        Meal_Details.DetailFragmentListener,
        shop_frag.shopfraglistener,
        PastMeals.HistoryFragmentInteractionListener,
        SearchMeal.SearchMealFragmentInteractionListener{


    private DrawerLayout drawer;
    private NavigationView navigationView;

    private Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new Database(getApplicationContext());

        setupDrawer();

        RandomMeal randomMeal = RandomMeal.newInstance(dbHelper, MainActivity.this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, randomMeal, "fragRandom");
        fragmentTransaction.commit();
    }

    @Override
    public void FirstFragmentInteraction(Meal meal, int status) {
        if(status == 0) {
            QuickAccess quickAccess = QuickAccess.newInstance(meal);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.add(R.id.fragment_container, quickAccess, "fragQuick");
            fragmentTransaction.addToBackStack("Top");
            fragmentTransaction.commit();
        }else if(status == 1){
            Meal_Details fragment = Meal_Details.newInstance(meal);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "fragdetails");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if (status == 2){
            shop_frag fragment = shop_frag.newInstance(meal,dbHelper, MainActivity.this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "fragshop");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void setupDrawer(){
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        drawer = findViewById(R.id.main_activity);
        navigationView = findViewById(R.id.nav_view);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_random) {
            changeFragment(RandomMeal.newInstance(dbHelper, MainActivity.this), "fragRandom");
        } else if (id == R.id.nav_pref) {

        } else if (id == R.id.nav_past) {
            changeFragment(PastMeals.newInstance(dbHelper), "fragMealHist");
        } else if (id == R.id.nav_search_name) {
            changeFragment(SearchMeal.newInstance("name"), "fragSearchMeal");
        } else if (id == R.id.nav_search_cat) {
            changeFragment(SearchMeal.newInstance("category"), "fragSearchCategory");
        } else if (id == R.id.nav_search_ing) {
            changeFragment(SearchMeal.newInstance("ingredient"), "fragSearchIngredient");
        } else if (id == R.id.nav_search_zone) {
            changeFragment(SearchMeal.newInstance("area"), "fragSearchArea");
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void DetailFragmentInteraction() {
        shop_frag fragment = shop_frag.newInstance(null ,dbHelper, MainActivity.this);
        changeFragment(fragment, "fragshop");
    }

    @Override
    public void HistoryFragmentInteraction(Meal meal) {
        Meal_Details fragment = Meal_Details.newInstance(meal);
        changeFragment(fragment, "fragdetails");
    }

    public void SecondFragmentInteraction() {
        RandomMeal fragmentOne = (RandomMeal) getSupportFragmentManager().findFragmentByTag("fragRandom");
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void shopfraginteraction() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void SearchMealFragmentInteraction(Meal meal) {
        Meal_Details fragment = Meal_Details.newInstance(meal);
        changeFragment(fragment, "meal_details");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void hideKeyboard(){
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void changeFragment(Fragment fragment, String name){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, name);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
