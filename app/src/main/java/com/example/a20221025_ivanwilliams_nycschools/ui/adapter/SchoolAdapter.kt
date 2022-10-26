package com.example.a20221025_ivanwilliams_nycschools.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a20221025_ivanwilliams_nycschools.R
import com.example.a20221025_ivanwilliams_nycschools.data.model.NYCSchoolResponse

class SchoolAdapter(
    private val dataSet: MutableList<NYCSchoolResponse> = mutableListOf(),
    private val clickEvent: (NYCSchoolResponse) -> Unit
) : RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    fun updateSchools(data: List<NYCSchoolResponse>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.school_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(dataSet[position], clickEvent)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class SchoolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val schoolName: TextView

        fun bind(dataItem: NYCSchoolResponse, clickEvent: (NYCSchoolResponse) -> Unit) {
            schoolName.text = dataItem.school_name

            itemView.setOnClickListener {
                clickEvent(dataItem)
            }
        }

        init {
            schoolName = itemView.findViewById(R.id.school_item_school_name)
        }
    }
}