package ru.tsu.bank.auth

import android.content.Context
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
                    /*"bank-application-client",
                    "password",*/
                    binding.editTextEmailAddressAuto.text.toString(),
                    binding.editTextPasswordAuto.text.toString(),

                    )
            )
        }
        initObserve()
    }

    private fun initObserve() = with(binding) {
        viewModel.authEvents.observe(this@AuthActivity) { token ->
            AccountActivity.start(this@AuthActivity, token.id.toString())
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            context.startActivity(intent)
        }
    }
}