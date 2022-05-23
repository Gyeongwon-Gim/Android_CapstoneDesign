package com.example.capstonedesign

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

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







}