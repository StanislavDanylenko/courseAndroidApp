package com.example.stanislav.myapplication.entity.proposal;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Report implements Serializable {

    private double humidity;
    private double radiation;
    private double pressure;
    private double airPollution;
    private double temperature;
    private List<byte[]> photo;
    private String description;
}
