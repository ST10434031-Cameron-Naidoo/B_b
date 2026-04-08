package com.dachkaboiz.b_b.data

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dachkaboiz.b_b.R

class LoginActivity : AppCompatActivity() {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showEmailDialog() {
        val emailInput = EditText(this).apply {
            hint = "Enter your email"
            setPadding(40, 40, 40, 40)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Email Required")
            .setMessage("Please enter your email address")
            .setView(emailInput)
            .setPositiveButton("Submit", null) // we override later
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()

        // Override positive button AFTER show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val email = emailInput.text.toString().trim()

            if (email.isEmpty() || !isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Email entered: $email", Toast.LENGTH_SHORT).show()
                dialog.dismiss() // only close when valid
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val usernameInput = findViewById<EditText>(R.id.etUsername)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val forgotPasswordTv = findViewById<TextView>(R.id.tvForgotPassword)
        val signUpTv = findViewById<TextView>(R.id.tvSignUp)

        // LOGIN BUTTON
        loginBtn.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Temporary login logic (replace with real authentication later)
            if (username == "admin" && password == "1234") {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
            }
        }

        // FORGOT PASSWORD
        forgotPasswordTv.setOnClickListener {
            showEmailDialog()
        }

        // SIGN UP
        signUpTv.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }
}