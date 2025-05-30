package com.halfacode.taxi.controller;


import com.halfacode.taxi.dto.CommonStatus;
import com.halfacode.taxi.dto.DriverDTO;
import com.halfacode.taxi.entity.Driver;
import com.halfacode.taxi.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public Optional<Driver> getDriverById(@PathVariable UUID id) {
        return driverService.getDriverById(id);
    }

    @GetMapping("/{driverId}/{status}")
    public int updateDriverStatus(@PathVariable UUID driverId, @PathVariable CommonStatus status) {
        return driverService.updateDriveStatus(driverId, status);
    }
    @PostMapping
    public String saveDriver(@RequestBody DriverDTO driverDTO) {
        return driverService.saveDriver(driverDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable UUID id) {
        driverService.deleteDriver(id);
    }
}

