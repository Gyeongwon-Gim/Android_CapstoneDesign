package com.example.capstonedesign

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.capstonedesign.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    lateinit private var binding: ActivityMainBinding

    private var preview: Preview?=null
    private var imageCapture: ImageCapture?=null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    //권한 정의
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val CAMERA_PERMISSION_REQUEST = 100

    val STORAGE_PERMISSION = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val STORAGE_PERMISSION_REQUEST = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        startCamera()
// 사진촬영 버튼 안씀
//        binding.cameraCaptureButton.setOnclickListener{
//            takePhoto()
//        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

//        binding.btnCameraPermission.setOnClickListener{
//            checkPermission(CAMERA_PERMISSION, CAMERA_PERMISSION_REQUEST)
//        }
//        binding.btnStoragePermission.setOnClickListener {
//            checkPermission(STORAGE_PERMISSION, STORAGE_PERMISSION_REQUEST)
//        }

        setSupportActionBar(toolbar)
        //툴바 기존 타이틀 지우기
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_registration.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("지금 바로 신고하시겠습니까?")
                .setPositiveButton("신고하기") { dialogInterface: DialogInterface, i: Int -> }
                .setNegativeButton("나중에 신고") { dialogInterface: DialogInterface, i: Int -> }
                .show()
        }
    }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.toolbar_menu, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.getItemId()) {
                R.id.action_search -> {
                    val intent = Intent(this, MypageActivity::class.java)
                    startActivity(intent)
                    return true
                }
                android.R.id.home-> {
                    AlertDialog.Builder(this)
                        .setTitle("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료") { dialogInterface: DialogInterface, i: Int -> finish() }
                        .setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int -> }
                        .show()
                    return true
                }
                else -> {
                    return super.onOptionsItemSelected(item)
                }
            }
        }

        var waitTime = 0L
        override fun onBackPressed() {

            if(System.currentTimeMillis() - waitTime >=1500 ) {
                waitTime = System.currentTimeMillis()
                Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
            } else {
                finish() // 액티비티 종료
            }
        }
//    private fun takePhoto(){
//        //Get a stable reference of the modifiable image capture use case
//        val imageCapture = imageCapture?:return
//
//        //Create time-stamped output file to hold the image
//        val photoFile = File(
//            outputDirectory,
//            newJpgFileName())
//
//        //Create output options object which contains file + metadata
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        //Set up image capture listener, which is triggered after photo has
//        //been taken
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object:ImageCapture.OnImageSavedCallback{
//                override fun onError(exc: ImageCaptureException){
//                    Log.d("CameraX-Debug", "Photo capture failed:${exc.message}", exc)
//                }
//
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val savedUri= Uri.fromFile(photoFile)
//                    val msg = "Photo capture succeeded: $savedUri"
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                    Log.d("CameraX-Debug", msg)
//                }
//            })
//    }

    private fun startCamera(){
        val cameraProviderFuture= ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            //Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()

            //Preview
            preview=Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            //ImageCapture
            imageCapture = ImageCapture.Builder()
                .build()

            //Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try{
                //Unbind use cases before rebinding
                cameraProvider.unbindAll()

                //Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture)
            }catch (exc:Exception){
                Log.d("CameraX-Debug", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun newJpgFileName():String{
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.jpg"
    }

    private fun getOutputDirectory():File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let{
            File(it, resources.getString(R.string.app_name)).apply{
                mkdirs()
            }
        }
        return if (mediaDir != null&&mediaDir.exists()) mediaDir
        else filesDir
    }

    fun checkPermission(permissions:Array<String>, permissionRequestNumber:Int){
        val permissionResult = ContextCompat.checkSelfPermission(this, permissions[0])

        when(permissionResult){
            PackageManager.PERMISSION_GRANTED->{
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                // Go Main Function
            }
            PackageManager.PERMISSION_DENIED->{
                ActivityCompat.requestPermissions(this, permissions, permissionRequestNumber)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CAMERA_PERMISSION_REQUEST->{
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                    //Go Main Function
                }else{
                    Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
                    //Finish() or Show Guidance on the need for permission
                }
            }
            STORAGE_PERMISSION_REQUEST->{
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
                    //Go Main Function
                }else{
                    Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
                    //Finish() or Show Guidance on the need for permission
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    fun toast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}