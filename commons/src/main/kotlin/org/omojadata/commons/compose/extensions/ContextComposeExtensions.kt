package org.omojadata.commons.compose.extensions

import android.app.Activity
import android.content.Context
import org.omojadata.commons.R
import org.omojadata.commons.extensions.baseConfig
import org.omojadata.commons.extensions.redirectToRateUs
import org.omojadata.commons.extensions.toast
import org.omojadata.commons.helpers.BaseConfig

val Context.config: BaseConfig get() = BaseConfig.newInstance(applicationContext)

fun Activity.rateStarsRedirectAndThankYou(stars: Int) {
    if (stars == 5) {
        redirectToRateUs()
    }
    toast(R.string.thank_you)
    baseConfig.wasAppRated = true
}
