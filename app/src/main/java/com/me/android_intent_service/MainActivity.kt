package com.me.android_intent_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.me.android_intent_service.databinding.ActivityMainBinding

const val FILTER_ACTION_KEY = "any_key"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val msg = it.getStringExtra("broadcastMessage")
                val currentText = binding.textView.text.toString()
                binding.textView.text = "$currentText \n $msg"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            val message = binding.inputText.text.toString()
            val intent = Intent(this, MyIntentService::class.java)
            intent.putExtra("message", message)
            startService(intent)
        }
    }

    private fun setReceiver(){
        val filter = IntentFilter()
        filter.addAction(FILTER_ACTION_KEY)
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter)
    }

    override fun onStart() {
        setReceiver()
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(myReceiver)
        super.onStop()
    }
}