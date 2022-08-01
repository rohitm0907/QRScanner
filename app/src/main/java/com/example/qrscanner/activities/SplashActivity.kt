package com.example.qrscanner.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.qrscanner.BaseActivity
import com.example.qrscanner.MyConstant
import com.example.qrscanner.R

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }

    override fun onResume() {
        super.onResume()
            startTimer()
    }

    private fun startTimer() {
        Handler().postDelayed({
            if(getPrefBoolean(MyConstant.IS_LOGIN)) {
                startActivity(Intent(this@SplashActivity, QRScannerActivity::class.java))
            }else{
                startActivity(Intent(this@SplashActivity, PhoneNumberActivity::class.java))
            }
            finish()
        }, 2000)
    }
}