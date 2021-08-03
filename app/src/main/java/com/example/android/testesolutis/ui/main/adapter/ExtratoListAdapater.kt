package com.example.android.testesolutis.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.testesolutis.databinding.ListExtratoItemBinding
import com.example.android.testesolutis.model.Extrato

class ExtratoListAdapater : ListAdapter<Extrato, ExtratoViewHolder>(ExtratoCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtratoViewHolder {
        return ExtratoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExtratoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ExtratoViewHolder(private val binding: ListExtratoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Extrato) {
        binding.extrato = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ExtratoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListExtratoItemBinding.inflate(inflater, parent, false)

            return ExtratoViewHolder(binding)
        }
    }
}

class ExtratoCallback : DiffUtil.ItemCallback<Extrato>() {
    override fun areItemsTheSame(oldItem: Extrato, newItem: Extrato): Boolean {
        return oldItem.data == newItem.data
    }

    override fun areContentsTheSame(oldItem: Extrato, newItem: Extrato): Boolean {
        return oldItem == newItem
    }
}

