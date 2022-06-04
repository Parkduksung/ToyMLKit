package com.example.toymlkit.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.toymlkit.R
import com.example.toymlkit.base.BaseActivity
import com.example.toymlkit.databinding.ActivitySplashBinding
import com.example.toymlkit.ui.onboard.OnBoardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 1초뒤에 화면 전환 (OnBoardActivity)
         */
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this, OnBoardActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
            }, 1000L
        )
    }
}