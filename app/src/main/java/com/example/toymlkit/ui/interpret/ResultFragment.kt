package com.example.toymlkit.ui.interpret

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.example.toymlkit.R
import com.example.toymlkit.base.BaseFragment
import com.example.toymlkit.databinding.FragmentResultBinding
import com.example.toymlkit.util.*
import com.example.toymlkit.util.MediaUtil.Companion.saveToGallery

class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val getFaceType = arguments?.getParcelable<FaceType>("FaceType")
        val getEyeType = arguments?.getParcelable<EyeType>("EyeType")
        val getEyeLength = arguments?.getParcelable<EyeLength>("EyeLength")
        val getEyeDirection = arguments?.getParcelable<EyeDirection>("EyeDirection")
        val getNoseLength = arguments?.getParcelable<NoseLength>("NoseLength")
        val getNoseArea = arguments?.getParcelable<NoseArea>("NoseArea")
        val getMouthLength = arguments?.getParcelable<MouthLength>("MouthLength")
        val getMouthThickness = arguments?.getParcelable<MouthThickness>("MouthThickness")


        with(binding) {

            faceType.text = "얼굴형 : ${getFaceType?.name}"
            eyebrew.text = getFaceType?.eyebrows
            blusher.text = getFaceType?.blusher
            shading.text = getFaceType?.shading


            if (faceType.text == "계란형") {
                containerFaceUrl.isVisible = false
            } else {
                faceUrl.text = "얼굴형 ${getFaceType?.name} 에 대한 유튜브 영상 보러가기"
                faceUrl.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                containerFaceUrl.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getFaceType?.url)))
                }
            }


            contentEyeType.text = "쌍커플 유무 : ${getEyeType?.isSingle}"
            eyetype.text = getEyeType?.content


            eyeUrl1.text = "쌍커플 유무 ${getEyeType?.isSingle} 에 대한 유튜브 영상 보러가기"
            eyeUrl1.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            containerEyeUrl1.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getEyeType?.url)))
            }

            eyelength.text = "눈길이 : ${getEyeLength?.length}"
            shadow.text = getEyeLength?.shadow
            eyeline.text = getEyeLength?.eyeLine
            mascara.text = getEyeLength?.mascara

            contentEyeDirection.text = "눈방향 : ${getEyeDirection?.direction}"
            shadow1.text = getEyeDirection?.shadow
            eyeline1.text = getEyeDirection?.eyeLine
            mascara1.text = getEyeDirection?.mascara

            if (getEyeLength?.length == "김") {
                shadow.isVisible = false
                contentEye2.isVisible = false
            }

            lengthUrl.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            angleUrl.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            lengthUrl.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=20GFfWsZkag")
                    )
                )
            }

            angleUrl.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=adM8jp5Hxdw")
                    )
                )
            }



            contentNoseType.text = "콧볼 넓이 : ${getNoseArea?.area}"
            nosearea.text = getNoseArea?.content

            contentNose1Type.text = "코 길이 : ${getNoseLength?.length}"
            noselength.text = getNoseLength?.content


            if (getNoseArea?.url != "" && getNoseArea?.area == "좁음") {
                noseAreaUrl.text = "콧볼 넓이가 넓은 유형에 대한 유튜브 영상 보러가기 "
                noseAreaUrl.setOnClickListener {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getNoseArea.url)
                        )
                    )
                }
                noseAreaUrl.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            } else {
                noseAreaUrl.isVisible = false
            }

            if (getNoseLength?.url != "") {
                noseLengthUrl.text = "코 길이가 ${getNoseLength?.length} 에 대한 유튜브 영상 보러가기"
                noseLengthUrl.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                noseLengthUrl.setOnClickListener {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getNoseLength?.url)
                        )
                    )
                }
            } else {
                noseLengthUrl.isVisible = false
            }


            contentMouseType.text = "입술 두께 : ${getMouthThickness?.thickness}"
            mousethickness.text = getMouthThickness?.content

            contentMouse1Type.text = "입술 길이 : ${getMouthLength?.length}"
            mouselength.text = getMouthLength?.content


            if (getMouthThickness?.url != "") {
                mouthThicknessUrl.text = "입술 두께 : ${getMouthThickness?.thickness} 에 대한 유튜브 영상 보러가기"
                mouthThicknessUrl.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                mouthThicknessUrl.setOnClickListener {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getMouthThickness?.url)
                        )
                    )
                }
            } else {
                mouthThicknessUrl.isVisible = false
            }



            routeHome.setOnClickListener {
                requireActivity().onBackPressed()
            }

            save.setOnClickListener {

                requireActivity().checkPermission { isGranted ->
                    if (isGranted) {

                        val totalBitmap = Bitmap.createBitmap(
                            containerInterpreted.getChildAt(0).width,
                            containerInterpreted.getChildAt(0).height - containerSaveOrRoute.height,
                            Bitmap.Config.ARGB_8888
                        )

                        val canvas = Canvas(totalBitmap)

                        canvas.drawColor(Color.WHITE)
                        containerInterpreted.draw(canvas)

                        val url = totalBitmap?.saveToGallery(requireContext())

                    }
                }
            }
        }
    }


    companion object {

        fun newInstance(
            faceType: FaceType,
            eyeType: EyeType,
            eyeLength: EyeLength,
            eyeDirection: EyeDirection,
            noseLength: NoseLength,
            noseArea: NoseArea,
            mouthLength: MouthLength,
            mouthThickness: MouthThickness
        ): ResultFragment = ResultFragment().apply {
            arguments = bundleOf(
                Pair("FaceType", faceType),
                Pair("EyeType", eyeType),
                Pair("EyeLength", eyeLength),
                Pair("EyeDirection", eyeDirection),
                Pair("NoseLength", noseLength),
                Pair("NoseArea", noseArea),
                Pair("MouthLength", mouthLength),
                Pair("MouthThickness", mouthThickness),
            )
        }
    }
}