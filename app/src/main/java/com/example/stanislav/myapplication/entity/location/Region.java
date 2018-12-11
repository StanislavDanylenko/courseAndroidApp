package com.example.stanislav.myapplication.entity.location;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Region {

    private Long id;

    @NonNull
    private String name;

    private Country country;

    private List<PopulatedPoint> populatedPoints = new ArrayList<>();

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", country=" + country.getName() +
                ", populatedPoints=" + populatedPoints +
                '}';
    }

}
