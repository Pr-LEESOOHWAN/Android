package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.databinding.ActivityMaandroid.content.Context
import android.content.Intent
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.SeekBar


class MainActivity : AppCompatActivity() {

    private var originalVolume: Int=0
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var volumeSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // AudioManager 초기화
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // 현재 볼륨 상태 저장
        originalVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        // 특정 앱 실행 (예: Google Chrome)
        val intent = packageManager.getLaunchIntentForPackage("com.android.chrome")
        startActivity(intent)

        // 앱에서 제공하는 소리 재생(예: Raw 리소스 파일 사용)
        mediaPlayer = MediaPlayer.create(this, R.raw.your_app_sound)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // 볼륨을 50%로 설정
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val desiredVolume = (0.5 * maxVolume).toInt()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, desiredVolume, 0)

        // 사용자의 소리 조절을 비활성화
        volumeSeekBar = findViewById(R.id.volulmeSeekBar) // SeekBar ID를 적절히 변경해야함
        volumeSeekBar.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()

        // 앱이 종료될 때 볼륨을 원래 상태로 복구하고 소리 정지
        val audioManager = getSystemSerivce(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0)

        mediaPlayer.stop()
        mediaPlayer.release()
}

    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'myapplication' library on application startup.
        init {
            System.loadLibrary("myapplication")
        }
    }
}