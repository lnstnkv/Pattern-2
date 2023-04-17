package ru.tsu.bank.transfer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.R
import ru.tsu.bank.databinding.ActivityOpenAccountBinding
import ru.tsu.bank.databinding.ActivityTransferBinding
import ru.tsu.bank.main.AccountActivity
import ru.tsu.bank.openaccount.OpenActivityViewModel

@AndroidEntryPoint
class TransferActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTransferBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<TransferViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        val accountId= getOwnerId()
        binding.buttonNext.setOnClickListener {
            val account = binding.editTexAccount.text.toString()
            val amount = binding.editTextAmount.text.toString().toFloat()
            viewModel.transfer(
                accountId,
                account,
                amount,
            )
        }

    }

    private fun initView() = with(binding) {
        viewModel.transferEvents.observe(this@TransferActivity) {
            Toast.makeText(this@TransferActivity, "Запрос выполняется", Toast.LENGTH_LONG)
                .show()
            finish()
        }
        viewModel.errorFlow.onEach { message ->
            Toast.makeText(this@TransferActivity, message, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }
    private fun getOwnerId(): String {
        return intent.getStringExtra(KEY_OWNER_ID) ?: error("KEY_OWNER_ID is null")
    }

    companion object {
        private const val KEY_OWNER_ID = "owner_id"
        fun start(context: Context, ownerId: String) {
            val intent = Intent(context, TransferActivity::class.java).apply {
                putExtra(KEY_OWNER_ID, ownerId)
            }
            context.startActivity(intent)
        }
    }
}