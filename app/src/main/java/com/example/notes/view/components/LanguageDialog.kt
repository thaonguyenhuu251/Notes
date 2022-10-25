package com.example.notes.view.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.LanguageDialogAdapter
import com.example.notes.databinding.DialogLanguageBinding

class LanguageDialog : DialogFragment() {
    private lateinit var binding: DialogLanguageBinding
    var layoutManager: LinearLayoutManager? = null
    private var listTextLanguage = mutableListOf<Int>()
    private var listDrawableLanguage = mutableListOf<Int>()
    private var languageAdapter:LanguageDialogAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dialog = Dialog(requireContext())
        val window = dialog.window
        window?.setBackgroundDrawableResource(R.drawable.bg_dialog_round)
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setStyle(STYLE_NORMAL, R.style.MyLanguageDialog)
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

    override fun onResume() {
        super.onResume()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun generateItemWork() {
        addListTextLanguage()
        addListDrawableLanguage()

        languageAdapter = LanguageDialogAdapter(requireContext(), listTextLanguage,  listDrawableLanguage)
        binding.recyclerview.adapter = languageAdapter
        languageAdapter?.notifyDataSetChanged()
    }

    private fun addListDrawableLanguage() {
        listDrawableLanguage.add(R.drawable.ic_language_vietnam)
        listDrawableLanguage.add(R.drawable.ic_language_english_uk)
        listDrawableLanguage.add(R.drawable.ic_language_russian)
        listDrawableLanguage.add(R.drawable.ic_language_south_korea)
    }

    private fun addListTextLanguage() {
        listTextLanguage.add(R.string.vietnam)
        listTextLanguage.add(R.string.englishuk)
        listTextLanguage.add(R.string.russian)
        listTextLanguage.add(R.string.south_korea)
    }


    companion object {
        const val TAG = "LanguageDialog"
    }
}