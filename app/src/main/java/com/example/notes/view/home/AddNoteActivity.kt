package com.example.notes.view.home

import android.annotation.SuppressLint
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
import com.example.notes.R
import com.example.notes.base.BaseActivity
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.util.*
import com.example.notes.view.components.DateDialog


class AddNoteActivity : BaseActivity(), DateDialog.OnDone {

    private lateinit var binding: ActivityAddNoteBinding

    private var year: Int = Calendar().year
    private var month: Int = Calendar().month
    private var day: Int = Calendar().day

    @SuppressLint("NewApi")
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

        binding.edtTitle.setText(intent.getStringExtra(Constants.NOTE_TITLE)?: "")
        binding.edtDescription.setText(intent.getStringExtra(Constants.NOTE_CONTENT)?: "")
        binding.tvToday.setText(
            SimpleDateFormat(getString(R.string.work_day))
                .format(intent.getLongExtra(Constants.NOTE_TIME, System.currentTimeMillis())) ?: ""
        )


        binding.imgSave.setOnClickListener{
            try {
                val id = intent.getLongExtra(Constants.NOTE_ID, System.currentTimeMillis())
                val titleNote = binding.edtTitle.text.toString().trim { it <= ' ' }
                val contentNote = binding.edtDescription.text.toString().trim { it <= ' ' }
                val calendar = Calendar()
                calendar.set(year , month , day)
                val timeNotify = calendar.timeInMillis
                val isMark = intent.getBooleanExtra(Constants.NOTE_MARK,false)

                val data = Intent()
                data.putExtra(Constants.NOTE_ID, id)
                data.putExtra(Constants.NOTE_TITLE, titleNote)
                data.putExtra(Constants.NOTE_CONTENT, contentNote)
                data.putExtra(Constants.NOTE_TIME, timeNotify)
                data.putExtra(Constants.NOTE_MARK, isMark)
                setResult(Activity.RESULT_OK, data)

                Toast.makeText(this, getString(R.string.data_success), Toast.LENGTH_SHORT).show()
                binding.edtTitle.setText("")
                binding.edtDescription.setText("")
                onBackPressed()
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.do_not_blank), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(isClick: Boolean, date: Long) {
        super.onClick(isClick, date)
        if (isClick) {
            if (date == Calendar().timeInMillis) {
                binding.tvToday.text = getString(R.string.today)
            } else {
                binding.tvToday.text = FileUtils.formatCalendars(date)
            }
            binding.tvToday.text = FileUtils.formatCalendars(date)
            this.year = SimpleDateFormat("yyyy").format(date).toString().toInt()
            this.month = SimpleDateFormat("MM").format(date).toString().toInt()
            this.day = SimpleDateFormat("dd").format(date).toString().toInt()
        }
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