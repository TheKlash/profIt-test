package ru.alexeypopov.profit_test.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import ru.alexeypopov.profit_test.R

/**
 * Базовая актиити, которую будут наследовать все остальные активити
 * Да, подход не совсем классический, но с другой стороны так мы сможем реализовать
 * стандартные методы показа тоста и покаха\скрытия прогрессбара,
 * не реализовывая их заново в каждой активити
 */
open class BaseActivity: AppCompatActivity(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun showProgress() {
        base_progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        base_progress_bar.visibility = View.GONE
    }

    override fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}