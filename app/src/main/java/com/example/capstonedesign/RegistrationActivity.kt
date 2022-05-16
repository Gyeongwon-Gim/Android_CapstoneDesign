package com.example.capstonedesign

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.toolbar

class RegistrationActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //툴바 불러오기
        setSupportActionBar(toolbar)
        //툴바 기존 타이틀 지우기
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title="신고하기"
        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_registration, menu)
        return true
    }
}