package ru.tsu.bank.openaccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.tsu.bank.databinding.ActivityOpenAccountBinding
import ru.tsu.bank.main.AccountActivity

@AndroidEntryPoint
class OpenAccountActivity : AppCompatActivity() {
    private val binding by lazy { ActivityOpenAccountBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<OpenActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getCurrencies()
        val ownerId=getOwnerId()
        binding.buttonNext.setOnClickListener {
            viewModel.selectedCurrency.value?.also { currency ->
                viewModel.createAccount(ownerId,currency)
            }
        }
        initView()
    }

    private fun initView() = with(binding) {
        spinner.prompt = "Не выбрано"
        viewModel.currenciesList.observe(this@OpenAccountActivity) { result ->
            val currencyAdapter =
                CurrencyAdapter(
                    this@OpenAccountActivity,
                    android.R.layout.simple_spinner_item,
                    result
                )
            spinner.adapter = currencyAdapter
            spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectCurrency(result[position])
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }

        viewModel.selectedCurrency.observe(this@OpenAccountActivity) { result ->
            spinner.prompt = result

        }
    }
    private fun getOwnerId(): String {
        return intent.getStringExtra(OpenAccountActivity.KEY_OWNER_ID) ?: error("KEY_OWNER_ID is null")
    }
        companion object{

        private const val KEY_OWNER_ID = "owner_id"
        fun startActivity(context: Context, ownerId: String) {
            val intent = Intent(context, OpenAccountActivity::class.java).apply {
                putExtra(OpenAccountActivity.KEY_OWNER_ID, ownerId)
            }
            context.startActivity(intent)
        }
    }


}
