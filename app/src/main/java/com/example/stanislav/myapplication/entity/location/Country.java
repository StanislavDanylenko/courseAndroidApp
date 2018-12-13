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
public class Country {

    private Long id;

    @NonNull
    private String name;

    private List<Region> regions = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
