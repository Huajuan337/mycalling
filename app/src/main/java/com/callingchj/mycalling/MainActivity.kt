package com.callingchj.mycalling

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
    private lateinit var  binding1: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)

        binding1 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding1.root)

        // dial a phone by entering phone number
//        binding1.buttonCall.setOnClickListener {
//            val dailIntent = Intent(Intent.ACTION_DIAL)
//            dailIntent.setData(Uri.parse("tel:" + "+86"))
//            startActivity(dailIntent)
//        }

        binding1.buttonCall.setOnClickListener{
            Intent(this, ThirdActivity::class.java).apply {
                startActivity(this)
            }
        }

//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
//        startActivityForResult(intent, 111)



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

        // get person's name
        val callIntent = intent?.extras?.getString(CREATE_CALL_NAME)
        val callIntentTele = intent?.extras?.getString(CREATE_CALL_TELE)

//        if (callIntentTele != null){
//            Intent(this, SecondActivity::class.java).apply {
//                putExtra("name", callIntent)
//                putExtra("telephone", callIntentTele)
//                // HOW TO GET INTENT PERSON INFO??
//                startActivity(this)
//            }
//        }


        // show contact list test， jump to third activity
        if (callIntentTele != null){
            Intent(this, ThirdActivity::class.java).apply {
                putExtra("name", callIntent)
                putExtra("telephone", callIntentTele)
                // HOW TO GET INTENT PERSON INFO??
                startActivity(this)
            }
        }
    }

}