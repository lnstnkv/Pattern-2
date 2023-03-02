package ru.tsu.bank.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.databinding.ActivityAccountBinding
import ru.tsu.bank.details.DetailsAccountActivity
import ru.tsu.bank.openaccount.OpenAccountActivity

@AndroidEntryPoint
class AccountActivity : AppCompatActivity() {


    private val binding by lazy { ActivityAccountBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<AccountViewModel>()
    private val accountAdapterListener = object : AccountAdapter.AccountAdapterListener {
        override fun onItemClick(item: AccountUiModel) {
            DetailsAccountActivity.startActivity(this@AccountActivity, item.id)
        }
    }
    private val accountAdapter = AccountAdapter(accountAdapterListener)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //TODO:: сюда надо передавать id пользователя по которому тапаю
        viewModel.getListAccounts("1")
        initView()
        binding.buttonAccount.setOnClickListener {
            OpenAccountActivity.startActivity(this)
        }
    }

    private fun initView() = with(binding) {
        accountRecycler.apply {
            layoutManager = LinearLayoutManager(this@AccountActivity)
            adapter = accountAdapter
            addItemDecoration(AccountItemDecorator())
        }
        viewModel.accountsEvents.observe(this@AccountActivity) { accounts ->
            accountAdapter.submitList(accounts)
        }
    }
}