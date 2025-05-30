package com.halfacode.taxi.service;


import com.halfacode.taxi.dto.CommonStatus;
import com.halfacode.taxi.entity.Cab;
import com.halfacode.taxi.repository.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CabService {

    private final CabRepository cabRepository;

    @Autowired
    public CabService(CabRepository cabRepository) {
        this.cabRepository = cabRepository;
    }

    public List<Cab> getAllCabs() {
        return cabRepository.findAll();
    }

    public Optional<Cab> getCabById(UUID id) {
        return cabRepository.findById(id);
    }

    public Cab saveCab(Cab cab) {
        return cabRepository.save(cab);
    }

    public void deleteCab(UUID id) {
        cabRepository.deleteById(id);
    }
    public boolean existsByRegistrationNumberAndCabStatus(String registrationNumber, CommonStatus cabStatus){
        return cabRepository.existsByRegistrationNumberAndCabStatus(registrationNumber, cabStatus);
    }
}
