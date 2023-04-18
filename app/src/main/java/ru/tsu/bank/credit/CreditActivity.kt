package ru.tsu.bank.credit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.tsu.bank.databinding.ActivityCreditBinding
import ru.tsu.bank.main.AccountActivity
import java.util.UUID

@AndroidEntryPoint
class CreditActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreditBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CreditViewModel>()

    private val accountId = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val ownerId = getOwnerId()
        binding.buttonNext.setOnClickListener {
            viewModel.createCredit(
                binding.editTextFirstName.text.toString().toInt(),
                binding.editTextSecondName.text.toString().toInt(),
                binding.editTextLastName.text.toString(),
                ownerId,
                accountId
            )
        }
        initView(ownerId, accountId)
    }

    private fun initView(ownerId: String, accountId: String) = with(binding) {
        viewModel.creditDetails.observe(this@CreditActivity) { result ->
            if (result) {
                Toast.makeText(this@CreditActivity, "Вы взяли кредит!", Toast.LENGTH_LONG).show()
                AccountActivity.start(this@CreditActivity, ownerId)
            }
        }
        viewModel.errorFlow.onEach { message ->
            Toast.makeText(this@CreditActivity, message, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

    }

    private fun getOwnerId(): String {
        return intent.getStringExtra(KEY_OWNER_ID) ?: error("KEY_OWNER_ID is null")
    }

    companion object {

        private const val KEY_OWNER_ID = "owner_id"
        fun startActivity(context: Context, ownerId: String) {
            val intent = Intent(context, CreditActivity::class.java).apply {
                putExtra(KEY_OWNER_ID, ownerId)
            }
            context.startActivity(intent)
        }
    }
}
