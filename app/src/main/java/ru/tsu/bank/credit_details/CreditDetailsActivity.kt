package ru.tsu.bank.credit_details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.databinding.ActivityCreditDetailsBinding
import ru.tsu.bank.main.AccountActivity
import ru.tsu.bank.main.AccountItemDecorator

@AndroidEntryPoint
class CreditDetailsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreditDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CreditDetailsViewModel>()
    private val paymentAdapter = PaymentAdapter()
    private val paymentOverdueAdapter = PaymentOverdueAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        val accountId = getAccountId()
        val ownerId = getOwnerId()
        binding.buttonAccount.setOnClickListener {
            viewModel.rating(ownerId)
        }
        viewModel.getListOverduePayment(accountId)
        viewModel.getListPayment(accountId)
    }
    override fun onResume() {
        super.onResume()
        val accountId = getAccountId()
        viewModel.getListOverduePayment(accountId)
        viewModel.getListPayment(accountId)
    }
    private fun initView() = with(binding) {
        paymentRecycler.apply {
            layoutManager = LinearLayoutManager(this@CreditDetailsActivity)
            adapter = paymentAdapter
            addItemDecoration(AccountItemDecorator())
        }
        creditRecycler.apply {
            layoutManager = LinearLayoutManager(this@CreditDetailsActivity)
            adapter = paymentOverdueAdapter
            addItemDecoration(AccountItemDecorator())
        }
        viewModel.ratingEvents.observe(this@CreditDetailsActivity){rating->
            Toast.makeText(this@CreditDetailsActivity, rating, Toast.LENGTH_SHORT).show()
        }

        viewModel.paymentEvents.observe(this@CreditDetailsActivity) { accounts ->
            Toast.makeText(this@CreditDetailsActivity, "Работает!", Toast.LENGTH_LONG).show()
            paymentAdapter.submitList(accounts)
        }

        viewModel.overduePaymentCreditEvents.observe(this@CreditDetailsActivity) { accounts ->
            Toast.makeText(this@CreditDetailsActivity, "Работает!", Toast.LENGTH_LONG).show()
            paymentOverdueAdapter.submitList(accounts)
        }
        viewModel.errorFlow.onEach { message ->
            Toast.makeText(this@CreditDetailsActivity, message, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }

    private fun getAccountId(): String {
        return intent.getStringExtra(CreditDetailsActivity.KEY_ACCOUNT_ID) ?: error("KEY_ACCOUNT_ID is null")
    }
    private fun getOwnerId(): String {
        return intent.getStringExtra(CreditDetailsActivity.KEY_OWNER_ID) ?: error("KEY_OWNER_ID is null")
    }
    companion object {
        private const val KEY_ACCOUNT_ID = "account_id"
        private const val KEY_OWNER_ID = "owner_id"

        fun startActivity(
            context: Context,
            accountId: String,
            ownerId:String
        ) {
            val intent = Intent(context, CreditDetailsActivity::class.java).apply {
                putExtra(KEY_ACCOUNT_ID, accountId)
                putExtra(KEY_OWNER_ID, ownerId)
            }
            context.startActivity(intent)

        }
    }
}