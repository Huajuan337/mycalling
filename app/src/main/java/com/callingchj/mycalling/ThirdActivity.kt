package com.callingchj.mycalling


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.callingchj.mycalling.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    lateinit var binding3: ActivityThirdBinding

    // contact permission code
    private val CONTACT_PERMISSION_CODE = 1

    // contact pick code (select the person to call)
    private val CONTACT_PICK_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding3 = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding3.root)


        // show phone contact list
        // check permission first
        if (checkContactPermission()){
            pickContact()

        }else {
            requestContactPermission()
        }
    }

    // PICK CONTACT
    private fun pickContact(){
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)     //被划线的原因是sdk的版本问题
    }


    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            if(resultCode == CONTACT_PICK_CODE){
                binding3.contactTv.text = ""

                // 游标
                val cursor1: Cursor
                val cursor2: Cursor

                // get data from intent
                val uri = data!!.data
                cursor1 = contentResolver.query(uri!!, null, null, null, null)!!
                if (cursor1.moveToFirst()){
                    val contactId =cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID))
                    val contactName =cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val contactHasNumber =cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    val hasPhoneNumber = contactHasNumber.toInt()

                    binding3.contactTv.append("ID: $contactId")
                    binding3.contactTv.append("Name: $contactName")


                    //check if contact has a phone number or not
                    if (hasPhoneNumber == 1){
                        cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null)!!

                        while (cursor2!!.moveToNext()){
                            //get phone number
                            val contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            binding3.contactTv.append("\nPhone: $contactNumber")
                        }
                        cursor2.close()
                    }
                    cursor1.close()
                }

            }
        }else{
            Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show()
        }
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
        val tele = intent.extras?.getString("telephone")
        if (CONTACT_PERMISSION_CODE == 1){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                pickContact()
            }else{
                Toast.makeText(this, "Sorry you cannot get the permission to access the user's data", Toast.LENGTH_SHORT).show()
            }
        }
    }


}