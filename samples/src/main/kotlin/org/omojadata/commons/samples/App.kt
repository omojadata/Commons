package org.omojadata.commons.samples

import com.github.ajalt.reprint.core.Reprint
import org.omojadata.commons.OmojadataApp

class App : OmojadataApp() {
    override fun onCreate() {
        super.onCreate()
        Reprint.initialize(this)
    }
}
