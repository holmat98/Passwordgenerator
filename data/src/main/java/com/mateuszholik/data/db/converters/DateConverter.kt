package com.mateuszholik.data.db.converters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@ProvidedTypeConverter
internal class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? = value?.let {
        LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? = date?.toEpochSecond(ZoneOffset.UTC)

}