package ru.tsu.bank.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ru.tsu.bank.R
import ru.tsu.bank.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {


    private val binding by lazy { ActivityAccountBinding.inflate(layoutInflater) }
    private val accountAdapterListener = object : AccountAdapter.AccountAdapterListener {
        override fun onItemClick(item: Account) {
        }
    }
    private val accounts = mutableListOf<Account>(
        Account(id = "12", name = "test", 6),
        Account(id = "13", name = "test1", 2555),
        Account(id = "14", name = "test4", 0)
    )
    private val accountAdapter = AccountAdapter(accountAdapterListener)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        accountRecycler.apply {
            layoutManager=LinearLayoutManager(this@AccountActivity)
            adapter = accountAdapter
            addItemDecoration(AccountItemDecorator())
        }
        accountAdapter.submitList(accounts)
    }
}