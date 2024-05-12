package com.conference.booking.config;

import com.conference.booking.model.Slot;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Map;
@Data
@Configuration
@ConfigurationProperties(prefix = "booking")
public class BookingConfig {
    int quarterlyDuration;
    int halfHourlyDuration;
    int hourlyDuration;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime minimumStartTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime maximumEndTime;
    private Map<String, Slot> maintenanceSlot;
}
