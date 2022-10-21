package com.example.notes.view.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.databinding.ActivityAddWorkBinding;
import com.example.notes.util.FileUtils;

import java.util.Calendar;

public class AddWorkActivity extends AppCompatActivity {
    private ActivityAddWorkBinding binding;
    TimePicker timepicker;
    private DatePicker datePicker;
    int hour;
    float minutes;
    TextView txtDone, txtExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddWorkBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.getRoot().setOnClickListener(v->{
            FileUtils.INSTANCE.hideKeyboard(this);
        });

        binding.txtBack.setOnClickListener(v->{
            onBackPressed();
        });

        binding.edtTimeComplete.setOnClickListener(v->{
            //openDialogTime(Gravity.CENTER);
        });

        binding.edtStartDay.setOnClickListener(v->{
            //openDialogDay(Gravity.CENTER);
        });

        binding.btnAddWork.setOnClickListener(v->{
            float timeComplete;
            String nameWork;
            String startDay;
            String contentWork;
            try {
                timeComplete = hour + (float) (minutes / 60);
                nameWork = binding.edtNameWork.getText().toString().trim();
                startDay = binding.edtStartDay.getText().toString().trim();
                contentWork = binding.edtContentWork.getText().toString().trim();

                if (timeComplete < 0 || timeComplete >8) {
                    Toast.makeText(this, "time >0 or time < 8", Toast.LENGTH_SHORT).show();
                    binding.edtTimeComplete.setText("");
                } else {
                    /*Work work = new Work();
                    work.setIdWork(System.currentTimeMillis());
                    work.setNameWork(nameWork);
                    work.setStartDay(startDay);
                    work.setContentWork(contentWork);
                    work.setTimeComplete(timeComplete);
                    MainActivity.roomDatabaseClass.workDao().addWork(work);*/
                    Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_SHORT).show();
                    binding.edtStartDay.setText("");
                    binding.edtNameWork.setText("");
                    binding.edtTimeComplete.setText("");
                    binding.edtContentWork.setText("");
                    onBackPressed();
                }
            } catch (Exception e) {
                Toast.makeText(this, "do not leave blank", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*private void openDialogTime(int gravity) {
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