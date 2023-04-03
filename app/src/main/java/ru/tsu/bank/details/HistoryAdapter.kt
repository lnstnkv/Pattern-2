package ru.tsu.bank.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.tsu.bank.R
import ru.tsu.bank.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<AccountHistoryUiModel, HistoryAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<AccountHistoryUiModel>() {
            override fun areItemsTheSame(oldItem: AccountHistoryUiModel, newItem: AccountHistoryUiModel) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: AccountHistoryUiModel, newItem: AccountHistoryUiModel) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemHistoryBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(historyItem: AccountHistoryUiModel) = with(binding) {
            textViewType.text = historyItem.type
            textViewCaller.text = historyItem.ownerId
            textViewDate.text=historyItem.date
           //TODO:: сделать payload

        }
    }
}
