package org.omojadata.commons.activities

import androidx.activity.ComponentActivity
import org.omojadata.commons.helpers.REQUEST_APP_UNLOCK

abstract class BaseComposeActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        maybeLaunchAppUnlockActivity(REQUEST_APP_UNLOCK)
    }
}
