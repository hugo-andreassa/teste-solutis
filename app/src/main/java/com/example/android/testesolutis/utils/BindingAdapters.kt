package com.example.android.testesolutis.utils

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.testesolutis.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Binding adapter usado para esconder o spinner
 */
@BindingAdapter("goneIfNotTrue")
fun goneIfNotTrue(view: View, it: Boolean?) {
    it?.let {
        view.visibility = if (it) View.VISIBLE else View.GONE
    }
}

/**
 * Binding adapter usado para desabilitar/habilitar o botão
 */
@BindingAdapter("disableIfNotTrue")
fun disableIfNotTrue(button: Button, it: Boolean?) {
    it?.let {
        button.isEnabled = !it
    }
}

/**
 * Binding adapter usado para colocar mascara no cpf/cnpj
 */
@BindingAdapter("cpfCnpjMask")
fun cpfCnpjMask(txtView: TextView, it: String?) {
    it?.let {
        if (it.length == 11) {
            val masked = String.format(
                "%s.%s.%s-%s",
                it.substring(0, 3),
                it.substring(3, 6),
                it.substring(6, 9),
                it.substring(9, 11)
            )
            txtView.text = masked
        } else {
            val masked = String.format(
                "%s.%s.%s/%s-%s",
                it.substring(0, 2),
                it.substring(2, 5),
                it.substring(5, 8),
                it.substring(8, 12),
                it.substring(12, 14)
            )
            txtView.text = masked
        }
    }
}

/**
 * Binding adapter usado para colocar mascara no dinheiro
 */
@BindingAdapter("moneyMask")
fun moneyMask(txtView: TextView, it: Double?) {
    it?.let {
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        txtView.text = format.format(it)
    }
}

/**
 * Binding adapter usado para colocar mascara no dinheiro com cor
 */
@BindingAdapter("moneyMaskWithColor")
fun moneyMaskWithColor(txtView: TextView, it: Double?) {
    it?.let {
        if (it <= 0) {
            txtView.setTextColor(txtView.context.getColor(R.color.red))
        } else {
            txtView.setTextColor(txtView.context.getColor(R.color.green))
        }

        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        txtView.text = format.format(it)
    }
}

/**
 * Binding adapter usado para colocar mascara de data
 */
@BindingAdapter("dateMask")
fun dateMask(txtView: TextView, it: String?) {
    it?.let {
        try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(it)
            val formatted = SimpleDateFormat("dd/MM/yyyy").format(date!!)
            txtView.text = formatted
        } catch (e: Exception) {
            txtView.text = txtView.context.getString(R.string.error_date)
        }
    }
}

/**
 * Binding adapter usado para colocar o tipo do extrato
 */
@BindingAdapter("extratoType")
fun extratoType(txtView: TextView, it: Double?) {
    it?.let {
        if (it <= 0) {
            txtView.text = txtView.context.getString(R.string.pagamento)
        } else {
            txtView.text = txtView.context.getString(R.string.recebimento)
        }
    }
}

