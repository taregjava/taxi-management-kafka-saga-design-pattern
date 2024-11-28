package com.halfacode.taxi.events;

import com.halfacode.taxi.dto.CommonStatus;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateDriverStatusEvent {
    private UUID driverId;
    private CommonStatus driverStatus;
}