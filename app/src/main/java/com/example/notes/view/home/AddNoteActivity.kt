package com.example.notes.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.view.components.DateDialog
import com.example.notes.view.components.TimeDialog

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.txtBack.setOnClickListener {
            onBackPressed()
        }

        binding.tvToday.setOnClickListener {
            val dialogDate = DateDialog()
            dialogDate.show(supportFragmentManager, dialogDate.tag)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}