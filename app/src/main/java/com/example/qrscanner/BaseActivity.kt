package com.example.qrscanner

import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity:AppCompatActivity() {
    private var sharedPref: SharedPreferences? = null
    companion object {
        var ON_CLICK_DELAY: Long = 700
        var lastTimeClicked = 0L
    }
    fun showToast(message:String){
        Toast.makeText(this@BaseActivity,message, Toast.LENGTH_SHORT).show()
    }

    fun isFastClicks(): Boolean {
        if (System.currentTimeMillis() - lastTimeClicked > ON_CLICK_DELAY) {
            lastTimeClicked = System.currentTimeMillis()
            return false
        } else {
            return true
        }
    }

    fun savePrefBoolean( key: String, value: Boolean) {
        sharedPref=getSharedPreferences("myQRPref", MODE_PRIVATE)
        val prefsEditor = sharedPref!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }




    fun getPrefBoolean( key: String): Boolean {
        sharedPref = getSharedPreferences("myQRPref", MODE_PRIVATE)
        return sharedPref!!.getBoolean(key, false)!!
    }

}