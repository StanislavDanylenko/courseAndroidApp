package com.example.stanislav.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stanislav.myapplication.R;
import com.example.stanislav.myapplication.SpeeerApplication;
import com.example.stanislav.myapplication.activity.menu.fragment.AddFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.AllFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.CanceledFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.HistoryFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.ProfileFragment;
import com.example.stanislav.myapplication.entity.User;
import com.example.stanislav.myapplication.entity.UserAuth;
import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;
import com.example.stanislav.myapplication.retrofit.interfaze.LocationSevice;
import com.example.stanislav.myapplication.retrofit.interfaze.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User user;
    private UserAuth credentials;
    private Retrofit retrofit;
    private List<Country> countryList;

    private OrderActivity activity = this;
    private SpeeerApplication application;

    public void updateProfile(View view){
        EditText firstName = findViewById(R.id.profile_first_name);
        EditText lastName = findViewById(R.id.profile_last_name);
        EditText patronymic = findViewById(R.id.profile_patronymic);

        User updatedUser = application.getUpdatedUser();
        updatedUser.setFirstName(firstName.getText().toString());
        updatedUser.setLastName(lastName.getText().toString());
        updatedUser.setPatronymic(patronymic.getText().toString());

        UserService userService = retrofit.create(UserService.class);

        userService.updateUser(updatedUser, credentials.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    user = response.body();
                    application.setUser(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity, "error while update user", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadUser(UserAuth credentials) {
        UserCredentialsModel model = new UserCredentialsModel(credentials.getEmail(), credentials.getPassword());
        UserService userService = retrofit.create(UserService.class);

        userService.getUser(model, credentials.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                application.setUpdatedUser(user);
                application.setUser(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity, "error while get user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFullLocations(UserAuth credentials) {
        UserCredentialsModel model = new UserCredentialsModel(credentials.getEmail(), credentials.getPassword());

        LocationSevice locationSevice = retrofit.create(LocationSevice.class);
        locationSevice.getFullLocation(model).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                countryList = response.body();
                application.setLocation(countryList);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Toast.makeText(activity, "error while get fLocation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        application = (SpeeerApplication) this.getApplication();

        retrofit = application.getRetrofit();
        credentials = application.getCredentials();
        loadUser(credentials);
        loadFullLocations(credentials);


        // todo float button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Set dome action later", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllFragment()).commit();
            getSupportActionBar().setTitle("Active orders");
            navigationView.setCheckedItem(R.id.nav_orders);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragment()).commit();
            getSupportActionBar().setTitle("Add orders");
        } else if (id == R.id.nav_orders) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllFragment()).commit();
            getSupportActionBar().setTitle("Active orders");
        } else if (id == R.id.nav_history) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
            getSupportActionBar().setTitle("History");
        } else if (id == R.id.nav_canceled) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CanceledFragment()).commit();
            getSupportActionBar().setTitle("Canceled orders");
        } else if (id == R.id.nav_profile) {
            //Toast.makeText(this, "email" + credentials.getEmail(), Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            getSupportActionBar().setTitle("Profile");
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(MainActivity.ACTION);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}