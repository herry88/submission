package com.dicodingsub.herryprasetyo.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicodingsub.herryprasetyo.data.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.local.entity.TvEntity


@Database(
    entities = [MovieEntity::class, TvEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TheMovieDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao

    companion object {

        @Volatile
        private var INSTANCE: TheMovieDatabase? = null

        fun getInstance(context: Context): TheMovieDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TheMovieDatabase::class.java,
                    "movie_tv.db"
                ).build()
            }
    }
}