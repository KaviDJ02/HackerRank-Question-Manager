package com.example.hackerankq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeNow {
    private String currentDateTime;

    public String DateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return now.format(formatter);
    }

    public String getCurrentDateTime() {
        currentDateTime = DateTime();
        return currentDateTime;
    }
}
