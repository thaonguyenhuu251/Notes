package com.example.notes.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityLoginPasswordBinding
import com.example.notes.view.home.MainActivity

class LoginPassword : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPasswordBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.imgPasswordFingerprint.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}