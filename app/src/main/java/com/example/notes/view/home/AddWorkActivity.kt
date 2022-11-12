package com.example.notes.view.home

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notes.NotificationReceiver
import com.example.notes.R
import com.example.notes.base.BaseActivity
import com.example.notes.databinding.ActivityAddWorkBinding
import com.example.notes.util.*
import com.example.notes.util.FileUtils.hideKeyboard
import com.example.notes.view.components.DateDialog
import com.example.notes.view.components.TimeDialog
import java.util.*

class AddWorkActivity : BaseActivity(), DateDialog.OnDone, TimeDialog.OnDone {
    private lateinit var binding: ActivityAddWorkBinding

    private var hour: Int = Calendar().hour
    private var minutes: Int = Calendar().minute
    private var year: Int = Calendar().year
    private var month: Int = Calendar().month
    private var day: Int = Calendar().day
    private lateinit var alarmManager: AlarmManager

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setTheme(PreferencesSettings.getBackground(this))
        binding = ActivityAddWorkBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        binding.root.setOnClickListener { hideKeyboard(this) }
        binding.txtBack.setOnClickListener { onBackPressed() }
        binding.edtTimeComplete.setOnClickListener {
            val dialogTime = TimeDialog(this)
            dialogTime.show(supportFragmentManager, dialogTime.tag)
        }
        binding.edtStartDay.setOnClickListener {
            val dialogDate = DateDialog(this)
            dialogDate.show(supportFragmentManager, dialogDate.tag)
        }

        binding.edtNameWork.setText(intent.getStringExtra(Constants.WORK_NAME) ?: "")
        binding.edtContentWork.setText(intent.getStringExtra(Constants.WORK_CONTENT) ?: "")
        binding.edtStartDay.setText(
            SimpleDateFormat(getString(R.string.work_day))
                .format(intent.getLongExtra(Constants.WORK_TIME, System.currentTimeMillis())) ?: ""
        )
        binding.edtTimeComplete.setText(
            SimpleDateFormat(getString(R.string.work_time))
                .format(intent.getLongExtra(Constants.WORK_TIME, System.currentTimeMillis())) ?: ""
        )


        binding.btnAddWork.setOnClickListener {
            try {
                val id = intent.getLongExtra(Constants.WORK_ID, System.currentTimeMillis())
                val nameWork = binding.edtNameWork.text.toString().trim { it <= ' ' }
                val contentWork = binding.edtContentWork.text.toString().trim { it <= ' ' }
                val calendar = Calendar()
                calendar.set(year, month, day, hour, minutes)
                val timeNotify = calendar.timeInMillis
                val isNoty = binding.ckbNotification.isChecked
                val isMark = intent.getBooleanExtra(Constants.WORK_MARK, false)
                val data = Intent()
                data.putExtra(Constants.WORK_ID, id)
                data.putExtra(Constants.WORK_NAME, nameWork)
                data.putExtra(Constants.WORK_CONTENT, contentWork)
                data.putExtra(Constants.WORK_TIME, timeNotify)
                data.putExtra(Constants.WORK_NOTIFY, isNoty)
                data.putExtra(Constants.WORK_MARK, isMark)
                setResult(Activity.RESULT_OK, data)
                createNotificationChannel()
                if (isNoty) {
                    alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                    val aIntent = Intent(this, NotificationReceiver::class.java)
                    val pendingIntent = PendingIntent.getBroadcast(
                        this,
                        Random().nextInt(Int.MAX_VALUE),
                        aIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeNotify, pendingIntent)
                }
                Toast.makeText(this, "Data Successfully saved", Toast.LENGTH_SHORT).show()
                binding.edtStartDay.setText("")
                binding.edtNameWork.setText("")
                binding.edtTimeComplete.setText("")
                binding.edtContentWork.setText("")
                onBackPressed()

            } catch (e: Exception) {
                Toast.makeText(this, "do not leave blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Play"
            val descriptionText = "Play notification music"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(Constants.channelID, name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun onClick(isClick: Boolean, date: Long) {
        super.onClick(isClick, date)
        if (isClick) {
            binding.edtStartDay.setText(FileUtils.formatCalendars(date))
            this.year = SimpleDateFormat("yyyy").format(date).toString().toInt()
            this.month = SimpleDateFormat("MM").format(date).toString().toInt()
            this.day = SimpleDateFormat("dd").format(date).toString().toInt()
        }
    }

    override fun onClickTime(isClick: Boolean, hour: Int, minutes: Int) {
        super.onClickTime(isClick, hour, minutes)
        if (isClick) {
            this.hour = hour
            this.minutes = minutes
            binding.edtTimeComplete.setText(FileUtils.formatTimeNew(hour, minutes))
        }
    }
}