package com.example.toymlkit.ui.home

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.toymlkit.R
import com.example.toymlkit.base.BaseActivity
import com.example.toymlkit.databinding.ActivityHomeBinding
import com.example.toymlkit.util.FlexibleTakePicture
import com.example.toymlkit.util.MediaUtil.Companion.getMediaUri
import com.example.toymlkit.util.MediaUtil.Companion.scanMediaToBitmap
import com.example.toymlkit.util.checkPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    private val choosePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        homeViewModel.choose(it)
    }

    private var photoUri: Uri? = null
    private val flexibleTakePicture = FlexibleTakePicture()

    private val takePhoto = registerForActivityResult(flexibleTakePicture) { isSuccess ->
        if (isSuccess) {
            photoUri?.let { uri ->
                scanMediaToBitmap(uri) {
                    runOnUiThread {
                        homeViewModel.takePhoto(it)
                    }
                }
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