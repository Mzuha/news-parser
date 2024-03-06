package com.mzuha.newsparser.util;

import java.time.LocalTime;

public enum TimeOfDay {
    MORNING(LocalTime.of(6, 0), LocalTime.of(11, 59)),
    DAY(LocalTime.of(12, 0), LocalTime.of(17, 59)),
    EVENING(LocalTime.of(18, 0), LocalTime.of(21, 59)),
    NIGHT(LocalTime.of(22, 0), LocalTime.of(23, 59)),
    NIGHT_NEXT_DAY(LocalTime.of(0, 0), LocalTime.of(5, 59));

    private final LocalTime startTime;
    private final LocalTime endTime;

    TimeOfDay(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeOfDay getTimeOfDay(LocalTime time) {
        for (TimeOfDay tod : values()) {
            if (isBetween(time, tod.startTime, tod.endTime)) {
                return tod;
            }
        }
        return null;
    }

    private static boolean isBetween(LocalTime time, LocalTime start, LocalTime end) {
        if (start.isBefore(end)) {
            return !time.isBefore(start) && !time.isAfter(end);
        } else {
            return !time.isBefore(start) || !time.isAfter(end);
        }
    }
}

