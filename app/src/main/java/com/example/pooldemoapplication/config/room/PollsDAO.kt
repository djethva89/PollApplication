package com.example.pooldemoapplication.config.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel
import com.example.pooldemoapplication.config.room.entity.PollsWithOption

@Dao
interface PollsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPool(pollsTableModel: PollsTableModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOption(optionTableEntity: List<OptionTableModel>): List<Long>

    suspend fun insert(pollsWithOption: PollsWithOption) {
        val id: Long? = pollsWithOption.pollsTableModel?.let { insertPool(it) }

        pollsWithOption.optionTableEntity?.let { it.forEach { i -> i.poolId = id } }
        pollsWithOption.optionTableEntity?.let { insertOption(it) }
    }

    @Transaction
    @Query("SELECT * FROM pool_table where is_give_percentage = :isHistoryData ORDER BY create_at DESC")
    fun getAllPoolWithOption(isHistoryData: Boolean? = false): LiveData<List<PollsWithOption>?>?

}