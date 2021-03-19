package com.example.contactsview.Model

import android.util.Log

object FriendsContactRepo {
    val mFriends = mutableListOf<FriendsContact>(
        FriendsContact("Jonas", "123", true , "abc@gmail.com", "AStreet", "www.google.co.uk"),
        FriendsContact("Anders", "1234", false,"def@gmail.com", "BStreet", "www.bbc.co.uk"),
        FriendsContact("Nikolaj", "12345", true,"xyz@gmail.com", "CStreet", "www.skysports.co.uk"),
        FriendsContact("Nadia", "12345678", true,"qrn@gmail.com", "AXStreet", "www.itv.co.uk"),
        FriendsContact("Michael", "23456789", true,"sht@gmail.com", "LStreet", "www.next.co.uk"),
        FriendsContact("Kacper", "87654321", false,"nro@gmail.com", "zStreet", "www.lyleandscott.co.uk")

    )

    fun getAll(): MutableList<FriendsContact> = mFriends


    fun getAllNames(): MutableList<String>  =  mFriends.map { x -> x.name }.toMutableList()

    fun deleteContactByName(name: String){
       val contact = mFriends.find { c -> c.name == name }
        Log.d("TAG","friend deleted name = $name")
        mFriends.remove(contact)
    }
    fun addContact(friend: FriendsContact){
        mFriends.add(friend)
    }
    fun findContactByName(name: String?): FriendsContact? {
        val friend =  mFriends.find { friend -> friend.name == name }
        return friend
    }


}

