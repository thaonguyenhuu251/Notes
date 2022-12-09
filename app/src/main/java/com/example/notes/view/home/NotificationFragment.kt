package com.example.notes.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.adapter.NotifAdapter
import com.example.notes.database.AlarmDatabase
import com.example.notes.databinding.FragmentNotifycationBinding
import com.example.notes.model.Alarm
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.launch

class NotificationFragment : Fragment() {
    private var listNotif = mutableListOf<Alarm>()
    lateinit var notifAdapter_Flow: NotifAdapter
    private lateinit var binding: FragmentNotifycationBinding
    private val notifDatabase by lazy { AlarmDatabase.getDatabase(requireContext())!!.alarmDao() }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotifycationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeNotif()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
    /*private fun generateNotif() {
        val notifList = notifDatabase?.getAlarm() as List<Alarm>
        notifsAdapter = NotifsAdapter(notifList, requireContext())
        binding.rcvNotification.adapter = notifsAdapter
        notifsAdapter.notifyDataSetChanged()
    }*/

    private fun setRecyclerView() {
        binding.rcvNotification.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvNotification.setHasFixedSize(true)
        notifAdapter_Flow = NotifAdapter()

        binding.rcvNotification.adapter = notifAdapter_Flow
    }


    private fun observeNotif() {
        lifecycleScope.launch {
            notifDatabase.getAlarm().collect{ notifList ->
                listNotif = notifList.toMutableList()
                notifAdapter_Flow.submitList(notifList)
            }
        }
    }
}
