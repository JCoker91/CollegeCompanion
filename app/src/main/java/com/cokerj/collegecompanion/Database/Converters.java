package com.cokerj.collegecompanion.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromString(Long date){
        return date == null ? null : new Date(date);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null: date.getTime();
    }
}
