package com.example.toymlkit.ui.interpret

import com.example.toymlkit.base.ViewState
import com.example.toymlkit.util.*

sealed class InterpretViewState : ViewState {

    data class GetResult(
        val faceType: FaceType,
        val eyeType: EyeType,
        val eyeLength: EyeLength,
        val eyeDirection: EyeDirection,
        val noseLength: NoseLength,
        val noseArea: NoseArea,
        val mouthLength: MouthLength,
        val mouthThickness: MouthThickness
    ) : InterpretViewState()
}