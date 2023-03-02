package ru.tsu.bank.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.databinding.ActivityAuthBinding
import ru.tsu.bank.main.AccountActivity
import ru.tsu.domain.authorization.model.AuthModel

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonNext.setOnClickListener {
            viewModel.login(
                AuthModel(
                    binding.editTextEmailAddressAuto.text.toString(),
                    binding.editTextPasswordAuto.text.toString()
                )
            )
        }
        initObserve()
    }

    private fun initObserve() = with(binding) {
        viewModel.authEvents.observe(this@AuthActivity) { result ->
            if (result) {
                val intent = Intent(this@AuthActivity, AccountActivity::class.java)
                startActivity(intent)

            }
        }

    }
}