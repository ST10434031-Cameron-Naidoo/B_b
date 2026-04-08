package com.dachkaboiz.b_b.ui

import android.R
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Calendar

open class BaseSignup : AppCompatActivity() {
    val today = Calendar.getInstance()
    val thisYear = today.get(Calendar.YEAR)
    val thisMonth = today.get(Calendar.MONTH)
    val thisDay = today.get(Calendar.DAY_OF_MONTH)
    protected lateinit var newPassword: EditText
    protected lateinit var email: EditText

    protected lateinit var confirmPassword: EditText
    protected lateinit var passwordRequirementsContainer: FrameLayout
    protected lateinit var logoIv: ImageView
    protected lateinit var pRequirementsView: View

    private val hideHandler = Handler()
    private val hideRunnable = Runnable {
        passwordRequirementsContainer.visibility = View.GONE
        logoIv.visibility = View.VISIBLE
    }

    protected fun setupYearSpinner(spinner: Spinner) {

        val years = (1930..thisYear).toList()
        spinner.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, years)
    }

    protected fun updateMonthSpinner(spinner: Spinner, year: Int) {
        val months = if (year == thisYear) {
            listOf(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            ).take(thisMonth + 1)
        } else {
            listOf(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            )
        }

        spinner.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, months)
    }


    protected fun updateDaysSpinner(spinner: Spinner, monthIndex: Int, year: Int) {
        val daysInMonth = when (monthIndex) {
            0 -> 31
            1 -> if (year % 4 == 0) 29 else 28
            2 -> 31
            3 -> 30
            4 -> 31
            5 -> 30
            6 -> 31
            7 -> 31
            8 -> 30
            9 -> 31
            10 -> 30
            else -> 31
        }
        val days = if (year == thisYear && monthIndex == thisMonth) {
            (1..thisDay).toList()   // ← limit to today
        } else {
            (1..daysInMonth).toList()
        }
        spinner.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, days)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun initPasswordViews(
        password: EditText,
        confirm: EditText,
        container: FrameLayout,
        logo: ImageView,
        requirementsView: View
    ) {
        newPassword = password
        confirmPassword = confirm
        passwordRequirementsContainer = container
        logoIv = logo
        pRequirementsView = requirementsView



        setupPasswordWatcher()
        setupConfirmPasswordWatcher()
    }

    private fun setupPasswordWatcher() {
        newPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                var strength = 0

                val tvHasDigit = pRequirementsView.findViewById<TextView>(com.dachkaboiz.b_b.R.id.tvPHasDigit)
                val tvHasUpperCase = pRequirementsView.findViewById<TextView>(com.dachkaboiz.b_b.R.id.tvPHasUpperCase)
                val tvHasLowerCase = pRequirementsView.findViewById<TextView>(com.dachkaboiz.b_b.R.id.tvPHasLowerCase)
                val tvLength = pRequirementsView.findViewById<TextView>(com.dachkaboiz.b_b.R.id.tvPLength)
                val tvHasSpecialChar = pRequirementsView.findViewById<TextView>(com.dachkaboiz.b_b.R.id.tvPHasSpecialCharacter)
                val pbPStrength = pRequirementsView.findViewById<ProgressBar>(com.dachkaboiz.b_b.R.id.pbPStrength)

                val hasDigit = password.any { it.isDigit() }
                val hasUppercase = password.any { it.isUpperCase() }
                val hasLowercase = password.any { it.isLowerCase() }
                val hasSpecialChar = password.any { !it.isLetterOrDigit() }
                val isLongEnough = password.length >= 10

                if (isLongEnough) strength += 20
                if (hasDigit) strength += 20
                if (hasUppercase) strength += 20
                if (hasLowercase) strength += 20
                if (hasSpecialChar) strength += 20

                fun TextView.setRuleColor(passed: Boolean) {
                    val colorRes = if (passed) com.dachkaboiz.b_b.R.color.rule_strong else com.dachkaboiz.b_b.R.color.rule_weak
                    setTextColor(ContextCompat.getColor(context, colorRes))
                }

                fun ProgressBar.setRules(strength: Int) {
                    max = 100
                    progress = strength

                    val colorRes = when {
                        strength <= 40 -> com.dachkaboiz.b_b.R.color.rule_weak
                        strength in 41..80 -> com.dachkaboiz.b_b.R.color.rule_medium
                        else -> com.dachkaboiz.b_b.R.color.rule_strong
                    }

                    val color = ContextCompat.getColor(context, colorRes)
                    progressTintList = ColorStateList.valueOf(color)
                }

                pbPStrength.setRules(strength)
                tvHasDigit.setRuleColor(hasDigit)
                tvLength.setRuleColor(isLongEnough)
                tvHasLowerCase.setRuleColor(hasLowercase)
                tvHasUpperCase.setRuleColor(hasUppercase)
                tvHasSpecialChar.setRuleColor(hasSpecialChar)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordRequirementsContainer.visibility = View.VISIBLE
                logoIv.visibility = View.INVISIBLE

                hideHandler.removeCallbacks(hideRunnable)
                hideHandler.postDelayed(hideRunnable, 1000)
            }
        })
    }

    private fun setupConfirmPasswordWatcher() {
        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val confirm = s.toString()
                val password = newPassword.text.toString()

                val colorRes = if (confirm == password) {
                    com.dachkaboiz.b_b.R.color.rule_strong
                } else {
                    com.dachkaboiz.b_b.R.color.rule_weak
                }

                confirmPassword.setTextColor(
                    ContextCompat.getColor(confirmPassword.context, colorRes)
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    private fun setupEmailWatcher() {
        email.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()

                if (!isValidEmail(text)) {
                    email.setTextColor(
                        ContextCompat.getColor(email.context, com.dachkaboiz.b_b.R.color.rule_weak)
                    )
                } else {
                    email.setTextColor(
                        ContextCompat.getColor(email.context, com.dachkaboiz.b_b.R.color.rule_strong)
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}