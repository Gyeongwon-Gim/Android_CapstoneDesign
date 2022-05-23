package com.example.capstonedesign

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.toolbar
import java.util.*

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

        et_calendar_select.setOnClickListener {
            showDatePicker()
        }
        et_time_select.setOnClickListener {
            showTimePicker()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_registration, menu)
        return true
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, y, m, d->
        Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show() },
        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show()
    }

    private fun showTimePicker() {
        val cal = Calendar.getInstance()
        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
        Toast.makeText(this, "$h:$m", Toast.LENGTH_SHORT).show() },
        cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show() }



}