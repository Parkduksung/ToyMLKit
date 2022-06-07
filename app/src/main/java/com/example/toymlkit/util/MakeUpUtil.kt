package com.example.toymlkit.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

object MakeUpUtil {

    /**
     * 얼굴형 인덱스 @param type  0~4 순으로 heart(계란형), oblong(긴형), oval(역삼각형), round(둥근형), square(각진형)
     */
    fun faceTypeResult(type: Int): FaceType? {
        return when (type) {

            //계란형
            0 -> {
                FaceType(
                    name = "계란형",
                    eyebrows = "",
                    blusher = "계란형 얼굴은 거의 모든 눈썹, 블러셔, 쉐딩이 무난하게 잘 어울린다. 따라서 그 때 상황에 맞게 과하지 않게 메이크업을 해주고 이목구비에 맞는 화장을 하는 것이 좋다.",
                    shading = "계란형 얼굴은 거의 모든 눈썹, 블러셔, 쉐딩이 무난하게 잘 어울린다. 따라서 그 때 상황에 맞게 과하지 않게 메이크업을 해주고 이목구비에 맞는 화장을 하는 것이 좋다.",
                    url = ""
                )
            }
            //긴형
            1 -> {
                FaceType(
                    name = "긴형",
                    eyebrows = "긴 얼굴형은 좁아 보이는 얼굴을 가로로 확장시켜 비율을 맞춰주고 이마 끝부터 턱 끝까지 흐르듯 내려오는 시선을 끊어주는 것이 중요하다. 이 때 효과적인 게 일자 눈썹이다. 반듯한 일자 눈썹이 아니어도 큰 휘어짐 없이 일자로 뻗는 눈썹도 좋다. 반대로 눈썹산을 살린 아치형 눈썹은 눈썹산 높이 만큼 얼굴이 더 길어 보인다.",
                    blusher = "밝은 색상을 사용하여 가로로 칠해주어 옆으로 넓어 보이게 하여 비율을 맞춰준다.",
                    shading = "이마와 턱 끝에 음영을 주어 얼굴의 전체적인 길이를 줄이고 동그란 인상을 주는 것이 좋다. 얼굴 전체적으로 쉐딩을 하는 경우 오히려 긴 얼굴형이 부각될 수 있어 피해준다.",
                    url = "https://www.youtube.com/watch?v=EV2O0rh6tZU"
                )
            }
            //역삼각형
            2 -> {
                FaceType(
                    name = "역삼각형",
                    eyebrows = "세미 아치형 눈썹이 잘 어울린다. 광대로 시선이 가지 않게 분산시켜 줄 수 있는 눈썹이다. 눈썹 앞머리를 연하게 일자로 그리고 눈썹 시작점과 높이를 비슷하게 만들어 준 뒤 눈썹을 길게 그려주면 촌스럽거나 세보이지 않고 자연스럽게 아치형 눈썹을 그릴 수 있다.",
                    blusher = "튀어나온 광대를 감싸듯이 사선으로 쓸어 내리듯이 넣어준다. 얼굴이 안쪽으로 넣어지는 효과를 준다.",
                    shading = "블러셔를 바른 곳 위에 쉐딩을 같이 넣어주어 광대를 좀 더 보안해준다. 하이라이터를 사용해 미간 부분, 코 끝, 입술산 위 쪽에 넣어주면 얼굴이 좀 더 짧아보이고 시선이 안쪽으로 분산되어 보인다.",
                    url = "https://www.youtube.com/watch?v=n3EJnYV7sg8"
                )
            }
            //둥근형
            3 -> {
                FaceType(
                    name = "둥근형",
                    eyebrows = "둥근형 얼굴은 동안으로 보이기도 하지만 눈썹을 일자로 그리거나 아래로 쳐지게 그릴 경우 촌스러운 느낌이 날 수 있다. 눈썹산을 살린 각진 아치형 눈썹이 둥글고 넓은 얼굴을 커버해줄 수 있다.",
                    blusher = "옆 광대부터 사선으로 쓸어 내리듯이 브러쉬로 가볍게 터치해주는 방법이 가장 좋다. 반대로 볼 가운데 동그랗게 칠하는 블러셔는 갸름한 얼굴형에는 귀여운 느낌을 주지만 둥글고 넙대대한 얼굴형에는 오히려 시선을 가운데로 모아 얼굴을 더 넓어 보이게 한다.",
                    shading = "전체적으로 얼굴 테두리를 감싸듯이 하되 턱과 이마의 끝은 생략함으로써 얼굴이 세로로 길어 보이는 것을 강조하고 갸름해 보이게 한다. 하이라이터도 같이 사용하면 더 극적인 효과를 줄 수 있다.",
                    url = "https://www.youtube.com/watch?v=fB0vBr78CJo"
                )
            }
            //각진형
            4 -> {
                FaceType(
                    name = "각진형",
                    eyebrows = "전체적으로 둥근 느낌을 주어 날카로운 느낌을 줄여야 하기 때문에 너무 얇지 않은 둥근 아치형 눈썹이 인상을 부드럽게 하고 전체적인 이미지를 고급스럽게 보이게 하는 눈썹이다. 반대로 일자 눈썹은 눈썹과 턱 사이에 각이 진 모양으로 시선을 가두어 오히려 각진 얼굴형이 부각되어 보이게 한다.",
                    blusher = "보통 쓰는 블러셔보다 한 톤 어두운 브라운에 가까운 블러셔로 쉐딩처럼 사용한다. 광대 아래쪽부터 관자놀이까지 사선방향으로 쓸어올리 듯 칠해준다. 반대로 활짝 웃은 뒤 튀어나온 광대 부분에는 블러셔를 하지 않는다.",
                    shading = "이미 블러셔로 한번 칠해준 광대의 살짝 아래에 겹쳐지듯이 쉐딩을 해준다. 이 때 튀어나온 광대와 턱 쪽을 한번 더 칠해주면 더욱 효과가 좋다. 반대로 각이 진 곳을 어둡게 하는 쉐딩이 아닌 웃었을 때 광대 아래 그림자가 지는 곳에 하는 쉐딩은 볼 살이 더 없어 보이고 광대가 더 부각되어 보인다.",
                    url = "https://www.youtube.com/watch?v=_diOu8yRyfM"
                )
            }
            else -> {
                null
            }
        }
    }


    /**
     * 눈 @param type 인덱스 0 : double(쌍꺼풀), 1: single(외꺼풀 or 무쌍)
     */
    fun eyeTypeResult(type: Int): EyeType? {
        return when (type) {
            0 -> {
                EyeType(
                    "유",
                    content = "쌍커풀 라인이 너무 도드라지면 별로라서 섀도우로 중화하면 좋음. 이미 강한 눈을 강조하기 보단 부드럽게 살려 주기 위해 밝고 옅은 색을 사용해서 아이홀까지만 베이스 발라준다. 눈이 튀어나온 동공 부분을 가장 많이 칠하고 나머지 부분은 브러쉬에 남은 양으로 쓸어준다. 포인트 컬러가 되는 섀도우는 뒷부분만 살살 아이라인을 풀어주는 느낌으로 눈매를 따라 그려준다. 언더는 눈매에 맞게 삼각존까지 채워준다.",
                    url = "https://www.youtube.com/watch?v=38TC3_RljWo"
                )
            }

            1 -> {
                EyeType(
                    "무",
                    content = "베이스로 펄감, 컬러감이 있는 섀도우를 사용하여 눈 앞부터 뒤까지 골고루 아이홀까지 발라준다. 섀도우는 너무 강하지 않은 색으로 눈매를 자연스럽게 따라 그린 다음 눈 앞과 뒤쪽 음영을 자연스럽게 연결시켜주는 느낌으로 발라준다. 언더는 눈 꼬리 부분을 열어준다는 느낌으로 발라준다.",
                    url = "https://www.youtube.com/watch?v=HlnztYFfDKM"
                )
            }
            else -> {
                null
            }
        }
    }

    //긴눈 : 2.7 <= result_long_eye <= 3.0 : 짧은눈
    fun eyeLengthResult(length: Float): EyeLength {
        return if (length < 2.7) {
            EyeLength(
                length = "김",
                shadow = "",
                eyeLine = "눈동자 중앙부분을 도톰하게 그려주고 눈머리부터 눈꼬리까지 자연스럽게 그려준다.",
                mascara = "전체적으로 자연스럽게 바른다."
            )
        } else if (length < 3.0) {
            EyeLength(
                length = "짧음",
                shadow = "베이스는 음영을 줄 수 있는 정도만의 밝은 계열의 컬러를 사용해 아이홀까지 발라준다. 짧은 눈을 길어 보이게 하기 위해 눈꼬리 쪽은 생각보다 더 뒤쪽으로 칠해준다. 눈썹 직전까지 해주면 좋다.",
                eyeLine = "아이라인은 뒤로 길게 빼주지만 눈꼬리 부분이 닫히지 않게 그려준다.",
                mascara = "동공 부분보단 눈 꼬리 부분에 집중해서 발라준다."
            )
        } else {
            EyeLength(
                length = "황금비율",
                shadow = GOLD_BALANCE,
                eyeLine = GOLD_BALANCE,
                mascara = GOLD_BALANCE
            )
        }
    }

    //처진눈 : 5 <= degree_eye2 <= 8 : 올라간눈
    fun eyeDirectionResult(cheek: Float): EyeDirection {
        return if (cheek < 5) {
            EyeDirection(
                direction = "처진눈",
                shadow = "섀도우 전 눈 밑 삼각존 부분에 음영이 있으면 더 쳐져 보이니 컨실러로 지워준다. 섀도우는 눈매를 따라 자연스럽게 그려준다. 언더는 눈 끝 점막 바깥 부위를 따라서 살살 그려준다.",
                eyeLine = "아이라인은 눈동자가 끝나는 지점부터 그려준다. 뒤부터 다 그린 후에 눈매를 따라서 앞으로 연결시켜 주는 것이 더 쉽다. 라인은 눈매를 따라서 길게 그리지만 눈꼬리 부분에서 살짝 위쪽으로 그려준다.",
                mascara = "마스카라는 속눈썹 전체에 꼼꼼하게 발라준다."
            )

        } else if (cheek > 8) {
            EyeDirection(
                direction = "올라간눈",
                shadow = "음영을 주는 브라운 컬러나 밝고 옅은 생긍로 베이스를 발라줘야 자연스럽다. 눈이 튀어나온 동공부분을 가장 많이 칠하고 나머지 부분은 브러쉬에 남은 양으로 쓸어준다. 포인트가 되는 섀도우 색으로는 눈 뒷부분만 살살 눈매를 따라 그려주세요. 언더는 컨실러를 사용해 눈꼬리 부분 음영을 지워서 눈이 덜 올라가 보이고 시원해 보일 수 있도록 해준다.",
                eyeLine = "눈매를 따라서 다 그리지 않고 눈 앞과 눈꼬리만 그려주세요. 눈 앞은 살짝 두껍고 동그랗게 그려준다. 눈 꼬리는 눈이 올라가 보이지 않게 내린 후에 눈 끝이 언더와 만나는 부분을 삼각형으로 작게 채워준다.",
                mascara = "전체적으로 자연스럽게 해준다."
            )
        } else {
            EyeDirection(
                direction = "황금비율",
                shadow = GOLD_BALANCE,
                eyeLine = GOLD_BALANCE,
                mascara = GOLD_BALANCE
            )
        }
    }


    //짧은 입술 : 1.4 <= mouth_length <= 1.6 : 긴 입술
    fun mouseLengthResult(length: Float): MouthLength {
        return if (length < 1.4) {
            MouthLength(
                length = "짧음",
                "되도록 옅은 색, 밝은 색, 펄감이 든 립스틱을 선택하여 입술 끝의 길이를 늘려서 그려준다. 채도가 높고 쨍한 컬러를 입 전체에 바르면 작은 입술이 강조되어 옹졸해 보이고 소심해 보이는 이미지를 줄 수 있다. 진한 컬러감이 입의 범위를 더 명확하게 해서 작은 입이 더 도드라지기 때문이다."
            )
        } else if (length > 1.6) {
            MouthLength(length = "김", "가지고 있는 입술보다 구각을 줄여서 발라준다.")
        } else {
            MouthLength(length = "황금비율", GOLD_BALANCE)
        }
    }

    //두꺼운 입술 : 2.9 <= mouth_thickness <= 3.1 :얇은 입술
    fun mouseThicknessResult(thickness: Float): MouthThickness {
        return if (thickness < 2.9) {
            MouthThickness(
                thickness = "두꺼움",
                "입술을 꽉 채워서 바르는 경우 두꺼운 입술이 더 부각되어 커 보이고 입술만 둥둥 떠보일 수 있다. 입술 구각 부분에 파운데이션이나 컨실러를 이용하여 눌러준 다음 안쪽에서부터 누드톤을 깔아주고 안쪽에 색을 먼저 입혀 바깥쪽으로 연하게 브러쉬를 이용하여 그라데이션으로 발라준다.",
                url = "https://www.youtube.com/watch?v=Nlrn0bCpScs"
            )
        } else if (thickness > 3.1) {
            MouthThickness(
                thickness = "얇음",
                "연한 누드톤 컬러의 립펜슬로 윗입술은 입술산과 입꼬리 끝을 이어준다고 생각하고 이어준다. 맨살 부분은 한번 더 터치해서 경계가 어색하지 않게 한다. 아랫입술은 가장자리 튀어나온 부분까지 그려 주시면 자연스럽다. 그라데이션을 연출할 때 립브러쉬를 이용하여 그라데이션으로 발라 주시면 쉽게 블랜딩할 수 있다.",
                url = "https://www.youtube.com/watch?v=R74GMyX-tqg"
            )
        } else {
            MouthThickness(thickness = "황금비율", "", url = "")
        }
    }


    //짧은 코 : 0.9 <= nose_length_y <= 1.1 : 긴코
    fun noseLengthResult(length: Float): NoseLength {
        return if (length < 0.9) {
            NoseLength(
                length = "짧음",
                "눈썹 앞머리부터 코 끝까지 연결하듯 코 쉐딩을 하면 코가 더욱 길어 보일 수 있다. 눈 앞쪽부터 쉐딩해 준다. 눈 앞쪽부터 코 아래로 짧게 쉐딩하고 손에 힘을 빼고 코 옆을 넓게 쉐딩해 준다. 더하여 코 끝 아래에 쉐딩으로 음영을 주어 코가 짧아 보일 수 있도록 해준다. 하이라이터는 코 끝부분을 중심적으로 발라준다.",
                url = "https://www.youtube.com/watch?v=lyuK6egz-tk"
            )
        } else if (length > 1.1) {
            NoseLength(
                length = "김",
                "짧은 코를 길어 보이도록 하기 위해 눈썹머리부터 코 끝까지 음영을 깊게 넣어주고 하이라이터를 미간부터 코 끝까지 발라준다.",
                url = "https://www.youtube.com/watch?v=r5As_vuyg8w"
            )
        } else {
            NoseLength(length = "황금비율", GOLD_BALANCE, url = "")
        }
    }

    //좁은 콧볼 : 0.9 <= nose_width_x <= 1.1 : 넓은 콧볼
    fun noseAreaResult(area: Float): NoseArea {
        return if (area < 0.9) {
            NoseArea(area = "좁음", "콧볼 쉐딩은 할 필요가 없다. 다른 부위에 맞게 화장하면 된다.", url = "")
        } else if (area > 1.1) {
            NoseArea(
                area = "넓음",
                "넓은 콧볼을 슬림해 보이게 하기 위해 코 옆부분에 음영을 강하게 준다. 콧구멍부터 콧대 위로 일직선으로 올라가며 쉐딩해 준다.",
                url = "https://www.youtube.com/watch?v=eLwye4T33-s"
            )
        } else {
            NoseArea(area = "황금비율", GOLD_BALANCE, url = "")
        }
    }
}

@Parcelize
data class FaceType(
    val name: String,
    val eyebrows: String,
    val blusher: String,
    val shading: String,
    val url: String
) : Parcelable

@Parcelize
data class EyeType(
    val isSingle: String,
    val content: String,
    val url: String
) : Parcelable

@Parcelize
data class EyeLength(
    val length: String,
    val shadow: String,
    val eyeLine: String,
    val mascara: String
) : Parcelable

@Parcelize
data class EyeDirection(
    val direction: String,
    val shadow: String,
    val eyeLine: String,
    val mascara: String
) : Parcelable

@Parcelize
data class MouthLength(
    val length: String,
    val content: String
) : Parcelable

@Parcelize
data class MouthThickness(
    val thickness: String,
    val content: String,
    val url: String
) : Parcelable

@Parcelize
data class NoseLength(
    val length: String,
    val content: String,
    val url: String
) : Parcelable

@Parcelize
data class NoseArea(
    val area: String,
    val content: String,
    val url: String
) : Parcelable

const val GOLD_BALANCE = "연출하고 싶은 분위기에 따라 화장하시면 됩니다."