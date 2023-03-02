package ru.tsu.bank.credit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.databinding.ActivityCreditBinding

@AndroidEntryPoint
class CreditActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreditBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CreditViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonNext.setOnClickListener {
            viewModel.createCredit(
                binding.editTextFirstName.text.toString().toInt(),
                binding.editTextSecondName.text.toString().toInt(),
                binding.editTextLastName.text.toString()
            )
        }
        initView()
    }

    private fun initView() = with(binding) {
        viewModel.creditDetails.observe(this@CreditActivity){ result->
            Toast.makeText(this@CreditActivity, "Вы взяли кредит!", Toast.LENGTH_LONG).show()
        }

    }
}