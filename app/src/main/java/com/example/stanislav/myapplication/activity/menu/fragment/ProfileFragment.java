package com.example.stanislav.myapplication.activity.menu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stanislav.myapplication.R;
import com.example.stanislav.myapplication.SpeeerApplication;
import com.example.stanislav.myapplication.entity.User;
import com.example.stanislav.myapplication.entity.enumeration.Localization;
import com.example.stanislav.myapplication.entity.location.Country;
import com.example.stanislav.myapplication.entity.location.PopulatedPoint;
import com.example.stanislav.myapplication.entity.location.Region;

import java.util.LinkedList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private SpeeerApplication application;
    private User user;
    private User updatedUser;
    private List<Country> locations;

    private int countryId;
    private int regionId;
    private int pointId;

    private String[] locales = new String[] {"ENGLISH", "УКРАЇНСЬКА"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        application = (SpeeerApplication) getActivity().getApplication();
        user = application.getUser();
        updatedUser = application.getUpdatedUser();
        locations = application.getLocation();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Spinner dropdownL10n = (Spinner) view.findViewById(R.id.profile_localization);

        final Spinner dropdownCountry = (Spinner) view.findViewById(R.id.profile_country);
        final Spinner dropdownRegion = (Spinner) view.findViewById(R.id.profile_region);
        final Spinner dropdownPoint = (Spinner) view.findViewById(R.id.profile_point);

        EditText email = view.findViewById(R.id.profile_email);
        EditText firstName = view.findViewById(R.id.profile_first_name);
        EditText lastName = view.findViewById(R.id.profile_last_name);
        EditText patronymic = view.findViewById(R.id.profile_patronymic);

        email.setText(user.getEmail());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        patronymic.setText(user.getPatronymic());
////////////
        String[] items = locales;
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdownL10n.setAdapter(adapter);

        dropdownL10n.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    updatedUser.setLocalization(Localization.ENGLISH);
                } else {
                    updatedUser.setLocalization(Localization.UKRAINIAN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (user.getLocalization() == Localization.ENGLISH) {
            dropdownL10n.setSelection(0);
        } else {
            dropdownL10n.setSelection(1);
        }
/////////////

        processLocation();

        final ArrayAdapter<Country> adapterCountry = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, locations);
        dropdownCountry.setAdapter(adapterCountry);

        final ArrayAdapter<Region> adapterRegion = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, new LinkedList<Region>());
        dropdownRegion.setAdapter(adapterRegion);

        final ArrayAdapter<PopulatedPoint> adapterPoint = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, new LinkedList<PopulatedPoint>());
        dropdownPoint.setAdapter(adapterPoint);


        dropdownCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Country country = (Country) dropdownCountry.getSelectedItem();
                adapterRegion.clear();
                adapterRegion.addAll(country.getRegions());
                dropdownRegion.setSelection(regionId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        dropdownRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Region region = (Region) dropdownRegion.getSelectedItem();
                adapterPoint.clear();
                adapterPoint.addAll(region.getPopulatedPoints());
                updatedUser.setDefaultPopulatedPoint(region.getPopulatedPoints().get(0).getId());
                dropdownPoint.setSelection(pointId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        dropdownPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PopulatedPoint point = (PopulatedPoint) dropdownPoint.getSelectedItem();
                updatedUser.setDefaultPopulatedPoint(point.getId());
                countryId = 0;
                regionId = 0;
                pointId = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        dropdownCountry.setSelection(countryId);




        return view;
    }

    private void processLocation() {
        int countryPos = -1;
        int regionPos;
        int pointPos;

        for (Country country : locations) {
            countryPos++;
            regionPos = -1;
            for (Region region : country.getRegions()) {
                regionPos++;
                pointPos = -1;
                for (PopulatedPoint point : region.getPopulatedPoints()) {
                    pointPos++;
                    if (point.getId() == user.getDefaultPopulatedPoint()) {
                        countryId = countryPos;
                        regionId = regionPos;
                        pointId = pointPos;
                    }
                }
            }
        }
    }

}
