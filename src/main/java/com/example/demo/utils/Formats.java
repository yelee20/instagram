package com.example.demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Formats {
    public static String formatTimeToString(LocalDateTime dateTimeToBeFormatted) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(dateTimeToBeFormatted, currentDateTime);
        long secondsDifference = duration.getSeconds();

        if (secondsDifference < 60) {
            return (int) secondsDifference / 60 + "분 전";
        } else if (secondsDifference < 60 * 60 * 24) {
            return (int) secondsDifference / (60 * 60) + "시간 전";
        } else if (secondsDifference < 60 * 60 * 24 * 31) {
            long daysDiff = ChronoUnit.DAYS.between(dateTimeToBeFormatted, currentDateTime);
            return daysDiff + "일 전";
        } else {
            if (dateTimeToBeFormatted.getYear() != currentDateTime.getYear()) {
                return dateTimeToBeFormatted.getYear() + "년 "
                        + dateTimeToBeFormatted.getMonthValue() + "월 "
                        + dateTimeToBeFormatted.getDayOfMonth() + "일";
            } else {
                return dateTimeToBeFormatted.getMonthValue() + "월 " + dateTimeToBeFormatted.getDayOfMonth() + "일";
            }
        }
    }
}
