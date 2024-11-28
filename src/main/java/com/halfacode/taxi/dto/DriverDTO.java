package com.halfacode.taxi.dto;

import lombok.Data;

@Data
public class DriverDTO {
    private String driverName;
    private String driverEmail;
    private String driverLocation;
    private CabDTO cabDTO;
}
