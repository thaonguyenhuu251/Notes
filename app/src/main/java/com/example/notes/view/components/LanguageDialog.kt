package com.example.notes.view.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.LanguageDialogAdapter
import com.example.notes.databinding.DialogLanguageBinding
import com.example.notes.model.Language

class LanguageDialog : DialogFragment() {
    private lateinit var binding: DialogLanguageBinding
    var listLanguage = mutableListOf<Language>()
    var layoutManager: LinearLayoutManager? = null
    private var languageAdapter: LanguageDialogAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dialog = Dialog(requireContext())
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setStyle(STYLE_NORMAL, R.style.MyDialog)
        /*val windowAttributes: WindowManager.LayoutParams = window.attributes!!
        windowAttributes.gravity = Gravity.CENTER_VERTICAL
        window.attributes = windowAttributes*/

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.txtOKLanguage.setOnClickListener {
            dismiss()
        }
        binding.txtCancelLanguage.setOnClickListener {
            dismiss()
        }
        binding.recyclerview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        binding.recyclerview.layoutManager = layoutManager

        generateItemWork()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun generateItemWork() {
        addListLanguage()

        languageAdapter =
            LanguageDialogAdapter(requireContext(), listLanguage)
        binding.recyclerview.adapter = languageAdapter
        languageAdapter?.notifyDataSetChanged()
    }

    private fun addListLanguage() {
        listLanguage.add(Language(R.string.vietnam, R.drawable.ic_language_vietnam))
        listLanguage.add(Language(R.string.englishuk, R.drawable.ic_language_english_uk))
        listLanguage.add(Language(R.string.russian, R.drawable.ic_language_russian))
        listLanguage.add(Language(R.string.south_korea, R.drawable.ic_language_south_korea))
    }

    companion object {
        const val TAG = "LanguageDialog"
    }
}