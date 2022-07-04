package com.callingchj.mycalling

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.callingchj.mycalling.BiiIntents.CREATE_CALL

class MainActivity : AppCompatActivity() {

    private val TAG = "CallMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"======= go into the handleintent? ========= %s")
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

        Log.d(TAG,"======= go into the handleintent ========= %s")
        if (callExercise != null){
            Log.d(TAG,"======= go into the handleintent ========= %s")
//                val intent = Intent(this, MainActivity2::class.java)
//            startActivity(intent)
        }
    }


}