package com.callingchj.mycalling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.callingchj.mycalling.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding2 = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        val name = intent.extras?.getString("name")
        binding2.calling.text = name

        // clickable button cancel calling (not finish yet)
        val exitBt = binding2.cancelBt
        exitBt.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            stopService(intent)
        }

    }
}