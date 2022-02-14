package com.backbase.assignment.ui.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.backbase.assignment.ui.data.model.movie.Movie


@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}