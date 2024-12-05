package org.omojadata.commons

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import org.omojadata.commons.extensions.appLockManager
import org.omojadata.commons.extensions.checkUseEnglish

open class OmojadataApp : Application() {

    open val isAppLockFeatureAvailable = false

    override fun onCreate() {
        super.onCreate()
        checkUseEnglish()
        setupAppLockManager()
    }

    private fun setupAppLockManager() {
        if (isAppLockFeatureAvailable) {
            ProcessLifecycleOwner.get().lifecycle.addObserver(appLockManager)
        }
    }
}
