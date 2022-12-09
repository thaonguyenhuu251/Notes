package com.example.notes.view.components

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.notes.R
import com.example.notes.databinding.DialogResultShowBinding
import com.example.notes.util.Constants

class ShowResultDialog: DialogFragment() {
    private lateinit var binding: DialogResultShowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dialog = Dialog(requireContext())
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        binding = DialogResultShowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments?.getBundle(Constants.WORK)!!

        val id = arg.getLong(Constants.WORK_ID, System.currentTimeMillis())
        val title = arg.getString(Constants.WORK_NAME)
        val content = arg.getString(Constants.WORK_CONTENT)
        val date = arg.getString(Constants.WORK_TIME)


        binding.txtTitle.text = title
        binding.txtContent.text = content

    }

    companion object {
        const val TAG = "ShowResultDialog"
    }

}