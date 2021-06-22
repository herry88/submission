package com.dicodingsub.herryprasetyo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.race604.drawable.wave.WaveDrawable
import com.dicodingsub.herryprasetyo.BuildConfig
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    private val waveDrawable: WaveDrawable by lazy {
        WaveDrawable(this, R.drawable.logo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        tv_version_app?.text = String.format("v%s", BuildConfig.VERSION_NAME)



        waveDrawable.isIndeterminate = true
        image_logo?.setImageDrawable(waveDrawable)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TIME_MS)
    }

    companion object {
        const val DELAY_TIME_MS = 3000L
    }
}
