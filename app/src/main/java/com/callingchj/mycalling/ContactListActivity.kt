package com.callingchj.mycalling

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import com.callingchj.mycalling.databinding.ActivityContactBinding
import com.callingchj.mycalling.databinding.ActivityMainBinding

class ContactListActivity : AppCompatActivity() {

    var result: Cursor? = null
    val col = listOf<String>(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID).toTypedArray()

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding4 = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding4.root)

        val from = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER).toTypedArray()
        val to = intArrayOf(R.id.item_name, R.id.item_number)
        result = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, col, null, null, null)

        var adapter = SimpleCursorAdapter(this, R.layout.contact_item, result, from, to, 0)  // cursor adapter
        binding4.contactList1.adapter = adapter   // bind adapter with listview
        binding4.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            // set a listener to inform when the search button is pressed
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                result = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, col, "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                    arrayOf("$newText"), null)
                adapter.changeCursor(result)
                adapter.notifyDataSetChanged()
                return false
            }
        })

        binding4.contactList1.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(Intent.ACTION_CALL)
            val newResult = result
            if(newResult?.moveToPosition(position) == true){
                val phoneNum = newResult.getString(newResult.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val name = newResult.getString(newResult.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                intent.putExtra("telephone", phoneNum)
                intent.data = Uri.parse("tel:$phoneNum")
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }
}