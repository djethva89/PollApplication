package com.example.pooldemoapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pooldemoapplication.config.room.AppDataBase
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel
import com.example.pooldemoapplication.config.room.entity.PollsWithOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PollsRepository {

    companion object {
        private var appDataBase: AppDataBase? = null

        private var poolTableEntity: LiveData<List<PollsWithOption>?>? = null

        private fun initializeDB(context: Context): AppDataBase {
            return AppDataBase.getDBClient(context)
        }


        fun insertPool(
            context: Context,
            pollsTableModel: PollsTableModel,
        ): Long {

            var id: Long = -1
            appDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                id = appDataBase!!.poolDao().insertPool(pollsTableModel = pollsTableModel)
            }

            return id
        }

        fun insertPoolWithOption(
            context: Context,
            pollsTableModel: PollsTableModel,
            optionTableEntity: List<OptionTableModel>
        ) {
            appDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                val poolOption = PollsWithOption()
                poolOption.pollsTableModel = pollsTableModel
                poolOption.optionTableEntity = optionTableEntity
                appDataBase!!.poolDao().insert(poolOption)
            }

        }

        fun getPoolWithOption(context: Context): LiveData<List<PollsWithOption>?>? {
            appDataBase = initializeDB(context)


            poolTableEntity = appDataBase!!.poolDao().getAllPoolWithOption()

            return poolTableEntity
        }
    }
}