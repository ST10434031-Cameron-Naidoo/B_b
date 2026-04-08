package com.dachkaboiz.b_b

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dachkaboiz.b_b.ui.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val helloWorldBtn = findViewById<TextView>(R.id.HWClick)

        helloWorldBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}