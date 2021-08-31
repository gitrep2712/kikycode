package com.example.kikyui.ui.home

import android.Manifest
import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kikyui.*
import com.example.kikyui.databinding.FragmentExplore2Binding
//import com.example.kikyui.databinding.FragmentExploreBinding
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_explore.viewFinder
import kotlinx.android.synthetic.main.fragment_explore2.*
import android.animation.ObjectAnimator

import android.animation.PropertyValuesHolder
import com.example.kikyui.fragment.GenderBottomSheet


class ExploreFragment : Fragment(),GenderBottomSheet.ItemClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentExplore2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentExplore2Binding.inflate(inflater, container, false)
        val root: View = binding.root
        val btnProfile = binding.btnProfile
//        val btnHistory= binding.btnHistory
        val btnGddess=binding.btnGddes
        val btnGender = binding.btnGender
        val closeTutorial = binding.closeTutorial
        val genderMsg = binding.titleGenderMsg
        val dottedTop = binding.llDottedTop
        val dotted = binding.llDotted
        val profileMsg = binding.tvProfileMsg
        val genderImages = binding.genderImages
        val rlOpaque = binding.rlOpaque
        val viewOpaque = binding.opaqueView
        val pulseView = binding.pulseView
//        pulseView.startPulse();

          // 50% transparent

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            }
        }


        btnProfile.setOnClickListener{
            val intent = Intent(activity,HistoryGalleryActivity::class.java)
            startActivity(intent)
        }
//        btnHistory.setOnClickListener {
//            val intent = Intent(activity, HistoryGalleryActivity::class.java)
//            startActivity(intent)
//        }
        btnGddess.setOnClickListener {
            val intent=Intent(activity,ChatActivity::class.java)
            startActivity(intent)
        }
        btnGender.setOnClickListener{
                showDialog("Gender Preferences")
        }
        closeTutorial.setOnClickListener {
            genderMsg.visibility=View.GONE
            dottedTop.visibility=View.GONE
            dotted.visibility=View.GONE
            profileMsg.visibility=View.GONE
            genderImages.visibility=View.GONE
            rlOpaque.visibility=View.GONE
            viewOpaque.visibility=View.GONE
            closeTutorial.visibility=View.GONE
            pulseView.alpha = 1f;
        }
//

        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            pulseView,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        )
        scaleDown.duration = 500

        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE

        scaleDown.start()
        pulseView.setOnClickListener {
            startActivity(Intent(activity,ConnectingActivity::class.java))
        }
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogGender(title: String) {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(true)
        dialog?.setContentView(R.layout.custom_gender_dialog)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
        dialog?.window?.setLayout(width,ActionBar.LayoutParams.WRAP_CONTENT);
//        val body = dialog?.findViewById(R.id.body) as TextView
//        body.text = title
//        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//        yesBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//        noBtn.setOnClickListener { dialog.dismiss() }
        dialog?.show()

    }

    private fun showDialog(title: String) {
        val addPhotoBottomDialogFragment: GenderBottomSheet =
            GenderBottomSheet()
        activity?.let {
            addPhotoBottomDialogFragment.show(
                it.supportFragmentManager,null
            )
        }
//
//        val dialog = activity?.let { Dialog(it) }
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog?.setCancelable(true)
//        dialog?.setContentView(R.layout.custom_report_dialog)
//        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
//        dialog?.window?.setLayout(width,ActionBar.LayoutParams.WRAP_CONTENT);
////        val body = dialog?.findViewById(R.id.body) as TextView
////        body.text = title
////        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
////        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
////        yesBtn.setOnClickListener {
////            dialog.dismiss()
////        }
////        noBtn.setOnClickListener { dialog.dismiss() }
//        dialog?.show()

    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {

         activity?.baseContext?.let {
             ctx->it
            ContextCompat.checkSelfPermission(
                ctx, it)
        } == PackageManager.PERMISSION_GRANTED
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(activity,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                activity?.finish()
            }
        }
    }
    private fun startCamera() {
        val cameraProviderFuture = activity?.let { ProcessCameraProvider.getInstance(it) }

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

        }, ContextCompat.getMainExecutor(activity))
    }
    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onItemClick(item: String?) {

    }

}