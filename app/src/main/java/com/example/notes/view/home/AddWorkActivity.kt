package com.example.notes.view.home

import com.example.notes.util.FileUtils.hideKeyboard
import androidx.appcompat.app.AppCompatActivity
import android.widget.TimePicker
import android.widget.DatePicker
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notes.databinding.ActivityAddWorkBinding
import com.example.notes.view.components.DateDialog
import com.example.notes.view.components.TimeDialog
import java.lang.Exception

class AddWorkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWorkBinding
    var timepicker: TimePicker? = null
    private val datePicker: DatePicker? = null
    var hour = 0
    var minutes = 0f
    var txtDone: TextView? = null
    var txtExit: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.root.setOnClickListener { v: View? -> hideKeyboard(this) }
        binding.txtBack.setOnClickListener { v: View? -> onBackPressed() }
        binding.edtTimeComplete.setOnClickListener { v: View? ->
            val dialogTime = TimeDialog()
            dialogTime.show(supportFragmentManager, dialogTime.tag)
        }
        binding.edtStartDay.setOnClickListener { v: View? ->
            val dialogDate = DateDialog()
            dialogDate.show(supportFragmentManager, dialogDate.tag)
        }
        binding.btnAddWork.setOnClickListener { v: View? ->
            val timeComplete: Float
            val nameWork: String
            val startDay: String
            val contentWork: String
            try {
                timeComplete = hour + (minutes / 60)
                nameWork = binding.edtNameWork.text.toString().trim { it <= ' ' }
                startDay = binding.edtStartDay.text.toString().trim { it <= ' ' }
                contentWork = binding.edtContentWork.text.toString().trim { it <= ' ' }
                if (timeComplete < 0 || timeComplete > 8) {
                    Toast.makeText(this, "time >0 or time < 8", Toast.LENGTH_SHORT).show()
                    binding.edtTimeComplete.setText("")
                } else {
                    /*Work work = new Work();
                    work.setIdWork(System.currentTimeMillis());
                    work.setNameWork(nameWork);
                    work.setStartDay(startDay);
                    work.setContentWork(contentWork);
                    work.setTimeComplete(timeComplete);
                    MainActivity.roomDatabaseClass.workDao().addWork(work);*/
                    Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_SHORT).show()
                    binding.edtStartDay.setText("")
                    binding.edtNameWork.setText("")
                    binding.edtTimeComplete.setText("")
                    binding.edtContentWork.setText("")
                    onBackPressed()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "do not leave blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    } /*private void openDialogTime(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_time);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        txtDone = dialog.findViewById(R.id.txtDone);
        txtExit = dialog.findViewById(R.id.txtExit);

        txtDone.setOnClickListener(v->{
            hour = timepicker.getCurrentHour();
            minutes = timepicker.getCurrentMinute();
            binding.edtTimeComplete.setText(FileUtils.formatTimeNew(hour, (int) minutes));
            dialog.dismiss();
        });

        txtExit.setOnClickListener(v->{
            dialog.dismiss();
        });
        timepicker = dialog.findViewById(R.id.timePicker);
        timepicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timepicker.setHour(01);
            timepicker.setMinute(00);
        }

        dialog.show();
    }*/
    /*@SuppressLint("DefaultLocale")
    private void openDialogDay(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_day);
        datePicker = dialog.findViewById(R.id.dayPicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        txtDone = dialog.findViewById(R.id.txtDone);
        txtExit = dialog.findViewById(R.id.txtExit);

        txtDone.setOnClickListener(v->{
            int day = this.datePicker.getDayOfMonth();
            int year = this.datePicker.getYear();
            int month = this.datePicker.getMonth() + 1;
            binding.edtStartDay.setText(FileUtils.formatCalendar(day, month, year));
            dialog.dismiss();
        });

        txtExit.setOnClickListener(v->{
            dialog.dismiss();
        });

        dialog.show();
    }*/
}