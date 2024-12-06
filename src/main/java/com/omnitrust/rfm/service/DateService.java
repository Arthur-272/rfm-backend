package com.omnitrust.rfm.service;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class DateService {

    // Get start and end timestamps for a custom day (e.g., 4 AM to 3:59 AM the next day)
    public Timestamp[] getShiftedDayRange(LocalDate date, ZoneId zoneId, int shiftStartHour) {
        // Start of the day (shifted)
        ZonedDateTime startOfDay = date.atTime(shiftStartHour, 0).atZone(zoneId);

        // End of the day (shifted to next day)
        ZonedDateTime endOfDay = date.plusDays(1).atTime(shiftStartHour - 1, 59, 59).atZone(zoneId);

        // Convert to UTC timestamps
        Timestamp startUTC = Timestamp.valueOf(startOfDay.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
        Timestamp endUTC = Timestamp.valueOf(endOfDay.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

        return new Timestamp[]{startUTC, endUTC};
    }

    // Convert a timestamp to a specific time zone
    public ZonedDateTime convertToZone(Timestamp timestamp, ZoneId zoneId) {
        return timestamp.toInstant().atZone(zoneId);
    }
}
