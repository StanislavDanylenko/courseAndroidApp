package com.example.stanislav.myapplication.entity.proposal;

import java.math.BigDecimal;
import java.util.UUID;

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
public class UserOrder {

    private LocalProposal localProposal;

    private OperationStatus status;

    private UUID uuid;

    private Long droneId;
    private Report report;
    private Double[] targetCoordinates;
    private BigDecimal price;

}
