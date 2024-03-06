package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMaandroid.content.Context
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
            override fun onProgressChanged(seekBar: SeekBar?, progress : Int, fromUser: Boolean) {
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

    // Lock 버튼 클릭 이벤트
    fun onLockButtonClick(view: View) {
        // Lock 버튼을 누를 때, 볼륨 조절이 잠겨있는지 여부를 업데이트
        isVolumeLocked = !isVolumeLocked
    }
}