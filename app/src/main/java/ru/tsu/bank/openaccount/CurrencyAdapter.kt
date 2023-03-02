package ru.tsu.bank.openaccount

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

class CurrencyAdapter(
    context: Context,
    @LayoutRes private val resource: Int,
    private val objects: List<String>
) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup) =
        createViewFromResource(position, convertView, parent)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup) =
        createViewFromResource(position, convertView, parent)

    private fun createViewFromResource(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view: TextView = convertView as? TextView ?: LayoutInflater.from(context)
            .inflate(resource, parent, false) as TextView
        view.text = objects[position]
        return view
    }
}