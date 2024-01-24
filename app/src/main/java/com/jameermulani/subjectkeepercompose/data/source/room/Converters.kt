package com.jameermulani.subjectkeepercompose.data.source.room

import androidx.room.TypeConverter
import java.util.Date


class Converters {
    @TypeConverter
    fun dateToMillisLong(localDateTime: Date) = localDateTime.time

    @TypeConverter
    fun millisLongToDate(millis: Long): Date = Date(millis)
}

