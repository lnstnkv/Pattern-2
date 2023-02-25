package ru.tsu.bank.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.tsu.bank.R
import ru.tsu.bank.databinding.ItemAccountBinding

class AccountAdapter(
    private val listener: AccountAdapterListener
) : ListAdapter<Account, AccountAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Account>() {
            override fun areItemsTheSame(oldItem: Account, newItem: Account) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Account, newItem: Account) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_account, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAccountBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(accountItem: Account) = with(binding) {
            textViewNameAccount.text = accountItem.name
            textViewCount.text = accountItem.count.toString()
            textViewNumberAccount.text = accountItem.id

        }
    }

    interface AccountAdapterListener {
        fun onItemClick(item: Account)
    }
}