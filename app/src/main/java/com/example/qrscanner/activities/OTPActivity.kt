package com.example.qrscanner.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.qrscanner.BaseActivity
import com.example.qrscanner.MyConstant
import com.example.qrscanner.R
import com.example.qrscanner.databinding.ActivityOtpactivityBinding

class OTPActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding:ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPhoneNumber()
        handleKeyboard()
        clicks()

    }

    private fun handleKeyboard() {
        binding.otpView.isFocusableInTouchMode=true
        binding.otpView.isSelected=true
        binding.otpView.requestFocus()
    }

    private fun setPhoneNumber() {
        binding.txtMobile.text=intent.getStringExtra(MyConstant.USER_PHONE).toString()
    }

    private fun clicks() {
        binding.btnNext.setOnClickListener(this@OTPActivity)
    }

    override fun onClick(p0: View?) {
        if(isFastClicks()){
            return
        }

        when(p0!!.id){
            R.id.btnNext ->{
                if(checkValidation()){
                    savePrefBoolean(MyConstant.IS_LOGIN,true)
                    startActivity(Intent(this@OTPActivity, QRScannerActivity::class.java))
                    finishAffinity()
                }
            }

        }


    }

    private fun checkValidation(): Boolean {
        if(binding.otpView.text.toString().length==0){
            showToast(resources.getString(R.string.enter_otp))
            return false
        }else if(!binding.otpView.text.toString().equals("123456")){
            showToast(resources.getString(R.string.incorrect_otp))
            return false
        }else{
            return true
        }

    }
}