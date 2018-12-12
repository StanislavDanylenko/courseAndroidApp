package com.example.stanislav.myapplication.entity.proposal;

import com.example.stanislav.myapplication.entity.location.PopulatedPoint;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LocalProposal {

    private PopulatedPoint populatedPoint;
    private Proposal proposal;
    private BigDecimal price;
    private Boolean isActive;
}
