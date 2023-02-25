package ru.tsu.bank.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.databinding.ActivityAuthBinding
import ru.tsu.domain.authorization.AuthModel
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

    private fun initObserve() = with(binding)  {
        viewModel.authEvents.observe(this@AuthActivity) { result ->
        if (result) {
            Toast.makeText(this@AuthActivity, "ВАУ!!!", Toast.LENGTH_LONG).show()
        }
    }

    }
}