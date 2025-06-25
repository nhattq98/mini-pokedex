package com.tahn.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tahn.data.local.database.dao.PokemonDao
import com.tahn.data.local.database.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false,
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        fun getInstance(context: Context): AppDatabase {
            val builder =
                Room
                    .databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DATABASE_NAME,
                    )
            return builder.build()
        }
    }
}
