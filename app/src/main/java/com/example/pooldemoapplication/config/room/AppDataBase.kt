package com.example.pooldemoapplication.config.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pooldemoapplication.config.utils.Constant
import com.example.pooldemoapplication.model.OptionTableEntity
import com.example.pooldemoapplication.model.PoolTableEntity

@Database(
    entities = [PoolTableEntity::class, OptionTableEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun poolDao(): PoolDAO

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getDBClient(context: Context): AppDataBase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE =
                    Room.databaseBuilder(context, AppDataBase::class.java, Constant.DATABASE_NAME)
                        .fallbackToDestructiveMigration().build()

                return INSTANCE!!
            }
        }
    }
}