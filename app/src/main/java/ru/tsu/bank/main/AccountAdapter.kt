package ru.tsu.bank.main

import android.annotation.SuppressLint
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
) : ListAdapter<AccountUiModel, AccountAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<AccountUiModel>() {
            override fun areItemsTheSame(oldItem: AccountUiModel, newItem: AccountUiModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AccountUiModel, newItem: AccountUiModel) = oldItem == newItem
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

        @SuppressLint("SetTextI18n")
        fun bind(accountItem: AccountUiModel) = with(binding) {
            textViewNameAccount.text = accountItem.number
            textViewNumberAccount.text = "Дебетовый счет"
            textViewCount.text = accountItem.value.toString()
                //"${accountItem.value} ${accountItem.currency}"

        }
    }

    interface AccountAdapterListener {
        fun onItemClick(item: AccountUiModel)
    }
}