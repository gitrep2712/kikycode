package com.example.kikyui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.android.synthetic.main.activity_connecting.*
import kotlin.collections.ArrayList

class ConnectingActivity : AppCompatActivity() {
    val userArray = ArrayList<userModel>()
    lateinit var db : FirebaseFirestore
    var myOwnId = "HvSJWvX0rpFA7j3ZwIjE"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connecting)
        onCreateCalled=true
        db = FirebaseFirestore.getInstance()

        val settings = firestoreSettings {
            isPersistenceEnabled = false
        }
        db.firestoreSettings = settings

        getDataFromDb()
        btnNext.isEnabled=false

        btnNext.setOnClickListener {
//
            disconnect(currentConnectedUser, currentUserId)
        }
    }


    fun disconnect(data: MutableMap<String, Any>, id: String,callNext:Boolean=true) {
        Toast.makeText(this,"disconnect..",Toast.LENGTH_SHORT).show()
        data["status"] = "available"
        db.collection("users").document(id).update(data).addOnSuccessListener {
            val myData = mutableMapOf<String, Any?>()
            myData["name"]="raeid"
            myData["status"] = "available"


            db.collection("users").document(myOwnId).update(myData).addOnSuccessListener {
                if(callNext)
                    getNextUser()
                else{
                    //remove all listeners

                }

            }
        }



    }

    override fun onPause() {
        super.onPause()

//        disconnect(currentConnectedUser, currentUserId,false)

    }

    override fun onResume() {
        super.onResume()
        if(onCreateCalled){
            onCreateCalled=false
            }
        else
             getNextUser()
//
    }

    fun transaction(id:String){
        db.runTransaction { transaction ->
            val sfDocRef = db.collection("users").document(id)

            val snapshot = transaction.get(sfDocRef)
            val data = snapshot.data

            if (id != "HvSJWvX0rpFA7j3ZwIjE" && data?.get("status") == "available") {
                matched=true

                currentConnectedUser = data

                currentUserId = id

                data["status"] = "busy"
                transaction.update(sfDocRef, data)



            }
            else{
                matched=false
                getNextUser()
            }


        }.addOnSuccessListener { result ->


            Toast.makeText(this@ConnectingActivity,result.toString(),Toast.LENGTH_SHORT).show()

            Log.d(TAG, "Transaction success: $result")
            if(matched){
            val myData = mutableMapOf<String, Any?>()
            myData["name"]="raeid"
            myData["status"] = "busy"

              db
                .collection("users").document(myOwnId)
                .update(myData)
                .addOnSuccessListener {
                val intent =Intent(this,VideoCallActivity::class.java)
                    startActivity(intent)
                    matched=false

            } }

        }.addOnFailureListener { e ->
            Log.w(TAG, "Transaction failure.", e)
        }
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
                        transaction(id)
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

        }
        else if (currentUserIndex <= userArray.size-1){

            if (currentUserIndex%3==0){

                startActivity(Intent(this@ConnectingActivity,AdVideoActivity::class.java))
            }
            else{
                ++currentUserIndex

                var id = userArray[currentUserIndex].id
                transaction(id)
            }



        }

    }

    companion object{
         var currentConnectedUser= mutableMapOf<String, Any>()
         var currentUserId= String()
         var currentUserIndex:Int=0
         var onCreateCalled = false
         var matched = false
    }
}