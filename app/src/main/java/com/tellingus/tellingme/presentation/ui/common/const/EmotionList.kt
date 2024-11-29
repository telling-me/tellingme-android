package com.tellingus.tellingme.presentation.ui.common.const

import android.util.Log
import com.tellingus.tellingme.R

data class Emotion(
    val emotionRes: Int,
    val emotionDesc: String,
    val index: Int
)

data class EmotionBadge(
    val emotionRes: Int,
    val badgeName: String,
    val badgeCode: String,
    val badgeMiddleName: String,
    val badgeCondition: String
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

val MediumEmotionBadgeList = listOf<EmotionBadge>(
    EmotionBadge(
        R.drawable.teller_emotion_badge_connexion_medium,
        "단골텔러",
        "BG_AGAIN_001",
        "또 오셨네요!",
        "연속 작성 7일 달성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_christmas_medium,
        "화이트 크리스마스",
        "BG_CHRISTMAS_2024",
        "흰 눈 사이에서,",
        "12월 23일 오전 6:00 ~ 12월 28일 오전 5:59 접속 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_traveler_medium,
        "탐험가 텔러",
        "BG_FIRST",
        "낯선 길에 첫 발자국,",
        "첫 답변 작성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_toomuch_medium,
        "투처미 토커",
        "BG_MUCH_001",
        "내 이야길 들어봐,",
        "280자 이상 답변 1회 작성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_mystery_medium,
        "미스터리 방문객",
        "BG_NEW",
        "아직은 낯설어요,",
        "회원가입 시 기본 제공"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_owl_medium,
        "올빼미 텔러",
        "BG_NIGHT_001",
        "다들 꿈꿀 때 글을 썼지,",
        "오전 12시 ~ 6시 사이 답변 3개 작성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_savings_medium,
        "나는야 저축왕",
        "BG_SAVE_001",
        "치즈를 모아모아,",
        "치즈 총 50개 달성 시"
    ),
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

val LargeEmotionBadgeList = listOf<EmotionBadge>(
    EmotionBadge(
        R.drawable.teller_emotion_badge_connexion_large,
        "단골텔러",
        "BG_AGAIN_001",
        "또 오셨네요!",
        "연속 작성 7일 달성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_christmas_large,
        "화이트 크리스마스",
        "BG_CHRISTMAS_2024",
        "흰 눈 사이에서",
        "12월 23일 오전 6:00 ~ 12월 28일 오전 5:59 접속 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_traveler_large,
        "탐험가 텔러",
        "BG_FIRST",
        "낯선 길에 첫 발자국",
        "첫 답변 작성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_toomuch_large,
        "투처미 토커",
        "BG_MUCH_001",
        "내 이야길 들어봐",
        "280자 이상 답변 1회 작성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_mystery_large,
        "미스터리 방문객",
        "BG_NEW",
        "아직은 낯설어요",
        "회원가입 시 기본 제공"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_owl_large,
        "올빼미 텔러",
        "BG_NIGHT_001",
        "다들 꿈꿀 때 난 글썼지",
        "오전 12시 ~ 6시 사이 답변 3개 작성 시"
    ),
    EmotionBadge(
        R.drawable.teller_emotion_badge_savings_large,
        "나는야 저축왕",
        "BG_SAVE_001",
        "치즈를 모아모아",
        "치즈 총 50개 달성 시"
    ),
)

/** index는 1~12까지, 리소스를 반환 **/
fun getMediumEmotion(index: Int): Int {
    MediumEmotionList.forEach {
        if (it.index == index) {
            return it.emotionRes
        }
    }
    return R.drawable.emotion_happy_medium
}

/** index는 1~12까지, 리소스를 반환 **/
fun getLargeEmotion(index: Int): Int {
    LargeEmotionList.forEach {
        if (it.index == index) {
            return it.emotionRes
        }
    }
    return R.drawable.emotion_happy_large
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

fun getMediumEmotionBadge(badgeCode: String): Int {
    MediumEmotionBadgeList.forEach {
        if (it.badgeCode == badgeCode) {
            return it.emotionRes
        }
    }
    return R.drawable.teller_emotion_badge_mystery_medium // 기본 이미지 리소스 ID
}

fun getLargeEmotionBadge(badgeCode: String): Int {
    LargeEmotionBadgeList.forEach {
        if (it.badgeCode == badgeCode) {
            return it.emotionRes
        }
    }
    return R.drawable.teller_emotion_badge_mystery_medium // 기본 이미지 리소스 ID
}
