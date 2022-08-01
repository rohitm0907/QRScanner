package com.example.qrscanner.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qrscanner.MyConstant.QR_RESULT
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class StartScanningActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    var scannerView: ZXingScannerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
        scannerView!!.startCamera()

    }

    override fun handleResult(rawResult: Result) {
//        QRScannerActivity.setText(rawResult.text)
        val output = Intent()
        output.putExtra(QR_RESULT, rawResult.text)
        setResult(RESULT_OK, output)
        onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        scannerView!!.stopCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView!!.setResultHandler(this)
        scannerView!!.startCamera()
    }
}