package com.training.weatherservice.business.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class Location implements Serializable {
    private double lat;
    private double lon;
    private String city;
    private String state;
}
