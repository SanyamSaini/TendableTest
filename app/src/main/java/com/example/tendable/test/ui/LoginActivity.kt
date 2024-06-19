package com.example.tendable.test.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tendable.test.R
import com.example.tendable.test.databinding.ActivityLoginBinding
import com.example.tendable.test.model.LoginModel
import com.example.tendable.test.util.MyPreferences
import com.example.tendable.test.viewmodel.LoginVM


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private var email = ""
    private var password = ""

    private val loginVM: LoginVM by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, LoginVM.Factory(activity.application))[LoginVM::class.java]
    }

    private val preferences: MyPreferences by lazy {
        MyPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        if(preferences.getIsUserLoggedIn()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        highlightRegister()
        clickListeners()
        observers()
    }

    private fun highlightRegister() {
        val spannableString = SpannableString(getString(R.string.dont_have_account))

        val clickable = object : ClickableSpan() {
            override fun onClick(view: View) {
                binding.btnLoginRegister.text = getString(R.string.register)
                highlightLogin()
            }
        }

        val foregroundColorSpanRed = ForegroundColorSpan(Color.BLUE)
        val underlineSpan = UnderlineSpan()

        spannableString.setSpan(clickable, 20, spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(foregroundColorSpanRed, 20, spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(underlineSpan, 20, spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvLoginRegister.setText(spannableString, TextView.BufferType.SPANNABLE)

        binding.tvLoginRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun highlightLogin() {
        val spannableString = SpannableString(getString(R.string.already_have_account))

        val clickable = object : ClickableSpan() {
            override fun onClick(view: View) {
                binding.btnLoginRegister.text = getString(R.string.login)
                highlightRegister()
            }
        }

        val foregroundColorSpanRed = ForegroundColorSpan(Color.BLUE)
        val underlineSpan = UnderlineSpan()

        spannableString.setSpan(clickable, 25, spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(foregroundColorSpanRed, 25, spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(underlineSpan, 25, spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvLoginRegister.setText(spannableString, TextView.BufferType.SPANNABLE)

        binding.tvLoginRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun clickListeners() {
        binding.btnLoginRegister.setOnClickListener(this)
    }

    private fun observers() {
        loginVM.loginUser.observe(this){
            if (it.isEmpty()) {
                preferences.putIsUserLoggedIn(true)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
            }
        }

        loginVM.registerUser.observe(this){
            if (it.isEmpty()) {
                binding.btnLoginRegister.text = getString(R.string.login)
                highlightRegister()
                binding.edtEmail.setText("")
                binding.edtPassword.setText("")
                Toast.makeText(this, getString(R.string.register_successfully), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun fieldsValidated() : Boolean {

        if(binding.edtEmail.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.email_error), Toast.LENGTH_LONG).show()
            return false
        }

        if(binding.edtPassword.text.isNullOrEmpty()) {
            Toast.makeText(this, getString(R.string.password_error), Toast.LENGTH_LONG).show()
            return false
        }

        email = binding.edtEmail.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()

        return true
    }

    private fun clickSubmit() {
        if (fieldsValidated()) {
            if (binding.btnLoginRegister.text == getString(R.string.login)) {
                loginVM.loginUser(LoginModel(email, password))
            } else {
                loginVM.registerUser(LoginModel(email, password))
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0) {
            binding.btnLoginRegister -> {
                clickSubmit()
            }
        }
    }
}