package com.example.toymlkit.ui.home


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.toymlkit.R
import com.example.toymlkit.base.BaseActivity
import com.example.toymlkit.databinding.ActivityHomeBinding
import com.example.toymlkit.ui.interpret.InterpretActivity
import com.example.toymlkit.ui.interpret.ProcessFragment.Companion.KEY_URI

import com.example.toymlkit.util.FlexibleTakePicture
import com.example.toymlkit.util.MediaUtil.Companion.getMediaUri
import com.example.toymlkit.util.checkPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val choosePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val intent = Intent(this@HomeActivity, InterpretActivity::class.java)
        intent.putExtra(KEY_URI, it.toString())
        startActivity(intent)
    }

    private var photoUri: Uri? = null
    private val flexibleTakePicture = FlexibleTakePicture()

    private val takePhoto = registerForActivityResult(flexibleTakePicture) { isSuccess ->
        if (isSuccess) {
            photoUri?.let { uri ->
                val intent = Intent(this@HomeActivity, InterpretActivity::class.java)
                intent.putExtra(KEY_URI, uri.toString())
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.containerTakePhoto.setOnClickListener {
            checkPermission { isGrant ->
                if (isGrant) {
                    val intent = flexibleTakePicture.newIntent()
                    photoUri = this@HomeActivity.getMediaUri(intent)
                    takePhoto.launch(photoUri)
                }
            }
        }

        binding.containerGetPhoto.setOnClickListener {
            checkPermission { isGrant ->
                if (isGrant) {
                    choosePhoto.launch("image/Pictures/*")
                }
            }
        }
    }

}