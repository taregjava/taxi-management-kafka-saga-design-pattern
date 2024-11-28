package com.halfacode.taxi.repository;

import com.halfacode.taxi.dto.CommonStatus;
import com.halfacode.taxi.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    @Modifying
    @Query("UPDATE Driver d SET d.driverStatus = :status WHERE d.driverId = :driverId")
    int updateDriverStatus(@Param("driverId") UUID driverId, @Param("status") CommonStatus status);
}
