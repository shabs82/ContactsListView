package com.example.contactsview.Gui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.contactsview.Model.FriendsContact
import com.example.contactsview.Model.FriendsContactRepo
import com.example.contactsview.Model.FriendsContactRepo.mFriends
import com.example.contactsview.R
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    var isCreate = true
    var contactWebsite = ""
    var contactEmail = arrayOf("")
    var LOG = "logs"
    var contactPhone = ""
    val PERMISSION_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if (intent.extras != null) {
            isCreate = false //checking i the user is or not available
            val extras: Bundle = intent.extras!!
            val name = extras.getString("name")
            val phone = extras.getString("phone")
            val favorite = extras.getBoolean("favorite")
            val address = extras.getString("address")
            val email = extras.getString("email")
            val website = extras.getString("website")
            contactWebsite = website.toString()
            contactEmail = arrayOf(email.toString())
            contactPhone = phone.toString()
            if(favorite){
                check_favourite.isChecked = true //setting if user is favourite
            }
            etName.setText(name)
            etPhone.setText(phone)
            etAddress.setText(address)
            etEmail.setText(email)
            etWebsite.setText(website)

        }
        else
        {
            Log.d("xyz", "system error: intent.extras for detailactivity is null!!!!")
        }
        if (intent.extras == null) {
            isCreate = false
            etName.text = null
            etPhone.text = null
            etAddress.text = null
            etEmail.text = null
            etWebsite.text = null
            btnDelete.visibility = View.INVISIBLE
        }

        btnDelete.setOnClickListener{ v -> onClickDelete(v)}
        btnSave.setOnClickListener{ v -> onClickSave(v)}

    }

    fun onClickBack(view: View) {
        finish() }


    fun onClickDelete(view: View) {
        val contact = FriendsContactRepo
        contact.deleteContactByName(etName.text.toString())
        finish()
    }
    fun addContact(friend: FriendsContact){
        mFriends.add(friend)
    }
    fun onClickSave(view: View) {
        if (isCreate == true) {
            val friendToCreate = FriendsContact(name = etName.text.toString(), phone = etPhone.text.toString(), isFavourite = check_favourite.isChecked ,
                                                 email = etEmail.text.toString(), address = etAddress.text.toString(), website = etWebsite.text.toString())
          FriendsContactRepo.addContact(friendToCreate)
        }else{
            val friendToUpdate = FriendsContactRepo.findContactByName(intent.extras?.getString("name"))
            if (friendToUpdate != null) {
                friendToUpdate.name = etName.text.toString()
                friendToUpdate.phone = etPhone.text.toString()
                friendToUpdate.isFavourite = check_favourite.isChecked
                friendToUpdate.address = etAddress.text.toString()
                friendToUpdate.email = etEmail.text.toString()
                friendToUpdate.website = etWebsite.text.toString()
            }
        }
        finish()
    }

    fun onClickAddContact(view: View){

    }

    fun onClickOpenSMS(view: View) {
        showYesNoDialog()
    }

    private fun showYesNoDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("SMS Handling")
        alertDialogBuilder
            .setMessage("Click Direct if SMS should be send directly. Click Start to start SMS app...")
            .setCancelable(true)
            .setPositiveButton("Direct") { dialog, id -> sendSMSDirectly() }
            .setNegativeButton("Start") { dialog, id -> startSMSActivity() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun startSMSActivity() {
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("sms:$contactPhone")
        startActivity(sendIntent)
    }

    private fun sendSMSDirectly() {
        Toast.makeText(this, "An sms will be send", Toast.LENGTH_LONG)
            .show()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_DENIED) {
                Log.d(LOG, "permission denied to SEND_SMS - requesting it")
                val permissions = arrayOf(Manifest.permission.SEND_SMS)
                requestPermissions(permissions, PERMISSION_REQUEST_CODE)
                return
            } else Log.d(LOG, "permission to SEND_SMS granted!")
        } else Log.d(LOG, "Runtime permission not needed")
        sendMessage()
    }

    private fun sendMessage() {
        val m = SmsManager.getDefault()
        val text = ""
        m.sendTextMessage(contactPhone, null, text, null, null)
    }

    fun onClickOpenCall(view: View) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$contactPhone")
        startActivity(callIntent)

    }
    fun onClickOpenEmail(view: View) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        val receivers = contactEmail
        Log.d(LOG, receivers.toString())
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Sent from my Phone")
        startActivity(emailIntent)
    }
    fun onClickOpenWebsite(view: View) {
        val url = contactWebsite
        Log.d(LOG, url)
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse(url)
        startActivity(browserIntent)
    }
}
