package com.example.pooldemoapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pooldemoapplication.config.room.AppDataBase
import com.example.pooldemoapplication.model.OptionTableEntity
import com.example.pooldemoapplication.model.PoolTableEntity
import com.example.pooldemoapplication.model.PoolWithOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PoolRepository {

    companion object {
        private var appDataBase: AppDataBase? = null

        private var poolTableEntity: LiveData<MutableList<PoolWithOption>?>?  = null

        private fun initializeDB(context: Context): AppDataBase {
            return AppDataBase.getDBClient(context)
        }


        fun insertPool(
            context: Context,
            poolTableEntity: PoolTableEntity,
        ): Long {

            var id: Long = -1
            appDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                id = appDataBase!!.poolDao().insertPool(poolTableEntity = poolTableEntity)
            }

            return id
        }

        fun insertPoolWithOption(
            context: Context,
            poolTableEntity: PoolTableEntity,
            optionTableEntity: List<OptionTableEntity>
        ) {
            appDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                val poolOption = PoolWithOption()
                poolOption.poolTableEntity = poolTableEntity
                poolOption.optionTableEntity = optionTableEntity
                appDataBase!!.poolDao().insertMain(poolOption)
            }

        }

        fun getPoolWithOption(context: Context): LiveData<MutableList<PoolWithOption>?>? {
            appDataBase = initializeDB(context)

            poolTableEntity = appDataBase!!.poolDao().getAllPoolWithOption()

            return poolTableEntity
        }
    }
}