package com.example.notes.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.notes.base.BaseActivity
import com.example.notes.databinding.ActivityProfileDetailsBinding
import com.example.notes.util.PreferencesSettings

class ProfileDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.txtBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}