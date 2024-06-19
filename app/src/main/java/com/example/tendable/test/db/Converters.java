package com.example.tendable.test.db;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

@ProvidedTypeConverter
public class Converters {


    @TypeConverter
    public static String fromString(List<String> values) {
        return new Gson().toJson(values);
    }

    @TypeConverter
    public static List<String> toString(String values) {
        return new Gson().fromJson(values, new TypeToken<List<String>>() {}.getType());
    }

    @TypeConverter
    public static String fromInt(List<Integer> values) {
        return new Gson().toJson(values);
    }

    @TypeConverter
    public static List<Integer> toInt(String values) {
        return new Gson().fromJson(values, new TypeToken<List<Integer>>() {}.getType());
    }
}
