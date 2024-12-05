package org.omojadata.commons.samples

import com.github.ajalt.reprint.core.Reprint
import org.omojadata.commons.FossifyApp

class App : FossifyApp() {
    override fun onCreate() {
        super.onCreate()
        Reprint.initialize(this)
    }
}
