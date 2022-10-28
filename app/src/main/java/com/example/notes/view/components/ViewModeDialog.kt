package com.example.notes.view.components

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.notes.R
import com.example.notes.databinding.LayoutViewmodeDialogBinding

class ViewModeDialog : DialogFragment() {
    private lateinit var binding: LayoutViewmodeDialogBinding

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
        binding = LayoutViewmodeDialogBinding.inflate(layoutInflater)
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
        initView()
    }

    private fun initView() {
        binding.txtGridView.setOnClickListener {
            binding.txtGridView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_grid_view,
                0,
                R.drawable.ic_choose_tick,
                0
            );
            binding.txtListView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_list_view,
                0,
                0,
                0
            );
        }
        binding.txtListView.setOnClickListener {
            binding.txtListView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_list_view,
                0,
                R.drawable.ic_choose_tick,
                0
            );
            binding.txtGridView.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_grid_view,
                0,
                0,
                0
            );
        }

        binding.txtOKViewmode.setOnClickListener {
            //do something
            dismiss()
        }
        binding.txtCancelViewmode.setOnClickListener {
            //do something
            dismiss()
        }
    }

    companion object {
        const val TAG = "ViewModeDialog"
    }
}