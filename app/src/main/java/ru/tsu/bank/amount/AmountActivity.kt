package ru.tsu.bank.amount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.databinding.ActivityAmountBinding

@AndroidEntryPoint
class AmountActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAmountBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AmountViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val purpose = getPurposeOpening()
        val accountId = getAccountId()
        binding.buttonNext.setOnClickListener {
            if (purpose == PurposeOpening.WITHDRAW) {
                viewModel.withdraw(accountId, binding.editTextFirstName.text.toString())
            } else {
                viewModel.topUp(accountId, binding.editTextFirstName.text.toString())
            }
        }
        initView()

    }

    private fun getPurposeOpening(): PurposeOpening {
        return intent.getSerializableExtra(KEY_PURPOSE_OPENING) as? PurposeOpening?
            ?: PurposeOpening.WITHDRAW
    }

    private fun getAccountId(): String {
        return intent.getStringExtra(KEY_ACCOUNT_ID)
            ?: error("KEY_ACCOUNT_ID is null")
    }

    private fun initView() = with(binding) {
        viewModel.topUpAccounts.observe(this@AmountActivity) { result ->
            Toast.makeText(
                this@AmountActivity, "Пополнение счета прошло успешно", Toast.LENGTH_LONG
            ).show()
        }
        viewModel.withdrawAccounts.observe(this@AmountActivity) { result ->
            Toast.makeText(this@AmountActivity, "Снятие со счета прошло успешно", Toast.LENGTH_LONG)
                .show()
        }
    }

    companion object {
        private const val KEY_ACCOUNT_ID = "account_id"
        private const val KEY_PURPOSE_OPENING = "purpose_opening"
        fun startActivity(
            context: Context, accountId: String, purposeOpening: PurposeOpening
        ) {
            val intent = Intent(context, AmountActivity::class.java).apply {
                putExtra(KEY_ACCOUNT_ID, accountId)
                putExtra(KEY_PURPOSE_OPENING, purposeOpening)
            }
            context.startActivity(intent)

        }
    }
}