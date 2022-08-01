package com.example.qrscanner.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import com.example.qrscanner.BaseActivity
import com.example.qrscanner.MyConstant
import com.example.qrscanner.MyConstant.QR_RESULT
import com.example.qrscanner.R
import com.example.qrscanner.databinding.ActivityQrscannerBinding
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions


class QRScannerActivity : BaseActivity(),View.OnClickListener {
    private lateinit var binding: ActivityQrscannerBinding
    private val REQUEST_CODE_QR_SCAN = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrscannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clicks()
        setAnimation()
    }

    private fun setAnimation() {
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500 //You can manage the blinking time with this parameter
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = 2
        binding.imgQR.startAnimation(anim)
    }

    private fun clicks() {
        binding.imgQR.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if(isFastClicks()){
            return
        }

        when(p0!!.id){
            R.id.imgQR ->{
         checkPermissions()
            }

            R.id.btnLogout ->{
                savePrefBoolean(MyConstant.IS_LOGIN,false)
                startActivity(Intent(this@QRScannerActivity, SplashActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun checkPermissions() {
        var alertType = ""
        lateinit var perms: Array<String>
            alertType = "This app needs access to your camera for Scanning QR Code"
            perms = arrayOf<String>(
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA
            )

        val rationale = alertType
        val options: Permissions.Options = Permissions.Options()
            .setRationaleDialogTitle("Info")
            .setSettingsDialogTitle("Warning")

        Permissions.check(
            this /*context*/,
            perms,
            rationale,
            options,
            object : PermissionHandler() {
                override fun onGranted() {
                    startQRScan()
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
                    // permission denied, block the feature.
                }
            })

    }

    private fun startQRScan() {
        val i = Intent(this@QRScannerActivity, StartScanningActivity::class.java)
        startActivityForResult(i, REQUEST_CODE_QR_SCAN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_QR_SCAN && resultCode == RESULT_OK && data != null) {
            binding.txtQRResult.text= data.getStringExtra(QR_RESULT).toString()
        }else{
            binding.txtQRResult.text=""
        }
    }

    private var backPressedTime:Long = 0
    lateinit var backToast: Toast
    override fun onBackPressed() {
        backToast = Toast.makeText(this, "Press back again to leave the app", Toast.LENGTH_SHORT)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            finishAffinity()
            return
        } else {
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }


}