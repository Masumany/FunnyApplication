package com.example.text

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CheckAdapter(private var dataList: List<data>) :
        RecyclerView.Adapter<CheckAdapter.CheckViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckAdapter.CheckViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(
           R.layout.check_item, parent, false
       )
        return CheckViewHolder(view)
    }
    override fun onBindViewHolder(holder: CheckAdapter.CheckViewHolder, position: Int) {
        val data = dataList[position]
        holder.titleTextView.text = data.hot_word
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CheckContentActivity::class.java)
            intent.putExtra("url", data.url)
            holder.itemView.context.startActivity(intent)
        }
    }

    class CheckViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val titleTextView: TextView= view.findViewById(R.id.desc)
    }



    override fun getItemCount(): Int {
       return dataList.size
    }

    fun updateData(newData: List<data>) {
        dataList = newData
        notifyDataSetChanged()
    }
}
