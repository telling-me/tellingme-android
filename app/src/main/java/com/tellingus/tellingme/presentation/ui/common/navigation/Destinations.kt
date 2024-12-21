package com.tellingus.tellingme.presentation.ui.common.navigation

object AuthDestinations {
    const val ROUTE = "authRoute"

    const val SPLASH = "splash"

    object Login {
        const val ROUTE = "loginRoute"

        const val LOGIN = "login"

    }

    object Signup {
        const val ROUTE = "signupRoute"

        const val SIGNUP_NICKNAME = "signupNickname"
        const val SIGNUP_BIRTH_GENDER = "signupBirthGender"
        const val SIGNUP_JOB = "signupJob"
        const val SIGNUP_WORRY = "signupWorry"
    }

}

object HomeDestinations {
    const val ROUTE = "homeRoute"

    const val HOME = "home"
    const val RECORD = "record"
    const val TELLER_CARD = "tellerCard"
    const val MY_TELLER_BADGE = "mytellerBadge"
    const val TELLER_CARD_TUNING = "tellercardTuning"
    const val USER_FEEDBACK = "userFeedback"
    const val USER_FEEDBACK_GOOD = "userFeedbackGood"
    const val USER_FEEDBACK_BAD = "userFeedbackBad"
    const val HOME_DETAIL = "homeDetail"
}

object MySpaceDestinations {
    const val ROUTE = "mySpaceRoute"

    const val MY_SPACE = "mySpace"
    const val RECORD = "record"
    const val CARD_DETAIL = "cardDetail"
}

object OtherSpaceDestinations {
    const val ROUTE = "otherSpaceRoute"

    const val OTHER_SPACE = "otherSpace"
}

object MyPageDestinations {
    const val ROUTE = "myPageRoute"

    const val MY_PAGE = "myPageSpace"
    const val ALARM = "alarm"
    const val MY_LOG = "myLog"
    const val SETTING = "setting"
    const val MY_INFO_EDIT = "myInfoEdit"
    const val WITH_DRAW = "withDraw"
    const val ABOUT_TELLING_ME = "aboutTellingMe"

}