package com.callingchj.mycalling

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.callingchj.mycalling.databinding.ActivitySecondBinding



class SecondActivity : AppCompatActivity() {
    private val TAG = "CallMainActivity"
    val CALL_PERMISSION_REQUEST_CODE:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding2 = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        // check permission allow to access the data or not:
        // if not allow to get permission, there is no action to make a cal
        checkPermissions()

        val name = intent.extras?.getString("name")
        val tele = intent.extras?.getString("telephone")


        // check intent (name and phone number) in the phone book





        if (tele != null){
            if (name != null){
                binding2.calling.text = "Do you want to call $name"
            }else{
                binding2.calling.text = "Do you want to call $tele"
            }
        }


        // no button to cancel call to return to original page
        binding2.NoBt.setOnClickListener{
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        // yes button to confirm to call someone
        binding2.YesBt.setOnClickListener{
            //click button to call someone's phone number
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$tele")
            startActivity(intent)
        }
    }

    private fun checkPermissions(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val tele = intent.extras?.getString("telephone")
        if (CALL_PERMISSION_REQUEST_CODE == 1){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Thank you, you can use your phone's data", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Sorry you cannot get the permission to access the user's data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}