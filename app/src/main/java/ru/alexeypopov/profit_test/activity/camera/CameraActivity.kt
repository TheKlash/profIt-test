package ru.alexeypopov.profit_test.activity.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.budiyev.android.codescanner.*
import kotlinx.android.synthetic.main.activity_camera.*
import ru.alexeypopov.profit_test.R
import ru.alexeypopov.profit_test.utils.CAMERA_PERMISSION
import ru.alexeypopov.profit_test.utils.CAMERA_RESULT_OK

/**
 * Активити камеры сканнера
 */
class CameraActivity : AppCompatActivity() {

    //Собственно объект камеры
    lateinit var codeScanner: CodeScanner
    //Разрешил ли пользователь доступ к камере?
    private var isPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        initScanner()
    }

    /**
     * Иницилизируем сканнер
     */
    private fun initScanner() {
        val scannerView = findViewById<CodeScannerView>(R.id.camera_scanner)
        codeScanner = CodeScanner(this, scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        /**
         * Коллбэк декодирования. Срабатывает автоматически при наведении на QR-код
         */
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Результат сканирования: ${it.text}", Toast.LENGTH_LONG).show()
                //Результат сканирования сохраняем в результат активити и тут же завершаем её
                val intent = Intent().putExtra("message", it.text)
                setResult(CAMERA_RESULT_OK, intent)
                finish()
            }
        }

        /**
         * Коллбэк ошибок камеры
         */
        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Ошибка инциализации камеры: ${it.message}",
                    Toast.LENGTH_LONG).show()
                //Начиная с API 23 надо явно запрашивать разрешение пользователя на
                //доступ к камере, навигации и некоторым другим функциям
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        isPermissionGranted = false
                        requestPermissions( Array(1) {Manifest.permission.CAMERA}, CAMERA_PERMISSION)
                    } else {
                        isPermissionGranted = true
                    }
                } else {
                    //Для API < 23 автоматически считаем, что разрешение есть
                    isPermissionGranted = true
                }

            }
        }

        //На всякий случай, если распознование кодов по какой-то причине не происходит автоматически
        //Можно просто нажать на саму вьюху сканера
        camera_scanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    /**
     * Здесь проверяем, выдал ли пользователь разрешение на доступ к камере
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true
                codeScanner.startPreview()
            } else {
                isPermissionGranted = false
            }
        }
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        //Не забываем очищать рессурсы вьюхи при паузе
        codeScanner.releaseResources()
        super.onPause()
    }
}
