package com.example.stanislav.myapplication.activity;

import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.stanislav.myapplication.R;
import com.example.stanislav.myapplication.SpeeerApplication;
import com.example.stanislav.myapplication.activity.menu.fragment.AddFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.AllFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.CanceledFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.HistoryFragment;
import com.example.stanislav.myapplication.activity.menu.fragment.ProfileFragment;
import com.example.stanislav.myapplication.background.MyService;
import com.example.stanislav.myapplication.entity.User;
import com.example.stanislav.myapplication.entity.UserAuth;
import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.model.UserCredentialsModel;
import com.example.stanislav.myapplication.entity.proposal.LocalProposal;
import com.example.stanislav.myapplication.entity.proposal.OperationStatus;
import com.example.stanislav.myapplication.entity.proposal.UserOrder;
import com.example.stanislav.myapplication.retrofit.interfaze.LocationSevice;
import com.example.stanislav.myapplication.retrofit.interfaze.ProposalService;
import com.example.stanislav.myapplication.retrofit.interfaze.UserService;

import java.util.List;
import java.util.Locale;

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
    private List<LocalProposal> proposals;

    private List<UserOrder> currentStatusOrderList;

    private OrderActivity activity = this;
    private SpeeerApplication application;

    public void updateProfile(View view){
        EditText firstName = findViewById(R.id.profile_first_name);
        EditText lastName = findViewById(R.id.profile_last_name);
        EditText patronymic = findViewById(R.id.profile_patronymic);

        User updatedUser = application.getUpdatedUser();

        String firstNameValue = firstName.getText().toString();
        String lastNameValue = lastName.getText().toString();
        String patronymicValue = patronymic.getText().toString();

        if ("".equals(firstNameValue)) {
            firstName.setError(getString(R.string.incorrect_input));
            return;
        }

        if ("".equals(lastNameValue)) {
            lastName.setError(getString(R.string.incorrect_input));
            return;
        }

        if ("".equals(patronymicValue)) {
            patronymic.setError(getString(R.string.incorrect_input));
            return;
        }

        updatedUser.setFirstName(firstNameValue);
        updatedUser.setLastName(lastNameValue);
        updatedUser.setPatronymic(patronymicValue);

        UserService userService = retrofit.create(UserService.class);

        userService.updateUser(application.getCookies(), updatedUser, credentials.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()) {
                    user = response.body();
                    application.setUser(user);


                    String localization;

                    switch (user.getLocalization()) {
                        case ENGLISH:
                            localization = "en";
                            break;
                        case UKRAINIAN:
                            localization = "uk";
                            break;
                        default:
                            localization = "en";
                    }

                    Locale locale = new Locale(localization);
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getBaseContext().getResources().updateConfiguration(configuration, null);

                    Toast.makeText(activity, R.string.success_update, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, R.string.error_update_user, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity, R.string.error_update_user, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadUser(UserAuth credentials) {

        UserService userService = retrofit.create(UserService.class);

        userService.getUser(application.getCookies(), credentials.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                application.setUpdatedUser(user);
                application.setUser(user);
                TextView userName  = findViewById(R.id.userName);
                userName.setText(user.getFirstName() + " " + user.getLastName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(activity, R.string.error_while_get_user, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFullLocations(UserAuth credentials) {

        LocationSevice locationSevice = retrofit.create(LocationSevice.class);
        locationSevice.getFullLocation(application.getCookies()).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                countryList = response.body();
                application.setLocation(countryList);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Toast.makeText(activity, R.string.error_while_get_fLocation, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadStatusOrder(UserAuth credentials, OperationStatus status) {

        ProposalService proposalService = retrofit.create(ProposalService.class);
        proposalService.getStatusProposals(application.getCookies(), credentials.getId(), status.name()).enqueue(new Callback<List<UserOrder>>() {
            @Override
            public void onResponse(Call<List<UserOrder>> call, Response<List<UserOrder>> response) {
                currentStatusOrderList = response.body();
                application.setCurrentStatusOrderList(currentStatusOrderList);
            }

            @Override
            public void onFailure(Call<List<UserOrder>> call, Throwable t) {
                Toast.makeText(activity, R.string.error_while_get_c_status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProposals(UserAuth credentials, Long pointId) {

        ProposalService proposalService = retrofit.create(ProposalService.class);
        proposalService.getProposals(application.getCookies(), pointId).enqueue(new Callback<List<LocalProposal>>() {
            @Override
            public void onResponse(Call<List<LocalProposal>> call, Response<List<LocalProposal>> response) {
                proposals = response.body();
                application.setProposals(proposals);
            }

            @Override
            public void onFailure(Call<List<LocalProposal>> call, Throwable t) {
                Toast.makeText(activity, R.string.error_while_get_c_s_list, Toast.LENGTH_SHORT).show();
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

        loadStatusOrder(credentials, OperationStatus.NEW);

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


        startService(
                new Intent(OrderActivity.this, MyService.class));


        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllFragment()).commit();
            getSupportActionBar().setTitle(getString(R.string.active_orders));
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
        TextView userEmail  = findViewById(R.id.userEmail);
        userEmail.setText(credentials.getEmail());
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
            Toast.makeText(activity, "Loading...", Toast.LENGTH_SHORT).show();
            loadUser(credentials);
            loadProposals(credentials, credentials.getDefaultPopulatedPoint());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
//            Toast.makeText(activity, "error while update user", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragment()).commit();
            getSupportActionBar().setTitle(getString(R.string.add_orders));
        } else if (id == R.id.nav_orders) {
            Toast.makeText(activity, getString(R.string.loading), Toast.LENGTH_SHORT).show();
            loadStatusOrder(credentials, OperationStatus.NEW);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllFragment()).commit();
            getSupportActionBar().setTitle(getString(R.string.active_orders));
        } else if (id == R.id.nav_history) {
            Toast.makeText(activity, getString(R.string.loading), Toast.LENGTH_SHORT).show();
            loadStatusOrder(credentials, OperationStatus.FINALIZED);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
            getSupportActionBar().setTitle(getString(R.string.history));
        } else if (id == R.id.nav_canceled) {
            Toast.makeText(activity, getString(R.string.loading), Toast.LENGTH_SHORT).show();
            loadStatusOrder(credentials, OperationStatus.CANCELED);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CanceledFragment()).commit();
            getSupportActionBar().setTitle(getString(R.string.canceled_orders));
        } else if (id == R.id.nav_profile) {
            Toast.makeText(activity, getString(R.string.loading), Toast.LENGTH_SHORT).show();
            loadUser(credentials);
            loadFullLocations(credentials);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            getSupportActionBar().setTitle(getString(R.string.profile));
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(MainActivity.ACTION);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(OrderActivity.this, MyService.class));
        super.onDestroy();
    }
}
