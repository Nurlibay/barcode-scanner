package uz.nurlibaydev.barcodescanner

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.CaptureManager
import uz.nurlibaydev.barcodescanner.databinding.ActivityBarcodeScannerBinding

class BarcodeScannerActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBarcodeScannerBinding.inflate(layoutInflater) }
    private lateinit var capture: CaptureManager
    private var isTorch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeQrScanner(savedInstanceState)
        binding.btnClose.setOnClickListener {
            finish()
        }
        binding.btnFlight.setOnClickListener {
            isTorch = isTorch.not()
            if (isTorch) binding.barCodeView.setTorchOn()
            else binding.barCodeView.setTorchOff()
            binding.btnFlight.setColorFilter(
                ContextCompat.getColor(
                    this,
                    if (isTorch) R.color.green
                    else R.color.white
                )
            )
        }
        binding.barCodeView.decodeSingle {
            Log.d("barcode_result_here", it.toString())
        }
    }

    private fun initializeQrScanner(savedInstanceState: Bundle?) = with(binding) {
        capture = CaptureManager(this@BarcodeScannerActivity, barCodeView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.setShowMissingCameraPermissionDialog(false)
        capture.decode()
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}