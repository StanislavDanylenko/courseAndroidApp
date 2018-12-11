package com.example.stanislav.myapplication.entity.location;

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
public class PopulatedPoint {

    private Long id;

    @NonNull
    private String name;

    private Region region;


    @Override
    public String toString() {
        return "PopulatedPoint{" +
                "name='" + name + '\'' +
                ", region=" + region.getName() +
                '}';
    }

}
