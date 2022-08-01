package com.example.qrscanner.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.qrscanner.BaseActivity
import com.example.qrscanner.MyConstant
import com.example.qrscanner.R
import com.example.qrscanner.databinding.ActivityPhoneNumberBinding

class PhoneNumberActivity : BaseActivity(),View.OnClickListener {
    private lateinit var binding: ActivityPhoneNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clicks()
    }

    private fun clicks() {
        binding.btnNext.setOnClickListener(this@PhoneNumberActivity)
    }

    override fun onClick(p0: View?) {
        if(isFastClicks()){
            return
            }
       when(p0!!.id){
           R.id.btnNext ->{
               OpenNextScreen()
           }


       }
    }

    private fun OpenNextScreen() {
        if(isValidate()) {
            val number = binding.edtPhoneNumber.text.toString()
            val code = binding.ccp.selectedCountryCode
            val intent = Intent(this@PhoneNumberActivity, OTPActivity::class.java)
            intent.putExtra(MyConstant.USER_PHONE, "+$code$number")
            startActivity(intent)
        }
    }

    private fun isValidate(): Boolean {
        if(binding.edtPhoneNumber.text.trim().isEmpty()){
            showToast(resources.getString(R.string.enter_phone))
            return false
        }else if(binding.edtPhoneNumber.text.trim().length!=10){
                showToast(resources.getString(R.string.invalid_phone))
                return false
            }

            else{
                return true

            }

    }



    private var backPressedTime:Long = 0
    lateinit var backToast:Toast
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