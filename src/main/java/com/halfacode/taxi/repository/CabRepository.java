package com.halfacode.taxi.repository;


import com.halfacode.taxi.dto.CommonStatus;
import com.halfacode.taxi.entity.Cab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CabRepository extends JpaRepository<Cab, UUID> {
    boolean existsByRegistrationNumberAndCabStatus(String registrationNumber, CommonStatus cabStatus);
}
