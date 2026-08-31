package com.accidentaldeveloper.briefly.database

import androidx.room3.ColumnTypeConverters
import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

@Database(
    entities = [NewsEntity::class],
    version = 1
)

@ConstructedBy(AppDatabaseConstructor::class)
@ColumnTypeConverters(value = [InstantTypeConvertor::class])

abstract class AppDatabase(): RoomDatabase() {

    abstract fun newsDao(): NewsDao

}

expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {

    // Each platform provides its own logic here to actually create the AppDatabase instance.
    override fun initialize(): AppDatabase
}

fun createDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    return builder.setDriver(BundledSQLiteDriver()).build()
}

val dataBaseFileName = "app_database.db"

