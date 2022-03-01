package com.cokerj.collegecompanion.Database;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static LocalDate fromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate newDate = LocalDate.now();
        try {
            newDate = LocalDate.parse(date, formatter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date == null ? null : newDate;
    }

    @TypeConverter
    public static String fromDate(LocalDate date){
        return date == null ? null: date.toString();
    }
}
