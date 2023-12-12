package com.example.pooldemoapplication.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pooldemoapplication.R
import com.example.pooldemoapplication.model.PoolTableEntity
import com.example.pooldemoapplication.model.PoolWithOption

class PoolListAdapter : RecyclerView.Adapter<PoolListAdapter.MyViewHolder>() {

    private var poolList = emptyList<PoolWithOption>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pool_row,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return  poolList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val poolItem = poolList[position]
        holder.itemView.findViewById<TextView>(R.id.pool_name).text = poolItem.poolTableEntity!!.poolName
    }

    fun setPoolData(user: List<PoolWithOption>) {
        this.poolList = user
        notifyDataSetChanged()
    }
}