package com.example.pooldemoapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
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

        fun getPoolWithOption(
            context: Context,
            isHistoryData: Boolean? = false
        ): LiveData<List<PollsWithOption>?>? {
            appDataBase = initializeDB(context)


            poolTableEntity =
                appDataBase!!.poolDao().getAllPoolWithOption(isHistoryData = isHistoryData)

            return poolTableEntity
        }

    }
}