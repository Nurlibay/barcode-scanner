package uz.nurlibaydev.barcodescanner

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import uz.nurlibaydev.barcodescanner.databinding.ActivityMainBinding
import uz.nurlibaydev.util.PermissionUtil

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnLaunchScanner.setOnClickListener {
            launchScanner()
        }
    }

    private fun launchScanner(){
        PermissionUtil.checkSinglePermission(
            this,
            object : PermissionUtil.MyPermissionListener {
                override fun onAllow() {
                    val options =ScanOptions()
                        .setOrientationLocked(false)
                        .setCaptureActivity(BarcodeScannerActivity::class.java)
                        .setCameraId(0)
                        .setBeepEnabled(false)
                        .setBarcodeImageEnabled(true)
                        .setDesiredBarcodeFormats(
                            ScanOptions.QR_CODE,
                            ScanOptions.CODE_128,
                            ScanOptions.CODE_39,
                            ScanOptions.CODE_93,
                            ScanOptions.CODE_128,
                            ScanOptions.EAN_13,
                            ScanOptions.DATA_MATRIX,
                            ScanOptions.EAN_8,
                            ScanOptions.ITF,
                            ScanOptions.PDF_417,
                            ScanOptions.RSS_14,
                            ScanOptions.RSS_EXPANDED,
                            ScanOptions.UPC_A,
                            ScanOptions.UPC_E
                        )
                    barcodeLauncher.launch(options)
                }

                override fun onDeny() {
                    Toast.makeText(this@MainActivity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            },
            Manifest.permission.CAMERA
        )
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()){
        if(!it.contents.isNullOrEmpty()){
            Toast.makeText(this, "Scan result: ${it.contents}", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}