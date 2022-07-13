package com.callingchj.mycalling


import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.callingchj.mycalling.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    lateinit var binding3: ActivityThirdBinding

    // convert list to array for showContact format
    var col = listOf<String>(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone._ID).toTypedArray()

    // contact permission code
    private val CONTACT_PERMISSION_CODE = 1

    // contact pick code (select the person to call)
    private val CONTACT_PICK_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding3 = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding3.root)

        if (checkContactPermission()){
            // show phone contact list
            showContact()
        }else {
            // check permission first
            requestContactPermission()
        }

    }

    // show all contacts
    private fun showContact(){

        // get intent's name, phone number
        val name = getIntent().extras?.getString("name")

        // what I want to display in the listview: name and phone number -> array
        var from = listOf<String>(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER).toTypedArray()
        var to = intArrayOf (android.R.id.text1, android.R.id.text2 )


        // get the cursor: null in the selection, show all contacts
        // if you want to filter condition, you can add something in selection, and the argument should add at selectionArgs
        // if I want to search someone's name -> selection: ${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?
        //                                       selectionArgs: $ ArrayOf("%input%")  %xxx% any contact contains those characters
        var result = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, col, "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
            arrayOf("$name"), null)

        // cursor adapter
        var adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, result, from, to, 0)

        // bind adapter with listview
        binding3.listview1.adapter = adapter
    }


    // check permission
    private fun checkContactPermission(): Boolean{
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }


    // if no permission, request permission
    private fun requestContactPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), CONTACT_PERMISSION_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (CONTACT_PERMISSION_CODE == requestCode){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                showContact()
            }else{
                Toast.makeText(this, "Sorry you cannot get the permission to access the user's data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}