package com.example.kikyui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kikyui.ConnectingActivity.Companion.currentConnectedUser
import com.example.kikyui.ConnectingActivity.Companion.currentUserId
import com.example.kikyui.ui.home.ExploreFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_video_call.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_explore.viewFinder

class VideoCallActivity : AppCompatActivity() {
    lateinit var db : FirebaseFirestore
    var myOwnId = "HvSJWvX0rpFA7j3ZwIjE"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)
        db= FirebaseFirestore.getInstance()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
        }

        Toast.makeText(this, currentConnectedUser["name"].toString(),Toast.LENGTH_SHORT).show()
        btnClose.setOnClickListener {
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


            db.collection("users")
                .document(myOwnId)
                .update(myData)
                .addOnSuccessListener {
                       finish()

            }
        }



    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                this, it)== PackageManager.PERMISSION_GRANTED
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    private fun startCamera() {
        val cameraProviderFuture =  ProcessCameraProvider.getInstance(this)

        cameraProviderFuture?.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }
    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}