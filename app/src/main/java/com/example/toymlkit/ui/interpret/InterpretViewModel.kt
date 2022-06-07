package com.example.toymlkit.ui.interpret

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.toymlkit.base.BaseViewModel
import com.example.toymlkit.ext.ioScope
import com.example.toymlkit.ext.uiScope
import com.example.toymlkit.ml.ConvertedModelEye
import com.example.toymlkit.ml.ConvertedModelFace
import com.example.toymlkit.util.MediaUtil.Companion.scanMediaToBitmap
import com.example.toymlkit.util.BitmapUtils
import com.example.toymlkit.util.MakeUpUtil
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.atan


@HiltViewModel
class InterpretViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    private val context: Context get() = getApplication()


    fun load(uri: Uri) {
        context.scanMediaToBitmap(uri) {
            ioScope {
                val transMLKit = async { translateMLKit(it) }
                val transFaceModule = async { translateFaceModel(it) }
                val transEyeModule = async { translateEyeModel(it) }
                checkProcess(transMLKit.await(), transFaceModule.await(), transEyeModule.await())
            }
        }
    }

    private fun checkProcess(transMLKit: Task<List<Face>>?, transFace: Int?, transEye: Int?) {
        if (transEye != null && transFace != null && transMLKit != null) {
            transMLKit
                .addOnCompleteListener {
                    Log.d("결과", "detector addOnCompleteListener")
                }
                .addOnSuccessListener { faces ->
                    /**
                     * 눈 특징 분류
                     */

                    //얼굴 윤곽선 중 왼쪽 (왼쪽 뺨의 x 좌표.)

                    Log.d(
                        "결과",
                        faces[0].getContour(FaceContour.LEFT_CHEEK)?.points?.size.toString()
                    )

                    //얼굴 윤곽선에 대한 값을 x 에 대한 오름차순으로 sort
                    val toSortXPointOutlineFace =
                        faces[0].getContour(FaceContour.FACE)!!.points.sortedBy { it.x }

                    //얼굴 윤곽선 중 맨 왼쪽.
                    val outlineMostLeftXPoint =
                        faces[0].getContour(FaceContour.FACE)!!.points.minByOrNull { it.x }!!.x

                    //얼굴 윤곽선 중 가운데.  (총 갯수의 절반 - 1) 가 toSortXPointOutlineFace 의 정 가운데.
                    val outlineMiddleXPoint =
                        toSortXPointOutlineFace[(toSortXPointOutlineFace.size / 2)].x

                    val outlineLeftToMiddleLength = outlineMiddleXPoint - outlineMostLeftXPoint

                    //왼쪽눈에 대한 값을 x 에 대한 오름차순으로 sort
                    val toSortXPointLeftEye =
                        faces[0].getContour(FaceContour.LEFT_EYE)!!.points.sortedBy { it.x }

                    //왼쪽눈의 맨 왼쪽 x 좌표
                    val leftEyeMostLeftXPoint =
                        faces[0].getContour(FaceContour.LEFT_EYE)!!.points.minByOrNull { it.x }!!.x

                    //왼쪽눈의 맨 오른쪽 x 좌표
                    val leftEyeMostRightXPoint =
                        faces[0].getContour(FaceContour.LEFT_EYE)!!.points.maxByOrNull { it.x }!!.x

                    //왼쪽눈의 길이 = 왼쪽눈의 맨 오른쪽 x 좌표 - 왼쪽눈의 맨 왼쪽 x 좌표
                    val leftEyeLength = leftEyeMostRightXPoint - leftEyeMostLeftXPoint

                    //눈의 길이 결과 = (왼쪽 얼굴부터 얼굴 가운데까지 길이 / 눈 길이)
                    val eyeLengthResult = (outlineLeftToMiddleLength / leftEyeLength)

                    Log.d("결과-눈 길이 결과", "$eyeLengthResult")

                    //눈의 방향

                    //왼쪽눈의 맨 왼쪽 y 좌표
                    val leftEyeMostLeftYPoint = toSortXPointLeftEye[0].y

                    //왼쪽눈의 맨 오른쪽 y 좌표
                    val leftEyeMostRightYPoint = toSortXPointLeftEye.last().y

                    //왼쪽눈의 맨 오른쪽 y 의 좌표에서 맨 왼쪽의 y 좌표의 차이.
                    val leftEyeHeight = leftEyeMostRightYPoint - leftEyeMostLeftYPoint

                    //# 처진눈 : 5 <= degree_eye2 <= 8 : 올라간눈
                    val eyeDegreeResult =
                        ((atan((leftEyeHeight / leftEyeLength).toDouble()))) * (180 / 3.14)

                    Log.d("결과-처진눈인지 올라가는 눈인지 결과", "${abs(eyeDegreeResult)}")

                    /**
                     * 입술 특징 분류
                     */

                    val upperLipTopToSortXPoint =
                        faces[0].getContour(FaceContour.UPPER_LIP_TOP)!!.points.sortedBy { it.x }

                    //입술 길이 (윗입술 기준)
                    val mouthLength =
                        upperLipTopToSortXPoint.last().x - upperLipTopToSortXPoint[0].x


                    //입술 높이 윗입술의 맨 위 와 아랫입술의 맨 아래의 길이.
                    val mouthHeight =
                        faces[0].getContour(FaceContour.LOWER_LIP_BOTTOM)!!.points.maxByOrNull { it.y }!!.y -
                                faces[0].getContour(FaceContour.UPPER_LIP_TOP)!!.points.minByOrNull { it.y }!!.y

                    // 짧은 입술인지 긴입술인지 확인값.
                    // 입술 길이를 왼쪽눈의 길이로 나눈 값.
                    val checkMouthLength = mouthLength / leftEyeLength

                    // 입술 두께
                    val mouthThickness = mouthLength / mouthHeight

                    Log.d("결과-짧은 입술인지 긴입술인지", "$checkMouthLength")
                    Log.d("결과-입술 두께", "$mouthThickness")

                    /**
                     * 코 특징 분류
                     */

                    //눈썹부터 코끝의 길이
                    val noseLengthA =
                        faces[0].getContour(FaceContour.NOSE_BOTTOM)!!.points.maxByOrNull { it.y }!!.y -
                                faces[0].getContour(FaceContour.NOSE_BRIDGE)!!.points.minByOrNull { it.y }!!.y

                    //코끝부터 턱까지의 길이
                    val noseLengthB =
                        faces[0].getContour(FaceContour.FACE)!!.points.maxByOrNull { it.y }!!.y -
                                faces[0].getContour(FaceContour.NOSE_BOTTOM)!!.points.maxByOrNull { it.y }!!.y

                    //짧은코인지 긴코인지의 결과
                    val noseLengthResult = noseLengthA / noseLengthB

                    Log.d("결과-짧은코인지 긴코인지의 결과", "$noseLengthResult")

                    //콧볼 길이
                    val noseCheekLength =
                        faces[0].getContour(FaceContour.NOSE_BOTTOM)!!.points.maxByOrNull { it.x }!!.x -
                                faces[0].getContour(FaceContour.NOSE_BOTTOM)!!.points.minByOrNull { it.x }!!.x


                    //미간의 길이
                    val glabellaLength =
                        faces[0].getContour(FaceContour.RIGHT_EYE)!!.points.minByOrNull { it.x }!!.x -
                                faces[0].getContour(FaceContour.LEFT_EYE)!!.points.minByOrNull { it.x }!!.x


                    // 좁은 콧볼인지 넓은 콧볼인지의 결과.
                    val noseCheekResult = noseCheekLength / glabellaLength

                    Log.d("결과-좁은 콧볼인지 넓은 콧볼인지의 결과", "$noseCheekResult")


                    val faceType = MakeUpUtil.faceTypeResult(transFace)
                    val eyeType = MakeUpUtil.eyeTypeResult(transEye)

                    val eyeLength = MakeUpUtil.eyeLengthResult(eyeLengthResult)
                    val eyeDirection = MakeUpUtil.eyeDirectionResult(eyeDegreeResult.toFloat())

                    val noseLength = MakeUpUtil.noseLengthResult(noseLengthResult)
                    val noseArea = MakeUpUtil.noseAreaResult(noseCheekResult)

                    val mouseLength = MakeUpUtil.mouseLengthResult(checkMouthLength)
                    val mouseThickness = MakeUpUtil.mouseThicknessResult(mouthThickness)


                    uiScope {
                        viewStateChanged(
                            InterpretViewState.GetResult(
                                faceType!!,
                                eyeType!!,
                                eyeLength,
                                eyeDirection,
                                noseLength,
                                noseArea,
                                mouseLength,
                                mouseThickness
                            )
                        )
                    }


                }
                .addOnFailureListener {
                    Log.d("결과", "detector addOnFailureListener")
                }
        } else {

        }
    }

    private suspend fun translateMLKit(bitmap: Bitmap): Task<List<Face>>? {
        return try {
            val toConvertBitmap = Bitmap.createScaledBitmap(
                bitmap,
                480,
                640, true
            )

            // High-accuracy landmark detection and face classification
            val highAccuracyOpts = FaceDetectorOptions.Builder()
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()

            val image = InputImage.fromBitmap(toConvertBitmap, 0)

            val detector = FaceDetection.getClient(highAccuracyOpts)

            detector.process(image)
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun translateFaceModel(bitmap: Bitmap): Int? {
        return try {
            val model = ConvertedModelFace.newInstance(context)

            val toConvertBitmap = Bitmap.createScaledBitmap(
                bitmap.copy(Bitmap.Config.ARGB_8888, false),
                200,
                200, true
            )

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)

            inputFeature0.loadBuffer(BitmapUtils.bitmapToByteBuffer(toConvertBitmap, 200, 200))

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            // Releases model resources if no longer used.
            model.close()

            outputFeature0.floatArray.indexOfFirst { it == outputFeature0.floatArray.maxOrNull() }
                ?: null
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun translateEyeModel(bitmap: Bitmap): Int? {
        return try {
            val model = ConvertedModelEye.newInstance(context)

            val toConvertBitmap = Bitmap.createScaledBitmap(
                bitmap.copy(Bitmap.Config.ARGB_8888, false),
                100,
                100, true
            )
            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 100, 100, 3), DataType.FLOAT32)

            inputFeature0.loadBuffer(BitmapUtils.bitmapToByteBuffer(toConvertBitmap, 100, 100))

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            // Releases model resources if no longer used.
            model.close()

            outputFeature0.floatArray[0].toInt() ?: null
        } catch (e: Exception) {
            null
        }
    }
}