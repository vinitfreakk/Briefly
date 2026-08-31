package com.accidentaldeveloper.briefly.database

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

fun getDatabaseBuilderForAndroid(context: Context) : RoomDatabase.Builder<AppDatabase> {
    val dbFile = context.getDatabasePath(dataBaseFileName)
    return Room.databaseBuilder<AppDatabase>(context=context,name=dbFile.absolutePath)
}