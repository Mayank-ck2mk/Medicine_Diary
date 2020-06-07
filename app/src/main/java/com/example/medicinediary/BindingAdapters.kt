package com.example.medicinediary

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinediary.database.IllnessMedicine
import com.example.medicinediary.illness.IllnessAdapter


@BindingAdapter("illnessList")
fun bindIllnessRecyclerView(recyclerView: RecyclerView, data: List<IllnessMedicine>?){
    val adapter = recyclerView.adapter as IllnessAdapter
    adapter.submitList(data)
}