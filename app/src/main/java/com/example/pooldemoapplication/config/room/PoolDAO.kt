package com.example.pooldemoapplication.config.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pooldemoapplication.model.OptionTableEntity
import com.example.pooldemoapplication.model.PoolTableEntity
import com.example.pooldemoapplication.model.PoolWithOption

@Dao
interface PoolDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPool(poolTableEntity: PoolTableEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoolOption(optionTableEntity: List<OptionTableEntity>): List<Long>

    suspend fun insertMain(poolWithOption: PoolWithOption) {
        val id: Long? = poolWithOption.poolTableEntity?.let { insertPool(it) }

        poolWithOption.optionTableEntity?.let { it.forEach { i -> i.poolId = id } }
        poolWithOption.optionTableEntity?.let { insertPoolOption(it) }
    }
//    @Transaction
//    @Query("SELECT * FROM pool_table")
//    fun getAllPool(poolTableEntity: PoolTableEntity): List<PoolTableEntity>


    @Transaction
    @Query("SELECT * FROM option_table WHERE id = :poolId")
    suspend fun getAllSelectedPoolOption(poolId: String): List<OptionTableEntity>

    @Transaction
    @Query("SELECT * FROM pool_table")
    fun getAllPoolWithOption(): LiveData<MutableList<PoolWithOption>?>?
}