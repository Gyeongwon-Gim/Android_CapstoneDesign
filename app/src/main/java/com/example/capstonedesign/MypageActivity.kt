package com.example.capstonedesign

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_mypage)

            //툴바 불러오기
            setSupportActionBar(toolbar)
            //툴바 기존 타이틀 지우기
            supportActionBar?.setDisplayShowTitleEnabled(false)
            toolbar.title="마이페이지(신고/보관 이력)"
            //뒤로가기 버튼
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            //신고하기 페이지로 이동. 페이지가 잘 작동하는지 확인하기 위해 만들어둠. (나중에 없앨 예정)
            btn_goto_registration.setOnClickListener {
                val nextIntent = Intent(this, RegistrationActivity::class.java)
                startActivity(nextIntent)
            }
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.toolbar_menu_mypage, menu)
            return true
        }
}