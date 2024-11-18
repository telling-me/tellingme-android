package com.tellingus.tellingme.presentation.ui.common.const

import com.tellingus.tellingme.R

data class Emotion(
    val emotionRes: Int,
    val emotionDesc: String,
    val index: Int
)

val MediumEmotionList = listOf<Emotion>(
    Emotion(R.drawable.emotion_happy_medium, "행복해요", 1),
    Emotion(R.drawable.emotion_proud_medium, "뿌듯해요", 2),
    Emotion(R.drawable.emotion_meh_medium, "그저 그래요", 3),
    Emotion(R.drawable.emotion_tired_medium, "피곤해요", 4),
    Emotion(R.drawable.emotion_sad_medium, "슬퍼요", 5),
    Emotion(R.drawable.emotion_angry_medium, "화나요", 6),
    Emotion(R.drawable.emotion_excited_medium, "설레요", 7),
    Emotion(R.drawable.emotion_thrilled_medium, "신나요", 8),
    Emotion(R.drawable.emotion_relaxed_medium, "편안해요", 9),
    Emotion(R.drawable.emotion_lethargic_medium, "무기력해요", 10),
    Emotion(R.drawable.emotion_lonely_medium, "외로워요", 11),
    Emotion(R.drawable.emotion_complicated_medium, "복잡해요", 12),
)

val LargeEmotionList = listOf<Emotion>(
    Emotion(R.drawable.emotion_happy_large, "행복해요", 1),
    Emotion(R.drawable.emotion_proud_large, "뿌듯해요", 2),
    Emotion(R.drawable.emotion_meh_large, "그저 그래요", 3),
    Emotion(R.drawable.emotion_tired_large, "피곤해요", 4),
    Emotion(R.drawable.emotion_sad_large, "슬퍼요", 5),
    Emotion(R.drawable.emotion_angry_large, "화나요", 6),
    Emotion(R.drawable.emotion_excited_large, "설레요", 7),
    Emotion(R.drawable.emotion_thrilled_large, "신나요", 8),
    Emotion(R.drawable.emotion_relaxed_large, "편안해요", 9),
    Emotion(R.drawable.emotion_lethargic_large, "무기력해요", 10),
    Emotion(R.drawable.emotion_lonely_large, "외로워요", 11),
    Emotion(R.drawable.emotion_complicated_large, "복잡해요", 12),
)

/** index는 1~12까지, 리소스를 반환 **/
fun getMediumEmotion(index: Int): Int {
    MediumEmotionList.forEach {
        if (it.index == index) {
            return it.emotionRes
        }
    }
    return 0
}

/** index는 1~12까지, 리소스를 반환 **/
fun getLargeEmotion(index: Int): Int {
    LargeEmotionList.forEach {
        if (it.index == index) {
            return it.emotionRes
        }
    }
    return 0
}

/** 감정에 해당하는 텍스트 조회 ex. 행복해요, 설레요 **/
fun getEmotionText(index: Int): String {
    LargeEmotionList.forEach {
        if (it.index == index) {
            return it.emotionDesc
        }
    }
    return ""
}