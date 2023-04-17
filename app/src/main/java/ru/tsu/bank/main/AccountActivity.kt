package ru.tsu.bank.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.credit.CreditActivity
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
        val ownerId = getOwnerId()
        //TODO:: сюда надо передавать id пользователя по которому тапаю
        initView()
        viewModel.getListAccounts(ownerId)
        binding.buttonAccount.setOnClickListener {
            OpenAccountActivity.startActivity(this, ownerId)
        }
        binding.buttonCredit.setOnClickListener {
            CreditActivity.startActivity(this, ownerId)
        }
    }

    override fun onResume() {
        super.onResume()
        val ownerId = getOwnerId()
        viewModel.getListAccounts(ownerId)
    }

    private fun initView() = with(binding) {
        accountRecycler.apply {
            layoutManager = LinearLayoutManager(this@AccountActivity)
            adapter = accountAdapter
            addItemDecoration(AccountItemDecorator())
        }
        viewModel.accountsEvents.observe(this@AccountActivity) { accounts ->
            Toast.makeText(this@AccountActivity, "Работает!", Toast.LENGTH_LONG).show()
            Log.e("123132132132", accounts.toString())
            accountAdapter.submitList(accounts)
        }
    }

    private fun getOwnerId(): String {
        return intent.getStringExtra(KEY_OWNER_ID) ?: error("KEY_OWNER_ID is null")
    }

    companion object {
        private const val KEY_OWNER_ID = "owner_id"
        fun start(context: Context, ownerId: String) {
            val intent = Intent(context, AccountActivity::class.java).apply {
                putExtra(KEY_OWNER_ID, ownerId)
            }
            context.startActivity(intent)
        }
    }
}