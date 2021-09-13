package com.example.kikyui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.android.synthetic.main.activity_connecting.*
import kotlin.collections.ArrayList

class ConnectingActivity : AppCompatActivity() {
    val userArray = ArrayList<userModel>()
    lateinit var db : FirebaseFirestore
    lateinit var currentConnectedUser : MutableMap<String, Any>
     lateinit var currentUserId : String
     var currentUserIndex:Int=0
    var myOwnId = "HvSJWvX0rpFA7j3ZwIjE"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connecting)
         db = FirebaseFirestore.getInstance()
        val settings = firestoreSettings {
            isPersistenceEnabled = false
        }
        db.firestoreSettings = settings

        getDataFromDb()

        btnNext.setOnClickListener {
//            startActivity(Intent(this,VideoCallActivity::class.java))
            disconnect(currentConnectedUser, currentUserId)
        }
    }

    fun connect(data: MutableMap<String, Any>, id: String) {
        Toast.makeText(this,"Connecting"+data["name"].toString(),Toast.LENGTH_SHORT).show()
        data["status"] = "busy"
        db.collection("users").document(id).update(data)
        val myData = mutableMapOf<String, Any?>()
        myData["name"]="raeid"
        myData["status"] = "busy"
        db.collection("users").document(myOwnId).update(myData)


    }
    fun disconnect(data: MutableMap<String, Any>, id: String) {
        Toast.makeText(this,"disconnect..",Toast.LENGTH_SHORT).show()
        data["status"] = "available"
        db.collection("users").document(id).update(data)
        val myData = mutableMapOf<String, Any?>()
        myData["name"]="raeid"
        myData["status"] = "available"


        db.collection("users").document(myOwnId).update(myData)

        getNextUser()

    }
    fun reconnect(){
    }

    fun getDataFromDb(){
        db.collection("users")
            .get(Source.SERVER)
            .addOnSuccessListener { result ->
                for (document in result){
                    var id = document.id

                    var user = userModel(document.data["name"].toString(),document.data["status"].toString(),id)
                    userArray.add(user)
                }

                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
                    var data = document.data
                    var id = document.id
                    var user = userModel(document.data["name"].toString(),document.data["status"].toString(),id)

                    if(id!= myOwnId && data["status"] == "available"){
                        currentConnectedUser = data
                        currentUserId =  id
                        currentUserIndex = userArray.indexOf(user)
                        connect(data,id)
                        break
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getNextUser(){
        if(currentUserIndex == userArray.size-1){
            currentUserIndex=0

            getDataFromDb()

//            db.collection("users").document(id)
//                .get(Source.SERVER)
//                .addOnSuccessListener { document ->
//
////                    for (document in result.data) {
////                    Log.d(TAG, "${document.id} => ${document.data}")
//                    if(document.data!=null) {
//                        var data = document.data
//                        var id = document.id
//                        var user = userModel(
//                            document.data!!["name"].toString(),
//                            document.data!!["status"].toString(),
//                            id
//                        )
//                        userArray.add(user)
//                        if (id != "HvSJWvX0rpFA7j3ZwIjE" && data?.get("status") == "available") {
//                            currentConnectedUser = data
//                            currentUserId = id
//                            currentUserIndex = userArray.size - 1
//                            connect(data, id)
//                        }
////                    }
//                    }
//
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents.", exception)
//                }
        }
        else if (currentUserIndex < userArray.size-1){
             ++currentUserIndex
           var id = userArray[currentUserIndex].id
            db.collection("users").document(id)
                .get(Source.SERVER)
                .addOnSuccessListener { document ->

//                    for (document in result.data) {
//                    Log.d(TAG, "${document.id} => ${document.data}")
                    if(document.data!=null) {
                        var data = document.data
                        var id = document.id

                        if (id != myOwnId && data?.get("status") == "available") {
                            currentConnectedUser = data
                            currentUserId = id
                            currentUserIndex = userArray.size - 1
                            connect(data, id)
                        }
                        else{
                            getNextUser()
                        }
//                    }
                    }

                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }

    }
}