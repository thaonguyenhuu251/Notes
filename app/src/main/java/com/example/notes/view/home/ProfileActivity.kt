package com.example.notes.view.home

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.notes.base.BaseActivity
import com.example.notes.database.UserDatabase
import com.example.notes.databinding.ActivityProfileBinding
import com.example.notes.model.UserProfile
import com.example.notes.util.PreferencesSettings

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val userDatabase by lazy { UserDatabase.getDataBase(applicationContext).userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewProfile()

        binding.txtEditProfile.setOnClickListener {
            binding.edtName.isEnabled = true
            binding.edtEmail.isEnabled = true
            binding.edtAddress.isEnabled = true
            binding.edtPhoneNumber.isEnabled = true
            binding.btnSave.isVisible = true
        }

        binding.txtBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            val name:String = binding.edtName.text.toString()
            val address:String = binding.edtAddress.text.toString()
            val phoneNumber:String = binding.edtPhoneNumber.text.toString()
            val mail:String = binding.edtEmail.text.toString()
            val userProfile = UserProfile(name, mail, address, phoneNumber)

            userDatabase.insertuser(userProfile)
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
            viewProfile()
        }
    }

    private fun viewProfile() {
        val listU = userDatabase.getUser()

        if (listU.isEmpty()) {
            binding.txtTitleName.text = ""
            binding.edtName.setText("")
            binding.edtName.isEnabled = false

            binding.edtEmail.setText("")
            binding.edtEmail.isEnabled = false

            binding.edtAddress.setText("")
            binding.edtAddress.isEnabled = false

            binding.edtPhoneNumber.setText("")
            binding.edtPhoneNumber.isEnabled = false

            binding.btnSave.isVisible = false
        } else {
            binding.txtTitleName.text = listU[0].userName
            binding.edtName.setText(listU[0].userName)
            binding.edtName.isEnabled = false

            binding.edtEmail.setText(listU[0].mail)
            binding.edtEmail.isEnabled = false

            binding.edtAddress.setText(listU[0].address)
            binding.edtAddress.isEnabled = false

            binding.edtPhoneNumber.setText(listU[0].phoneNumber)
            binding.edtPhoneNumber.isEnabled = false

            binding.btnSave.isVisible = false
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}