package com.example.android.roomwordssample

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class RoomConverters {
//for date and time convertions
@TypeConverter
fun calendarToDateStamp(calendar: Calendar): Long = calendar.timeInMillis

@TypeConverter
fun dateStampToCalendar(value: Long): Calendar =
    Calendar.getInstance().apply { timeInMillis = value }

//list of cutome object in your database
@TypeConverter
fun saveAddressList(listOfString: ListWordTag): String? {
    return Gson().toJson(listOfString)
}

@TypeConverter
fun getAddressList(listOfString: String?): ListWordTag {
    return Gson().fromJson(
        listOfString,
        object : TypeToken<ListWordTag>() {}.type
    )
}

/*  for converting List<Double?>?  you can do same with other data type*/
@TypeConverter
fun saveDoubleList(listOfString: List<String>){
    Gson().toJson(listOfString)
}

@TypeConverter
fun getDoubleList(listOfString: List<String>): List<Double> {
    return Gson().fromJson(
        listOfString.toString(),
        object : TypeToken<List<Double?>?>() {}.type
    )
}
/*
// for converting the json object or String into Pojo or DTO class
@TypeConverter
fun toCurrentLocationDTO(value: String?): CurrentLocationDTO {
    return  Gson().fromJson(
        value,
        object : TypeToken<CurrentLocationDTO?>() {}.type
    )
}

@TypeConverter
fun fromCurrentLocationDTO(categories: CurrentLocationDTO?): String {
    return Gson().toJson(categories)

}*/

}