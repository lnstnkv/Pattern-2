package ru.tsu.bank.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.auth.AuthActivity
import ru.tsu.bank.databinding.ActivityRegisterBinding
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
                    middleName = binding.editTextSecondName.text.toString(),
                    lastName = binding.editTextLastName.text.toString(),
                    password = binding.editTextPasswordAuto.text.toString(),
                    username = binding.editTextEmailAddressAuto.text.toString(),
                    role = Role.CLIENT,
                    status = StatusRegister.ACTIVE,
                )
            )

        }
        initObserve()
    }

    private fun initObserve() = with(binding) {
        viewModel.registrationEvents.observe(this@RegisterActivity) { result ->
            if (result) {
                val intent = Intent(this@RegisterActivity, AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }
}