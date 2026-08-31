package com.accidentaldeveloper.briefly.database

import androidx.room3.ColumnTypeConverter
import kotlin.time.Instant

class InstantTypeConvertor {

    @ColumnTypeConverter
    fun fromInstant(value: Instant) = value.toString()

    @ColumnTypeConverter
    fun toInstant(value: String): Instant = Instant.parse(value)


}

