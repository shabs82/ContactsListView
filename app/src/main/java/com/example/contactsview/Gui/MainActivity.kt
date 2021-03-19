package com.example.contactsview.Gui

import android.app.ListActivity
import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewParent
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.contactsview.Model.FriendsContact
import com.example.contactsview.Model.FriendsContactRepo
import com.example.contactsview.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contacts = FriendsContactRepo
        var contactNames = contacts.getAllNames()

        val adapter: ListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, contactNames
        )

        ContactView.adapter = adapter
        ContactView.setOnItemClickListener{_, _,position, _ -> onListItemClick(position)}

    }

     fun onListItemClick(position: Int){
           /* val friendsName = FriendsContactRepo().getAll()[position].name
        Toast.makeText(
            this,
            "Hi $friendsName  how you been",
            Toast.LENGTH_LONG)
            .show()*/
         val intent = Intent(this, DetailActivity::class.java)
         val contact = FriendsContactRepo.getAll()[position]
         intent.putExtra("name", contact.name )
         intent.putExtra("phone", contact.phone)
         intent.putExtra("favorite", contact.isFavourite)
         intent.putExtra("address", contact.address)
         intent.putExtra("email" , contact.email)
         intent.putExtra("website" , contact.website)

         startActivity(intent)
     }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.friendmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        when (id) {
            R.id.action_settings -> {
                Toast.makeText(this, "Action Settings Selected...", Toast.LENGTH_LONG).show()
                true
            }
            R.id.btnAddContact->{
                val Intent = Intent(this,  DetailActivity::class.java )
                startActivity(Intent)
                Toast.makeText(this, "Add New Contact selected ...", Toast.LENGTH_LONG).show()
                true
            }
            R.id.btnSearch -> {
                Toast.makeText(this, "Search for all Contacts ", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.btnClose ->{
                Toast.makeText(this, "Go back to main view", Toast.LENGTH_SHORT).show()
                finish()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val contact = FriendsContactRepo
        val friendNames = contact.getAllNames()

        val adapter: ListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, friendNames
        )

        ContactView.adapter = adapter

        ContactView.setOnItemClickListener { _, _, position, _ -> onListItemClick(position) }

    }
}



