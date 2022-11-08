package com.example.notes.view.home

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.base.BaseActivity
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.util.Calendar
import com.example.notes.util.Constants
import com.example.notes.util.PreferencesSettings
import com.example.notes.view.components.DateDialog


class AddNoteActivity : BaseActivity(), DateDialog.OnDone {

    private lateinit var binding: ActivityAddNoteBinding

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.txtBack.setOnClickListener {
            onBackPressed()
        }

        binding.tvToday.setOnClickListener {
            val dialogDate = DateDialog(this)
            dialogDate.show(supportFragmentManager, dialogDate.tag)
        }

        binding.imgSave.setOnClickListener{
            try {
                val id = System.currentTimeMillis()
                val titleNote = binding.edtTitle.text.toString().trim { it <= ' ' }
                val contentNote = binding.edtDescription.text.toString().trim { it <= ' ' }
                val calendar = Calendar()
                calendar.set(year , month , day)
                val timeNotify = calendar.timeInMillis
                val isNoty = false

                val data = Intent()
                data.putExtra(Constants.NOTE_ID, id)
                data.putExtra(Constants.NOTE_TITLE, titleNote)
                data.putExtra(Constants.NOTE_CONTENT, contentNote)
                data.putExtra(Constants.NOTE_TIME, timeNotify)
                setResult(Activity.RESULT_OK, data)

                Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_SHORT).show()
                binding.edtTitle.setText("")
                binding.edtDescription.setText("")
                onBackPressed()
            } catch (e: Exception) {
                Toast.makeText(this, "Do not leave blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(isClick: Boolean, date: Long) {
        super.onClick(isClick, date)
    }

    fun buttonBold(view: View?) {
        val spannableString: Spannable = SpannableStringBuilder(binding.edtDescription.text)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            binding.edtDescription.selectionStart,
            binding.edtDescription.selectionEnd,
            0
        )
        binding.edtDescription.setText(spannableString)
    }

    fun buttonItalics(view: View?) {
        val spannableString: Spannable = SpannableStringBuilder(binding.edtDescription.text)
        spannableString.setSpan(
            StyleSpan(Typeface.ITALIC),
            binding.edtDescription.selectionStart,
            binding.edtDescription.selectionEnd,
            0
        )
        binding.edtDescription.setText(spannableString)
    }

    fun buttonUnderline(view: View?) {
        val spannableString: Spannable = SpannableStringBuilder(binding.edtDescription.text)
        spannableString.setSpan(
            UnderlineSpan(),
            binding.edtDescription.selectionStart,
            binding.edtDescription.selectionEnd,
            0
        )
        binding.edtDescription.setText(spannableString)
    }

    fun buttonNoFormat(view: View?) {
        val stringText: String = binding.edtDescription.text.toString()
        binding.edtDescription.setText(stringText)
    }


    fun buttonAlignmentLeft(view: View?) {
        binding.edtDescription.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        val spannableString: Spannable = SpannableStringBuilder(binding.edtDescription.text)
        binding.edtDescription.setText(spannableString)
    }

    fun buttonAlignmentCenter(view: View?) {
        binding.edtDescription.textAlignment = View.TEXT_ALIGNMENT_CENTER
        val spannableString: Spannable = SpannableStringBuilder(binding.edtDescription.text)
        binding.edtDescription.setText(spannableString)
    }

    fun buttonAlignmentRight(view: View?) {
        binding.edtDescription.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        val spannableString: Spannable = SpannableStringBuilder(binding.edtDescription.text)
        binding.edtDescription.setText(spannableString)
    }

    fun buttonAlignmentGravity(view: View?) {
        binding.edtDescription.textAlignment = View.TEXT_ALIGNMENT_GRAVITY
        val spannableString: Spannable = SpannableStringBuilder(binding.edtDescription.text)
        binding.edtDescription.setText(spannableString)
    }

}