package com.example.myapplication

import android.app.ActivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.provider.MediaStore.Audio
import android.widget.SeekBar
import android.view.View


class YourActivity : AppCompatActivity() {

    private lateinit var audioManager: AudioManager
    private lateinit var volumeSeekBar: SeekBar
    private var isVolumeLocked = false
    private val lockedPackageName = "com.HaHa.lockedapp" //실제 HaHa의 패키지 이름 넣어야함.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AudioManager 인스턴스 가져오기
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // 볼륨 조절 이벤트 감지
        volumeControlStream = AudioManager.STREAM_MUSIC

        // 사용자가 원하는 볼륨 값으로 초기화
        volumeSeekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        //SeekBar 변경 이벤트 리스너 등록
        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 사용자가 SeekBar를 조절하면 볼륨 값을 변경
                if (fromUser && !isVolumeLocked) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?){
                // 사용자가 SeekBar 조절을 시작할 때 동작
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // 사용자가 SeekBar 조절을 멈출 때 동작
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // 특정 앱이 실행 중일 때 볼륨 조절 잠그기
        if(isAppRunning(lockedPackageName)) {
            isVolumeLocked = true
        }
    }

    override fun onPause() {
        super.onPause()
        // 앱이 종료되면 볼륨 조절 잠금 해제
        isVolumeLocked = false
    }

    private fun isAppRunning(packageName: String): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        runningAppProcesses.forEach { processInfo ->
            if(processInfo.processName == packageName) {
                return true
            }
        }
        return false
    }
}