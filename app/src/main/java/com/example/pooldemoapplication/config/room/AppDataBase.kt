package com.example.pooldemoapplication.config.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pooldemoapplication.config.utils.Constant
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel

@Database(
    entities = [PollsTableModel::class, OptionTableModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun poolDao(): PollsDAO

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