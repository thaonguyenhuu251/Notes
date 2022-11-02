package com.example.notes.view.home

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.annotation.SuppressLint
import android.os.Bundle
import com.example.notes.R
import android.view.View
import android.content.Intent
import com.example.notes.databinding.ActivityProfileBinding
import com.example.notes.databinding.LayoutDisplaySettingsColorBinding
import com.example.notes.view.home.ProfileDetailsActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.edtProfilDetails.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ProfileDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.txtBack.setOnClickListener {
            onBackPressed()
        }
        binding.edtExit.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}