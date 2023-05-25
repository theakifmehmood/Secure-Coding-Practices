package com.example.secureprogramming.feature.home.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.secureprogramming.databinding.ItemPasswordBinding
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel

class HomeAdaptor(private val listener: OnItemClickListener) : ListAdapter<PasswordPresentationModel, MyViewHolder>(MyItemDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(password: PasswordPresentationModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPasswordBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding, listener = listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun updatePasswordList(newList: List<PasswordPresentationModel>) {
        submitList(newList)
    }
}

class MyViewHolder(private val binding: ItemPasswordBinding,private val listener: HomeAdaptor.OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PasswordPresentationModel) {
        binding.titleTextView.text = item.title
        binding.accountTextView.text = item.userName
        binding.constrainLayout.setOnClickListener {
            listener.onItemClick(item)
        }
    }
}

class MyItemDiffCallback : DiffUtil.ItemCallback<PasswordPresentationModel>() {
    override fun areItemsTheSame(oldItem: PasswordPresentationModel, newItem: PasswordPresentationModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PasswordPresentationModel, newItem: PasswordPresentationModel): Boolean {
        return oldItem == newItem
    }
}
