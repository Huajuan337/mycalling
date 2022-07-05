package com.callingchj.mycalling

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.callingchj.mycalling.BiiIntents.CREATE_CALL
import com.callingchj.mycalling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "CallMainActivity"
    private lateinit var  binding1: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)

        binding1 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding1.root)

        // clickable button connect to the second activity
        val secondActBt = binding1.buttonCall
        secondActBt.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }


//        val secondActBt = findViewById<Button>(R.id.button_call)
//        secondActBt.setOnClickListener{
//            val intent = Intent(this, SecondActivity::class.java)
//            startActivity(intent)
//        }

        // catch intent through voice info
        intent?.handleIntent()
    }

    private fun Intent.handleIntent() {
        Log.d(TAG,"======= go into the actions? ========= %s")
        when (action) {
            // When the BII is matched, Intent.Action_VIEW will be used
            Intent.ACTION_VIEW -> handleIntent(data)
        }

    }

    // when new intent comes, uit will update the new intent view rather than the old one
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG,"======= go into the new handleintent? ========= %s")
        intent?.handleIntent()
    }


    private fun handleIntent(data: Uri?) {

        val callExercise = intent?.extras?.getString(CREATE_CALL)

        if (callExercise != null){
            val intent = Intent(this, SecondActivity::class.java)
            // HOW TO GET INTENT PERSON INFO??
            // intent.extras.getString()
            startActivity(intent)
        }
    }

}