package com.mateuszholik.data.db.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

@ProvidedTypeConverter
internal class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? = value?.let {
        LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? = date?.toEpochSecond(ZoneOffset.UTC)
}