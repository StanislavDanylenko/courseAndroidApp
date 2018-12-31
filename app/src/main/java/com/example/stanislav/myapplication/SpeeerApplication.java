package com.example.stanislav.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stanislav.myapplication.entity.User;
import com.example.stanislav.myapplication.entity.UserAuth;
import com.example.stanislav.myapplication.entity.enumeration.Localization;
import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.proposal.LocalProposal;
import com.example.stanislav.myapplication.entity.proposal.Report;
import com.example.stanislav.myapplication.entity.proposal.UserOrder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SpeeerApplication extends Application {

    private UserAuth credentials;
    private User user;
    private User updatedUser;
    private List<Country> location;
    private List<LocalProposal> proposals;
    private String cookies;

    private List<UserOrder> currentStatusOrderList;

    public static String BASE_URL;

    private Retrofit retrofit;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserAuth getCredentials() {
        return credentials;
    }

    public void setCredentials(UserAuth credentials) {
        this.credentials = credentials;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Country> getLocation() {
        return location;
    }

    public void setLocation(List<Country> location) {
        this.location = location;
    }


    public Retrofit getRetrofit() {
        if (retrofit == null) {
            createRetrofit(BASE_URL);
        }
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    public List<UserOrder> getCurrentStatusOrderList() {
        return currentStatusOrderList;
    }

    public void setCurrentStatusOrderList(List<UserOrder> currentStatusOrderList) {
        this.currentStatusOrderList = currentStatusOrderList;
    }

    public List<LocalProposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<LocalProposal> proposals) {
        this.proposals = proposals;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void savePreferences(String key, String value) {
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public String loadPreferences(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void createRetrofit(String url) {
        BASE_URL = url;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public String getReportString(Report report) {
        StringBuilder sb = new StringBuilder();

        String humidity = (report.getHumidity() == -9999) ? "-" : "" + report.getHumidity();
        String radiation = (report.getRadiation() == -9999) ? "-" : "" + report.getRadiation();
        String pressure = (report.getPressure() == -9999) ? "-" : "" + report.getPressure();
        String airPollution = (report.getAirPollution() == -9999) ? "-" : "" + report.getAirPollution();
        String temperature = (report.getTemperature() == -9999) ? "-" : "" + report.getTemperature();

        sb
                .append(R.string.humidity)
                .append(humidity)
                .append('\n')

                .append(R.string.rediation)
                .append(radiation)
                .append('\n')

                .append(R.string.pressure)
                .append(pressure)
                .append('\n')

                .append(R.string.air_pollution)
                .append(airPollution)
                .append('\n')

                .append(R.string.temperature)
                .append(temperature);

        return sb.toString();
    }


}
