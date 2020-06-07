package com.example.medicinediary.illness

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinediary.R
import com.example.medicinediary.database.IllnessMedicine
import com.example.medicinediary.databinding.IllnessItemBinding
import kotlinx.android.synthetic.main.illness_item.view.*

class IllnessAdapter(private val clickListener: IllnessClickListener) : ListAdapter<IllnessMedicine, IllnessAdapter.IllnessViewHolder>(DiffCallback) {


    companion object DiffCallback : DiffUtil.ItemCallback<IllnessMedicine>(){
        override fun areItemsTheSame(oldItem: IllnessMedicine, newItem: IllnessMedicine): Boolean {
           return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IllnessMedicine,
            newItem: IllnessMedicine
        ): Boolean {
            return oldItem == newItem
        }

    }


    class IllnessViewHolder(private val binding: IllnessItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IllnessMedicine?, clickListener: IllnessClickListener) {
            binding.clickListener = clickListener
            binding.illnessMedicine = item
            binding.executePendingBindings()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IllnessViewHolder {
        return IllnessViewHolder(IllnessItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: IllnessViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

}

class IllnessClickListener(val clickListener: (currentIllness: IllnessMedicine) -> (Unit)){
    fun onClick(currentIllness: IllnessMedicine) = clickListener(currentIllness)
}