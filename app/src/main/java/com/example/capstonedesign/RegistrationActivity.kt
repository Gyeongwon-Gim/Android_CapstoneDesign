package com.example.capstonedesign

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.toolbar
import java.util.*

class RegistrationActivity:AppCompatActivity() {
        var dateString = ""
        var timeString = ""
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

//        et_calendar_select.setOnClickListener {
//            showDatePicker()
//        }
//        et_time_select.setOnClickListener {
//            showTimePicker()
//        }

        et_calendar_select.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                et_calendar_select.text = dateString
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        et_time_select.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                timeString = "${hourOfDay}시 ${minute}분"
                et_time_select.text = timeString
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }


        btn_registration_registration.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("신고하시겠습니까?")
                .setPositiveButton("신고") { dialogInterface: DialogInterface, i: Int -> }
                .setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int -> }
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_registration, menu)
        return true
    }

//    private fun showDatePicker() {
//        val cal = Calendar.getInstance()
//        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, y, m, d->
//        Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show() },
//        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show()
//    }
//
//    private fun showTimePicker() {
//        val cal = Calendar.getInstance()
//        TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
//        Toast.makeText(this, "$h:$m", Toast.LENGTH_SHORT).show() },
//        cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show() }



}