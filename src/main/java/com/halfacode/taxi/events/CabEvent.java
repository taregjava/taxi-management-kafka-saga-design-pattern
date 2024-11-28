package com.halfacode.taxi.events;

import com.halfacode.taxi.dto.CabTypes;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CabEvent {
    private UUID driverId;
    private CabTypes cabType;
    private String registrationNumber;
}