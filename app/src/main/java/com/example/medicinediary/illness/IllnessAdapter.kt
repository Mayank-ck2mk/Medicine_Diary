package com.example.medicinediary.illness

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinediary.R
import com.example.medicinediary.database.IllnessMedicine
import kotlinx.android.synthetic.main.illness_item.view.*

class IllnessAdapter() : RecyclerView.Adapter<IllnessAdapter.IllnessViewHolder>(){

    var data = ArrayList<IllnessMedicine>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IllnessViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.illness_item,parent,false)
        return IllnessViewHolder(view)
    }

    override fun onBindViewHolder(holder: IllnessViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = data.size

    class IllnessViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val illnessIcon : ImageView = itemView.illness_item_img
        val illnessText : TextView = itemView.illness_item_txt

        fun bind(currentItem: IllnessMedicine) {
            val res = itemView.context.resources
            illnessText.text = currentItem.illness
            illnessIcon.setImageResource(R.drawable.ic_baseline_local_hospital_24)

//            illnessIcon.setImageResource(when (currentItem.illnessName) {
//                0 -> R.drawable.ic_sleep_0
//                1 -> R.drawable.ic_sleep_1
//                2 -> R.drawable.ic_sleep_2
//                3 -> R.drawable.ic_sleep_3
//                4 -> R.drawable.ic_sleep_4
//                5 -> R.drawable.ic_sleep_5
//                else -> R.drawable.ic_sleep_active
//            })
        }
    }


    fun update(x : List<IllnessMedicine>){
        data.clear()
       for(t in x){
           data.add(t)
       }
        Log.i("tagg","Updated")
    }
}