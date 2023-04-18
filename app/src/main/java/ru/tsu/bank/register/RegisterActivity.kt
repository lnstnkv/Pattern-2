package ru.tsu.bank.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.auth.AuthActivity
import ru.tsu.bank.databinding.ActivityRegisterBinding
import ru.tsu.bank.main.AccountActivity
import ru.tsu.domain.authorization.model.RegistrationModel
import ru.tsu.domain.authorization.model.Role
import ru.tsu.domain.authorization.model.StatusRegister

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonNext.setOnClickListener {
            viewModel.register(
                RegistrationModel(
                    firstName = binding.editTextFirstName.text.toString(),
                    lastName = binding.editTextLastName.text.toString(),
                    password = binding.editTextPasswordAuto.text.toString(),
                    username = binding.editTextEmailAddressAuto.text.toString(),
                    role = Role.CLIENT,
                    status = StatusRegister.ACTIVE,
                )
            )
        }
        initObserve()
        binding.buttonBackAuto.setOnClickListener {
            AuthActivity.startActivity(this)
        }
    }

    private fun initObserve() = with(binding) {
        viewModel.registrationEvents.observe(this@RegisterActivity) { result ->
            Toast.makeText(this@RegisterActivity, "Не, ну работаю", Toast.LENGTH_LONG).show()
            AccountActivity.start(this@RegisterActivity, result.id.toString())
        }
    }
}
