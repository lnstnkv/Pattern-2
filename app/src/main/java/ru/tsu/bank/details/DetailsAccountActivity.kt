package ru.tsu.bank.details

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.amount.AmountActivity
import ru.tsu.bank.amount.PurposeOpening
import ru.tsu.bank.databinding.ActivityDetailsAccountBinding
import ru.tsu.bank.main.AccountItemDecorator
import ru.tsu.bank.transfer.TransferActivity

@AndroidEntryPoint
class DetailsAccountActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailsViewModel>()
    private val binding by lazy { ActivityDetailsAccountBinding.inflate(layoutInflater) }
    private val historyAdapter = HistoryAdapter()
    private val linearLayoutManager = LinearLayoutManager(this@DetailsAccountActivity).apply {
        reverseLayout = true
        stackFromEnd = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val accountId = getAccountId()
        viewModel.getAccount(accountId)
        viewModel.fetchHistoryData(accountId)
        binding.buttonNext.setOnClickListener {
            viewModel.deleteAccount(accountId)
        }
        binding.buttonWithdraw.setOnClickListener {
            AmountActivity.startActivity(this, accountId, PurposeOpening.WITHDRAW)
        }
        binding.buttonTopUp.setOnClickListener {
            AmountActivity.startActivity(this, accountId, PurposeOpening.TOPUP)
        }
        binding.buttonTransfer.setOnClickListener {
            TransferActivity.start(this, accountId)
        }
        initView()
    }

    override fun onResume() {
        super.onResume()
        val accountId = getAccountId()
        viewModel.getAccount(accountId)
    }

    private fun getAccountId(): String {
        return intent.getStringExtra(KEY_ACCOUNT_ID) ?: error("KEY_ACCOUNT_ID is null")
    }

    @SuppressLint("SetTextI18n")
    private fun initView() = with(binding) {
        historyRecycler.apply {
            layoutManager = linearLayoutManager
            adapter = historyAdapter
            addItemDecoration(AccountItemDecorator())
        }
        viewModel.accountsHistoryEvents.observe(this@DetailsAccountActivity) { histories ->
            historyAdapter.submitList(histories) {
                linearLayoutManager.scrollToPositionWithOffset(histories.size - 1, 20)
            }
        }
        viewModel.accountsEvents.observe(this@DetailsAccountActivity) { events ->
            if (events == DetailsEvents.AccountWasBlocked) {
                Toast.makeText(this@DetailsAccountActivity, "ВАУ!!!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    this@DetailsAccountActivity,
                    "Не удалось закрыть счет",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        viewModel.accountDetails.observe(this@DetailsAccountActivity) { account ->
            textViewNameAccount.text = account.id
            textViewCount.text = account.value.toString()
            //"${account.value} ${account.currency}"
        }
        viewModel.errorFlow.onEach { message ->
            Toast.makeText(this@DetailsAccountActivity, message, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
        viewModel.rejectedEvents.onEach {
            Toast.makeText(this@DetailsAccountActivity, "Операция отклонена", Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val KEY_ACCOUNT_ID = "account_id"

        fun startActivity(
            context: Context,
            accountId: String
        ) {
            val intent = Intent(context, DetailsAccountActivity::class.java).apply {
                putExtra(KEY_ACCOUNT_ID, accountId)
            }
            context.startActivity(intent)

        }
    }
}
