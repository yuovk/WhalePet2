package com.yuyu.whalepet2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.yuyu.whalepet2.service.OverlayService

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Settings.canDrawOverlays(this)) {
            startPet()
            Toast.makeText(this, "蓝色小鲸鱼游到你身边陪你啦 🐋", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, REQ_OVERLAY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_OVERLAY) {
            if (Settings.canDrawOverlays(this)) {
                startPet()
                Toast.makeText(this, "蓝色小鲸鱼游到你身边陪你啦 🐋", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "需要悬浮窗权限，哥哥才进得来哦...", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    private fun startPet() {
        val svc = Intent(this, OverlayService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(svc)
        } else {
            startService(svc)
        }
    }

    companion object {
        private const val REQ_OVERLAY = 100
    }
}