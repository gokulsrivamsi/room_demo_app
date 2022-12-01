package com.vr.room_demo_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vr.room_demo_app.databinding.LayoutSearchViewBinding
import kotlinx.coroutines.*

class SearchAdapter(val context: Context, var dataSet: MutableList<OrganizationData>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    @OptIn(DelicateCoroutinesApi::class)
    inner class ViewHolder constructor(val binder: LayoutSearchViewBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun binding(model: OrganizationData) {
            binder.data = model

            binder.root.setOnClickListener {
                val intent = Intent(context, EditDataActivity::class.java)
                intent.putExtra("room_data", model)
                context.startActivity(intent)
            }

            binder.enableSubseccd.setOnClickListener {
                GlobalScope.launch {
                    model.has_subseccd = binder.enableSubseccd.isChecked
                    AppController.instance?.roomDbHelper?.OrganizationDao()
                        ?.updateHasSubseccd(binder.enableSubseccd.isChecked, model.room_db_id?:0)
                }
                notifyItemChanged(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutSearchViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: MutableList<OrganizationData>) {
        dataSet = filteredList
        notifyDataSetChanged()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun removeItem(position: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            AppController.instance?.roomDbHelper?.OrganizationDao()?.deleteSingleData(dataSet[position])
            dataSet.removeAt(position)
        }
        notifyItemRemoved(position)
    }

}