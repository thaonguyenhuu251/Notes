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
import com.example.notes.util.Constants
import com.example.notes.util.Event

class LanguageDialog : DialogFragment() {
    private lateinit var binding: DialogLanguageBinding
    var listLanguage = mutableListOf<String>()
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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.txtOKLanguage.setOnClickListener {
            Event.eventChangeLanguage()
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

        languageAdapter = LanguageDialogAdapter(requireContext(), listLanguage)
        binding.recyclerview.adapter = languageAdapter
        languageAdapter?.notifyDataSetChanged()
    }

    private fun addListLanguage() {
        listLanguage.add(Constants.LG_VIETNAMESE)
        listLanguage.add(Constants.LG_KOREAN)
        listLanguage.add(Constants.LG_ENGLISH)
        listLanguage.add(Constants.LG_LAOS)
        listLanguage.add(Constants.LG_MYANMAR)
        listLanguage.add(Constants.LG_RUSSIAN)
        listLanguage.add(Constants.LG_THAI)
        listLanguage.add(Constants.LG_CHINESE)
        listLanguage.add(Constants.LG_JAPANESE)
        listLanguage.add(Constants.LG_FILIPINO)
        listLanguage.add(Constants.LG_INDONESIAN)
        listLanguage.add(Constants.LG_SPANISH)
        listLanguage.add(Constants.LG_FRENCH)
        listLanguage.add(Constants.LG_INDIAN)
        listLanguage.add(Constants.LG_GERMAN)
        listLanguage.add(Constants.LG_ITALIAN)
    }

    companion object {
        const val TAG = "LanguageDialog"
    }
}