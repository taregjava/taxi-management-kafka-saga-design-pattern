package com.halfacode.taxi.listener;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.halfacode.taxi.dto.CommonStatus;
import com.halfacode.taxi.entity.Cab;
import com.halfacode.taxi.events.CabEvent;
import com.halfacode.taxi.events.UpdateDriverStatusEvent;
import com.halfacode.taxi.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CabListener {
    private final CabService cabService;
   // private KafkaTemplate<String, UpdateDriverStatusEvent> updateDriverStatusEventKafkaTemplate;
    @Autowired
    public CabListener(CabService cabService){
        this.cabService = cabService;
       // this.updateDriverStatusEventKafkaTemplate = updateDriverStatusEventKafkaTemplate;
    }

    @KafkaListener(topics = "add-cab-event", groupId = "driver-group")
    public void addCabEventDetails(String event) throws Exception {
        CabEvent cabEvent = new ObjectMapper().readValue(event, CabEvent.class);
        boolean cabExists = cabService.existsByRegistrationNumberAndCabStatus(
                cabEvent.getRegistrationNumber(), CommonStatus.SUCCESS);
        if(cabExists){
            saveCabDetailsAndUpdateDriverEvent(cabEvent, CommonStatus.FAILED);
        }
        else {
            saveCabDetailsAndUpdateDriverEvent(cabEvent, CommonStatus.SUCCESS);
        }

    }

    private void saveCabDetailsAndUpdateDriverEvent(CabEvent cabEvent, CommonStatus commonStatus) {
        Cab cab = saveCabDetails(cabEvent, commonStatus);
        updateDriverEvent(cab, commonStatus);
    }

    private void updateDriverEvent(Cab cab, CommonStatus driverStatus) {
        UpdateDriverStatusEvent statusEvent = UpdateDriverStatusEvent.builder()
                .driverId(cab.getDriverId())
                .driverStatus(driverStatus)
                .build();
        //updateDriverStatusEventKafkaTemplate.send("update-driver-event", statusEvent);
    }

    private Cab saveCabDetails(CabEvent cabEvent, CommonStatus cabStatus) {
        return cabService.saveCab(Cab.builder()
                .driverId(cabEvent.getDriverId())
                .registrationNumber(cabEvent.getRegistrationNumber())
                .cabType(cabEvent.getCabType())
                .cabStatus(cabStatus)
                .build());
    }
}
