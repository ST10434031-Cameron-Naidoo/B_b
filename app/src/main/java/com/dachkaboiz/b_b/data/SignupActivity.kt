package com.dachkaboiz.b_b.data

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.dachkaboiz.b_b.R
import com.dachkaboiz.b_b.data.BaseSignup
import com.dachkaboiz.b_b.data.LoginActivity

class SignupActivity : BaseSignup() {

        private lateinit var newUsername: EditText
        private lateinit var signUpBtn: Button
        private lateinit var loginTv: TextView


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_signup)
            val spinnerDay = findViewById<Spinner>(R.id.spDay)
            val spinnerMonth = findViewById<Spinner>(R.id.spMonth)
            val spinnerYear = findViewById<Spinner>(R.id.spYear)

// Use inherited functions

            setupYearSpinner(spinnerYear)
            spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val year = spinnerYear.selectedItem as Int

                    // Update months based on max date logic
                    updateMonthSpinner(spinnerMonth, year)

                    // Update days based on new month + year
                    val monthIndex = spinnerMonth.selectedItemPosition
                    updateDaysSpinner(spinnerDay, monthIndex, year)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

// 3. When MONTH changes → update DAYS
            spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val year = spinnerYear.selectedItem as Int
                    updateDaysSpinner(spinnerDay, position, year)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            newUsername = findViewById(R.id.etNewUsername)
            newPassword = findViewById(R.id.etNewPassword)
            confirmPassword = findViewById(R.id.etConfirmPassword)
            email = findViewById(R.id.etEmail)

            signUpBtn = findViewById(R.id.btnSignUp)
            loginTv = findViewById(R.id.tvLogin)
            logoIv = findViewById(R.id.ivLogo)

            passwordRequirementsContainer = findViewById(R.id.passwordRequirementsContainer)
            pRequirementsView = layoutInflater.inflate(
                R.layout.dialog_password_requirements,
                passwordRequirementsContainer,
                true
            )

            initPasswordViews(
                newPassword,
                confirmPassword,
                passwordRequirementsContainer,
                logoIv,
                pRequirementsView
            )

            loginTv.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            signUpBtn.setOnClickListener {
                val username = newUsername.text.toString().trim()
                val email = email.text.toString().trim()
                val password = newPassword.text.toString().trim()
                val confirm = confirmPassword.text.toString().trim()

                if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password != confirm) {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(!isValidEmail(email)){
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }