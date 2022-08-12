package com.callingchj.mycalling

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import com.callingchj.mycalling.BiiIntents.CREATE_CALL_NAME
import com.callingchj.mycalling.BiiIntents.CREATE_CALL_TELE
import com.callingchj.mycalling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "CallMainActivity"
//    private lateinit var  binding1: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding1 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding1.root)

        // dial a phone by entering phone number
        binding1.buttonCall.setOnClickListener {
            val dailIntent = Intent(Intent.ACTION_DIAL)
            dailIntent.setData(Uri.parse("tel:" + "+86"))
            startActivity(dailIntent)
        }

        binding1.buttonContact.setOnClickListener{
            Intent(this, ContactListActivity::class.java).apply {
                startActivity(this)
            }
        }

        // catch intent through voice info
        intent?.handleIntent()
    }

    private fun Intent.handleIntent() {
        when (action) {
            // When the BII is matched, Intent.Action_VIEW will be used
            Intent.ACTION_VIEW -> handleIntent(data)
        }

    }

    // when new intent comes, uit will update the new intent view rather than the old one
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.handleIntent()
    }


    private fun handleIntent(data: Uri?) {

        // get person's name
        val callIntent = intent?.extras?.getString(CREATE_CALL_NAME)
        val callIntentTele = intent?.extras?.getString(CREATE_CALL_TELE)

        if (callIntent != null){
            Intent(this, ThirdActivity::class.java).apply {
                putExtra("name", callIntent)
                putExtra("telephone", callIntentTele)
                // HOW TO GET INTENT PERSON INFO??
                startActivity(this)
            }
        }
    }
}